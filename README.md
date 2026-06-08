# Blink - Minimalistische Social Media App 📱

**Blink** ist eine native Android-Applikation, entwickelt im Rahmen der Lehrveranstaltung **"Mobile Platforms" (Übung 4)** an der FH JOANNEUM.

Die App demonstriert die Umsetzung einer modernen Benutzeroberfläche mit **Jetpack Compose** und folgt den Prinzipien des **Material Design 3**.

---

## 📋 Projektbeschreibung

Die Aufgabe bestand darin, eine eigene App-Idee unter Verwendung von Android-Technologien (Java oder Kotlin) umzusetzen. **Blink** ist ein minimalistischer Social-Media-Client, der es Nutzern ermöglicht, Status-Updates zu verfassen und durch verschiedene Bereiche (Home, Search, Account) zu navigieren.

### Kern-Features
* **UI Toolkit**: Vollständig in **Jetpack Compose** geschrieben (kein XML).
* **Navigation**: Implementierung einer `BottomNavigationBar` und `TopAppBar` mittels `Scaffold`.
* **Interaktion**: `PostSender` Komponente zur Eingabe von Texten mit State-Management.
* **Design**: Nutzung von Material 3 Komponenten (`Surface`, `Cards`, `InputFields`) und dynamischem Theming.
* **State Management**: Persistierung von UI-Zuständen mittels `rememberSaveable`.

---

## 🛠 Tech-Stack & Anforderungen

Das Projekt erfüllt die definierten Anforderungen der Übung 4:

| Kategorie | Details | Status |
| :--- | :--- | :---: |
| **Sprache** | Kotlin | ✅ |
| **Framework** | Jetpack Compose | ✅ |
| **Min SDK** | API 29 (Android 10)+ | ✅ |
| **Target Device** | Pixel 8 Pro (1344x2992 px) | ✅ |
| **Architektur** | MVVM-Ansatz (View/Model Trennung) | ✅ |

### Projektstruktur
* `MainActivity.kt`: Einstiegspunkt & Scaffold-Setup.
* `PostSender.kt`: UI-Komponente zum Erstellen neuer Posts.
* `BottomLayout.kt`: Navigationsleiste Logik.
* `TopLayout.kt`: Kopfzeile der App.
* `PageLocation.kt`: Enum zur Verwaltung der Navigationsziele.
* `ui.theme`: Definition des Corporate Designs (BlinkTheme).

---

## 🚀 Installation & Ausführung

1.  **Repository klonen** oder ZIP entpacken.
2.  Projekt in **Android Studio** öffnen (`File -> Open...`).
3.  **Gradle Sync** abwarten.
4.  Einen Emulator erstellen (Empfohlen: **Pixel 8 Pro** mit API 35).
5.  App starten (`Run 'app'`).

---

## 📝 Hinweise zur Bewertung

* **Login/Datenbank**: Wie in der Aufgabenstellung vermerkt ("falls verfügbar"), wurde auf eine komplexe Backend-Anbindung oder Login-Screens verzichtet, um den Fokus auf eine saubere Compose-Implementierung und UI-Logik zu legen.
* **Dark Mode**: Die App nutzt Material 3 `ColorScheme`, wodurch sie grundsätzlich auch im Dark Mode lesbar bleibt, auch wenn kein explizites Dark-Theme gefordert war.

---

*Erstellt für den Kurs Mobile Platforms | FH JOANNEUM*
