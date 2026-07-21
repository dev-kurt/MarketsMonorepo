# Markets

[![CI](https://github.com/dev-kurt/MarketsMonorepo/actions/workflows/ci.yml/badge.svg)](https://github.com/dev-kurt/MarketsMonorepo/actions/workflows/ci.yml)

Native Android crypto app built on the [CoinGecko](https://www.coingecko.com/en/api) demo
API: a paginated coins list, coin detail, a locally persisted watchlist and a dashboard
that surfaces it.

**The app is deliberately small — the architecture is the point.** This repository
demonstrates a production-grade, multi-application monorepo at showcase scale: 53 Gradle
modules for 5 features, on purpose.

Kotlin · Compose · Navigation 3 · Koin annotations · Ktor · Paging 3 · DataStore · Coil

## Running it

Open in Android Studio and run. **No API key is required**: every endpoint the app uses
(`/ping`, `/coins/markets`, `/coins/{id}`) also answers unauthenticated at CoinGecko's
public rate limit (roughly 5–15 requests/minute, per IP). That is enough to browse the
app; if you scroll aggressively you may see throttled requests surface as retryable
errors.

A free [demo key](https://www.coingecko.com/en/api/pricing) lifts the limit to the demo
tier (30 requests/minute):

```properties
# local.properties
cgDemoApiKey=CG-xxxxxxxxxxxx
```

The key travels `local.properties → BuildConfig → network layer` and is never committed.
CI runs the full pipeline with no secrets for the same reason.

## Why a monorepo

- **One source of truth for conventions.** Every module is configured by the same
  `build-logic` convention plugins and the same version catalog — build behaviour is
  changed in exactly one place.
- **Atomic cross-cutting changes.** A contract change and every consumer of it land in a
  single commit; there is no version skew between "the SDK repo" and "the app repo".
- **One quality gate.** The same CI, tests, detekt rules and dependency checks guard all
  of it — nothing lives outside the fence.
- **AI-assisted development sees the whole system.** Agents and LLM tooling operate with
  every contract, consumer and convention in a single workspace, which makes
  cross-module refactors and reviews dramatically more reliable than in split repos.
- **Room for more than one app.** `shared-features` is app-agnostic by design; a second
  application can be added to this repo and reuse the same foundation without touching
  the existing one.

## Layout

```
markets/            app shell — Application + MainActivity, nothing else
markets-features/   features of this app: graph hosts, coins-list, coin-detail,
                    dashboard, watchlist
shared-features/    app-agnostic capabilities: ui, navigation, network, serialization,
                    paging, coroutines, logger, dev-tools
template-feature/   the canonical feature skeleton, consumed by scripts/create_layer.sh
build-logic/        convention plugins — the single source of build configuration
```

The flat, prefixed grouping (`markets-features` vs `shared-features`) is what makes the
multi-app vision concrete: a hypothetical second app would add its own
`<app>-features/` group and keep consuming `shared-features` unchanged.

## Feature anatomy

A feature is composed of `data / domain / ui / di` — **but only the layers it needs**,
and each layer can itself split into `api` / `impl` when its contract should be
consumable without exposing the implementation:

- `ui:api` carries only the feature's navigation contract (its routes); `ui:impl`
  carries the screens. Other features depend on the contract, never on the screens.
- `domain:api` carries repository interfaces and models. Watchlist, for example, has no
  `domain:impl` — its logic lives in the repository, and pass-through use cases were
  deliberately removed.
- Shared capabilities follow the same rule at their own scale: `network` is
  `api/impl/di`, `serialization` is `api/di`, `paging` is just `api`.
- `di` is the feature's composition root and the only module the app aggregates.

The split is enforced, not aspirational: the module graph only exposes `api` modules to
other features, so reaching into an `impl` is a compile error.

## Architecture decisions

- **Clean-architecture layering sits at the data/domain boundary.** ViewModels depend on
  repository interfaces from `domain:api`; implementations live in `data`. Use cases
  exist only when they carry real orchestration — `template-feature` keeps one as the
  reference shape.
- **MVI shapes the UI layer.** Immutable `State`, `sealed interface Event`, and an
  effect channel where one-shot side effects exist. A `Wrapper` collects state and wires
  the ViewModel, a `Screen` (one composable per file) renders it, `section/` composables
  break it down.
- **Navigation 3 with graph markers.** Each route belongs to exactly one graph
  (`GraphMain`, `GraphBottom`, `GraphDashboard`, `GraphList`); detail screens push onto
  the main graph, and the watchlist opens through a custom bottom-sheet scene strategy.
  Features contribute their own entries and route serializers.
- **DI is compile-time.** Koin annotations with the Koin compiler plugin; the entire
  graph is aggregated at the single `startKoin` call site. Cross-feature wiring uses
  multibinding: features contribute navigation entries, serializers and dev-tool actions
  as `List<T>` collected by hosts that never know who provided them.

## Quality gates

- **Unit tests** — coroutines-test + Turbine; the network is faked with Ktor's
  `MockEngine`, no mocking framework anywhere.
- **E2E** — [Maestro](https://maestro.mobile.dev/) flows live next to their feature
  (`<feature>/.maestro/`) and drive the app through UI test tags on a real device.
- **detekt** — every module is scanned through the same convention plugin with
  `warningsAsErrors`; the codebase is at **zero findings with no baseline file**, so a
  new finding fails the build instead of joining a suppression list.
- **Dependency analysis (DAGP)** — `./gradlew buildHealth -Pdagp.enabled=true` fails on
  unused dependencies and reports api/implementation misdeclarations, keeping the module
  graph honest. Opt-in locally for build performance; always on in CI.
- **CI** — on every push and PR: JDK 21, Gradle cache, then
  `assembleDebug test detekt` followed by `buildHealth`. Superseded runs are cancelled,
  test reports are uploaded on failure, and no secrets are configured — the pipeline
  proves the repo builds exactly as a fresh clone would.

## Tooling

- **`scripts/create_layer.sh`** — scaffolds a new feature or layer from
  `template-feature`, so new code starts life already matching the conventions. Also
  available as an IDE run configuration.
- **`scripts/run_maestro.sh`** — runs every Maestro flow in the repo against the
  connected device (or a single flow via argument) and prints a pass/fail summary.
- **`api-collections/`** — a Bruno collection of the exact CoinGecko endpoints in use,
  with response contract tests. API contracts are versioned next to the code, and the
  collection is step zero of any API change: observe the real response first, then
  update the DTOs.
- **Dev-tools sheet** — shake the device on a debug build. Today it holds a single
  action (*Clear watchlist*, the fastest way to reach the dashboard's empty state); it
  is built as a growth point where any feature can contribute developer actions through
  DI.
