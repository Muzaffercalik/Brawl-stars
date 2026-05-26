package com.example.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.GameViewModel

@Composable
fun DocsScreen(
    viewModel: GameViewModel,
    modifier: Modifier = Modifier
) {
    val stats by viewModel.playerStats.collectAsState()
    var selectedTopic by remember { mutableStateOf("Mekanikler") } // Mechanics, Maps, Economy, Unity, Prompts

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFF080707))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0C0C0C))
                    .border(BorderStroke(1.dp, Color(0xFF222222)))
                    .statusBarsPadding()
                    .padding(vertical = 12.dp, horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModel.navigateTo(GameViewModel.Screen.MainMenu) }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "DESIGNER DOCS & GUIDES",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        color = Color.White,
                        letterSpacing = (-0.5).sp,
                        style = androidx.compose.ui.text.TextStyle(
                            fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                        )
                    )
                    Text(
                        text = "Teknik döküman ve geliştirici rehberleri",
                        fontSize = 11.sp,
                        color = Color(0xFFFF0000).copy(alpha = 0.8f),
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                }
            }

            // Topics selector tabs
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0B0E))
                    .horizontalScroll(rememberScrollState())
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TopicTabButton(label = "Temel Mekanikler", active = selectedTopic == "Mekanikler", onClick = { selectedTopic = "Mekanikler" })
                TopicTabButton(label = "10 Harita Konsepti", active = selectedTopic == "Haritalar", onClick = { selectedTopic = "Haritalar" })
                TopicTabButton(label = "Ekonomi & Progression", active = selectedTopic == "Ekonomi", onClick = { selectedTopic = "Ekonomi" })
                TopicTabButton(label = "Unity Teknik Öneriler", active = selectedTopic == "Unity", onClick = { selectedTopic = "Unity" })
                TopicTabButton(label = "Midjourney Promptları", active = selectedTopic == "Promptlar", onClick = { selectedTopic = "Promptlar" })
            }

            // Scrollable Content
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                when (selectedTopic) {
                    "Mekanikler" -> MechanicsSection(viewModel, stats?.forbiddenModeUnlocked == true)
                    "Haritalar" -> MapsSection(viewModel)
                    "Ekonomi" -> EconomySection(viewModel)
                    "Unity" -> UnityTechnicalSection()
                    "Promptlar" -> MidjourneyPromptsSection()
                }

                Spacer(modifier = Modifier.height(48.dp))
            }
        }
    }
}

@Composable
fun TopicTabButton(
    label: String,
    active: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(6.dp))
            .background(if (active) Color(0xFFFF9900) else Color(0xFF131317))
            .border(1.dp, if (active) Color(0xFFFF9900) else Color(0xFF2C2C35), RoundedCornerShape(6.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = if (active) Color.Black else Color.White
        )
    }
}

