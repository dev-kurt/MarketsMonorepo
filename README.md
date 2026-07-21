# Markets

[![CI](https://github.com/dev-kurt/Markets/actions/workflows/ci.yml/badge.svg)](https://github.com/dev-kurt/Markets/actions/workflows/ci.yml)

A native Android cryptocurrency markets app built on
the [CoinGecko](https://www.coingecko.com/en/api) demo API.
Browse coins with infinite scrolling, inspect coin details, and maintain a locally persisted
watchlist
surfaced on the dashboard.

The goal of this repository is not the feature set — it is the **architecture**. The project mirrors
a
production-grade multi-module setup (convention plugins, api/impl module isolation, compile-time DI
aggregation, per-feature navigation contracts) at showcase scale: **53 Gradle modules** for 5
features,
on purpose.

## Features

- **Coins list** — paginated market data (Paging 3), pull-to-refresh, skeleton loading, error/retry
  states
- **Coin detail** — market stats, ATH/ATL, supply, description
- **Watchlist** — star coins anywhere; persisted locally with DataStore; survives restarts
- **Dashboard** — top-10 watched coins, "See all" opens the full watchlist as a bottom sheet
- **Developer tools** — shake the device (debug builds only) for a dev action sheet, e.g. *Clear
  watchlist*

## Architecture

### Module layout

```
markets/                  # app shell: Application + MainActivity, nothing else
markets-features/         # product features
  ├── graph-main|bottom|dashboard|list/   # navigation graph hosts
  ├── coins-list/  coin-detail/  dashboard/  watchlist/
shared-features/          # cross-cutting capabilities
  ├── ui/  navigation/  network/  serialization/  paging/
  ├── coroutines/  logger/  dev-tools/
template-feature/         # canonical feature skeleton used by the scaffolding script
build-logic/              # convention plugins (single source of build configuration)
```

Every feature splits into `data / domain:api / ui:api / ui:impl / di`. The `api`/`impl` split is
enforced by the module graph: a feature can depend on another feature's `api` modules only, so
implementation details are unreachable at compile time — the compiler is the architecture cop.

Use cases exist **only when they carry logic**. ViewModels talk to repository interfaces
(`domain:api`) directly; a pass-through use case layer was deliberately removed. The
`template-feature` keeps one as a reference for when real orchestration appears.

### Dependency injection

Koin **annotations** with the Koin compiler plugin: each feature ships a `@Module @Configuration`
class, and the whole graph is aggregated **at compile time** at the single `startKoin` call site.
Cross-feature contributions use multibinding — features contribute `GraphMainRoutes`,
`MkSerializersModule`, `DevToolsAction` and `GraphMainContent` instances that the host collects as
`List<T>` injections without knowing who provided them.

### Navigation

Navigation 3 with a graph-marker hierarchy (`GraphMain`, `GraphBottom`, `GraphDashboard`,
`GraphList`). Each route belongs to exactly one graph; detail screens are pushed onto the main
graph so they cover the bottom bar. Features register their own entries (and their route
serializers — routes are interfaces, not sealed, so polymorphic serializers are contributed via
DI). The watchlist "See all" opens through a custom `OverlayScene`-based bottom sheet strategy;
transition animations are per-entry metadata.

### UI state

MVI-flavored: immutable `State`, `sealed interface Event`, optional effect channel.
`Wrapper` (collects state, wires the ViewModel) → `Screen` (single composable per file) →
`section/` composables. Feedback is standardized (`MkFeedbackPlaceholder` with typed
error/info/success variants, skeleton rows for loading).

## Stack

| Area             | Choice                                                          |
|------------------|-----------------------------------------------------------------|
| Language / build | Kotlin 2.4, AGP 9.3, Gradle convention plugins, version catalog |
| UI               | Jetpack Compose (BOM 2026.06), Material 3                       |
| Navigation       | Navigation 3 (`1.2.0-alpha06`)                                  |
| DI               | Koin 4.2 annotations + compiler plugin                          |
| Networking       | Ktor 3.5 + kotlinx-serialization                                |
| Images           | Coil 3                                                          |
| Persistence      | DataStore (custom JSON serializer)                              |
| Paging           | Paging 3                                                        |
| Logging          | Kermit                                                          |

## Testing

- **Unit tests** — JUnit + coroutines-test + Turbine; network faked with Ktor `MockEngine`
  (no mocking framework). Covers watchlist repository (DataStore behavior, DTO mapping,
  empty-input short-circuit) and ViewModel state (load / error / retry / toggle).
- **E2E** — [Maestro](https://maestro.mobile.dev/) flows per feature (`<feature>/.maestro/`),
  driven by UI test tags. `./scripts/run_maestro.sh` discovers and runs every flow against a
  connected device and prints a pass/fail summary.
- **CI** — GitHub Actions: `assembleDebug` + unit tests on every push/PR. The build needs **no
  secrets** (see below).

## Getting started

```bash
git clone https://github.com/dev-kurt/Markets.git
```

Open in Android Studio and run. **The project builds and runs without any API key** — CoinGecko's
public endpoints are used unauthenticated. For the higher demo-tier rate limit, add your free
[demo key](https://www.coingecko.com/en/api/pricing) to `local.properties`:

```properties
cgDemoApiKey=CG-xxxxxxxxxxxx
```

The key travels `local.properties → BuildConfig → network layer`; it is never committed.

## Tooling

| Tool                      | Purpose                                                                                                                                                 |
|---------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------|
| `scripts/create_layer.sh` | Scaffolds a new feature/layer from `template-feature` (also an IDE run config)                                                                          |
| `scripts/run_maestro.sh`  | Batch/single Maestro runner with device handling and a summary                                                                                          |
| `api-collections/`        | Bruno collection of the exact CoinGecko endpoints in use — API contracts versioned next to the code, with response tests and schema pitfalls documented |
| Dev tools sheet           | Shake gesture (debug only) → runtime actions contributed per feature via DI                                                                             |

## Roadmap

Deliberately out of scope for now: search, price charts, global market stats, splash screen.
The Bruno collection's README tracks the corresponding endpoints.
