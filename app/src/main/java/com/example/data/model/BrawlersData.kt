package com.example.data.model

object BrawlersData {

    val brawlers = listOf(
        Brawler(
            id = "lilith",
            name = "Lilith (Kan Kraliçesi)",
            rarity = Brawler.Rarity.DARK,
            backstory = "Lanetli bir şatoda hapsedilmiş antik bir vampir klanının tek varisidir. Gladyatörlerin taze kanıyla beslenmek için arenaya katılmıştır. Saldırdığında düşmanın fışkıran kanlarını emerek kendini besler ve acı çığlıklarıyla rakiplerine korku salar.",
            hp = 3400,
            damage = 480,
            speed = 1.2f,
            attackInfo = "Kan Tırpanı: Yakın mesafedeki düşmanları tırpanıyla keserek canlarının %20'sini kendisi için çalar.",
            superName = "Kan Havuzu",
            superDesc = "Likit formuna dönüşerek yere yayılır. Bu alandaki tüm rakipleri sürekli zehir ve yavaşlama etkisine maruz bırakır.",
            ultimateName = "Erotik Kabus (Forbidden Ultimate)",
            ultimateDesc = "Hava yükselip rakiplerinin zihnine girer; tüm düşmanlar hipnotize olarak 3 saniye boyunca saldırısız şekilde ona doğru sürüklenir. Bu sırada zırhları tamamen kırılır.",
            sounds = listOf("Sinsi ve tahrik edici fısıltılar", "Kurbanın can havliyle attığı ıslak çığlıklar", "Kemikleri eriten asit tıslamaları"),
            animations = listOf("Saldırı: Tırpandan sıçrayan kalın kan şeritleri", "Ölüm: Bedeninin tamamen parçalanıp yüzlerce küçük yarasaya dönüşerek dağılması"),
            skins = listOf(
                BrawlerSkin("lilith_default", "Orijinal Lilith", 0, false, "Karanlık gotik transparan dantelli zırh."),
                BrawlerSkin("lilith_succubus", "Aşırı Şehvet Succubus (Forbidden)", 180, true, "Gore efektli, çıplaklık ve dehşet temalı şeytani skin."),
                BrawlerSkin("lilith_bride", "Kanlı Gelinlik", 90, false, "Kırmızı göğüs dekoltesi ve yırtık kanlı duvak.")
            )
        ),
        Brawler(
            id = "kassap",
            name = "Kassap (Psikopat Mezbaha Devine)",
            rarity = Brawler.Rarity.COMMON,
            backstory = "Yer altı dehlizlerinde kurbanlarını asarak insan etinden özel mezeler hazırlayan tescilli bir akıl hastasıdır. Elindeki paslı dev satır her sallandığında rakiplerinin uzuvlarını koparmaya ant içmiştir.",
            hp = 5200,
            damage = 700,
            speed = 0.85f,
            attackInfo = "Paslı Satır: Önündeki geniş alana dev bir satır darbesi indirerek kemik kırma sesleri eşliğinde ağır hasar verir.",
            superName = "Kasap Kancası",
            superDesc = "Rakipleri doğrudan kendine çeken devasa demir bir kanca fırlatır. Kurban çekildiğinde kısa süreliğine sersemler.",
            ultimateName = "Doğrama Seansı (Forbidden Ultimate)",
            ultimateDesc = "Çılgınca dönerek çevresindeki her şeyi doğrar. Değdiği her rakipte devasa kan fışkırması (blood explosion) ve parçalanma gerçekleşir.",
            sounds = listOf("Islak et kesme ve parçalama sesleri", "Zincir şangırtıları ve çıldırtan gülüşler", "Kurbanların boğuk kemik kırılmaları"),
            animations = listOf("Saldırı: Satırın yere inip zemini çatlatması ve kan fışkırtması", "Ölüm: Bedeninin kendi satırı altında ikiye bölünerek yere yığılması"),
            skins = listOf(
                BrawlerSkin("kassap_default", "Varsayılan Kasap", 0, false, "Kan gölüne dönmüş kasap önlüğü ve deri maske."),
                BrawlerSkin("kassap_slasher", "Mezbaha Cerrahı (Forbidden)", 140, true, "Organların dışarı fırladığı, her vuruşta gore sıçramaları sağlayan tıbbi skin."),
                BrawlerSkin("kassap_pig", "Domuz Kafalı Cellat", 80, false, "Tüyler ürpertici domuz kafası maskesi ve devasa testere.")
            )
        ),
        Brawler(
            id = "malakor",
            name = "Malakor (İblis Çağrıcısı)",
            rarity = Brawler.Rarity.LEGENDARY,
            backstory = "Ruhunu cehennem lorduna teslim ederek yasaklı fısıltıları öğrenen eski bir inanç lideridir. Amacı arenada ölenlerin canlarını toplayarak cehennem kapısını tamamen dünyaya açmaktır.",
            hp = 2900,
            damage = 620,
            speed = 1.0f,
            attackInfo = "Cehennem Kıvılcımı: Rakipleri yakan ve geride ateş izi bırakan plazma küreleri fırlatır.",
            superName = "Gehenna Kapısı",
            superDesc = "Zeminde bir yarık açarak hedefe doğru koşan ve patladığında kan donduran çığlıklar atan 3 küçük iblis çağırır.",
            ultimateName = "Şeytan Ayini (Forbidden Ultimate)",
            ultimateDesc = "Kendi canının %30'unu feda ederek devasa bir pentagram çizer. Bu pentagram rakiplerin can barlarını doğrudan sıfırlamaya yakın hasar verir ve onları köleye dönüştürür.",
            sounds = listOf("Ters Latince dinsel ayin mırıltıları", "İblislerin cehennem fısıltıları", "Patlayan kükürt sesleri"),
            animations = listOf("Saldırı: Havada mor-siyah dumanlar çıkaran parmak çıtlatması", "Ölüm: Yerde açılan cehennem portalı tarafından içine çekilerek kaybolması"),
            skins = listOf(
                BrawlerSkin("malakor_default", "Pentagram Rahibi", 0, false, "Siyah cübbe ve keçi boynuzlu maske."),
                BrawlerSkin("malakor_demon", "Saf İblis Lordu (Forbidden)", 220, true, "Tümüyle alevler içinde, çıplak şeytani hatlar ve kanatlarla bezenmiş cehennem formu."),
                BrawlerSkin("malakor_corrupt", "Bâtıl Hoca", 110, false, "Müridlerinin ruhlarını emen fıstık yeşili tonlarda asidik cübbe.")
            )
        ),
        Brawler(
            id = "elena",
            name = "Elena (Asit Ağlayan)",
            rarity = Brawler.Rarity.RARE,
            backstory = "Savaşta vahşice öldürülen çocuklarının yasını tutarken aklını kaçırmış bir annedir. Gözyaşları zaman içinde lanetlenerek dokunduğu her dokuyu canlı canlı eriten ölümcül asidik bir sıvıya dönüşmüştür.",
            hp = 3100,
            damage = 430,
            speed = 1.05f,
            attackInfo = "Asit Hıçkırığı: Konik bir alanda düşman metalini ve derisini anında eriten asit damlaları püskürtür.",
            superName = "Feryat Yağmuru",
            superDesc = "Haritada geniş bir alana asit yağmuru yağdırarak duvarları ve engelleri aşındırıp rakiplere saniyelik yüksek hasar verir.",
            ultimateName = "Keder Çığlığı (Forbidden Ultimate)",
            ultimateDesc = "Ruhani ve yırtıcı bir çığlık atarak etrafındaki tüm rakiplerin kulaklarını patlatır, onları 4 saniye boyunca tamamen kör ve sağır eder.",
            sounds = listOf("Yürek burkan derinden ağlama sesleri", "Kemik eriten asit tıslamaları", "Yırtıcı feryat çığlıkları"),
            animations = listOf("Saldırı: Yüzünü kapatarak öne doğru asit fırlatması", "Ölüm: Bedeninin tamamen eriyerek yeşil ve kanlı köpüklü bir sıvıya dönüşmesi"),
            skins = listOf(
                BrawlerSkin("elena_default", "Yastaki Anne", 0, false, "Yırtık pırtık siyah tül kıyafet ve mor gözyaşı lekeleri."),
                BrawlerSkin("elena_skin_show", "Islak İntikam (Forbidden)", 160, true, "Sadece asit tabakalarıyla örtülü çıplak yara izlerine sahip dehşet verici form."),
                BrawlerSkin("elena_weeping", "Ağlayan Melek", 90, false, "Mermerden yapılmış, gözlerinden kanlı yaş sızan gotik heykel giysisi.")
            )
        ),
        Brawler(
            id = "gorgon",
            name = "Gorgon (Medusa Varisi)",
            rarity = Brawler.Rarity.EPIC,
            backstory = "Yer altı dehlizlerinde yılanların emzirdiği, yarı insan yarı sürüngen bir suikastçıdır. Doğrudan gözlerine bakan gladyatörlerin damarlarındaki kanı kireçlendirerek onları taşa çevirme gücüne sahiptir.",
            hp = 3800,
            damage = 500,
            speed = 1.1f,
            attackInfo = "Zehirli Tükürük: Rakipleri saniyelerce zehirleyen hızlı yılan dişleri fırlatır.",
            superName = "Taşlaştıran Bakış",
            superDesc = "Önündeki tüm rakipleri 2.5 saniyeliğine tamamen donduran (taşlaştıran) yeşil bir şua yayar.",
            ultimateName = "Yılan Yuvası (Forbidden Ultimate)",
            ultimateDesc = "Saçındaki tüm engerekleri haritaya saçar. Bu engerekler düşmanları kovalar, ısırıp ağır yavaşlama ve dayanılmaz acıyla sarsar.",
            sounds = listOf("Yılan fısıltıları ve tıslamalar", "Taş kütlesinin kırılma/çatlama sesleri", "Sinsi tıslama tebessümleri"),
            animations = listOf("Saldırı: Gözlerinin parlayıp öne doğru yeşil ışın saçması", "Ölüm: Kendi gücüyle tamamen taşa dönüşüp parçalara ayrılması"),
            skins = listOf(
                BrawlerSkin("gorgon_default", "Engerek Saçlı", 0, false, "Zümrüt yeşili pullarla kaplı zırh ve canlı yılanlar."),
                BrawlerSkin("gorgon_gilded", "Altın Medusa (Forbidden)", 190, true, "Lüks, cüretkar altın göğüs plaka kaplamaları ve kırmızı dehşet gore göz yapısı."),
                BrawlerSkin("gorgon_python", "Piton Kraliçesi", 90, false, "Sarı siyah alacalı piton desenli deri giysi.")
            )
        ),
        Brawler(
            id = "slasher",
            name = "Slasher (Ayna Kesici)",
            rarity = Brawler.Rarity.EPIC,
            backstory = "Narsist bir gladyatörün kırık bir aynaya hapsedilmiş gölgesidir. Arenadaki bedenleri parçalayarak gerçek dünyaya geçmeyi amaçlar. Saldırılarında kırık ayna keskinliği kullanır.",
            hp = 3200,
            damage = 580,
            speed = 1.25f,
            attackInfo = "Ayna Parçası: Önündeki hedefleri kesen, düşmana çarptığında sekerek diğerine geçen ayna şarapnelleri fırlatır.",
            superName = "Yansıma",
            superDesc = "Hedef noktada kırık ayna illüzyonu yaratıp oradaki düşmanla yer değiştirerek arkadan bıçaklar.",
            ultimateName = "Cam Kırığı Tufanı (Forbidden Ultimate)",
            ultimateDesc = "Etrafında dönen keskin cam girdabı oluşturur. Bu girdap değdiği her rakibin derisini yüzercesine ağır kanama (Bleeding) hasarı verir.",
            sounds = listOf("Tiz cam kırılma sesleri", "Ritmik ayna sürtünmeleri", "Kahkahayla karışık yankılanan fısıltılar"),
            animations = listOf("Saldırı: Elindeki aynadan kristal parlaklıkta yaylar fırlatması", "Ölüm: Bedeninin camdan yapılmış bir heykel gibi tuzla buz olması"),
            skins = listOf(
                BrawlerSkin("slasher_default", "Kırık Estetik", 0, false, "Aynı parçalarından oluşan parlak, keskin maskeli ceket."),
                BrawlerSkin("slasher_shattered", "Çıplak Gerçeklik (Forbidden)", 170, true, "Cam parçalarının vücuduna batmış olduğu çıplak ve aşırı gore görünümlü ayna iblisi."),
                BrawlerSkin("slasher_phantom", "Hayalet Maske", 80, false, "Karanlık parlak maskeli, kırmızı pelerinli suikastçı.")
            )
        ),
        Brawler(
            id = "bane",
            name = "Bane (Lav Golemi)",
            rarity = Brawler.Rarity.LEGENDARY,
            backstory = "Yer kabuğunun en derin ve cehennemi lav göllerinde uyanmış antik bir magmadır. Vücudu soğumayan kızgın taşlar, lavlar ve erimiş cevherlerden oluşur. Adımları basıldığı yerde alev bırakır.",
            hp = 6000,
            damage = 400,
            speed = 0.8f,
            attackInfo = "Magma Püskürmesi: Ağzından rakiplerin üzerine erimiş lav fışkırtır, lav etki ettiği alanı 3 saniye yakmaya devam eder.",
            superName = "Lav Havzası",
            superDesc = "Yere vurarak etrafındaki tüm engelleri, duvarları yıkar ve alanı eriyen bir lav gölüne çevirerek rakipleri yakar.",
            ultimateName = "Kıyamet Sütunu (Forbidden Ultimate)",
            ultimateDesc = "Gökten devasa bir magma meteoru indirir. Çarptığı her şeyi kavurarak yok eder, mağlup olan rakiplerin küllerini etrafa saçar.",
            sounds = listOf("Fokurdayan lav sesleri", "Deprem benzeri ağır gümbürtüler", "Eriyen metallerin cızırtıları"),
            animations = listOf("Saldırı: Göğsündeki korlardan ateş püskürmesi", "Ölüm: Lavlarının tamamen sönerek taş yığını haline gelip dağılması"),
            skins = listOf(
                BrawlerSkin("bane_default", "Magma Golemi", 0, false, "Ateş rengi parlamalarla bezenmiş obsidyen gövde."),
                BrawlerSkin("bane_obsidian", "Kara Lav Şeytanı (Forbidden)", 200, true, "Gore lav püskürten, cehennem azabı sembolleriyle bezenmiş çıplak kaya formu."),
                BrawlerSkin("bane_frozen", "Donmuş Kıyamet", 100, false, "Mavi lav püskürten, buz tutmuş volkanik canavar.")
            )
        ),
        Brawler(
            id = "valeri",
            name = "Valeri (Zehirli Kara Dul)",
            rarity = Brawler.Rarity.DARK,
            backstory = "Asil bir aileye mensupken örümcek kraliçesinin ısırığıyla mutasyona uğramıştır. Evlendiği tüm eşleri zifiri karanlık gecelerde zehirleyerek kanlarını kadehlerle içen acımasız bir baştan çıkarıcıdır.",
            hp = 3500,
            damage = 490,
            speed = 1.15f,
            attackInfo = "Örümcek İğnesi: Rakiplerin sinir sistemini felç eden sinsi örümcek iğneleri fırlatır, yavaş yavaş can eritir.",
            superName = "Karanlık Koza",
            superDesc = "Belirli bir bölgeyi komple örümcek ağıyla ve kozalarla sarar, içine giren düşmanlar tamamen kilitlenir.",
            ultimateName = "Arachnid Ziyafeti (Forbidden Ultimate)",
            ultimateDesc = "Örümcek bacaklarını açığa çıkararak rakiplerinin üzerine atlar. Onları ısırıp saniyede dev can emerek canını tamamen fuller.",
            sounds = listOf("Tiksindirici eklembacaklı tıkırtıları", "Valeri'nin erotik kıkırdamaları", "Kozanın büzüşme sesleri"),
            animations = listOf("Saldırı: Sırtından fırlayan örümcek bacaklarıyla hızlı dürtüşler", "Ölüm: Kabuk değiştirir gibi can verip içinin boş bir koza olarak kalması"),
            skins = listOf(
                BrawlerSkin("valeri_default", "Dantelli Örümcek", 0, false, "Karanlık dekolteli gotik dantel elbise ve mor örümcek bacakları."),
                BrawlerSkin("valeri_widow", "Dul Şehveti (Forbidden)", 190, true, "Arachnid mutasyonunun tüm vücutta çıplakça belirdiği, kanlı örümcek kafalı dehşet skin."),
                BrawlerSkin("valeri_brood", "Yavruların Annesi", 95, false, "Bedeninden küçük yeşil örümcekçiklerin fırladığı askeri zırhlı kostüm.")
            )
        ),
        Brawler(
            id = "kala",
            name = "Kala (Kemik Koleksiyoncusu)",
            rarity = Brawler.Rarity.COMMON,
            backstory = "Mezarlıklarda ailesiz büyümüş küçük ve psikopat bir yetimdir. Ölülerin kemiklerini toplayarak onlardan keskin mızraklar ve duvarlar inşa eder. Ölümden korkmaz, zira ölüm onun tek oyun arkadaşıdır.",
            hp = 3200,
            damage = 410,
            speed = 1.1f,
            attackInfo = "Kemik Mızrağı: Rakiplerin etini delip geçen keskin insan kemiği mızrakları fırlatır.",
            superName = "Kemik Surları",
            superDesc = "Zeminden sivri ve ürkütücü kemik duvarlar fışkırtır. Bu duvarlar rakipleri bloke eder ve dokunanlara ağır yavaşlama verir.",
            ultimateName = "Nekro Ayin (Forbidden Ultimate)",
            ultimateDesc = "Toprağın altından yüzlerce çürümüş gladyatör eli çıkararak rakipleri aşağı doğru çeker, onları sarsar ve saniyede ağır ezilme hasarı uygular.",
            sounds = listOf("Kemik tıkırtısı ve sürtünme sesleri", "Bir çocuğun korkunç, histerik kahkahası", "Kırılan iskeletlerin çatırtıları"),
            animations = listOf("Saldırı: Cebinden çıkardığı keskin kemikleri fırlatması", "Ölüm: Bedeninin tamamen dağılarak kuru kafa yığını haline gelmesi"),
            skins = listOf(
                BrawlerSkin("kala_default", "Öksüz Nekroman", 0, false, "Kuru kafa kolyesi taşıyan gri yırtık pelerinli çocuk."),
                BrawlerSkin("kala_reaper", "Ufak Azrail (Forbidden)", 130, true, "Kanlı tırpan taşıyan, yüzü olmayan simsiyah çıplak dehşet iskelet vücudu."),
                BrawlerSkin("kala_pumpkin", "Balkabağı Kabusu", 70, false, "Başına oyulmuş kanlı cadılar bayramı kabağı geçirmiş form.")
            )
        ),
        Brawler(
            id = "arax",
            name = "Arax (Veba Doktoru)",
            rarity = Brawler.Rarity.RARE,
            backstory = "Orta çağ veba salgınında hastaları tedavi etmek yerine onları canlı canlı mezara gömen deli bir doktordur. Maskesinin ardında hırıldayarak nefes alır ve arenayı toksik sarı gazlarıyla boğar.",
            hp = 3600,
            damage = 440,
            speed = 1.0f,
            attackInfo = "Veba Bombası: Çarptığı yerde zehirli sarı aralıklar bırakan asit sülfür kavanozları fırlatır.",
            superName = "Toksik Ölüm Bulutu",
            superDesc = "Büyük bir toksik duman bulutu yayar. Gazın içindeki rakiplerin canı hızla tükenir ve görüş alanları sıfırlanır.",
            ultimateName = "Gaz Odası (Forbidden Ultimate)",
            ultimateDesc = "Tüm haritaya yayılan aşırı konsantre gaz salgılar. Bu gaz kurbanlarda kusma ve yavaşlamayla birlikte her vuruş alanına kan sıçratır.",
            sounds = listOf("Boğuk ve mekanik maske nefesleri", "Kırılan cam deney tüpü şakırtıları", "Rakiplerin kusma ve boğulma hırıltıları"),
            animations = listOf("Saldırı: Kolunun altından cam kavanozlar fırlatması", "Ölüm: Maskesinin infilak edip içinden çıkan yeşil dumanda erimesi"),
            skins = listOf(
                BrawlerSkin("arax_default", "Veba Maskelisi", 0, false, "Klasik gagalı deri maske ve simsiyah muşamba pelerin."),
                BrawlerSkin("arax_quarantine", "Karantina Kasabı (Forbidden)", 150, true, "Deforme olmuş, çıplak sızdıran urlara sahip sadist bir cerrah görünümü."),
                BrawlerSkin("arax_steampunk", "Buharlı İşkenceci", 85, false, "Pirinç borular döşenmiş gaz maskesi ve mekanik asit tüfeği.")
            )
        ),
        Brawler(
            id = "nyx",
            name = "Nyx (Karanlığın Gölgesi)",
            rarity = Brawler.Rarity.DARK,
            backstory = "Hiç ışık almayan zindanların en dip hücresinde gölgelerle birleşerek hayatta kalmıştır. Bedenini tamamen gölgeye dönüştürüp rakiplerin arkasından fısıldayarak yaklaşan gizemli bir gölge suikastçısıdır.",
            hp = 3000,
            damage = 600,
            speed = 1.3f,
            attackInfo = "Gölge Hançer: İki hızlı gölge bıçağı fırlatır, düşmana arkadan vurursa iki kat hasar uygular.",
            superName = "Gölge Sisi",
            superDesc = "Anında görünmez olur ve hareket hızı %50 artar. İlk saldırısı düşmanı sersemletir (Taş keser).",
            ultimateName = "Karanlık Çöküş (Forbidden Ultimate)",
            ultimateDesc = "Rakiplerin görüşünü tamamen sıfırlayan mutlak karanlık yaratır ve kendisi bu sırada tüm hedeflere teleport olup onları tek tek boğazlar.",
            sounds = listOf("Karanlıkta yankılanan sinsi fısıltılar", "Hızlı hançer kesiş rüzgarları", "Korku dolu kalp atış ritimleri"),
            animations = listOf("Saldırı: Elindeki siyah pusların ileri doğru fırlaması", "Ölüm: Eriyip yerdeki basit bir karaltıya gölgeye dönüşmesi"),
            skins = listOf(
                BrawlerSkin("nyx_default", "Ölüm Sisi", 0, false, "Gözleri parıldayan karanlık ninja kıyafeti."),
                BrawlerSkin("nyx_demoness", "Siyah Şehvet Şeytanı (Forbidden)", 210, true, "Sadece parıldayan simli çıplak gölge çizgileri ve kırmızı kanatlara sahip form."),
                BrawlerSkin("nyx_assassin", "Teknolojik Suikastçı", 110, false, "Kırmızı neon hatları olan karbon fiber zırh.")
            )
        ),
        Brawler(
            id = "tormentor",
            name = "Tormentor (Zindan Gardiyanı)",
            rarity = Brawler.Rarity.LEGENDARY,
            backstory = "Acıyı hissetmeyen, aksine rakiplerine acı çektirdikçe ve kendisi darbe aldıkça zevk alan sadomazoşist bir zindancıdır. Elindeki dikenli ağır zincirlerle tüm arenayı bir işkence odasına çevirir.",
            hp = 4700,
            damage = 530,
            speed = 0.95f,
            attackInfo = "Dikenli Kırbaç: Elindeki dikenli zinciri ileri uzatarak vurur. Islak et koparma sesleri baki kalır.",
            superName = "Mazoşist Öfke",
            superDesc = "Zırhını kaldırır. 5 saniye boyunca aldığı tüm hasarların %50'sini rakiplerine yansıtır.",
            ultimateName = "İşkence Çarkı (Forbidden Ultimate)",
            ultimateDesc = "Rakipleri zincirleyip merkezindeki dönen dikenli çarklara çeker. Kıymaya dönüştüren gore parçalama efektleri eşlik eder.",
            sounds = listOf("Sert zincir kırbaçlama sesleri", "Mazoşist inilti ve acı dolu kahkahalar", "Kopan et parçalarının yere düşüş tıpırtıları"),
            animations = listOf("Saldırı: Dikenli zincirin halkalarının havada şaklaması", "Ölüm: Kendi zincirleri tarafından boğularak yere yığılması"),
            skins = listOf(
                BrawlerSkin("tormentor_default", "Prangalı Cellat", 0, false, "Dikenli tellerle kaplı göğüs kasları ve deri başlık."),
                BrawlerSkin("tormentor_bound", "Kölelik Efendisi (Forbidden)", 180, true, "Gore efektli ağır BDSM temaları, deri tasmalar ve çıplak kanlı deri detayları."),
                BrawlerSkin("tormentor_iron", "Demir Maskeli Sapık", 90, false, "Kafasında perçinlenmiş demir maske ve devasa dikenli gürz zinciri.")
            )
        ),
        Brawler(
            id = "zephyr",
            name = "Zephyr (Ruh Çalan)",
            rarity = Brawler.Rarity.EPIC,
            backstory = "Savaş alanlarındaki sahipsiz gladyatör ruhlarını emerek hayatta kalan bir hortlaktır. Arenadaki varlığı tamamen havada süzülen bir ruh fırtınası biçimindedir. Dokunduğu her can can kaybeder.",
            hp = 3300,
            damage = 460,
            speed = 1.1f,
            attackInfo = "Ruh Atışı: Düşmana çarptığında patlayan ve ruh gücünden beslenen yeşil tözler atar.",
            superName = "Hortlak Girdabı",
            superDesc = "Kendi etrafında hortlak rüzgarları yaratarak yakındaki düşmanların can barlarını emip kendi canını onarır.",
            ultimateName = "Ruh Emen Tufan (Forbidden Ultimate)",
            ultimateDesc = "Rakiplerinin can yüzdesinin yarısını doğrudan emerek onları halsiz ve sıfır hasar gücüyle savunmasız bırakır.",
            sounds = listOf("Uğuldayan ölüm fırtınası rüzgarı", "Hortlakların feryat mırıltıları", "Erimiş buz çatlamaları"),
            animations = listOf("Saldırı: Ellerinden yeşil dumanlar çıkararak ileri fırlatması", "Ölüm: Pelerininin tamamen sönüp yere düşmesi ve ruhunun göğe kaçması"),
            skins = listOf(
                BrawlerSkin("zephyr_default", "Ruhani Rehber", 0, false, "Yeşil tözler yayan yırtık gotik hayalet pelerini."),
                BrawlerSkin("zephyr_ghoul", "Mezarlık Şehveti (Forbidden)", 160, true, "Zombi anatomisine sahip, çıplak kemikli çürümüş dehşet hortlak beden."),
                BrawlerSkin("zephyr_spectre", "Yanan Ruh", 80, false, "Mavi alevlerle yanan simsiyah pelerinli kukuletalı form.")
            )
        ),
        Brawler(
            id = "ignis",
            name = "Ignis (Kül Rahibi)",
            rarity = Brawler.Rarity.COMMON,
            backstory = "Kutsal ateş tarikatında kendi bedenini feda eden çılgın bir rahiptir. Kendini her an alev alev yakarak rakiplerine doğru koşar. Ölüm onun için kutsal ateşe kavuşmaktır.",
            hp = 4200,
            damage = 510,
            speed = 1.05f,
            attackInfo = "Köz Saçma: İleri doğru yakıcı kül parçaları püskürtür. Bu küller hedefin üzerinde 2 saniye alev hasarı bırakır.",
            superName = "Sonsuz Alev",
            superDesc = "Bedenini alevlerle kaplar. Yürüdüğü her yerde rakipleri yakan kalıcı bir kül/ateş patikası bırakır; hızı %30 artar.",
            ultimateName = "İnfilak (Forbidden Ultimate)",
            ultimateDesc = "Kendini feda edercesine patlatır. Çevresindeki tüm rakipleri küle çeviren, kan ve et parçalarını sıçratan dev bir patlama yapar.",
            sounds = listOf("Çatırdayan odun ve et yanma sesleri", "Fanatik ve deli çığlıklar", "Dev patlama gümbürtüleri"),
            animations = listOf("Saldırı: Ellerini öne açarak kıvılcım dalgaları fışkırtması", "Ölüm: Tamamen kül haline gelip yere yığılması ve rüzgarda kıvılcımlarla uçması"),
            skins = listOf(
                BrawlerSkin("ignis_default", "Kendini Yakan", 0, false, "Yarı yanmış sargılar içinde, alevli gözlere sahip rahip."),
                BrawlerSkin("ignis_ash", "Sonsuz Azap (Forbidden)", 140, true, "Derisinin eriyip kemiklerinin göründüğü çıplak, aşırı gerçekçi alev şeytanı."),
                BrawlerSkin("ignis_pyro", "İtfaiyeci Sapık", 75, false, "Asit fışkırtan paslı sarı yanmaz elbise ve tıslayan tüp.")
            )
        ),
        Brawler(
            id = "hecate",
            name = "Hecate (Karanlık Cadı)",
            rarity = Brawler.Rarity.EPIC,
            backstory = "Şeytani gölge ayinlerinin kraliçesidir. Rakiplerinin zihnini zehirleyerek onları kendi yandaşlarını öldürmeye sevk eden tehlikeli kara büyüler kullanır. Karanlığın üç yüzünü temsil eder.",
            hp = 3000,
            damage = 550,
            speed = 1.0f,
            attackInfo = "Kara Büyü Cıvatası: Hedefe doğru karanlık gölge küresi fırlatır, düşmanları %15 zafiyete sokar.",
            superName = "Zihin Kontrolü",
            superDesc = "İsabet alan rakip 3 saniye boyunca kendi takım arkadaşlarına saldırmaya başlar. Solo modda ise rastgele duvarlara çarpar.",
            ultimateName = "Cadılar Gecesi (Forbidden Ultimate)",
            ultimateDesc = "Oluşturduğu devasa lanetli halka içindeki her rakibin can barını hızla azaltarak saniyede gore efektleriyle canlarını parçalar.",
            sounds = listOf("Mistik ve boğuk kazan kaynama sesleri", "Hecate'nin şeytani mırıltıları", "Ruhani tiz fısıltılar"),
            animations = listOf("Saldırı: Elindeki asadan mor mor kıvılcımlar yayması", "Ölüm: Bedeninin tamamen mor bir dumana dönüşüp havaya uçması"),
            skins = listOf(
                BrawlerSkin("hecate_default", "Yasak Ritüel", 0, false, "Mor pelerin, geniş sihirbaz şapkası ve asit yeşili asâ."),
                BrawlerSkin("hecate_ritual", "Karanlık Bakire (Forbidden)", 190, true, "Ritüel çıplaklığı ve kanla yıkanmış sargılar barındıran okült gore kostümü."),
                BrawlerSkin("hecate_goth", "Gothic Lolita", 90, false, "Siyah dantelli gotik elbise ve mor oyuncak bebek.")
            )
        )
    )

