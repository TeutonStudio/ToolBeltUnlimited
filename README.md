# Tool Belt Unlimited

Tool Belt Unlimited ist eine kleine NeoForge-Erweiterung fuer Minecraft 1.21.1.
Der Mod erweitert die Tool-Belt-Upgrades ueber die urspruengliche 9-Slot-Grenze hinaus und fuegt dafuer ein dynamisches Sewing-Kit-Rezept hinzu.

## Funktionen

- erhöht das Tool-Belt-Slotlimit standardmässig auf 20 Slots
- macht das maximale Slotlimit über die Server-Config anpassbar
- behült das normale Tool-Belt-Upgrade-Verhalten bis 9 Slots bei
- ermöglicht ab 9 Slots weitere Upgrades am Sewing Table
- erlaubt standardmässig alle Items mit dem Tag `#c:tools` im Tool Belt
- macht die zusätzliche `#c:tools`-Freigabe über die Server-Config abschaltbar

## Rezept ab 9 Slots

- Tool Belt mit mindestens 9 Slots
- Tool Belt Pouch
- String
- Netherite-oder-bessere Sewing Needle (`#sewingkit:needles/netherite_or_higher`)

Das Ergebnis wird dynamisch berechnet: Slotanzahl des Eingabe-Tool-Belts plus 1.
Das Rezept ist nur verfügbar, solange der Tool Belt unter dem konfigurierten Maximum liegt.

## Server-Config

Die Server-Config wird von NeoForge unter `serverconfig/toolbeltunlimited-server.toml` erzeugt.

- `maxSlots`: maximales Tool-Belt-Slotlimit, Standard `20`, Minimum `9`
- `allowCommonTools`: erlaubt Items mit `#c:tools` im Tool Belt, Standard `true`

## Abhaengigkeiten

Dieser Mod muss auf dem Client und Server installiert sein.

- Minecraft `1.21.1`
- NeoForge `21.1.230` oder kompatibel
- Tool Belt `1.21.1-2.2.9`
- Sewing Kit `1.21-2.0.9`

## Bekannte Einschraenkungen

Tool Belt wurde ursprünglich für maximal 9 Slots entworfen. Höhere Slotzahlen funktionieren, können je nach UI-Situation aber unübersichtlicher werden.
Die aktuelle Version wurde im Spiel mit dem Standardlimit von 20 Slots und gui-größe 2 getestet.

## Build aus dem Source Code

```bash
./gradlew build
```

Die gebaute Jar liegt danach unter `build/libs/`.

Die Abhängigkeiten werden über CurseMaven bezogen; lokale JARs in `libs/` sind nicht erforderlich.
