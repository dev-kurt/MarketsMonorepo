---
description: Run the full quality gate and report results before a PR
---

Run the quality gate in order; stop and report at the first failure.

1. `./gradlew detekt` — must be **zero findings** (suppressions and baselines are not accepted
   in this repo; fix the code instead).
2. `./gradlew test` — attach the failing test names if any fail.
3. `./gradlew assembleDebug`
4. `./gradlew buildHealth -Pdagp.enabled=true` — apply obvious advice (unused/misdeclared
   dependencies); flag anything debatable instead of applying it.
5. If UI changed: `./scripts/run_maestro.sh` (requires a connected device/emulator) and list
   which flows ran.

Then summarize: what passed, what changed since `origin/main` (`git diff --stat origin/main`),
and propose a conventional commit message in English.