    // 10 Map concepts formatted for detailed design docs
    val maps = listOf(
        MapConcept(
            title = "Çöl Harabesi (Desert of the Damned)",
            lore = "Kavurucu rüzgarların kemikleri aşındırdığı, altında binlerce gladyatör cesedinin yattığı unutulmuş bir çöldür. Kum fırtınaları anlık olarak görüşü engeller.",
            dangerZones = "Kum Tuzakları: İçine giren brawlerlar yavaşlar ve çöl akrepleri tarafından ısırılarak zehirlenir.",
            primaryColorHex = "#D4A373",
            visualPrompt = "A cinematic retro 2D top-down view of a cursed gladiator arena, crumbling ancient ruins partially buried in blood-red sands, dynamic skull shrines in corners, dark metal chains connecting broken pillars."
        ),
        MapConcept(
            title = "Kanlı Arena (The Bloodpit)",
            lore = "Gladyatörlerin dövüştüğü, zemini sırılsıklam kırmızı kalın kan göletleriyle kaplı ana kolezyumdur. Her vuruşta kan sıçramaları zemini daha da boyar.",
            dangerZones = "Kan Gölleri: Bu sulu/kanlı alanlara giren brawlerlar kayar, hızları %20 artar ancak nişan almaları zorlaşır.",
            primaryColorHex = "#7A0010",
            visualPrompt = "Gothic brutalist colosseum arena from above, completely drenched in deep crimson blood lakes, rusty iron spikes lining the walls, dark metal grates glowing with hellfire underneath."
        ),
        MapConcept(
            title = "Terk Edilmiş Hastane (Asylum of Slaughter)",
            lore = "Lanetli ruhların ve akıl hastalarının çığlıklarının koridorlarında yankılandığı eski bir akıl hastanesi kalıntısıdır. Ortamda sürekli dumanlar ve sedyeler bulunur.",
            dangerZones = "Yasaklı Şırıngalar: Yere saçılmış zehirli şırıngalara basan her brawler rastgele yön sarsıntısı yaşar.",
            primaryColorHex = "#3F4E4F",
            visualPrompt = "A dirty abandoned psychiatric ward, cracked green tiles, pools of dark blood, decaying stretchers blocking paths, toxic green gas venting from rusty ceiling pipes."
        ),
        MapConcept(
            title = "Cehennem Kapısı (Gates of Gehenna)",
            lore = "Yeraltının en derin katmanı olan Gehenna kapısı önüdür. Duvarlardan ateş fışkırır ve lav nehirleri haritanın ortasından akar.",
            dangerZones = "Eriyen Magma: Haritanın ortasından geçen lav köprüleri çöktüğünde lav gölüne düşenler anında can verir.",
            primaryColorHex = "#FF3300",
            visualPrompt = "Grim dark fantasy underworld, massive black basalt gates etched with glowing red runes, rivers of molten lava bisecting the level, volcanic smoke, skulls embedded in molten earth."
        ),
        MapConcept(
            title = "Uzay İstasyonu Enkazı (Void Drifter)",
            lore = "Uzay boşluğunda sürüklenen, oksijeni tükenmekte olan terk edilmiş askeri araştırma üssüdür. Kozmik karanlık her yerdedir.",
            dangerZones = "Basınç Odası: Periyodik olarak açılan vakum kapıları yakındaki gladyatörleri boşluğa çekmeye çalışır.",
            primaryColorHex = "#0B132B",
            visualPrompt = "A dark sci-fi spaceship interior with breached corridors opening into deep space nebula, sparks flying from broken monitors, cold neon blue lights and hazard stripes."
        ),
        MapConcept(
            title = "Lav Gölü (Lava Crypt)",
            lore = "Eski lav ejderhalarının mezarı olan bu kript, kıpkırmızı erimiş lav fışkırmalarıyla tünelleri doldurur.",
            dangerZones = "Periyodik Alevler: Her 15 saniyede bir, zemindeki deliklerden alev fışkırarak kurbanları anında yakar.",
            primaryColorHex = "#E05A47",
            visualPrompt = "Underground obsidian cathedral filled with boiling red lava, crumbling stone platforms, gargoyles spitting fire, sulfur smoke rising."
        ),
        MapConcept(
            title = "Asit Bataklığı (Plague Mire)",
            lore = "Veba doktoru Arax'ın kimyasal atıklarını döktüğü, yeşil köpüklü asit bataklığı ile kaplı çürümüş bir ormandır.",
            dangerZones = "Asit Köpükleri: Bataklığa girenlerin can barı yavaşça erir ve zırhları kalıcı olarak %30 azalır.",
            primaryColorHex = "#2B4C3F",
            visualPrompt = "A putrid radioactive swamp with glowing neon-green acid pools, weeping willow trees draped in toxic mold, decay, bones floating on acidic slush."
        ),
        MapConcept(
            title = "Kozmik Boşluk (The Astral Abyss)",
            lore = "Yıldızların öldüğü, yerçekiminin büküldüğü ve zaman boyutunun akmadığı esrarengiz bir uçurumdur.",
            dangerZones = "Karadelikler: Haritanın merkezinde oluşan küçük çekim girdapları mermileri ve gladyatörleri içine büker.",
            primaryColorHex = "#1D0047",
            visualPrompt = "Eerie psychedelic abstract astral plane, vortexes of purple and magenta light, floating jagged asteroid platforms connected by magical neon bridges."
        ),
        MapConcept(
            title = "Antik Zindan (Catacombs of Torment)",
            lore = "İşkence gardiyanı Tormentor'un rütbe kazandığı kurban zincirleri, demir kafesler ve giyotinlerle dolu katakombtur.",
            dangerZones = "Giyotin Bıçakları: Düzenli sallanan dev giyotin bıçakları altından geçen gladyatörleri biçer.",
            primaryColorHex = "#1A1A1A",
            visualPrompt = "Subterranean medieval dungeon, brick walls with iron manacles, torture racks, swinging bloodstained pendulum blades, skeletons behind metal grates."
        ),
        MapConcept(
            title = "Lanetli Orman (Whispering Woods)",
            lore = "Ağaçların insan kurban ederek büyüdüğü kızıl yapraklı, sürekli fısıltılar fırlatan korkunç bir gölgelik mekandır.",
            dangerZones = "Lanetli Sarmaşıklar: Brawlerları yakalayarak 2 saniye kımıldatmayan gölge kökleri.",
            primaryColorHex = "#4A1C1C",
            visualPrompt = "Cursed forest at midnight, twisted black trees with faces carved in bark, glowing red sap running down trunks, fog settling over crimson leaves."
        )
    )
}
