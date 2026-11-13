# Einstellungen

**Deutsch** · [English](Einstellungen-en) · [Tool Belt Unlimited](Home)

Die Einstellungen sind serverseitig und werden für die Sitzung an Clients übertragen. Im Einzelspieler übernimmt der integrierte Server diese Rolle.

![Servereinstellungen mit den Standardwerten true und 20](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-settings-default.png)

*Native NeoForge-Konfigurationsoberfläche in einer geladenen Welt, Standardwerte. Oben: `allowCommonTools` auf „An“; darunter: `maxSlots` auf 20. Die unübersetzten Beschriftungen werden von NeoForge gekürzt.*

| Schlüssel | Standard | Bedeutung |
| --- | --- | --- |
| `maxSlots` | `20` | Maximale Gürtelplätze; gültiger Bereich `9` bis `2147483647`. |
| `allowCommonTools` | `true` | Zusätzliche Freigabe für `#c:tools`; mit `false` gelten nur die ursprünglichen Regeln. |

Der große zulässige Zahlenbereich ist eine technische Eingabegrenze, keine Zusage für Spielbarkeit oder Leistung bei extremen Werten. Ein Gürtel erhält durch Erhöhen des Limits keine Plätze automatisch.

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
