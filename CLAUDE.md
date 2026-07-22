# CLAUDE.md

Operational guidance for AI agents working in this repository. Humans should start with
[README.md](README.md); this file encodes the rules an agent must not break.

## Commands

| Task | Command |
|------|---------|
| Build | `./gradlew assembleDebug` |
| Unit tests | `./gradlew test` |
| Static analysis | `./gradlew detekt` |
| Dependency health | `./gradlew buildHealth -Pdagp.enabled=true` |
| E2E (device required) | `./scripts/run_maestro.sh` |
| Scaffold a layer | `./gradlew createLayer --layer=<layer> --feature=:markets-features:<name>` |

Definition of done for any change: `detekt` + `test` + `assembleDebug` all green.
If UI behavior changed, run the affected Maestro flows too.

## Architecture rules (non-negotiable)

- **Module groups:** `markets/` (app shell — Application only), `markets-features/`,
  `shared-features/`, `template-feature/` (canonical skeleton), `build-logic/` (all build config).
- **Feature anatomy:** `data` / `domain:api` / `domain:impl` (only when real logic exists) /
  `ui:api` / `ui:impl` / `di`.
- **The dependency law:** a module may depend on another feature's `:api` modules **only**.
  Never add a dependency on someone else's `:impl` to fix an unresolved symbol — wiring
  belongs in `:di`. The module graph is the architecture; do not route around it.
- **`domain:api` is pure JVM** (`marketsJvmLibrary`): no Android, no Ktor, no Compose imports.
- **`ui:impl` does not depend on `ui:api`.** The `di` module binds `Route → Wrapper` via
  `scope.entry<Route> { Wrapper() }`.
- **Build config lives in convention plugins** (`build-logic/`). Feature `build.gradle.kts`
  files declare plugin aliases, `namespace`, and dependencies — nothing else.

## Conventions

- **MVI naming:** immutable `State` (data class) · `sealed interface Event` · `ViewModel` ·
  `Wrapper` (binds VM, collects state) · `Screen` (pure `(state, onEvent)` — previewable) ·
  `section/` for sub-composables.
- **DI:** one Koin `@Module @Configuration` class per feature, in `:di`. All bindings live there.
- **Routes:** `@Serializable` `NavKey` in `ui:api`. Register the polymorphic serializer in `:di`
  **and** update the feature's serializer test — a missing registration compiles fine and
  crashes at runtime.
- **Language:** code, comments, docs, commits — English. Conventional commits
  (`feat:` `fix:` `docs:` `refactor:` `test:` `ci:` `chore:`).
- **Quality bar:** zero detekt findings, no `@Suppress`, no baseline files. If detekt
  complains, fix the code, not the rule.
- **Tests:** new repository or ViewModel logic ships with unit tests. Hand-written fakes over
  mocking frameworks; network is faked with Ktor `MockEngine`.

## Known pitfalls

- `createLayer` must generate `di` **last**, or dependency lines get duplicated
  (see [template-feature/README.md](template-feature/README.md)).
- Generated packages carry a location segment (`markets_features.<name>`) while handwritten
  modules do not — align manually after scaffolding ("Known gaps" in the template README).
- UI test tags come from `MkTestTag`; when renaming tags, update the feature's
  `.maestro/` flows in the same change.
- API work starts in `api-collections/` (Bruno): verify the endpoint contract there before
  writing DTOs.
