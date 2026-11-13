# Entwicklung und Prüfungen

**Deutsch** · [English](Entwicklung-und-Pruefungen-en) · [Tool Belt Unlimited](Home)

## Bauen

Voraussetzung: Java 21; Gradle verwendet die Java-21-Toolchain. Die Mod-Abhängigkeiten kommen über CurseMaven, lokale JARs in `libs/` sind nicht nötig.

```sh
./gradlew build
./gradlew runClient
```

Die gebaute Mod-JAR liegt unter `build/libs/`. Unter Windows nutze `gradlew.bat`.

## Dokumentation prüfen

```sh
python3 scripts/verify-wiki.py
```

Der Prüfer kontrolliert Sprachpaare, interne Seiten-/Abschnittsverweise, Navigation, Medienzuordnung, PNG-Dateien und Prüfsummen sowie die kurze englische README. Das Medieninventar führt jede dokumentierte Funktion mit Quellcode und passenden Bildern auf.

## Spielnachweise

![Tatsächliches Ergebnis des zusätzlichen Neun-auf-zehn-Rezepts](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-9-to-10.png)

*Frische Aufnahme des unveränderten veröffentlichten Addons 1.3.0, mit separater Aufnahme-Fixture in einer neuen kreativen Flachwelt.*

Der Lauf prüft echte Nähtisch-Rezeptergebnisse für 8 → 9, 9 → 10, 19 → 20 und 11 → 12 bei geändertem Limit, verweigerte weitere Rezepte bei 20 beziehungsweise zwölf, erhaltene Gürtelkomponenten sowie Zulassung und Einlagerung von zwölf getaggten Werkzeugen mit ein- und ausgeschalteter Zusatzfreigabe. Die Fixture bereitet Zutaten, Gürtel und Einstellungen vor; dies ist kein manuell erspielter Survival-Durchlauf.

Die vorhandene Aufnahme des Auswahlrads mit 14 Werkzeugen wurde übernommen. Neue Aufnahmen belegen keinen Mehrspieler-Dauertest und keine Obergrenze oberhalb von 20. [Bildnachweise](Bildnachweise) erläutert die Grenzen. [Wiki-Quellen](https://github.com/TeutonStudio/ToolBeltUnlimited/tree/master/wiki) · [Medieninventar](https://github.com/TeutonStudio/ToolBeltUnlimited/blob/master/docs/wiki-media-coverage.json).
