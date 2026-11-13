# Development and checks

[Deutsch](Entwicklung-und-Pruefungen) · **English** · [Tool Belt Unlimited](English)

## Build

Requires Java 21; Gradle uses the Java 21 toolchain. Mod dependencies come from CurseMaven; local JARs in `libs/` are unnecessary.

```sh
./gradlew build
./gradlew runClient
```

The built mod JAR is in `build/libs/`. On Windows, use `gradlew.bat`.

## Check documentation

```sh
python3 scripts/verify-wiki.py
```

The verifier checks language pairs, internal page/section targets, navigation, media coverage, PNG files and hashes, and the short English README. The media inventory maps each documented feature to source code and matching captures.

## In-game evidence

![Actual output of the added nine-to-ten-slot recipe](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-9-to-10.png)

*Fresh capture of the unchanged published 1.3.0 addon, with a separate capture fixture in a new creative flat world.*

The run checks actual Sewing Table results for 8 → 9, 9 → 10, 19 → 20 and 11 → 12 with a custom limit; absent further recipes at 20 and twelve; retained belt components; and acceptance and insertion of twelve tagged tools with extra permission enabled and disabled. The fixture prepares ingredients, belts and settings; this is not a manually played survival progression.

The existing 14-tool radial-menu capture was reused. Fresh captures do not establish long-term multiplayer behavior or limits above 20. [Media provenance](Bildnachweise-en) explains their scope. [Wiki sources](https://github.com/TeutonStudio/ToolBeltUnlimited/tree/master/wiki) · [Media inventory](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json).
