<div align="center">

# چیستان · Chistan for Android

**برنامهٔ اندروید بازی جدول کلمات متقاطع فارسی و انگلیسی با ۹٬۹۹۹ مرحله، بدون نیاز به اینترنت**
**The Android app of the bilingual Persian and English crossword game, 9,999 levels, fully offline**

[فارسی](#fa) &nbsp;|&nbsp; [English](#en)

</div>

---

<a id="fa"></a>

<div dir="rtl">

# فارسی

این پروژه بازی «چیستان» را در قالب یک برنامهٔ اندروید بسته‌بندی می‌کند. تمام فایل‌های بازی داخل خود برنامه هستند و بازی بدون اینترنت اجرا می‌شود. برنامه هیچ مجوزی (permission) نمی‌خواهد و هیچ کتابخانهٔ خارجی ندارد: فقط یک `Activity` و یک `WebView` که بازی را نشان می‌دهد.

- **گوشی‌های پشتیبانی‌شده:** اندروید ۷٫۰ (API 24) و بالاتر
- **پیشرفت بازی** روی خود گوشی ذخیره می‌شود و با پاک کردن داده‌های برنامه یا حذف آن از بین می‌رود
- **زبان و پوسته** مثل نسخهٔ وب خودکار با زبان و حالت تاریک گوشی هماهنگ می‌شوند
- **دکمهٔ برگشت گوشی:** اول پنجرهٔ بازشده را می‌بندد، بعد از جدول به فهرست مرحله‌ها برمی‌گردد و در فهرست از برنامه خارج می‌شود

## گرفتن فایل APK

فایل APK همان فایل نصب برنامه است. سه راه دارید. راه ۱ بدون نصب هیچ برنامه‌ای روی رایانه جواب می‌دهد.

### راه ۱: ساخت خودکار با GitHub Actions (ساده‌ترین راه)

1. در GitHub یک مخزن (repository) تازه بسازید. خصوصی (Private) هم کافی است.
2. محتوای همین پوشه را در آن بارگذاری کنید. با git:

   ```bash
   git init -b main
   git add .
   git commit -m "Chistan Android app"
   git remote add origin https://github.com/USERNAME/REPO.git
   git push -u origin main
   ```

   اگر از بارگذاری در سایت GitHub استفاده می‌کنید، مطمئن شوید پوشهٔ `.github` هم بالا رفته است. اگر نرفت، در سایت «Add file ← Create new file» را بزنید، نام فایل را `.github/workflows/android.yml` بگذارید و محتوای همان فایل را در آن بچسبانید.
3. به تب **Actions** بروید. با هر push خودکار ساخته می‌شود، یا گردش‌کار «Build Android APK» را باز کنید و **Run workflow** را بزنید.
4. بعد از چند دقیقه (معمولاً ۵ تا ۱۰ دقیقه) اجرا تمام می‌شود. صفحهٔ آن اجرا را باز کنید، در بخش **Artifacts** فایل `Chistan-apk` را دانلود کنید و از حالت زیپ دربیاورید:
   - `Chistan.apk` نسخهٔ اصلی برای نصب
   - `Chistan-debug.apk` نسخهٔ آزمایشی

اگر یک برچسب (tag) مثل `v1.0.0` بزنید، همین دو فایل به صفحهٔ **Releases** مخزن هم پیوست می‌شوند.

### راه ۲: Android Studio

1. Android Studio را نصب و باز کنید و **Open** را بزنید و همین پوشه را انتخاب کنید.
2. صبر کنید همگام‌سازی Gradle تمام شود. اگر بسته‌های SDK را پیشنهاد داد (SDK 34)، نصب‌شان کنید.
3. از منو **Build ← Build Bundle(s) / APK(s) ← Build APK(s)** را بزنید. بعد از ساخت، روی **locate** در پیام پایین صفحه بزنید.

فایل در `app/build/outputs/apk/debug/app-debug.apk` است. اگر Android Studio پیشنهاد به‌روزرسانی Android Gradle Plugin داد، می‌توانید قبول کنید.

### راه ۳: خط فرمان

به JDK 17 یا بالاتر و Android SDK نیاز دارید (متغیر `ANDROID_HOME` یا فایل `local.properties` با خط `sdk.dir=/path/to/Android/Sdk`).

```bash
./gradlew assembleDebug      # خروجی: app/build/outputs/apk/debug/app-debug.apk
./gradlew assembleRelease    # خروجی: app/build/outputs/apk/release/app-release.apk
```

در ویندوز به‌جای `./gradlew` بنویسید `gradlew.bat`.

## نصب روی گوشی

1. فایل APK را به گوشی منتقل کنید (کابل، تلگرام، ایمیل یا هر روش دیگر).
2. روی فایل بزنید. اگر اندروید گفت نصب از این منبع مجاز نیست، در همان پنجره **Settings** را بزنید و برای همان برنامه (مرورگر یا مدیر فایل) گزینهٔ **Allow from this source** را روشن کنید و برگردید.
3. **Install** را بزنید. اگر Google Play Protect هشدار داد که برنامه از فروشگاه نیست، **Install anyway** را بزنید، چون این برنامه را خودتان ساخته‌اید.

یا با کابل و `adb`:

```bash
adb install -r Chistan.apk
```

## امضای نسخهٔ اصلی با کلید خودتان (الزامی برای کافه‌بازار)

نسخهٔ اصلی (`Chistan.apk`) هرگز با کلید debug امضا نمی‌شود، چون کافه‌بازار چنین فایلی را نمی‌پذیرد. بدون کلید، ساخت release با خطا متوقف می‌شود (`assembleDebug` همچنان کار می‌کند). کلید را این‌طور بسازید:

```bash
keytool -genkeypair -v -keystore chistan.jks -alias chistan \
        -keyalg RSA -keysize 2048 -validity 10000
```

برای ساخت روی رایانه این متغیرهای محیطی را بگذارید و `./gradlew assembleRelease` را بزنید:

```
CHISTAN_KEYSTORE_FILE=/path/to/chistan.jks
CHISTAN_KEYSTORE_PASSWORD=...
CHISTAN_KEY_ALIAS=chistan
CHISTAN_KEY_PASSWORD=...
```

برای GitHub Actions در **Settings ← Secrets and variables ← Actions** چهار Secret بسازید: `KEYSTORE_BASE64` (خروجی `base64 -w0 chistan.jks`)، `KEYSTORE_PASSWORD`، `KEY_ALIAS` و `KEY_PASSWORD`.

**مهم:** فایل `chistan.jks` و رمزهایش را نگه دارید و در مخزن قرار ندهید. اندروید نسخهٔ جدید را فقط روی نسخه‌ای نصب می‌کند که با همان کلید امضا شده باشد. اگر کلید را عوض کنید یا گم کنید، باید برنامهٔ قبلی را حذف کنید و پیشرفت بازی از بین می‌رود.

## به‌روزرسانی بازی داخل برنامه

بازی در `app/src/main/assets/www` است. اگر نسخهٔ وب را تغییر دادید (پوشهٔ `chistan-crossword`):

```bash
node tools/sync-web.js /path/to/chistan-crossword
```

این دستور فقط فایل‌های لازم (`index.html`، `css`، `js`، `fonts`) را کپی می‌کند. بعد در `app/build.gradle` مقدار `versionCode` را یکی زیاد کنید (و در صورت تمایل `versionName` را) و دوباره APK بسازید. با نصب APK با `versionCode` بالاتر روی نسخهٔ قبلی، پیشرفت بازی حفظ می‌شود (اگر با همان کلید امضا شده باشد).

## ساختار پروژه

```
chistan-android/
├── app/
│   ├── build.gradle                         تنظیمات برنامه (نسخه، SDK، امضا)
│   └── src/main/
│       ├── AndroidManifest.xml              بدون هیچ مجوزی
│       ├── java/app/chistan/crossword/
│       │   └── MainActivity.java            WebView، سرو فایل‌ها، دکمهٔ برگشت
│       ├── assets/www/                      خود بازی (HTML, CSS, JS, فونت‌ها)
│       └── res/                             آیکون، رنگ‌ها، پوسته (روشن و تیره)، نام برنامه
├── design/                                  فایل SVG آیکون
├── tools/sync-web.js                        کپی بازی وب داخل برنامه
├── .github/workflows/android.yml            ساخت خودکار APK
├── build.gradle, settings.gradle, gradle.properties
└── gradlew, gradlew.bat, gradle/wrapper/    Gradle Wrapper (نسخهٔ ۸٫۹)
```

## نحوهٔ کار

`MainActivity` یک `WebView` می‌سازد و صفحهٔ `https://appassets.androidplatform.net/index.html` را باز می‌کند. این نشانی فقط داخل برنامه معنا دارد: همهٔ درخواست‌ها به آن را برنامه می‌گیرد و فایل مربوط را از `assets/www` برمی‌گرداند، پس چیزی از شبکه نمی‌آید. هر درخواست به جای دیگری با خطای ۴۰۳ رد می‌شود. استفاده از یک نشانی https واقعی به‌جای `file://` باعث می‌شود `localStorage` (جایی که بازی پیشرفت شما را نگه می‌دارد) و بارگذاری فونت‌ها مثل یک وب‌سایت معمولی کار کنند.

## نسخه‌های تنظیم‌شده

Android Gradle Plugin 8.7.3، Gradle 8.9، JDK 17، `compileSdk` و `targetSdk` برابر 34، `minSdk` برابر 24. آیدی برنامه `app.chistan.crossword` است. اگر بخواهید آن را عوض کنید، `applicationId` و `namespace` را در `app/build.gradle` و پوشهٔ `java/app/chistan/crossword` را با هم تغییر دهید.

## عیب‌یابی

| مشکل | راه حل |
|---|---|
| `SDK location not found` | فایل `local.properties` بسازید با خط `sdk.dir=/path/to/Android/Sdk` یا `ANDROID_HOME` را تنظیم کنید |
| `Unsupported class file major version` یا خطای نسخهٔ Java | از JDK 17 (یا نسخهٔ جدیدتری که Android Studio همراه دارد) استفاده کنید |
| `Permission denied` برای `gradlew` | `chmod +x gradlew` را بزنید (در GitHub Actions خودکار انجام می‌شود) |
| «App not installed» هنگام نصب | نسخهٔ قبلی با کلید دیگری امضا شده. آن را حذف کنید و دوباره نصب کنید (پیشرفت بازی پاک می‌شود) |
| صفحهٔ سفید یا بازی بالا نمی‌آید | **Android System WebView** (و Chrome) را از Google Play به‌روز کنید |
| خطای Gradle دربارهٔ نسخهٔ AGP در Android Studio جدید | از پیشنهاد به‌روزرسانی (AGP Upgrade Assistant) استفاده کنید |

## مجوز

MIT. فونت‌های Vazirmatn و Lalezar زیر مجوز SIL OFL هستند و متن مجوزشان در `app/src/main/assets/www/fonts` است.

</div>

---

<a id="en"></a>

# English

This project packages the Chistan crossword game as an Android app. All the game files are inside the app, so it runs without internet. It asks for no permissions and has no third-party libraries: just one `Activity` and one `WebView` that shows the game.

- **Supported phones:** Android 7.0 (API 24) and newer
- **Progress** is stored on the phone and is lost if you clear the app's data or uninstall it
- **Language and theme** follow the phone's language and dark mode automatically, like the web version
- **The phone's Back button** first closes an open dialog, then leaves a puzzle for the level list, and exits the app from the list

## Getting the APK

An APK is the install file of an Android app. There are three ways to get one. Way 1 needs nothing installed on your computer.

### Way 1: let GitHub build it (easiest)

1. Create a new repository on GitHub. A private one is fine.
2. Upload the contents of this folder. With git:

   ```bash
   git init -b main
   git add .
   git commit -m "Chistan Android app"
   git remote add origin https://github.com/USERNAME/REPO.git
   git push -u origin main
   ```

   If you upload in the GitHub website instead, check that the `.github` folder went up too. If it did not, choose "Add file → Create new file", name it `.github/workflows/android.yml` and paste the contents of that file.
3. Open the **Actions** tab. It builds on every push, or you can open the "Build Android APK" workflow and press **Run workflow**.
4. After a few minutes (usually 5 to 10) the run finishes. Open that run, download `Chistan-apk` under **Artifacts**, and unzip it:
   - `Chistan.apk`, the main build to install
   - `Chistan-debug.apk`, the debug build

If you push a tag such as `v1.0.0`, the same two files are also attached to the repository's **Releases** page.

### Way 2: Android Studio

1. Install and open Android Studio, press **Open**, and choose this folder.
2. Wait for the Gradle sync to finish. If it offers to install SDK packages (SDK 34), accept.
3. Choose **Build → Build Bundle(s) / APK(s) → Build APK(s)**. When it finishes, click **locate** in the message at the bottom.

The file is `app/build/outputs/apk/debug/app-debug.apk`. If Android Studio offers to upgrade the Android Gradle Plugin, you can accept.

### Way 3: command line

You need JDK 17 or newer and the Android SDK (the `ANDROID_HOME` variable, or a `local.properties` file with the line `sdk.dir=/path/to/Android/Sdk`).

```bash
./gradlew assembleDebug      # output: app/build/outputs/apk/debug/app-debug.apk
./gradlew assembleRelease    # output: app/build/outputs/apk/release/app-release.apk
```

On Windows use `gradlew.bat` instead of `./gradlew`.

## Installing on a phone

1. Copy the APK to the phone (cable, messaging app, email, anything).
2. Tap the file. If Android says installing from this source is not allowed, tap **Settings** in that dialog, switch on **Allow from this source** for that app (your browser or file manager), and go back.
3. Tap **Install**. If Google Play Protect warns that the app is not from the store, tap **Install anyway**, since you built it yourself.

Or with a cable and `adb`:

```bash
adb install -r Chistan.apk
```

## Signing the main build with your own key (required for Cafe Bazaar)

The main build (`Chistan.apk`) is never signed with the debug key, because Cafe Bazaar rejects debug-signed packages. Without a key the release build stops with an error (`assembleDebug` still works). Create a key:

```bash
keytool -genkeypair -v -keystore chistan.jks -alias chistan \
        -keyalg RSA -keysize 2048 -validity 10000
```

To build on your computer, set these environment variables and run `./gradlew assembleRelease`:

```
CHISTAN_KEYSTORE_FILE=/path/to/chistan.jks
CHISTAN_KEYSTORE_PASSWORD=...
CHISTAN_KEY_ALIAS=chistan
CHISTAN_KEY_PASSWORD=...
```

For GitHub Actions, create four secrets under **Settings → Secrets and variables → Actions**: `KEYSTORE_BASE64` (the output of `base64 -w0 chistan.jks`), `KEYSTORE_PASSWORD`, `KEY_ALIAS` and `KEY_PASSWORD`.

**Important:** keep `chistan.jks` and its passwords safe and out of the repository. Android only installs an update over a version signed with the same key. If you change or lose the key, you have to uninstall the old app first, and the game progress is lost.

## Updating the game inside the app

The game lives in `app/src/main/assets/www`. If you change the web version (the `chistan-crossword` folder):

```bash
node tools/sync-web.js /path/to/chistan-crossword
```

This copies only the files the app needs (`index.html`, `css`, `js`, `fonts`). Then raise `versionCode` by one in `app/build.gradle` (and `versionName` if you like) and build the APK again. Installing an APK with a higher `versionCode` over the old one keeps the game progress, as long as it is signed with the same key.

## Project layout

```
chistan-android/
├── app/
│   ├── build.gradle                         app settings (version, SDK, signing)
│   └── src/main/
│       ├── AndroidManifest.xml              no permissions
│       ├── java/app/chistan/crossword/
│       │   └── MainActivity.java            WebView, file serving, Back button
│       ├── assets/www/                      the game itself (HTML, CSS, JS, fonts)
│       └── res/                             icon, colours, themes (light and dark), app name
├── design/                                  icon SVG sources
├── tools/sync-web.js                        copies the web game into the app
├── .github/workflows/android.yml            automatic APK build
├── build.gradle, settings.gradle, gradle.properties
└── gradlew, gradlew.bat, gradle/wrapper/    Gradle Wrapper (version 8.9)
```

## How it works

`MainActivity` creates a `WebView` and opens `https://appassets.androidplatform.net/index.html`. That address only exists inside the app: the app answers every request to it by returning the matching file from `assets/www`, so nothing comes from the network. A request to any other address is refused with a 403. Using a real https address instead of `file://` lets `localStorage` (where the game keeps your progress) and font loading behave like on a normal website.

## Configured versions

Android Gradle Plugin 8.7.3, Gradle 8.9, JDK 17, `compileSdk` and `targetSdk` 34, `minSdk` 24. The app id is `app.chistan.crossword`. To change it, change `applicationId` and `namespace` in `app/build.gradle` together with the `java/app/chistan/crossword` folder.

## Troubleshooting

| Problem | Fix |
|---|---|
| `SDK location not found` | Create `local.properties` with the line `sdk.dir=/path/to/Android/Sdk`, or set `ANDROID_HOME` |
| `Unsupported class file major version` or a Java version error | Use JDK 17 (or the newer one bundled with Android Studio) |
| `Permission denied` for `gradlew` | Run `chmod +x gradlew` (GitHub Actions does it automatically) |
| "App not installed" when installing | The old version was signed with a different key. Uninstall it and install again (game progress is lost) |
| Blank page or the game does not start | Update **Android System WebView** (and Chrome) from Google Play |
| Gradle complains about the AGP version in a newer Android Studio | Use the suggested AGP Upgrade Assistant |

## License

MIT. The Vazirmatn and Lalezar fonts are under the SIL OFL, and their license texts are in `app/src/main/assets/www/fonts`.