@Composable
fun MechanicsSection(viewModel: GameViewModel, forbiddenUnlocked: Boolean) {
    Text(text = "TEMEL OYUN MEKANİKLERİ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF003C))
    Text(
        text = "Shadow Brawl, her biri karanlık ve lanetli gladyatörlerden oluşan 3v3 veya solo gladyatör arenalarına odaklanan, tamamen internet gerektirmeyen bir aksiyon brawler tasarımıdır. Tüm fizik ve rakipler yerel işlemci üzerinde çalışır.",
        fontSize = 12.sp,
        color = Color(0xFFE6E6EB),
        lineHeight = 18.sp
    )

    Spacer(modifier = Modifier.height(8.dp))

    DocSubCard(title = "1. Oyun Modları (3-5 Dakikalık Maçlar)") {
        Text(
            text = "• 3v3 Takımlı Savaş: Üçer gladyatörden oluşan iki takım, arenanın merkezindeki kan pınarlarından fışkıran Ruh Rezonanslarını toplamaya çalışır. En çok töz toplayan kazanır.\n" +
                    "• 1v1 Gladyatör Düellosu: Tamamen yeteneğe dayalı, tek turluk ve ragdoll ölümün sertçe sergilendiği arena düellosu.\n" +
                    "• Solo Deathmatch: En sona kalan hayatta kalır. Sınırlar zehir bulutlarıyla periyodik olarak daralır.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp
        )
    }

    DocSubCard(title = "2. Savaş ve Yetenek Çeşitliliği") {
        Text(
            text = "Her karakter standart yön kollarıyla hareket eder. Üç fonksiyonlu saldırı şemasına sahiptir:\n" +
                    "• Normal Vuruş: Karakterin asıl silah mermisi.\n" +
                    "• Süper Yetenek: Rakiplere vurdukça şarj olan büyük stratejik kitle kontrol gücü veya can çalma.\n" +
                    "• Forbidden Ultimate: En karanlık durumlarda açılan ekran temizleyici, aşırı vahşet veya hipnoz barındıran nihai büyü.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp
        )
    }

    DocSubCard(title = "3. Dinamik Vahşet / Gore Fizik Motoru") {
        Text(
            text = "• Ragdoll Ölüm Animasyonları: Karakterler son darbeyi aldığında önceden tasarlanmış animasyonlar yerine, aldıkları darbenin fiziksel yönüne göre kemik kırılmaları ve gerçekçi eklem sarsıntıları ile yere savrulurlar.\n" +
                    "• Kan Sıçramaları (Fluid Blooding): Saldırılar acts as a fluid emitter. Karakterlerin can barları azaldıkça üstlerinden ve aldıkları darbelerden zemine kalıcı kan sıçramaları gerçekleşir, böylece her maç sonunda harita tamamen kırmızıya boyanır.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp
        )
    }

    DocSubCard(title = "4. Forbidden Mode Yasaklı Katman") {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                if (forbiddenUnlocked) Icons.Default.CheckCircle else Icons.Default.Lock,
                contentDescription = "Status",
                tint = if (forbiddenUnlocked) Color(0xFF1DB954) else Color(0xFFB300FF)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(
                    text = if (forbiddenUnlocked) "DURUM: AKTİF VE SERBEST" else "DURUM: KİLİTLİ (MARKETTE AÇILABİLİR)",
                    fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.White
                )
                Text(
                    text = "Açıldığında tüm brawlers maksimum kudrete kavuşur, haritaların kan oranları %300 artar, brawler ses ve çığlıklarında sansürsüz acı çığlıkları aktif olur.",
                    fontSize = 11.sp, color = Color(0xFF8A8A93)
                )
            }
        }
    }
}

