# Промпт для генерації дизайну (лаб 4, крок 2)

Дані взяті з серверного репозиторію `dotnet-learning-platform`, сервіс Enrollments.
Промпт навмисно короткий: стиль, палітру, розкладку й компоненти обирає генератор. У промпті лишається те, що він знати не може: що це за застосунок, які екрани й дані існують і які теми потрібні.

---

## Промпт

```text
Design the UI for "Learning Platform", a cross-platform education app (Android phone and desktop, built with Compose Multiplatform).

What it does: a student browses the course catalog of a university platform, enrolls in one or more courses, and follows the enrollment status. All data comes from a REST API, the app itself has no content. Copy is Ukrainian, with an English version.

Screens: an onboarding pager (3-5 slides, first launch), the course catalog, course details, a new enrollment (courses with unit counts, a note, a preferred schedule), my enrollments, enrollment details with a status track and confirm/cancel actions, and settings (current student, language, server address).

The API contains nothing else, so do not design for it: a course is only title, price and seats available; an enrollment is id, status, date, note, schedule and its items. No search, filters, images, ratings or reviews. Enrollment statuses: Submitted, AwaitingValidation, SeatsConfirmed, Paid, Completed, Cancelled; a cancellation is allowed only before the Paid status.

Use realistic sample data: courses "Основи C#" 1 200.00 (30 місць), "ASP.NET Core API" 1 800.00 (20 місць), "MongoDB для .NET" 1 500.00 (0 місць); students "Олена Петренко" and "Андрій Шевченко"; one enrollment "#1042" from 12.03.2026 with the Submitted status, plus one Completed and one Cancelled.

Choose the visual style yourself: palette, typography, shapes, layout. A calm education product, one accent color, a clear reading hierarchy, and the light and dark themes built from the same system. At the end, list the colors and fonts you used.

Deliver the phone screens (390x844) in both themes, a desktop 1280 px variant of the catalog and my enrollments, and one English variant of the catalog.
```

## Якщо генеруєш екран за екраном

```text
Generate the onboarding pager first, light and dark, phone 390x844.
Then the catalog, then course details, then new enrollment, then my enrollments, then enrollment details, then settings. Light and dark for each.
```
