# Learning Platform - клієнт для Android і Desktop (Kotlin Multiplatform)

Клієнт навчальної платформи: студент переглядає каталог курсів, записується на курс і слідкує за статусом запису. Дані надає серверний репозиторій `dotnet-learning-platform`; власної бази даних клієнт не має.

UI і логіка написані один раз на Kotlin Multiplatform (Compose Multiplatform) і працюють на Android і Desktop. Різні лише точки входу і кілька платформних функцій.

На цьому етапі виконана лабораторна 6: мережевий стек авторизації на Ktor, безпечне зберігання токенів на KSafe, автоматичне оновлення токенів, MVI-екран входу зі снекбаром і навігація за ознакою авторизованого користувача.

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
│       │       ├── data/          Ktor-мережа, DTO, сховище токенів
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

- Navigation 3: `NavDisplay` і `NavBackStack` на KMP-бібліотеці з AndroidX runtime. Стартовий екран залежить від прапорця онбордингу і стану авторизації: онбординг, вхід або головний екран.
- Koin: `di/AppModule.kt`, `KoinApplication` у `App.kt`, ViewModel-и на кожен NavEntry через `lifecycle-viewmodel-navigation3`.
- MVI онбордингу: `OnboardingContract`, `OnboardingViewModel`, `CompleteOnboardingUseCase`, `SettingsOnboardingRepository`. Чотири слайди: каталог, запис і оплата, навчання, тести й сертифікат.
- Екран входу: MVI з ефектами, валідація пошти і пароля, показ пароля, клавіатурні дії Next і Done. Помилки мережі й логіну показуються снекбаром через `AuthEffect.ShowError`.
- Мережевий стек: `data/network/AuthApi` з DTO `LoginRequest`, `RefreshRequest`, `AuthResponse`, `ProblemDetails`, `KtorAuthApi` на `HttpClient` з `ContentNegotiation` і `Logging`. Базовий URL: `https://learning-platform-mock.rommmmaha.workers.dev`.
- Авторизований клієнт: `HttpClient` з плагіном `Auth` і Bearer-токенами. При `401` клієнт викликає `POST /api/auth/refresh` і повторює запит.
- Токени: сесія з `accessToken`, `refreshToken` і часом закінчення лежить у `TokenStore` на KSafe. Android: `KSafe(context)` з Keystore, Desktop: `KSafe()` на JVM.
- Автооновлення: `NetworkAuthRepository.refreshIfNeeded()` перевіряє термін дії перед відображенням і оновлює сесію через `refresh`. Помилка оновлення очищає токени і повертає застосунок на екран входу.
- Головний екран як налаштування: тема, мова, повторний онбординг, вихід. Вихід очищає токени через `SignOutUseCase` і веде на екран входу. Стан зберігається через multiplatform-settings.
- Новий стиль: зелена палітра, картки з радіусом 16, типографіка Inter, сегментовані перемикачі, онбординг з ілюстраціями. Світла, темна і системна тема; системну ознаку «темно» дає `isSystemInDarkTheme()`.
- Іконка застосунку: біла «L» на зеленій плитці; адаптивна іконка Android з монохромним шаром для Themed icons.
- Локалізація українською та англійською без перезапуску застосунку.
- Логування через kermit з тегом `LearningPlatform` і Ktor `Logging`.
- Тести: MVI онбордингу, валідація входу, ефекти снекбару, оновлення сесії, стартовий екран, Koin-граф.

Динамічні кольори Material You і палітра KDE у темі не застосовуються: кольори завжди беруться з палітри застосунку.

## Запуск

Застосунок працює з деплоєним моком `https://learning-platform-mock.rommmmaha.workers.dev`. Локальний мок з `.docs/mock` для запуску не потрібен.

```bash
./gradlew :androidApp:assembleDebug
./gradlew :desktopApp:run
./gradlew :shared:jvmTest
```

Тестові дані мока: `student1@example.com`, пароль `learning2026`.

Desktop-пакет збирається у `.deb`, `.msi` або `.dmg` через `nativeDistributions`; головний клас `rommmmaha.learning_platform_client.MainKt`.

## Межі

Каталог, запис, оплата, навчання, тести й сертифікати описані в `docs/design/target`, але не реалізовані. Авторизований `HttpClient` зареєстрований у Koin-графі і готовий до цих екранів, але Bearer-запитів поза `/api/auth` клієнт поки не робить.

## Відповідність лабораторній 6

| Вимога | Де в коді |
| --- | --- |
| Ktor | `gradle/libs.versions.toml`, `data/network/ApiClient.kt`, `data/network/AuthApi.kt` |
| KSafe | `data/auth/TokenStore.kt`, `App.kt`, точки входу |
| Мережевий стек авторизації | `data/network/`, `domain/auth/Auth.kt` |
| Токени в безпечному сховищі | `data/auth/Session.kt`, `data/auth/TokenStore.kt` |
| Автооновлення токенів | `Auth` плагін у `ApiClient.kt`, `refreshIfNeeded()` у `NetworkAuthRepository`, запуск у `MainViewModel` |
| MVI екрана авторизації | `ui/auth/AuthContract.kt`, `ui/auth/AuthViewModel.kt`, `ui/auth/AuthScreen.kt` |
| Обробка помилок і снекбар | `AuthEffect.ShowError`, `SnackbarHost` у `AuthScreen.kt` |
| Головний екран і перехід після входу | `ui/home/HomeScreen.kt`, `App.kt` |
| Навігація за ознакою авторизації | `ui/main/MainViewModel.kt`, `App.kt` |
