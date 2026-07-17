# Markets API Collections (CoinGecko Demo)

Markets uygulamasının kullandığı CoinGecko Demo (ücretsiz) uçlarının
[Bruno](https://www.usebruno.com/) koleksiyonu. Düz YAML → git ile diff'lenebilir,
versiyonlanabilir; API sözleşmeleri kodla aynı repoda yaşar.

## Kurulum

1. Bruno'da **Open Collection** → bu klasörü (`api-collections/`) aç.
2. Sağ üstten **Markets Demo** environment'ını seç.
3. `cg_demo_api_key` secret'ına CoinGecko Demo key'ini gir (Bruno lokalde saklar,
   dosyaya yazılmaz — repoya sır sızmaz).
4. **00. Health → Ping** çalıştır → `(V3) To the Moon!` görürsen key geçerli.

- **Base URL:** `https://api.coingecko.com/api/v3`
- **Auth:** `x-cg-demo-api-key` header'ı (değer environment'ta)

## Klasör → feature eşlemesi

| Klasör | Endpoint | Feature |
|--------|----------|---------|
| 00. Health | `/ping` | — (sağlık) |
| 01. Coins | `/coins/markets` | `coins-list` |
| 01. Coins | `/coins/{id}` | `coin-detail` |
| 01. Coins | `/simple/price` | `favorites` (fiyat tazeleme) |
| 02. Charts | `/coins/{id}/market_chart`, `/ohlc` | `coins-chart` |
| 03. Search | `/search`, `/search/trending` | `search` |
| 04. Global | `/global` | dashboard özeti |

## Konvansiyon: API değişikliği iş akışı (ZORUNLU)

Bir endpoint'in davranışı/şeması değiştiğinde, data katmanına dokunmadan **önce**:

1. **Bruno'da güncelle** — request'i çalıştır, yeni yanıt şeklini gözlemle.
2. **Doğrula** — `after-response` test'i yeşil mi, kritik alanlar yerinde mi?
3. **Sonra** DTO / mapper / repository'yi bu doğrulanmış şekle göre güncelle.

Yani koleksiyon her API değişikliğinin **0. adımıdır**; data katmanı değişikliğinin
kaynağı gözlemlenen gerçektir, tahmin değil. Bu disiplin koleksiyonu güncel
(çürümemiş) tutar.

> **Not (bilinen boşluk):** Bu akış *insan* zorlamasıdır — atlanırsa drift sessizce
> oluşur. Makine-zorlaması için CI'a `bruno run` smoke-test'i eklenebilir; o zaman
> koleksiyon ile gerçek API uyuşmazlığı pipeline'ı kırmızıya çevirir. Şu an bilinçli
> olarak (1) disiplin katmanıyla yetiniyoruz.
