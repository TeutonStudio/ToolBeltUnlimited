# Wiki- und README-Prüfung — 13. November 2026

## Ergebnis

- Öffentliche Ausgangswiki: nur `Home.md` mit Begrüßung, Commit `bedcf52f58e64770dc86cf580dbbb9672903f451`.
- Neue Wiki: 20 Markdown-Dateien, neun vollständige deutsch/englische Inhaltspaare, Sidebar und Footer.
- Deutsch ist Hauptsprache und erster Einstieg; jede Inhaltsseite hat einen direkten Sprachwechsel.
- README: ausschließlich Englisch, sieben Zeilen und 59 Wörter einschließlich Links.
- Acht eingebaute Funktionsthemen mit Bildern in beiden Sprachen abgedeckt.
- Zwölf unveränderte Ingame-PNGs: elf neue Aufnahmen und das vorhandene Auswahlrad mit 14 Werkzeugen.
- Alle Nutzeranleitungen enthalten passende Bilder, einschließlich Installation, Konfiguration und Fehlerhilfe.

## Fachlicher Abgleich

Geprüft wurden Konfigurationsregistrierung, beide Configwerte, alle drei Mixins,
dynamisches Nähtischrezept samt JSON-Registrierung, Mod-Metadaten und Build-Abhängigkeiten.
Die Wiki erläutert Originalupgrades bis neun, zusätzliche Ein-Platz-Upgrades, Obergrenzen,
Komponentenerhalt, zusätzliche Werkzeugfreigabe und deren Abschaltung.

Korrekturen gegenüber der ursprünglichen README:

- `config/toolbeltunlimited-server.toml` ist der beobachtete Standardpfad im NeoForge-21.1.230-Setup;
  eine vorhandene Weltdatei unter `serverconfig/` kann ihn überschreiben. Der FML-ConfigTracker wurde
  zusätzlich auf diese Priorität geprüft.
- `allowCommonTools = false` aktiviert die ursprüngliche Zulassungsprüfung und sperrt nicht pauschal
  alle Werkzeuge. Bei `true` läuft die Zusatzfreigabe vor der ursprünglichen Blacklist.
- Technisch erlaubte sehr große Slotzahlen sind keine geprüfte Spielbarkeitszusage.
- Die native Gürteloberfläche reicht bei 20 Plätzen über ihren ursprünglichen Hintergrund hinaus.
- Die feste Rezeptvorschau mit zehn Plätzen ist vom dynamischen tatsächlichen Ergebnis unterschieden.

## Aufnahme und Prüfung

Unverändertes veröffentlichtes 1.3.0-JAR, SHA-256
`33e3839320f13e5849a1ad398857c36afa2f4b55226e9f3c1ce85f8dbe3ba9a7`.
Minecraft 1.21.1, NeoForge 21.1.230, Kotlin for Forge 5.10.0,
Tool Belt 2.2.9, Sewing Kit 2.0.9, Java 21, 1280 × 800, GUI-Größe 2.
Neue kreative Flachwelt mit separater temporärer Fixture; keine persönliche Welt geöffnet.

Der erfolgreiche Lauf bestätigt:

- Native Nähtischergebnisse 8 → 9, 9 → 10, 19 → 20 und bei geändertem Limit 11 → 12.
- Kein weiteres Zusatzrezept bei 20 beziehungsweise zwölf Plätzen.
- Bereits eingelagerte Diamantspitzhacke und benutzerdefinierter Name in den Ergebnissen erhalten;
  zusätzlich geöffnetes Ergebnisinventar fotografiert.
- Zwölf tatsächliche `#c:tools`-Gegenstände durch native Gürtelplätze zugelassen und eingelagert
  beziehungsweise abgewiesen. Für den Vergleich war Tool Belts ursprüngliche automatische Freigabe
  nicht stapelbarer Items ausgeschaltet; das steht auf beiden Sprachseiten und im Medieninventar.
- Native NeoForge-Configoberfläche mit Standardwerten und Beispielkonfiguration zwölf/false aufgenommen.
- Abschlussmarker `WIKI_CAPTURE_COMPLETE`, danach sauberer Spielabschluss und gespeicherte Dimensionen.

Alle zwölf Bilddateien wurden visuell geprüft. Es wurden keine Pixel bearbeitet.
Mehrspieler-Langzeitbetrieb und Limits über 20 wurden nicht neu geprüft. Die Aufnahmen sind eine
kontrollierte Demonstration und kein Survival-Durchlauf. Die bestehende Auswahlradaufnahme hat
eine ausdrücklich eingeschränkte historische Herkunftsbeschreibung.

`python3 scripts/verify-wiki.py` bestätigt 20 Seiten, neun Sprachpaare, zwölf Bilder und 8/8
bebilderte Funktionsthemen. `git diff --check` ist erfolgreich. Produktquellen, Modversion,
Release-Tag und veröffentlichtes JAR bleiben unverändert.

## Veröffentlichung

Hauptrepository und Wiki werden separat aktualisiert. Anschließend werden die tatsächlichen
deutschen und englischen GitHub-Seiten sowie die Bildauslieferung geprüft.