@Composable
fun MapsSection(viewModel: GameViewModel) {
    Text(text = "30+ PLANLANMIŞ ARENANIN İLK 10 KONSEPTİ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9900))
    Text(
        text = "Shadow Brawl haritaları sadece arka plandan ibaret değildir. Her harita dinamik tuzaklar, yıkılabilir duvarlar ve saniyede hasar veren kimyasal bölgeler barındırır.",
        fontSize = 12.sp,
        color = Color(0xFFE6E6EB)
    )

    Spacer(modifier = Modifier.height(8.dp))

    viewModel.staticMaps.forEachIndexed { idx, map ->
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
            border = BorderStroke(1.dp, Color(android.graphics.Color.parseColor(map.primaryColorHex)))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "${idx + 1}. ${map.title}", fontSize = 14.sp, fontWeight = FontWeight.Black, color = Color.White)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "EVREN HİKAYESİ: ${map.lore}", fontSize = 11.sp, color = Color(0xFF8A8A93))
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "ÇEVRE TEHLİKESİ: ${map.dangerZones}",
                    fontSize = 11.sp,
                    color = Color(0xFFFF5722),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun EconomySection(viewModel: GameViewModel) {
    val stats by viewModel.playerStats.collectAsState()

    Text(text = "PROGRESSION VE EKONOMİ VE SOUL SİSTEMİ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFB300FF))
    Text(
        text = "Shadow Brawl tamamen offline'dır ve P2W barındırmaz. Oyundaki her şey sadece oynayarak kazanılan Golden Souls (Altın) ve Shadow Essence ile yönetilir.",
        fontSize = 12.sp,
        color = Color(0xFFE6E6EB)
    )

    Spacer(modifier = Modifier.height(8.dp))

    // Match output table
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
        border = BorderStroke(1.dp, Color(0xFF2C2C35))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Maç Sonu Ödül Dağılım Tablosu", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "Durum", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                Text(text = "Altın Soul", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.weight(1f))
                Text(text = "Shadow Essence", fontSize = 11.sp, color = Color.Gray, modifier = Modifier.weight(1.5f))
            }
            Divider(color = Color(0xFF2C2C35), modifier = Modifier.padding(vertical = 4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "ZAFER (Win)", fontSize = 11.sp, color = Color(0xFF1DB954), modifier = Modifier.weight(1f))
                Text(text = "+150 Altın", fontSize = 11.sp, color = Color.White, modifier = Modifier.weight(1f))
                Text(text = "+20 Essence", fontSize = 11.sp, color = Color.White, modifier = Modifier.weight(1.5f))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(text = "MAĞLUBİYET", fontSize = 11.sp, color = Color(0xFFFF003C), modifier = Modifier.weight(1f))
                Text(text = "+50 Altın", fontSize = 11.sp, color = Color.White, modifier = Modifier.weight(1f))
                Text(text = "+5 Essence (+2 / Leş)", fontSize = 11.sp, color = Color.White, modifier = Modifier.weight(1.5f))
            }
        }
    }

    DocSubCard(title = "Karakter Seviye Yükseltme Maliyeti") {
        Text(
            text = "Karakterleri arenada daha dayanıklı ve hasar verici hale getirmek için Altın Souls basılır:\n" +
                    "• Seviye 1 -> 2: 400 Altın\n" +
                    "• Seviye 2 -> 3: 800 Altın\n" +
                    "• Seviye 3 -> 4: 1200 Altın\n" +
                    "• Seviye 4 -> 5: 1600 Altın\n" +
                    "Her seviye karakter sağlığını %10, hasarını ise %10 arttırır.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp
        )
    }

    DocSubCard(title = "Shadow Essence Mağazası Tarifesi") {
        Text(
            text = "Yapay zeka arenadan can alarak toplanan Shadow Essence ile gladyatörler açılır:\n" +
                    "• Common Kahraman: Başlangıçta serbest (Kassap, Kala, Ignis)\n" +
                    "• Rare Kahmanlar (Elena, Arax): 150 Essence\n" +
                    "• Epic Kahramanlar (Medusa, Slasher, Hecate, Zephyr): 300 Essence\n" +
                    "• Legendary Kahramanlar (Malakor, Bane, Tormentor): 500 Essence\n" +
                    "• Dark Kahramanlar (Lilith, Valeri, Nyx): 800 Essence\n\n" +
                    "Sıradışı kan efektli, dehşet temalı premium skinler ise 90 ila 220 Shadow Essence arasındadır.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 16.sp
        )
    }
}

