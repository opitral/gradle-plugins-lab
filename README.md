# Лабораторна робота — Автоматизація роботи з програмними проєктами (Java / Gradle)

Проєкт-демонстрація власного Gradle-плагіна та додаткового task-у у `build.gradle.kts`.

## Стек
- Gradle 9.3 (Kotlin DSL), Java 25, JUnit 5.

## Структура
```
lab3/
├── build.gradle.kts            # застосування плагіна + task packageSources
├── settings.gradle.kts         # includeBuild("build-logic")
├── src/main/java/com/opitral/   # застосунок: App, TextStats
├── src/test/java/com/opitral/   # JUnit-тести
└── build-logic/                # included build з власним плагіном
    └── src/main/java/com/opitral/
        ├── ProjectToolsPlugin.java   # id "com.opitral.project-tools"
        ├── BuildInfoTask.java        # task 1
        └── CountLinesTask.java       # task 2
```

## 1. Власний плагін (`com.opitral.project-tools`)
Реалізований як **included build** `build-logic` (`java-gradle-plugin`).
Реєструє два task-и у групі `project tools`:

| Task | Призначення |
|------|-------------|
| `generateBuildInfo` | Генерує клас `com.opitral.generated.BuildInfo` з назвою, версією та часом збірки. Згенерована директорія підключається до `main` sourceSet, а `compileJava` залежить від цього task-у — тож застосунок використовує згенеровані дані. |
| `countLinesOfCode` | Обходить `src/main/java`, рахує файли та рядки коду, друкує звіт і зберігає його у `build/reports/loc/loc.txt`. |

## 2. Task у `build.gradle.kts`
| Task | Призначення |
|------|-------------|
| `packageSources` (тип `Zip`) | Пакує `src/` у `build/dist/lab3-sources-<version>.zip`. Залежить від `test`. |

## 3. Доречність логіки
Усі task-и працюють із реальним вмістом проєкту: генерують метадані для застосунку,
аналізують власний код і архівують вихідники — типові операції автоматизації збірки.

## Команди запуску
```bash
./gradlew tasks --group "project tools"   # перелік task-ів плагіна
./gradlew generateBuildInfo               # генерація BuildInfo
./gradlew countLinesOfCode                # звіт про обсяг коду
./gradlew packageSources                  # zip-архів вихідників
./gradlew build                           # повна збірка + тести
./gradlew run                             # запуск застосунку
```
