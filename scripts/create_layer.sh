#!/bin/bash

# Make arrays 0-based when running under zsh (bash compatibility)
if [ -n "$ZSH_VERSION" ]; then
    setopt KSH_ARRAYS
fi

# Drop the broken Java path Android Studio injects
unset JAVA_HOME

declare -a LAYERS
declare -a FEATURES
declare -a SOURCE_LAYERS
declare -a SOURCES

BOLD='\033[1m'
DIM='\033[2m'
GREEN='\033[0;32m'
CYAN='\033[0;36m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
RESET='\033[0m'

print_header() {
  echo ""
  echo "${BOLD}═══════════════════════════════════════════════════════════${RESET}"
  echo "${BOLD}  🏗️  Layer Generator ${DIM}(${#LAYERS[@]} layer(s) queued)${RESET}"
  echo "${BOLD}═══════════════════════════════════════════════════════════${RESET}"
}

while true; do
  print_header

  if [ ${#LAYERS[@]} -gt 0 ]; then
      echo ""
      echo "  ${GREEN}Queued layers:${RESET}"
      i=0
      while [ $i -lt ${#LAYERS[@]} ]; do
          num=$((i+1))
          echo "    ${GREEN}${num}.${RESET} ${LAYERS[$i]} → ${FEATURES[$i]}"
          i=$((i+1))
      done
  fi

  echo ""
  echo "  ${BOLD}Enter comma separated values (* required):${RESET}"
  echo ""
  echo "    ${CYAN}1)${RESET} Target layer*     ${DIM}→ e.g. domain:impl, data, ui:api${RESET}"
  echo "    ${CYAN}2)${RESET} Target feature*   ${DIM}→ e.g. :markets-features:coin-detail${RESET}"
  echo "    ${CYAN}3)${RESET} Source layer      ${DIM}→ empty = target layer${RESET}"
  echo "    ${CYAN}4)${RESET} Source feature    ${DIM}→ empty = :template-feature${RESET}"
  echo ""
  echo "  ${DIM}Example: ui:api, :markets-features:coin-detail, , :markets-features:coins-list${RESET}"
  echo ""

  if [ ${#LAYERS[@]} -gt 0 ]; then
      echo "  ${YELLOW}▸ Empty + Enter → start generating${RESET}"
  fi
  echo "  ${YELLOW}▸ q → cancel${RESET}"
  echo ""

  INPUT=""
  if [ -n "$ZSH_VERSION" ]; then
      vared -p "  ➜ " INPUT
  else
      read -e -p "  ➜ " INPUT
  fi

  INPUT=$(echo "$INPUT" | xargs 2>/dev/null || echo "$INPUT")

  if [ "$INPUT" = "q" ] || [ "$INPUT" = "Q" ]; then
      echo ""
      echo "  Cancelled. No layers were created."
      exit 0
  fi

  if [ -z "$INPUT" ]; then
      if [ ${#LAYERS[@]} -eq 0 ]; then
          echo ""
          echo "  ${RED}❌ Error: at least one layer is required.${RESET}"
          sleep 1
          continue
      fi
      break
  fi

  IFS=',' read -r TEMP_LAYER TEMP_FEATURE TEMP_SOURCE_LAYER TEMP_SOURCE <<< "$INPUT"

  TEMP_LAYER=$(echo $TEMP_LAYER | xargs 2>/dev/null || echo "$TEMP_LAYER")
  TEMP_FEATURE=$(echo $TEMP_FEATURE | xargs 2>/dev/null || echo "$TEMP_FEATURE")
  TEMP_SOURCE_LAYER=$(echo $TEMP_SOURCE_LAYER | xargs 2>/dev/null || echo "$TEMP_SOURCE_LAYER")
  TEMP_SOURCE=$(echo $TEMP_SOURCE | xargs 2>/dev/null || echo "$TEMP_SOURCE")

  # Strip trailing : and / (typo guard)
  TEMP_LAYER="${TEMP_LAYER%:}"
  TEMP_LAYER="${TEMP_LAYER%/}"
  TEMP_FEATURE="${TEMP_FEATURE%:}"
  TEMP_FEATURE="${TEMP_FEATURE%/}"
  TEMP_SOURCE_LAYER="${TEMP_SOURCE_LAYER%:}"
  TEMP_SOURCE_LAYER="${TEMP_SOURCE_LAYER%/}"
  TEMP_SOURCE="${TEMP_SOURCE%:}"
  TEMP_SOURCE="${TEMP_SOURCE%/}"

  if [ -z "$TEMP_LAYER" ] || [ -z "$TEMP_FEATURE" ]; then
      echo ""
      echo "  ${RED}❌ Error: target layer and target feature are required!${RESET}"
      sleep 1
      continue
  fi

  if [ -z "$TEMP_SOURCE_LAYER" ]; then
      TEMP_SOURCE_LAYER="$TEMP_LAYER"
  fi

  LAYERS+=("$TEMP_LAYER")
  FEATURES+=("$TEMP_FEATURE")
  SOURCE_LAYERS+=("$TEMP_SOURCE_LAYER")
  SOURCES+=("$TEMP_SOURCE")

  echo ""
  echo "  ${GREEN}✓ Queued: ${TEMP_LAYER} → ${TEMP_FEATURE}${RESET}"
  sleep 0.5
done

echo "🚀 Generating ${#LAYERS[@]} layer(s)..."

PROJECT_ROOT="$(cd "$(dirname "$0")/.." && pwd)"
cd "$PROJECT_ROOT"

# createLayer appends an include on every run. On the next Gradle invocation that
# freshly included project would be configured before its sources exist, which fails.
# So the original settings file is restored before each run and the includes are
# appended once, at the end.
SETTINGS_FILE="$PROJECT_ROOT/settings.gradle.kts"
SETTINGS_BACKUP="${SETTINGS_FILE}.bak"
cp "$SETTINGS_FILE" "$SETTINGS_BACKUP"

count=1
total=${#LAYERS[@]}
i=0
while [ $i -lt $total ]; do
  LAYER="${LAYERS[$i]}"
  FEATURE="${FEATURES[$i]}"
  SOURCE_LAYER="${SOURCE_LAYERS[$i]}"
  SOURCE="${SOURCES[$i]}"

  echo "--------------------------------------------------"
  echo "Step $count / $total"
  echo "New layer      : $LAYER"
  echo "Target feature : $FEATURE"
  echo "Source layer   : $SOURCE_LAYER"
  echo "Source feature : $SOURCE"
  echo "--------------------------------------------------"

  GRADLE_ARGS=("createLayer" "--layer=$LAYER" "--feature=$FEATURE")

  if [ -n "$SOURCE" ]; then
      GRADLE_ARGS+=("--source=$SOURCE")
  fi

  if [ -n "$SOURCE_LAYER" ]; then
      GRADLE_ARGS+=("--source-layer=$SOURCE_LAYER")
  fi

  cp "$SETTINGS_BACKUP" "$SETTINGS_FILE"

  ./gradlew "${GRADLE_ARGS[@]}" -Dorg.gradle.configureondemand=true --no-configuration-cache || {
    echo "❌ Step $count failed, stopping."
    cp "$SETTINGS_BACKUP" "$SETTINGS_FILE"
    rm -f "$SETTINGS_BACKUP"
    exit 1
  }
  count=$((count+1))
  i=$((i+1))
done

cp "$SETTINGS_BACKUP" "$SETTINGS_FILE"
rm -f "$SETTINGS_BACKUP"

echo ""
echo "📝 Updating settings.gradle.kts..."
i=0
while [ $i -lt $total ]; do
  FEATURE="${FEATURES[$i]}"
  LAYER="${LAYERS[$i]}"
  FORMATTED_LAYER=$(echo "$LAYER" | tr '/' ':')
  INCLUDE_LINE="include(\"${FEATURE}:${FORMATTED_LAYER}\")"

  if ! grep -qF "$INCLUDE_LINE" "$SETTINGS_FILE"; then
    FEATURE_PREFIX="include(\"${FEATURE}:"
    LAST_FEATURE_LINE=$(grep -n "$FEATURE_PREFIX" "$SETTINGS_FILE" | tail -1 | cut -d: -f1)

    if [ -n "$LAST_FEATURE_LINE" ]; then
      sed -i '' "${LAST_FEATURE_LINE} a\\
${INCLUDE_LINE}" "$SETTINGS_FILE"
      echo "  ✓ ${FEATURE}:${FORMATTED_LAYER} (added to feature group)"
    else
      echo "" >> "$SETTINGS_FILE"
      echo "$INCLUDE_LINE" >> "$SETTINGS_FILE"
      echo "  ✓ ${FEATURE}:${FORMATTED_LAYER} (appended)"
    fi
  else
    echo "  ✓ ${FEATURE}:${FORMATTED_LAYER} (already present)"
  fi

  i=$((i+1))
done

echo ""
echo "✅ Done!"
