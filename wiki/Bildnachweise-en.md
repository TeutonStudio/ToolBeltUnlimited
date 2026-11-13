# Media provenance

[Deutsch](Bildnachweise) · **English** · [Tool Belt Unlimited](English)

The wiki uses **twelve unedited in-game PNGs**: eleven fresh captures from 13 November 2026 and one existing radial-menu capture. Both languages share the German game screenshots; captions explain them in the page's language. No generated illustration or mockup is presented as an in-game screenshot.

## Fresh captures

Minecraft 1.21.1, NeoForge 21.1.230, Kotlin for Forge 5.10.0, Tool Belt 2.2.9, Sewing Kit 2.0.9, Java 21; 1280 × 800 pixels, GUI scale 2. The addon is the unchanged published 1.3.0 JAR, SHA-256 `33e3839320f13e5849a1ad398857c36afa2f4b55226e9f3c1ce85f8dbe3ba9a7`.

A separate temporary capture mod prepared belts, ingredients, a Sewing Table and settings in a new creative flat world. It used native recipe menus, native belt slots and NeoForge's native configuration screen. The extra tool comparison deliberately disables Tool Belt's original automatic permission; see [Allowing tools](Werkzeuge-en). No personal world was opened. This is a controlled demonstration, not a survival playthrough, long-term multiplayer test or test of arbitrary slot counts.

![Native nine-to-ten recipe output](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-9-to-10.png)

## Capture inventory

| File | Shows and limits |
| --- | --- |
| `wiki-radial-14.png` | Existing in-game radial menu capture with 14 tools; capture settings and exact historical mod build were not fully recorded. |
| `wiki-upgrade-8-to-9.png` | Original Tool Belt recipe, eight to nine slots, output with tooltip. |
| `wiki-upgrade-9-to-10.png` | Added recipe, nine to ten slots, with prepared ingredients and a pre-filled input belt. |
| `wiki-contents-retained.png` | Opened ten-slot output of the nine-to-ten recipe, retaining the pre-existing diamond pickaxe. |
| `wiki-upgrade-19-to-20.png` | Same added recipe, 19 to 20 slots, output with tooltip. |
| `wiki-maximum-reached.png` | Prepared 20-slot belt and complete ingredients; no further added recipe with maxSlots 20. |
| `wiki-common-tools-enabled.png` | Twelve actual #c:tools items inserted through native belt slots; addon permission true, original allowAllNonStackableItems false, empty whitelist. |
| `wiki-common-tools-disabled.png` | Same twelve tools rejected through native belt slots and remaining in player inventory; addon permission false, original automatic permission also false, empty whitelist. |
| `wiki-settings-default.png` | Native NeoForge server configuration of the published addon with defaults 20 and true; the addon does not provide its own config screen. |
| `wiki-settings-custom.png` | Native configuration with deliberately set example values maxSlots 12 and allowCommonTools false. |
| `wiki-upgrade-11-to-12.png` | Actual eleven-to-twelve recipe with maxSlots 12; allowCommonTools restored to true for this recipe capture. |
| `wiki-custom-maximum-reached.png` | No added recipe at twelve slots with maxSlots 12. |

The [machine-readable inventory](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json) stores image hashes, dimensions, origins, implementation hashes, language pairs and feature assignments. `shown` means an appropriate, scoped image exists, not full release acceptance. [Development and checks](Entwicklung-und-Pruefungen-en) describes validation.
