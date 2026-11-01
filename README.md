# Learning Platform - клієнт для Android і Desktop (Kotlin Multiplatform)

Клієнт навчальної платформи: студент переглядає каталог курсів, записується на курс і слідкує за статусом запису. Дані надає серверний репозиторій `dotnet-learning-platform`; власної бази даних клієнт не має.

UI і логіка написані один раз на Kotlin Multiplatform (Compose Multiplatform) і працюють на Android і Desktop. Різні лише точки входу і кілька платформних функцій.

На цьому етапі виконана лабораторна 5: Navigation 3, Koin, MVI для онбордингу, екран входу, перехід онбординг → авторизація → головний екран і новий візуальний стиль. Мережевого шару, каталогу й екранів навчання ще немає.

## Структура

```text
/
├── androidApp/    точка входу Android
├── desktopApp/    точка входу Desktop
├── docs/design/
│   ├── v2/        стиль продукту, який застосований у застосунку
│   └── target/    цільовий макет повного студентського застосунку
├── shared/
│   └── src/
│       ├── commonMain/
│       │   ├── composeResources/  рядки uk/en і шрифти Inter
│       │   └── kotlin/.../
│       │       ├── di/            Koin-модуль
│       │       ├── domain/        репозиторії та use cases
│       │       ├── settings/      multiplatform-settings
│       │       ├── theme/         кольори, типографіка, MaterialTheme
│       │       └── ui/            онбординг, вхід, налаштування
│       ├── androidMain/  actual-реалізації для Android
│       └── jvmMain/      actual-реалізації для Desktop
└── gradle/        версії залежностей
```

`androidApp` і `desktopApp` містять лише точки входу. Екрани, навігація, DI і логіка лежать у `shared`.

## Що вже є

- Navigation 3: `NavDisplay` і `NavBackStack` на KMP-бібліотеці з AndroidX runtime. Стартовий екран залежить від прапорця онбордингу.
- Koin: `di/AppModule.kt`, `KoinApplication` у `App.kt`, ViewModel-и на кожен NavEntry через `lifecycle-viewmodel-navigation3`.
- MVI онбордингу: `OnboardingContract`, `OnboardingViewModel`, `CompleteOnboardingUseCase`, `SettingsOnboardingRepository`. Чотири слайди: каталог, запис і оплата, навчання, тести й сертифікат.
- Екран входу: MVI, валідація імені користувача і пароля, показ пароля, клавіатурні дії Next і Done. Авторизація локальна: `FakeAuthRepository` приймає будь-які коректні дані, бо серверного auth API ще немає.
- Головний екран як налаштування: тема, мова, повторний онбординг, вихід. Стан зберігається через multiplatform-settings.
- Новий стиль: зелена палітра, картки з радіусом 16, типографіка Inter, сегментовані перемикачі, онбординг з ілюстраціями. Світла, темна і системна тема; системна бере з платформи тільки ознаку «темно».
- Локалізація українською та англійською без перезапуску застосунку.
- Логування через kermit з тегом `LearningPlatform`.
- Тести: MVI онбордингу, валідація входу, стартовий екран, Koin-граф, парсер кольорів KDE.

Динамічні кольори Material You і палітра KDE у темі більше не застосовуються. Код парсера KDE лишився для визначення системної темної теми і тестів; кольори завжди беруться з палітри застосунку.

## Запуск

```bash
./gradlew :androidApp:assembleDebug
./gradlew :desktopApp:run
./gradlew :shared:jvmTest
```

Desktop-пакет збирається у `.deb`, `.msi` або `.dmg` через `nativeDistributions`; головний клас `rommmmaha.learning_platform_client.MainKt`.

## Межі

Каталог, запис, оплата, навчання, тести й сертифікати описані в `docs/design/target`, але не реалізовані. Для них потрібен HTTP-клієнт, DTO, репозиторії та серверні контракти. Екран входу не звертається до сервера.

## Відповідність лабораторній 5

| Вимога | Де в коді |
| --- | --- |
| Navigation 3 KMP + Android | `App.kt`, `gradle/libs.versions.toml` |
| Koin | `di/AppModule.kt`, `App.kt` |
| multiplatform-settings | `settings/UserSettings.kt`, `domain/onboarding/Onboarding.kt` |
| MVI онбордингу і use case | `ui/onboarding/`, `domain/onboarding/` |
| Екран авторизації | `ui/auth/AuthScreen.kt` |
| Перехід онбординг → авторизація | `App.kt` |
| ViewModel і use case перевірки онбордингу | `ui/main/MainViewModel.kt`, `IsOnboardingCompletedUseCase` |
| Валідація і клавіатура | `ui/auth/AuthViewModel.kt`, `ui/auth/AuthScreen.kt` |
