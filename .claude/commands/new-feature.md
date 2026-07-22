---
description: Scaffold a new feature following the template-feature contract
---

Scaffold a new feature named `$ARGUMENTS` under `markets-features/`.

1. Read [template-feature/README.md](../../template-feature/README.md) first — token table,
   layer anatomy, known gaps.
2. Generate layers **in this order** (di last, or dependencies duplicate):
   ```bash
   ./gradlew createLayer --layer=domain:api  --feature=:markets-features:$ARGUMENTS
   ./gradlew createLayer --layer=data        --feature=:markets-features:$ARGUMENTS
   ./gradlew createLayer --layer=ui:api      --feature=:markets-features:$ARGUMENTS
   ./gradlew createLayer --layer=ui:impl     --feature=:markets-features:$ARGUMENTS
   ./gradlew createLayer --layer=di          --feature=:markets-features:$ARGUMENTS
   ```
   Add `domain:impl` only if the feature has real orchestration logic.
3. Fix the generated package's location segment if it disagrees with handwritten modules
   (template README → "Known gaps").
4. Fill in the blanks: real endpoint (verify the contract in `api-collections/` first),
   response DTO, mapper, `State` fields, `TopBar` placeholder title.
5. Pick the Route's parent graph (`GraphMain` / `GraphBottom` / `GraphDashboard`) and update
   **both** the polymorphic serializer registration in `:di` and the serializer test.
6. Write unit tests for the repository (Ktor `MockEngine`) and the ViewModel (fakes + Turbine).
7. Add a Maestro flow in `markets-features/$ARGUMENTS/.maestro/` using `MkTestTag` tags.
8. Verify: `./gradlew detekt test assembleDebug` — all green before proposing a commit.
``