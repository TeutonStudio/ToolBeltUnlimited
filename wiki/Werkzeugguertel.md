# Werkzeuggürtel verwenden

**Deutsch** · [English](Werkzeugguertel-en) · [Tool Belt Unlimited](Home)

Das Addon hebt die bisherige Grenze von neun Plätzen an. **20 ist die Standardobergrenze**, kein automatisch voll ausgebauter Gürtel. Jeder erfolgreiche Upgrade-Schritt fügt einen Platz hinzu. Unterhalb von neun Plätzen gelten die bisherigen Tool-Belt-Rezepte; danach geht es am [Nähtisch](Upgrades) weiter.

## Einlagern und auswählen

- Öffne den Gürtel mit der normalen Tool-Belt-Bedienung; mit Gürtel in der Hand öffnet Schleichen + Rechtsklick sein Inventar.
- Lege erlaubte Werkzeuge in die Gürtelplätze.
- Nutze das Tool-Belt-Auswahlrad zum Wechseln. Die Tasten findest du unter **Optionen → Steuerung → Tastenbelegung → Tool Belt**; eigene Belegungen und Konflikte können abweichen.

## Raster und Ringebenen ab 1.4.0

Das Gürtelinventar hat standardmäßig neun Spalten. Ab 1.5.0 bestimmt `itemsPerRing` die Spalten- und Ringplatzanzahl; siehe [Einstellungen](Einstellungen). Jede Zeile entspricht genau einer Ringebene im Auswahlrad: Zeile 1 = innerster Ring, Zeile 2 = zweiter Ring usw. Die erste Spalte liegt oben im Ring, die weiteren folgen im Uhrzeigersinn. Leere Plätze behalten ihren Winkel, auch wenn Tool Belt sie ausblendet; eine unvollständige letzte Zeile bildet einen entsprechend unvollständigen Ring.

Reicht der Bildschirm nicht für alle Zeilen oder Ringe, blätterst du mit dem Mausrad durch die Ebenen. Die Ringnummern entsprechen weiterhin den ursprünglichen Zeilennummern. Die bisherigen Tool-Belt-Funktionen für Mausklick, Auswahl beim Loslassen und Weiterschalten bleiben erhalten.

![Gürtelraster mit 20 Plätzen in drei Zeilen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/grid-20.png)

*Lokaler Entwicklungsstand 1.4.0: 20 Plätze als neun, neun und zwei Slots; die Zahlen rechts benennen die Ringebenen.*

![Drei Ringebenen mit wenigen belegten Plätzen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-20-sparse.png)

*Vier Werkzeuge auf ihren tatsächlichen Plätzen 1, 10, 19 und 20. Ausgeblendete Leerplätze bleiben als abgedunkelte Positionen erkennbar und sind nicht auswählbar.*

![Leerplatz im zweiten Ring ausgewählt](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-20-empty-slots.png)

*Mit aktivierter Tool-Belt-Anzeige leerer Plätze ist auch der leere Platz in Zeile 2, Spalte 2 auswählbar.*

### Viele Ebenen

![Letzte Zeilen eines Gürtels mit 90 Plätzen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/grid-90-scrolled.png)

![Letzte Ringebenen eines Gürtels mit 90 Plätzen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-90-scrolled.png)

*Vorbereiteter Testgürtel mit erhöhtem Limit 90, angeforderter GUI-Größe 4 und Minecrafts tatsächlich möglicher Skalierung. Das Mausrad erreicht die letzte Zeile und den zehnten Ring. Dies ist ein kontrollierter Oberflächentest mit integriertem Server.*

## Augen und zyklischer Ringwechsel ab 1.5.0

Neben jeder Inventarzeile sitzt ein Auge. Ein Klick blendet genau diese Zeile aus der Ringauswahl aus, der nächste blendet sie wieder ein. Die Gegenstände bleiben im Inventar zugänglich. Ausgeblendete Zeilen werden beim Scrollen und beim Weiterschalten per Taste übersprungen. Auch alle Zeilen dürfen ausgeblendet sein; über das Inventar lassen sie sich jederzeit wieder einschalten.

![Zweite Zeile über ihr Auge ausgeblendet](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-grid-six-eyes.png)

![Ringauswahl überspringt die ausgeblendete Zeile](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-skip-row.png)

*Bei sechs Plätzen pro Zeile und maximal zwei sichtbaren Ringen werden zunächst Zeilen 1 und 3 angezeigt; Zeile 2 ist ausgeblendet.*

![Ein Schritt verschiebt die Ringe nach innen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-shift-inward.png)

*Ein Mausradschritt nach unten entfernt den innersten Ring, verschiebt den bisherigen zweiten Ring nach innen und zeigt die nächste aktivierte Zeile außen. Nach oben läuft es umgekehrt: außen fällt eine Ebene weg, innen kommt die vorherige hinzu.*

![Zyklischer Übergang von der letzten zur ersten Zeile](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-wrapped.png)

*Nach der letzten aktivierten Zeile folgt wieder die erste. Die eingeblendeten Nummern bezeichnen weiterhin die ursprünglichen Inventarzeilen. Sind bereits alle aktivierten Zeilen sichtbar, bleibt die Anordnung beim Scrollen stehen.*

## Selektives Einfügen ab 1.5.1

Halte ein erlaubtes Item in der Hand und wähle einen leeren Ringplatz, um genau dort einzulagern. Leere Plätze sind dafür auch bei ausgeschalteter Tool-Belt-Leerplatzanzeige auswählbar. Ohne gehaltenes Item gilt die Leerplatzanzeige wie bisher. Die Augenfilter bleiben aktiv; Einfügen über die Mitte und Austauschen belegter Plätze funktionieren weiterhin.

![Gehaltenes Item gezielt in einen leeren Ringplatz einsetzen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/insert-empty-slot-selected.png)

*Spieltest mit ausgeschalteter Leerplatzanzeige: Slot 2 ist als Einfügeziel ausgewählt. Klick und Auswahl beim Loslassen wurden mit dem tatsächlichen Serverergebnis geprüft, ebenso ein Einfügeziel nach dem Scrollen.*

## Historische Aufnahmen

Die folgenden Bilder zeigen die frühere Oberfläche vor dem neuen Raster und den Ringebenen.

![Gürtelinventar mit 20 Plätzen und zwölf eingelagerten Werkzeugen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-enabled.png)

*Gürtel mit 20 Plätzen; zwölf Plätze sind mit Werkzeugen belegt. Für diesen Vergleich ist die ursprüngliche automatische Freigabe nicht stapelbarer Items in Tool Belt ausgeschaltet, die Zusatzfreigabe des Addons ist eingeschaltet.*

![Auswahlrad mit mehr als neun Werkzeugen](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-radial-14.png)

*Vorhandene Spielaufnahme mit 14 Werkzeugen im Auswahlrad.*

In der alten Oberfläche reicht die Slotzeile bei 20 Plätzen über den ursprünglichen Inventarhintergrund hinaus. Ab 1.4.0 ersetzt das Raster diese einzelne Zeile. Die historischen Bilder belegen weiterhin die Einlagerung und frühere Auswahloberfläche; sie zeigen das neue Layout nicht.
