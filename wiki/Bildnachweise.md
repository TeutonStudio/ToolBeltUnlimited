# Bildnachweise

**Deutsch** · [English](Bildnachweise-en) · [Tool Belt Unlimited](Home)

Die Wiki verwendet **24 unveränderte Ingame-PNGs**: eine Aufnahme des lokalen Stands 1.5.1, sechs des Stands 1.5.0, fünf des Stands 1.4.0, elf der veröffentlichten 1.3.0 und eine ältere Aufnahme des Auswahlrads. Beide Sprachen teilen dieselben Spielbilder. Kein generiertes Bild oder Mockup wird als Spielaufnahme ausgegeben.

## Einstellungen und Augen 1.5.0

Sechs zusätzliche Aufnahmen zeigen den lokalen Stand 1.5.0 mit vorbereiteten Testgürteln. Die Fixture `-PconfigUiValidation` verwendet sechs Plätze pro Ring, maximal zwei beziehungsweise einen sichtbaren Ring, die Augenfilter, zyklische Verschiebung und das ursprüngliche Auswahlrad bei ausgeschaltetem Ringmodus. Gleiche Abhängigkeiten und Fenstergröße wie 1.4.0, GUI-Größe 2. Angaben und Bilddateien gehören zum Test mit integriertem Server vom 14. November 2026.

## Raster und Ringe 1.4.0

Die fünf neuen Bilder stammen aus `devtests/ui-validation.init.gradle` und einer separaten kreativen Testwelt unter `build/ui-validation-run/`. Gleiche Minecraft-/Mod-Abhängigkeiten wie unten, lokaler Entwicklungsstand 1.4.0, Java 21 und 1280 × 800 Pixel. Die 20-Platz-Bilder nutzen GUI-Größe 2; für 90 Plätze ist GUI-Größe 4 angefordert, die Minecraft auf die bei dieser Fenstergröße verfügbare Skalierung begrenzt. Gegenstände und Limits werden von der Fixture vorbereitet. Die Bilder wurden über Minecrafts Screenshot-API aufgenommen und nicht bearbeitet.

## Frische Aufnahmen (1.3.0)

Minecraft 1.21.1, NeoForge 21.1.230, Kotlin for Forge 5.10.0, Tool Belt 2.2.9, Sewing Kit 2.0.9, Java 21; 1280 × 800 Pixel, GUI-Größe 2. Das Addon ist die unveränderte veröffentlichte 1.3.0-JAR, SHA-256 `33e3839320f13e5849a1ad398857c36afa2f4b55226e9f3c1ce85f8dbe3ba9a7`.

Ein separates temporäres Aufnahme-Mod bereitete Gürtel, Zutaten, Nähtisch und Einstellungen in einer neuen kreativen Flachwelt vor. Genutzt wurden native Rezeptmenüs, native Gürtelplätze und die native NeoForge-Konfigurationsoberfläche. Der Werkzeugvergleich schaltet bewusst die ursprüngliche automatische Tool-Belt-Freigabe ab; siehe [Werkzeuge](Werkzeuge). Keine persönliche Welt wurde geöffnet. Dies ist eine kontrollierte Demonstration, kein Survival-Durchlauf, Mehrspieler-Dauertest oder Test beliebiger Platzanzahlen.

![Natives Ergebnis des Neun-auf-zehn-Rezepts](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-9-to-10.png)

## Aufnahmeverzeichnis

