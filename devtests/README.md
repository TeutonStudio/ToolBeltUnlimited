# Raster und Ringmenü prüfen

```sh
./gradlew -I devtests/ui-validation.init.gradle runClient --offline
```

Benötigt die bereits geladenen Build-Abhängigkeiten und einen Grafikdesktop. Ohne lokalen Cache `--offline` weglassen. Die Fixture gehört ausschließlich zum separaten Entwicklungs-Source-Set und landet nicht in der Mod-JAR.

Der Lauf erzeugt eine neue kreative Flachwelt unter `build/ui-validation-run/`, setzt dort eigene Optionen und beendet Minecraft nach Abschluss. Persönliche Spielstände und die normalen `run/`-Optionen werden nicht verwendet. Aufnahmen liegen unter `build/ui-validation-run/screenshots/`. Erfolgsmarker: `UI_VALIDATION_COMPLETE`; Fehler: `UI_VALIDATION_FAILED` oder Minecraft-Absturz.

Geprüft werden Raster und identische Slotpositionen auf Client und integriertem Server für 2, 9, 10 und 20 Plätze, gesperrter gehaltener Gürtel, Klick auf den letzten Platz, Shift-Klick in beide Richtungen, ein Gürtel mit 90 Plätzen bei GUI-Größe 4, Scrollen samt ausgeblendeten Slots, stabile Ringpositionen bei ausgeblendeten Leerplätzen, sichtbare Leerplätze, Mausklick- und Loslassen-Auswahl mit tatsächlichem Serverergebnis sowie Tastenzyklus über Ringseiten hinweg.

Zuordnung: neun Plätze pro Zeile/Ring; Slot 0–8 = Ring 1, 9–17 = Ring 2, 18–26 = Ring 3 usw. Spalte 1 liegt oben, die weiteren Spalten folgen im Uhrzeigersinn. Ein unvollständiger letzter Ring behält dieselben Winkel. Bei zu wenig Bildschirmplatz zeigt das Mausrad einen anderen Ausschnitt der Ringebenen; die angezeigten Ringnummern bleiben die Nummern der Inventarzeilen.

## Konfiguration und Zeilenaugen ab 1.5.0

```sh
./gradlew -I devtests/ui-validation.init.gradle -PconfigUiValidation runClient --offline
```

Dieser zusätzliche Lauf prüft sechs Plätze pro Zeile, ein Auge je Zeile, tatsächliche Button-Pakete samt Client-Synchronisierung, erhaltene Inhalte und fremde CustomData, Speichern und Upgrades, abgewiesene ungültige Zeilennummern, beidseitiges Verschieben und zyklischen Übergang, übersprungene ausgeblendete Zeilen bei Maus und Tastatur, Sichtlimits eins/zwei, ursprüngliches Auswahlrad bei `useRings = false`, Wiedereinschalten, alle Zeilen ausgeblendet sowie die Grenzwerte eins und 24 Plätze pro Zeile. Breite Inventarzeilen werden bei Bedarf horizontal gescrollt. Erfolgsmarker: `CONFIG_UI_VALIDATION_COMPLETE` und `UI_VALIDATION_COMPLETE`.

Die reine Reihenfolgeprüfung benötigt keinen Spielstart:

```sh
mkdir -p build/ring-window-tests
javac -d build/ring-window-tests src/main/java/de/teutonstudio/toolbeltunlimited/client/RingWindow.java devtests/pure/RingWindowTest.java
java -cp build/ring-window-tests RingWindowTest
```

## Selektives Einfügen ab 1.5.1

```sh
./gradlew -I devtests/ui-validation.init.gradle -PinsertUiValidation runClient --offline
```

Reproduziert mit Version 1.5.0 den nicht auswählbaren leeren Slot bei ausgeschalteter Leerplatzanzeige. Prüft danach echtes Einsetzen in den ausgewählten Slot per Klick und Loslassen, unveränderte belegte Slots, erhaltene Item-Komponenten und keine Duplikate, Einfügen über die Mitte, Austauschen belegter Plätze, vollen Gürtel, leere Hand sowie Einfügen nach Scrollen bei anderer Ringgröße. Erfolgsmarker: `INSERT_UI_VALIDATION_COMPLETE` und `UI_VALIDATION_COMPLETE`.
