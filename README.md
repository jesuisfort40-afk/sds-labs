# 📱 SDS Labs — PHP Mastery (Android)

Application Android complète de la plateforme **SDS Labs PHP Mastery**, convertie depuis le code HTML/JS original avec tous les bugs corrigés.

---

## 🐛 Bugs corrigés

| Bug original (HTML) | Correction Android |
|---|---|
| `navClick(document.querySelectorAll('.nav-tab')[3],'terminal')` dans `renderResult()` — référence JS cassée | Utilisation de `navigateTo(R.id.nav_terminal)` propre |
| `loadLesson(2)` appelé avant que le DOM soit prêt | Chargement dans `onViewCreated()` après inflation |
| Variable `$x` non initialisée dans do...while de la leçon | Corrigé dans le contenu HTML de la leçon |
| Simulateur PHP trop basique, ne gérait pas les sorties réelles | Simulateur Kotlin complet avec regex et patterns |
| Section `cours` non activée correctement depuis `openLesson()` | Navigation via Fragment Manager propre |
| Leçon courante non sauvegardée entre sessions | Persistance via `SharedPreferences` |

---

## 🏗️ Structure du projet

```
SDSLabs/
├── .github/
│   └── workflows/
│       └── build_apk.yml          ← Build automatique GitHub Actions
├── app/
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/sdslabs/phpmastery/
│       │   ├── SplashActivity.kt
│       │   ├── MainActivity.kt
│       │   ├── model/
│       │   │   ├── LessonData.kt  ← Tout le contenu (leçons, quiz, défis)
│       │   │   └── ChatMessage.kt
│       │   ├── ui/
│       │   │   ├── home/HomeFragment.kt
│       │   │   ├── cours/CoursFragment.kt
│       │   │   ├── quiz/QuizFragment.kt
│       │   │   ├── terminal/TerminalFragment.kt
│       │   │   └── ai/AiMentorFragment.kt + ChatAdapter.kt
│       │   └── util/XPManager.kt  ← Système XP persistant
│       └── res/
│           ├── layout/            ← Tous les layouts XML
│           ├── drawable/          ← Shapes, backgrounds, icônes
│           ├── menu/              ← Navigation bottom bar
│           ├── anim/              ← Transitions
│           └── values/            ← Colors, strings, themes
└── build.gradle
```

---

## 🚀 Build via GitHub Actions (recommandé)

### Étape 1 — Pousser sur GitHub

```bash
cd SDSLabs
git init
git add .
git commit -m "Initial commit - SDS Labs PHP Mastery Android"
git branch -M main
git remote add origin https://github.com/VOTRE_USERNAME/sds-labs-php-mastery.git
git push -u origin main
```

### Étape 2 — Télécharger l'APK

1. Allez sur votre repo GitHub → onglet **Actions**
2. Cliquez sur le workflow **Build APK** en cours
3. Une fois terminé, téléchargez l'artifact **SDS-Labs-PHP-Mastery-debug**
4. Installez `app-debug.apk` sur votre téléphone

> ✅ Le workflow tourne automatiquement à chaque `git push`.

---

## 🛠️ Build local (optionnel)

### Prérequis
- Android Studio Hedgehog (2023.1.1) ou plus récent
- JDK 17
- Android SDK API 34

### Commandes

```bash
# Debug APK
./gradlew assembleDebug

# APK disponible à :
# app/build/outputs/apk/debug/app-debug.apk

# Installer directement sur téléphone connecté
./gradlew installDebug
```

---

## ⚙️ Configuration API Claude (AI Mentor)

L'AI Mentor utilise l'API Anthropic. Pour l'activer :

1. Obtenez votre clé sur [console.anthropic.com](https://console.anthropic.com)
2. Dans `AiMentorFragment.kt`, l'API est appelée directement
3. Pour production, ajoutez la clé dans `local.properties` :

```properties
ANTHROPIC_API_KEY=sk-ant-...
```

Puis dans `build.gradle` :
```gradle
buildConfigField "String", "API_KEY", "\"${localProperties['ANTHROPIC_API_KEY']}\""
```

---

## 📱 Fonctionnalités

| Écran | Description |
|---|---|
| 🏠 **Accueil** | Dashboard avec stats XP, progression, 8 modules |
| 📚 **Cours** | 5 leçons Module 03 avec code coloré via WebView |
| 🧠 **Quiz** | 6 questions adaptatives avec feedback et XP |
| ⚡ **Terminal** | Éditeur PHP + simulateur + 6 défis pratiques |
| 🤖 **AI Mentor** | Chat avec Claude Sonnet pour questions PHP |

---

## 🎨 Design

Thème sombre fidèle à l'original HTML :
- Fond : `#080c14`
- Accent : `#00d4ff` (cyan)  
- Accent2 : `#7c3aed` (violet)
- Accent3 : `#10b981` (vert)

---

*SDS Labs PHP Mastery — v2.0 Édition Premium*
