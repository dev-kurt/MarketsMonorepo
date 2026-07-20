#!/usr/bin/env zsh

# ================================================
# Maestro Test Runner — Markets (Android only)
# ================================================
# Usage:
#   ./scripts/run_maestro.sh                 # run every flow, print summary
#   ./scripts/run_maestro.sh <flow.yaml>     # run a single flow
#   ./scripts/run_maestro.sh --fail-fast     # stop at first failure

set -uo pipefail

BOLD='\033[1m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
RESET='\033[0m'

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
APP_ID="com.devkurt.markets"

FAIL_FAST=0
SINGLE_FLOW=""
for arg in "$@"; do
    case "$arg" in
        --fail-fast) FAIL_FAST=1 ;;
        -h|--help)
            echo "Usage: $0 [flow.yaml] [--fail-fast]"
            exit 0
            ;;
        *) SINGLE_FLOW="$arg" ;;
    esac
done

if ! command -v maestro &>/dev/null; then
    echo -e "${YELLOW}Maestro not found, installing...${RESET}"
    curl -Ls "https://get.maestro.mobile.dev" | bash
    export PATH="$HOME/.maestro/bin:$PATH"
    command -v maestro &>/dev/null || { echo -e "${RED}Maestro installation failed${RESET}"; exit 1; }
fi

MAESTRO_COUNT=$(which -a maestro 2>/dev/null | sort -u | wc -l | tr -d ' ')
if [ "$MAESTRO_COUNT" -gt 1 ]; then
    echo -e "${YELLOW}Multiple Maestro installations detected:${RESET}"
    which -a maestro 2>/dev/null | sort -u
    echo "  Keep only ~/.maestro/bin/maestro to avoid conflicts."
    exit 1
fi

FLOWS=()
if [ -n "$SINGLE_FLOW" ]; then
    [[ "$SINGLE_FLOW" = /* ]] || SINGLE_FLOW="$PROJECT_ROOT/$SINGLE_FLOW"
    [ -f "$SINGLE_FLOW" ] || { echo -e "${RED}Flow not found: $SINGLE_FLOW${RESET}"; exit 1; }
    FLOWS+=("$SINGLE_FLOW")
else
    while IFS= read -r line; do
        FLOWS+=("$line")
    done < <(find "$PROJECT_ROOT/markets-features" "$PROJECT_ROOT/shared-features" -path "*/.maestro/*.yaml" 2>/dev/null | sort)
fi

[ ${#FLOWS[@]} -gt 0 ] || { echo -e "${RED}No maestro flows found${RESET}"; exit 1; }

STUCK_PID=$(lsof -ti :7001 2>/dev/null || true)
if [ -n "$STUCK_PID" ]; then
    echo -e "${YELLOW}Clearing stuck Maestro daemon on port 7001 (PID: $STUCK_PID)...${RESET}"
    kill -9 $STUCK_PID 2>/dev/null || true
    sleep 3
fi

# COMPAT: wireless adb device ids may contain spaces; split on tab, not whitespace.
DEVICE_ID=$(adb devices 2>/dev/null | grep -w 'device$' | head -1 | awk -F'\t' '{print $1}' || true)
[ -n "$DEVICE_ID" ] || { echo -e "${RED}No connected Android device${RESET}"; exit 1; }

adb forward --remove-all 2>/dev/null || true

# PERF: Maestro 2.x uninstalls its driver after every run. Installing the bundled
# driver APKs manually and passing --reinstall-driver=false keeps it on the device.
# https://github.com/mobile-dev-inc/maestro/issues/1096
DRIVER_INSTALLED=$(adb -s "$DEVICE_ID" shell pm list packages 2>/dev/null | grep -c "dev.mobile.maestro" || true)
if [ "$DRIVER_INSTALLED" -lt 2 ]; then
    echo -e "${YELLOW}Maestro driver missing; installing bundled APKs...${RESET}"
    MAESTRO_JAR=$(ls ~/.maestro/lib/maestro-client*.jar 2>/dev/null | head -1)
    [ -n "$MAESTRO_JAR" ] && [ -f "$MAESTRO_JAR" ] || { echo -e "${RED}maestro-client.jar not found in ~/.maestro/lib${RESET}"; exit 1; }
    TMP_APK_DIR=$(mktemp -d)
    unzip -oq "$MAESTRO_JAR" maestro-app.apk maestro-server.apk -d "$TMP_APK_DIR"
    adb -s "$DEVICE_ID" install -r -t "$TMP_APK_DIR/maestro-app.apk" >/dev/null
    adb -s "$DEVICE_ID" install -r -t "$TMP_APK_DIR/maestro-server.apk" >/dev/null
    rm -rf "$TMP_APK_DIR"
    echo -e "${GREEN}Driver APKs installed${RESET}"
else
    echo -e "${GREEN}Maestro driver present on device${RESET}"
fi

# COMPAT: on Maestro 2.4.0 + Android 16 the driver does not always start on its own.
adb -s "$DEVICE_ID" shell am instrument -w dev.mobile.maestro.test/androidx.test.runner.AndroidJUnitRunner &>/dev/null &
sleep 3
adb -s "$DEVICE_ID" forward tcp:7001 tcp:7001 >/dev/null 2>&1 || true

echo ""
echo -e "${BOLD}═══════════════════════════════════════════════${RESET}"
echo -e "  Device:  ${CYAN}$DEVICE_ID${RESET}"
echo -e "  APP_ID:  ${CYAN}$APP_ID${RESET}"
echo -e "  Flows:   ${CYAN}${#FLOWS[@]}${RESET}"
echo -e "${BOLD}═══════════════════════════════════════════════${RESET}"

PASSED=()
FAILED=()
START_TS=$SECONDS

for ((i=1; i<=${#FLOWS[@]}; i++)); do
    FLOW="${FLOWS[$i]}"
    SHORT_FLOW="${FLOW#$PROJECT_ROOT/}"
    echo ""
    echo -e "  [${i}/${#FLOWS[@]}] ${CYAN}$SHORT_FLOW${RESET}"
    if maestro --device "$DEVICE_ID" test --reinstall-driver=false -e APP_ID="$APP_ID" "$FLOW"; then
        PASSED+=("$SHORT_FLOW")
    else
        FAILED+=("$SHORT_FLOW")
        if [ $FAIL_FAST -eq 1 ]; then
            echo -e "${RED}Fail-fast: stopping at first failure${RESET}"
            break
        fi
    fi
done

ELAPSED=$((SECONDS - START_TS))

echo ""
echo -e "${BOLD}═══════════════════════════════════════════════${RESET}"
echo -e "  Duration: ${CYAN}$((ELAPSED / 60))m $((ELAPSED % 60))s${RESET}"
echo -e "  ${GREEN}Passed: ${#PASSED[@]}${RESET}"
echo -e "  ${RED}Failed: ${#FAILED[@]}${RESET}"

if [ ${#FAILED[@]} -gt 0 ]; then
    echo ""
    for f in "${FAILED[@]}"; do
        echo -e "    ${RED}•${RESET} $f"
    done
    exit ${#FAILED[@]}
fi

echo -e "${GREEN}${BOLD}All flows passed${RESET}"
exit 0