@Composable
fun UnityTechnicalSection() {
    Text(text = "UNITY / ENGINE TEKNİK ÖNERİLER", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1DB954))
    Text(
        text = "Shadow Brawl'ı Unity veya Unreal Engine üzerinde tam bir mobil projesi olarak derleme aşamasına taşırsanız, profesyonel şu mimari yapılara sadık kalınmalıdır:",
        fontSize = 12.sp,
        color = Color(0xFFE6E6EB)
    )

    Spacer(modifier = Modifier.height(8.dp))

    DocSubCard(title = "1. Brawler Prefab Yapısı (OOP Sınıflandırma)") {
        Text(
            text = "Her brawler tek bir ana 'BaseBrawler.cs' scriptinden beslenmelidir. ScriptableObject kullanılarak her karakterin canı, hızı, hasarı ve yetenek mermisi prefabların içine asenkron inject edilmelidir. Animasyon triggerları tek bir Animator controller override ile yönetilebilir.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 15.sp
        )
    }

    DocSubCard(title = "2. Procedural Harita Oluşturucu (MapGenerator.cs)") {
        Text(
            text = "Kullanıcı sonsuz oynamak istediğinden haritayı kurgulamak için 2B Matris (Matrix Grid) tabanlı gürültü algoritması (Perlin Noise) kullanın. 0 olan hücreleri zemin, 1 olanlara yıkılabilir duvar, 2 olanlara tuzak (asit/çamur köpükleri) yerleştirin. Her rampa başında Unity NavMesh2D kullanarak AI yollarını o an generate ettirin.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 15.sp
        )
    }

    DocSubCard(title = "3. Shader Graph Tabanlı Gore ve Kan Boyama") {
        Text(
            text = "Yüzlerce kan parçacığını GameObject render olarak çizmek hantallık yaratır. Bunun yerine zemindeki harita kaplamasına (Terrain/Plane Mesh) ait bir 'SplatMap' mask kaplaması atayın. Mermi hedefi vurduğunda çarptığı nokta koordinatını Raycast vasıtasıyla shader'a gönderip SplatMap üzerinde dinamik olarak kırmızı fırça darbesi yaratın. Sıfır performans kaybıyla harita kıpkırmızı bir göle dönecektir.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 15.sp
        )
    }

    DocSubCard(title = "4. Yerel Yapay Zeka AI Durum Makinesi") {
        Text(
            text = "Tüm AI rakipler internet gerektirmeksizin yerel işlemcide 'Finite State Machine' (FSM) ile çalışmalıdır. Karakterlerin üç durumu bulunur:\n" +
                    "- DEVREYE GİRİŞ (Idle/Patrol): Rastgele harita tözü arama veya devriye gezme.\n" +
                    "- HÜCUM KOŞUSU (Chase): Bir düşman brawler algılama alanına (Detection Trigger) girdiğinde hedef seçip koşma.\n" +
                    "- SALDIRI (Attack): Menzile girene değin atış rotası çizmeyi sürdürme ve ateşe başlama.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 15.sp
        )
    }

    DocSubCard(title = "5. JSON Yerel Kaydetme (Local Serialization)") {
        Text(
            text = "Unity için 'PlayerPrefs' yerine şifrelenmiş JSON dosyası kullanın. Android yerel path'ine ('Application.persistentDataPath') kaydettiğiniz JSON dosyasını her zafer sonrası asenkron olarak kaydedin, böylece bağlantı olmasa da kullanıcının gladyatör kilitleri ve rütbeleri asla kaybolmaz.",
            fontSize = 11.sp, color = Color(0xFF8A8A93), lineHeight = 15.sp
        )
    }
}

@Composable
fun MidjourneyPromptsSection() {
    Text(text = "GÖRSEL ÜRETİMİ İÇİN MIDJOURNEY PROMPTLARI", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF9900))
    Text(
        text = "Shadow Brawl dünyasını görselleştirmek ve reklam/pazarlama materyalleri veya Unity UI mockup tasarımları oluşturmak için aşağıdaki Midjourney/DALL-E 3 komutlarını kullanabilirsiniz:",
        fontSize = 12.sp,
        color = Color(0xFFE6E6EB)
    )

    Spacer(modifier = Modifier.height(8.dp))

    DocSubCard(title = "Splash Screen (Brawler Karşılama Ekranı)") {
        Column {
            Text(
                text = "Prompt:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )
            Text(
                text = "Epic dark fantasy splash art for a gladiator action game named Shadow Brawl, a fearsome gothic vampire queen with glowing crimson eyes, wielding a giant blood-drenched scythe, beside a psycho butcher monster with a rusty cleaver mask, standing inside a brutalist colosseum filled with crimson lakes, dramatic spotlight, highly detailed, Unreal Engine 5 render style, 8k --ar 16:9 --v 6.0",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }

    DocSubCard(title = "2D Top-Down Retro Arena Sahneleri") {
        Column {
            Text(
                text = "Prompt:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )
            Text(
                text = "A cinematic retro 2D top-down isometric tactical video game screen, a cursed ancient temple gladiator arena, crumbling stone pillars connected by dark metal chains, glowing magma lava rivers flowing down the middle, blood splatters on the stone floor, dark ominous atmosphere, concept art --v 6.0",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }

    DocSubCard(title = "Vampir Kız Lilith 3D Konsept Tasarımı") {
        Column {
            Text(
                text = "Prompt:",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFF9900)
            )
            Text(
                text = "A detailed 3D game character portrait of Lilith from Shadow Brawl, a hauntingly beautiful vampire princess, gothic pale skin, glowing red eyes, black leather corsetry with red lace highlights, wings made of shadow mist, holding a crystal vial of crimson liquor, dark fantasy, highly polished rendering, game design asset --v 6.0 --style raw",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = Color.LightGray
            )
        }
    }
}

@Composable
fun DocSubCard(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF131317)),
        border = BorderStroke(1.dp, Color(0xFF2C2C35))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = Color.White)
            Spacer(modifier = Modifier.height(8.dp))
            content()
        }
    }
}
