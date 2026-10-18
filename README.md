# Learning Platform - клієнт для Android і Desktop (Kotlin Multiplatform)

Клієнтська частина навчальної платформи: студент переглядає каталог курсів, записується на курс і бачить статус запису.
Дані надає серверний репозиторій `dotnet-learning-platform` з мікросервісами .NET; клієнт звертається до нього по HTTP і власної бази даних не має.

UI і логіка написані один раз на Kotlin Multiplatform (Compose Multiplatform) і працюють на Android і Desktop; відмінності платформ сховані за `expect`/`actual`.

## Структура

```text
/
├── androidApp/    точка входу Android
├── desktopApp/    точка входу Desktop
├── docs/design/   макети Stitch, токени теми, промпт
└── shared/        спільний код: commonMain, androidMain, jvmMain
```

## Що вже є

- Тема Academic Precision зі шрифтом Inter: світла, темна і системна. Системна бере динамічні кольори Android (API 31+) або кольори KDE з `~/.config/kdeglobals`; без них лишається кастомна палітра.
- Онбординг на 4 слайди (`HorizontalPager`); прапорець у налаштуваннях, тому показується лише першого разу.
- Локалізація українською та англійською у `shared/src/commonMain/composeResources/values*`; мову перемикає `expect object LocalAppLocale` без перезапуску застосунку.
- Налаштування користувача (тема, мова, онбординг) через multiplatform-settings: `SharedPreferences` на Android, `java.util.prefs` на JVM.
- Логування через kermit з тегом `LearningPlatform`.

## Запуск

```bash
./gradlew :androidApp:assembleDebug
./gradlew :desktopApp:run
./gradlew :shared:jvmTest
```
