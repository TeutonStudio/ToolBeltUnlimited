# Einstellungen

**Deutsch** · [English](Einstellungen-en) · [Tool Belt Unlimited](Home)

Kapazität, Werkzeugfreigabe und Plätze pro Ring sind serverseitig und werden an Clients übertragen. Ringmodus und Sichtlimit gehören zur lokalen Client-Konfiguration. Im Einzelspieler übernimmt der integrierte Server die Serverrolle.

![Servereinstellungen mit den Standardwerten true und 20](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-settings-default.png)

*Native NeoForge-Konfigurationsoberfläche in einer geladenen Welt, Standardwerte. Oben: `allowCommonTools` auf „An“; darunter: `maxSlots` auf 20. Die unübersetzten Beschriftungen werden von NeoForge gekürzt.*

| Schlüssel | Standard | Bedeutung |
| --- | --- | --- |
| `maxSlots` | `20` | Maximale Gürtelplätze; gültiger Bereich `9` bis `2147483647`. |
| `allowCommonTools` | `true` | Zusätzliche Freigabe für `#c:tools`; mit `false` gelten nur die ursprünglichen Regeln. |
| `itemsPerRing` | `9` | Plätze pro Inventarzeile und Ring, Bereich `1`–`24`. Gruppiert vorhandene Plätze neu, ohne ihre Anzahl oder ihren Inhalt zu ändern. |

Der große zulässige Zahlenbereich ist eine technische Eingabegrenze, keine Zusage für Spielbarkeit oder Leistung bei extremen Werten. Ein Gürtel erhält durch Erhöhen des Limits keine Plätze automatisch.

## Ringanzeige ab 1.5.0

Die Datei **`config/toolbeltunlimited-client.toml`** gilt nur für den jeweiligen Client. In NeoForge findest du die Optionen unter **Tool Belt Unlimited → Client**.

| Schlüssel | Standard | Bedeutung |
| --- | --- | --- |
| `useRings` | `true` | Mehrere Ringebenen verwenden. `false` stellt das ursprüngliche einzelne Tool-Belt-Auswahlrad wieder her. |
| `maxVisibleRings` | `3` | Gleichzeitig sichtbare Ringe, Bereich `1`–`16`; auf kleinen Bildschirmen können es weniger sein. |

```toml
useRings = true
maxVisibleRings = 3
```

Die Serverdatei ergänzt:

```toml
itemsPerRing = 9
```

![Sechs Plätze pro Zeile mit Augen für die Ringauswahl](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-grid-six-eyes.png)

*Testwert `itemsPerRing = 6`: 30 Plätze ergeben fünf Zeilen. Das geschlossene Auge blendet die zweite Zeile nur aus der Ringauswahl aus.*

![Nur ein sichtbarer Ring](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-limit-one.png)

*Testwert `maxVisibleRings = 1`; weitere aktivierte Zeilen sind per Mausrad erreichbar.*

![Ursprüngliches einzelnes Auswahlrad](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-native-single-ring.png)

*`useRings = false`: ursprüngliches Auswahlrad. Die Augenfilter gelten für den Ringmodus; im ursprünglichen Rad sind alle Plätze wieder verfügbar.*

Das Auge speichert seine Auswahl pro Gürtel und übersteht Speichern sowie Upgrades. Eine Änderung von `itemsPerRing` gruppiert die Plätze neu; ausgeblendete Zeilennummern behalten ihre Nummer. Bei breiten Zeilen auf kleinen Bildschirmen verschiebt **Umschalt + Mausrad** die sichtbaren Spalten im Inventar.

## Datei und Oberfläche

Die Datei heißt **`toolbeltunlimited-server.toml`**. Im hier geprüften NeoForge-21.1.230-Setup wird sie unter `config/` der Instanz erzeugt. Eine bereits vorhandene Datei im `serverconfig/`-Ordner der jeweiligen Welt kann diesen Wert überschreiben; im Einzelspieler liegt dieser Ordner unter `saves/<Welt>/serverconfig/`, auf dem Server im Weltordner. Prüfe bei abweichenden Werten daher auch die Weltdatei. Die frühere README nannte nur `serverconfig/` und war damit unvollständig.

Öffne in einer geladenen Welt die NeoForge-Konfigurationsoberfläche für **Tool Belt Unlimited → Server** oder bearbeite die aktive Datei bei beendetem Spiel/Server. Starte anschließend die Welt beziehungsweise den Server neu. Auf einem entfernten Server entscheidet dessen Konfiguration.

```toml
allowCommonTools = true
maxSlots = 20
```

## Beispiel: zwölf Plätze

![Beispielkonfiguration mit zwölf Plätzen und ausgeschalteter Zusatzfreigabe](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-settings-custom.png)

*Beispielwerte: `maxSlots = 12`, `allowCommonTools = false`. Dies sind bewusst geänderte Werte.*

![Nähtischupgrade von elf auf zwölf Plätze bei geändertem Limit](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-11-to-12.png)

*Mit zwölf als Obergrenze funktioniert der Schritt elf → zwölf.*

![Kein weiteres Nähtischrezept ab zwölf Plätzen bei geändertem Limit](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-custom-maximum-reached.png)

*Bei zwölf endet das Zusatzrezept. Für diese beiden Rezeptbilder wurde die Werkzeugfreigabe wieder eingeschaltet; sie beeinflusst das Rezeptlimit nicht.*

Entnimm vor dem Senken von `maxSlots` die Gegenstände aus Plätzen oberhalb des neuen Limits. Die Platzabfrage wird auf das neue Maximum begrenzt; höhere Plätze können dadurch unzugänglich werden.
