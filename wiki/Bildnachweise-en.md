# Media provenance

[Deutsch](Bildnachweise) · **English** · [Tool Belt Unlimited](English)

The wiki uses **24 unedited in-game PNGs**: one capture of local build 1.5.1, six of build 1.5.0, five of build 1.4.0, eleven of published 1.3.0, and one older radial-menu capture. Both languages share the same game images. No generated illustration or mockup is presented as an in-game screenshot.

## Settings and eyes in 1.5.0

Six additional captures show local build 1.5.0 with prepared test belts. The `-PconfigUiValidation` fixture uses six slots per ring, limits of two and one visible rings, row eyes, cyclic scrolling and the original menu with ring mode disabled. Dependencies and window size match 1.4.0, with GUI scale 2. These captures come from the integrated-server test on 14 November 2026.

## Grid and rings in 1.4.0

The five new images come from `devtests/ui-validation.init.gradle` and a separate creative test world under `build/ui-validation-run/`. They use the Minecraft/mod dependencies below, local development build 1.4.0, Java 21 and 1280 × 800 pixels. The 20-slot images use GUI scale 2; the 90-slot checks request scale 4, which Minecraft limits to the available scaling at this window size. The fixture prepares items and limits. Captures use Minecraft's screenshot API and have not been edited.

## Fresh captures (1.3.0)

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
| `grid-20.png` | Twenty-slot belt in three grid rows; local 1.4.0 development build, GUI scale 2. |
| `rings-20-sparse.png` | Three ring layers with tools in slots 1, 10, 19 and 20; hidden empty slots retain their angles. |
| `rings-20-empty-slots.png` | Empty slot in row 2, column 2 selected with the native display-empty-slots option enabled. |
| `grid-90-scrolled.png` | Prepared ninety-slot belt scrolled to the last grid row; GUI scale 4 requested, Minecraft limits the actual scaling. |
| `rings-90-scrolled.png` | Prepared ninety-slot belt, final ring layers including ring 10; integrated server, GUI scale 4 requested. |
| `config-grid-six-eyes.png` | Local 1.5.0 build: itemsPerRing 6, thirty slots in five rows; second row hidden using its eye. |
| `config-rings-skip-row.png` | At most two visible rings, enabled rows 1 and 3; row 2 hidden. |
| `config-rings-shift-inward.png` | After one downward scroll step: previous outer ring moves inside and next enabled row appears outside. |
| `config-rings-wrapped.png` | Cyclic wrap with rows 5 and 1 visible. |
| `config-rings-limit-one.png` | maxVisibleRings 1: exactly one ring layer visible. |
| `config-native-single-ring.png` | useRings false: original Tool Belt radial menu with all thirty slots, including the row hidden in ring mode. |
| `insert-empty-slot-selected.png` | Build 1.5.1: held item and selected empty slot with empty-slot display disabled; direct insertion verified by actual server results. |

The [machine-readable inventory](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json) stores image hashes, dimensions, origins, implementation hashes, language pairs and feature assignments. `shown` means an appropriate, scoped image exists, not full release acceptance. [Development and checks](Entwicklung-und-Pruefungen-en) describes validation.