| Datei | Motiv und Aussagegrenze |
| --- | --- |
| `wiki-radial-14.png` | Vorhandene Spielaufnahme des Auswahlrads mit 14 Werkzeugen; Aufnahmeparameter und genauer damaliger Modstand nicht vollständig protokolliert. |
| `wiki-upgrade-8-to-9.png` | Originalrezept von Tool Belt, acht auf neun Plätze, Ergebnis mit Tooltip. |
| `wiki-upgrade-9-to-10.png` | Zusatzrezept, neun auf zehn Plätze, mit vorbereiteten Zutaten und bereits gefülltem Eingabegürtel. |
| `wiki-contents-retained.png` | Geöffnetes Zehn-Platz-Ergebnis des Neun-auf-zehn-Rezepts: die vorhandene Diamantspitzhacke ist weiter im Gürtel. |
| `wiki-upgrade-19-to-20.png` | Dasselbe Zusatzrezept, 19 auf 20 Plätze, Ergebnis mit Tooltip. |
| `wiki-maximum-reached.png` | Vorbereiteter Gürtel mit 20 Plätzen und vollständigen Zutaten; kein weiteres Zusatzrezept bei maxSlots 20. |
| `wiki-common-tools-enabled.png` | Zwölf Werkzeuge mit echtem #c:tools-Tag über native Gürtelplätze eingelagert; Addon-Freigabe true, Tool Belts allowAllNonStackableItems false, leere Whitelist. |
| `wiki-common-tools-disabled.png` | Dieselben zwölf Werkzeuge über native Gürtelplätze abgewiesen, daher im Spielerinventar; Addon-Freigabe false, ursprüngliche automatische Freigabe ebenfalls false, leere Whitelist. |
| `wiki-settings-default.png` | Native NeoForge-Serverkonfiguration des veröffentlichten Addons mit Standardwerten 20 und true; keine eigene Config-Oberfläche des Addons. |
| `wiki-settings-custom.png` | Native Konfiguration mit bewusst gesetzten Beispielwerten maxSlots 12 und allowCommonTools false. |
| `wiki-upgrade-11-to-12.png` | Echtes Rezept elf auf zwölf bei maxSlots 12; allowCommonTools für diese Rezeptaufnahme wieder true. |
| `wiki-custom-maximum-reached.png` | Kein zusätzliches Rezept bei zwölf Plätzen und maxSlots 12. |
| `grid-20.png` | Gürtel mit 20 Plätzen in drei Rasterzeilen; lokaler Entwicklungsstand 1.4.0, GUI-Größe 2. |
| `rings-20-sparse.png` | Drei Ringebenen mit Werkzeugen auf Plätzen 1, 10, 19 und 20; ausgeblendete Leerplätze behalten ihren Winkel. |
| `rings-20-empty-slots.png` | Leerer Platz in Zeile 2, Spalte 2 ausgewählt; native Leerplatzanzeige eingeschaltet. |
| `grid-90-scrolled.png` | Vorbereiteter 90-Platz-Gürtel, bis zur letzten Rasterzeile gescrollt; GUI-Größe 4 angefordert, Minecraft begrenzt die tatsächliche Skalierung. |
| `rings-90-scrolled.png` | Vorbereiteter 90-Platz-Gürtel, letzte Ringebenen einschließlich Ring 10; integrierter Server, GUI-Größe 4 angefordert. |
| `config-grid-six-eyes.png` | Lokaler Stand 1.5.0: itemsPerRing 6, 30 Plätze in fünf Zeilen; zweite Zeile über ihr Auge ausgeblendet. |
| `config-rings-skip-row.png` | Maximal zwei sichtbare Ringe; aktivierte Zeilen 1 und 3, Zeile 2 ausgeblendet. |
| `config-rings-shift-inward.png` | Nach einem Schritt nach unten: bisheriger äußerer Ring innen, nächste aktivierte Zeile außen. |
| `config-rings-wrapped.png` | Zyklischer Übergang mit sichtbaren Zeilen 5 und 1. |
| `config-rings-limit-one.png` | maxVisibleRings 1: genau eine Ringebene sichtbar. |
| `config-native-single-ring.png` | useRings false: ursprüngliches Tool-Belt-Auswahlrad mit allen 30 Plätzen, auch der im Ringmodus ausgeblendeten Zeile. |
| `insert-empty-slot-selected.png` | Stand 1.5.1: gehaltenes Item und ausgewählter leerer Slot bei ausgeschalteter Leerplatzanzeige; gezieltes Einfügen durch Serverergebnis bestätigt. |

Das [maschinenlesbare Inventar](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json) speichert Bildprüfsummen, Abmessungen, Herkunft, Implementierungsprüfsummen, Sprachpaare und Feature-Zuordnung. `shown` bedeutet eine passende, eingegrenzte Abbildung und keine vollständige Release-Abnahme. [Entwicklung und Prüfungen](Entwicklung-und-Pruefungen) beschreibt die Prüfung.
