# Wiki pflegen und veröffentlichen

Die Quellen liegen unter `wiki/`. Deutsch ist Einstieg und Hauptnavigation; jede Inhaltsseite
hat eine vollständige englische Partnerseite und einen direkten Sprachwechsel. `README.md`
bleibt ein kurzer englischer Einstieg. Benutzeranleitungen und Build-Hinweise stehen im Wiki.

## Vor Änderungen

Funktionen mit Implementierung und veröffentlichtem Modstand vergleichen. Jede eingebaute
Benutzerfunktion braucht einen passenden Screenshot oder ein GIF in beiden Sprachfassungen.
Der aktuelle Stand verwendet ausschließlich echte PNG-Spielaufnahmen. Synthetische Bilder
dürfen nicht als Spielaufnahme ausgegeben werden. Screenshots auf beiden Sprachseiten sind
identisch; die Bildtexte sind vollständig übersetzt.

`docs/wiki-media-coverage.json` ordnet acht Funktionsthemen ihren Quellen, Sprachpaaren und
Bildern zu. Es enthält außerdem Bildherkunft, Abmessungen, SHA-256-Prüfsummen und die
Implementierungsbaseline. Nach einer Codeänderung muss die Wiki fachlich geprüft werden,
bevor diese Baseline aktualisiert wird. Ein Bild mit Status `shown` ist eine eingegrenzte
Darstellung und keine vollständige Release-Abnahme.

## Prüfen

```sh
python3 scripts/verify-wiki.py
git diff --check
```

Die Prüfung benötigt nur Python 3 mit Standardbibliothek. Sie kontrolliert Sprachpaare,
Navigation, interne Seiten-/Abschnittsziele, tatsächliche PNG-Dateien samt Chunk-Prüfsummen,
Bild-Hashes und -Abmessungen, vollständige Medienzuordnung sowie die kurze englische README.
Der Workflow `.github/workflows/docs.yml` führt sie auch bei Implementierungsänderungen aus.
Neue Funktionen müssen zusätzlich im Inventar und in der erwarteten Featureliste des Prüfers
eingetragen werden; die automatische Prüfung ersetzt keinen inhaltlichen Codeabgleich.

Die Aufnahmen vom 13. November 2026 wurden mit dem unveränderten veröffentlichten 1.3.0-JAR,
einem separaten temporären Aufnahme-Mod und einer neuen kreativen Flachwelt erstellt.
Die Fixture liegt außerhalb der Produktquellen; sie bereitete Inventare, Zutaten und
Konfigurationswerte vor. `docs/wiki-capture-results.txt` enthält den erfolgreichen Prüfauszug.
Bei Werkzeugen wurde über die nativen Gürtelplätze eingesetzt, einschließlich tatsächlicher
Ablehnung bei deaktivierten Freigaben. Die Bildnachweisseiten erklären den besonderen
Tool-Belt-Konfigurationszustand dieses Vergleichs und die Grenzen der Aufnahmen.

## Veröffentlichen

Das öffentliche Wiki ist ein eigenes Git-Repository:
`https://github.com/TeutonStudio/ToolBeltUnlimited.wiki.git`.
Ein Push ins Hauptrepository allein aktualisiert die Wiki nicht.

1. Aktuellen Wiki-Stand abrufen; Änderungen anderer Autoren erhalten.
2. `wiki/*.md` und `wiki/assets/` an die Wurzel des Wiki-Checkouts übernehmen.
3. Diff und Prüfung kontrollieren, dann Hauptrepository und Wiki getrennt committen und pushen.
4. Deutsche und englische GitHub-Darstellung, Sprachwechsel und tatsächlich geladene Bilder prüfen.

Die Bildlinks zeigen auf
`https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/`.
HTTP-Erfolg allein beweist keine sichtbare Einbindung; nach Veröffentlichung auch im Browser
prüfen. Reine Dokumentationsänderungen ändern weder Modversion noch Release-Tag oder Release-JAR.
