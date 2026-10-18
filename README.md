# Learning Platform - клієнт для Android і Desktop (Kotlin Multiplatform)

Клієнтська частина навчальної платформи: студент переглядає каталог курсів, записується на курс і бачить статус запису.
Дані надає серверний репозиторій `dotnet-learning-platform` з мікросервісами .NET; клієнт звертається до нього по HTTP і власної бази даних не має.

UI і логіка написані один раз на Kotlin Multiplatform (Compose Multiplatform) і працюють на Android і Desktop; відмінності платформ сховані за `expect`/`actual`.

## Структура

```text
/
├── androidApp/   точка входу Android
├── desktopApp/   точка входу Desktop
└── shared/       спільний код: commonMain, androidMain, jvmMain
```

## Запуск

```bash
./gradlew :androidApp:assembleDebug
./gradlew :desktopApp:run
```
