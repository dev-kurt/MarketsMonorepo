# Markets API Collections (CoinGecko Demo)

[Bruno](https://www.usebruno.com/) collection for the CoinGecko Demo (free) endpoints
used by the Markets app. Plain YAML → git-diffable and versioned; API contracts live
in the same repo as the code.

## Setup

1. In Bruno, **Open Collection** → select this folder (`api-collections/`).
2. Pick the **Markets Demo** environment (top right).
3. Set the `cg_demo_api_key` secret to your CoinGecko Demo key (Bruno stores secrets
   locally — they are never written to these files, so nothing leaks into the repo).
4. Run **00. Health → Ping** → seeing `(V3) To the Moon!` means the key works.

- **Base URL:** `https://api.coingecko.com/api/v3`
- **Auth:** `x-cg-demo-api-key` header (value comes from the environment)

## Folder → feature mapping

The collection contains **only the endpoints the app actually uses**. Each request's
`docs` section lists the fields mapped into DTOs plus the schema pitfalls.

| Folder     | Endpoint         | Feature       |
|------------|------------------|---------------|
| 00. Health | `/ping`          | — (key check) |
| 01. Coins  | `/coins/markets` | `coins-list`  |
| 01. Coins  | `/coins/{id}`    | `coin-detail` |
| 02. Search | `/search`        | `search`      |

### Out of scope (roadmap)

These endpoints were deliberately removed and will be added back when their features
are built: `/simple/price` (favorites), `/coins/{id}/market_chart` and `/ohlc`
(charts), `/search/trending` (trending), `/global` (dashboard summary).

## Convention: API change workflow (REQUIRED)

When an endpoint's behaviour or schema changes, **before** touching the data layer:

1. **Update it in Bruno** — run the request, observe the new response shape.
2. **Verify** — is the `after-response` test green, are the critical fields present?
3. **Then** update the DTO / mapper / repository against that verified shape.

In other words, the collection is **step 0** of every API change; the data layer is
derived from observed reality, not from guesswork. This discipline keeps the
collection current.

> **Note (known gap):** this workflow is *human*-enforced — if it is skipped, drift
> happens silently. For machine enforcement, a `bruno run` smoke test can be added to
> CI so that a mismatch between the collection and the live API turns the pipeline
> red. For now we deliberately rely on the discipline layer alone.
