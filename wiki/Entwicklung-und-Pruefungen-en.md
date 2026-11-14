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

The existing 14-tool radial-menu capture was reused. These 1.3.0 captures do not establish long-term multiplayer behavior or limits above 20. [Media provenance](Bildnachweise-en) explains their scope. [Wiki sources](https://github.com/TeutonStudio/ToolBeltUnlimited/tree/master/wiki) · [Media inventory](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json).

## UI validation since 1.4.0

`./gradlew -I devtests/ui-validation.init.gradle runClient --offline` starts the new isolated test world. It checks grids for 2, 9, 10 and 20 slots, inventory clicks and shift-click, the locked held belt, empty and occupied ring positions, actual server results for mouse click and release-to-swap, and scrolling and keyboard cycling with 90 slots. See [the fixture documentation](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/devtests/README.md). Successful runs log `UI_VALIDATION_COMPLETE`. An integrated server does not establish long-term dedicated multiplayer behavior.

## Config and eye validation since 1.5.0

`./gradlew -I devtests/ui-validation.init.gradle -PconfigUiValidation runClient --offline` additionally checks config boundaries, actual eye button packets and client synchronization, retained contents and CustomData, row preferences after upgrades and serialization, cyclic ring shifts, hidden rows for mouse and keyboard, ring mode on/off and all rings hidden. Successful runs log `CONFIG_UI_VALIDATION_COMPLETE`. The fixture documentation also describes the standalone `RingWindowTest` ordering checks.
