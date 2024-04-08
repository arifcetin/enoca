Spring security katmanlı token ile güvenli giriş yapılan, ürün fiyatı ve sepet bilgilerini her zaman güncel tutan bir alışveriş uygulaması.

# End-Point

- (Post) /login -> kullanıcı girişi
- (Post) /register -> kullanıcı kaydı
- (Get) /customer ->kullanıcıların listesi
- (Post) /product ->ürün ekleme
- (Get) /product ->ürünleri listeleme
- (Put) /product{pId} ->ürün güncelleme
- (Delete) /product{pId} ->ürün silme
- (Post) /cart/add ->sepete ürün ekleme
- (Post) /cart/delete ->sepetten ürün silme
- (Get) /cart ->sepeti listeleme
- (Delete) /cart ->sepeti silme
- (Get) /order ->sipariş verme
- (Get) /order/get ->kullanıcının siparişlerinin listesi
- (Get) /order/all ->tüm siparişlerin listesi

