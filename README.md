# Markets

[![CI](https://github.com/dev-kurt/Markets/actions/workflows/ci.yml/badge.svg)](https://github.com/dev-kurt/Markets/actions/workflows/ci.yml)

Native Android crypto app on the [CoinGecko](https://www.coingecko.com/en/api) demo API:
paginated coins list, coin detail, a locally persisted watchlist, and a dashboard that
surfaces it.

**The app is small on purpose — the architecture is the point.** It mirrors a
production-grade multi-module setup at showcase scale: 53 Gradle modules for 5 features.

Kotlin · Compose · Navigation 3 · Koin annotations · Ktor · Paging 3 · DataStore · Coil

## Run it

Open in Android Studio and run — **no API key required**. Optional, for a higher rate
limit:

```properties
# local.properties
cgDemoApiKey=CG-xxxxxxxxxxxx
```

## Layout

```
markets/            app shell (Application + MainActivity, nothing else)
markets-features/   graph hosts + coins-list, coin-detail, dashboard, watchlist
shared-features/    ui, navigation, network, serialization, paging, coroutines, logger, dev-tools
template-feature/   canonical skeleton consumed by scripts/create_layer.sh
build-logic/        convention plugins — single source of build configuration
```

Every feature splits into `data / domain:api / ui:api / ui:impl / di`.

## The decisions that matter

- **api/impl isolation is enforced by the module graph.** A feature can only depend on
  another feature's `api` modules; reaching into an `impl` is a compile error.
- **Features contribute, hosts collect.** Navigation entries, route serializers and
  dev-tool actions are contributed via Koin multibinding, and the whole DI graph is
  aggregated at compile time at the single `startKoin` call site.
- **ViewModels depend on repository interfaces.** Use cases exist only when they carry
  real logic — pass-through use cases were deliberately removed; `template-feature`
  keeps one as the reference shape.
- **Navigation 3 with graph markers.** Each route belongs to exactly one graph; detail
  screens push onto the main graph, and the watchlist opens through a custom
  bottom-sheet scene strategy.

## Verification

- **Unit tests** — coroutines-test + Turbine; network faked with Ktor `MockEngine`, no
  mocking framework
- **E2E** — Maestro flows per feature (`<feature>/.maestro/`), run with
  `./scripts/run_maestro.sh`
- **Static analysis** — detekt (zero findings, no baseline) and dependency hygiene via
  `./gradlew buildHealth -Pdagp.enabled=true`
- **CI** — all of the above on every push, no secrets required

## Tooling

- `scripts/create_layer.sh` — scaffolds a new feature/layer from `template-feature`
- `api-collections/` — Bruno collection of the exact CoinGecko endpoints in use, with
  response contract tests
- Shake the device (debug builds) for the dev-tools sheet, e.g. *Clear watchlist*
