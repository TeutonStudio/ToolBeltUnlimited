# Werkzeuge zulassen

**Deutsch** · [English](Werkzeuge-en) · [Tool Belt Unlimited](Home)

Mit `allowCommonTools = true` lässt das Addon Gegenstände mit dem gemeinsamen Werkzeugtag **`#c:tools`** zu. Der Standard ist `true`. Entscheidend ist der Tag, nicht der angezeigte Name oder das Aussehen eines Gegenstands.

![Zwölf getaggte Werkzeuge werden mit eingeschalteter Zusatzfreigabe im Gürtel gelagert](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-enabled.png)

*Zwölf tatsächlich getaggte Werkzeuge im Gürtel, darunter Spitzhacke, Axt, Schaufel, Hacke, Schwert, Schere, Pinsel, Schild, Dreizack, Bogen, Armbrust und Angel.*

## Freigabe ausschalten

Setze `allowCommonTools = false` in den [Einstellungen](Einstellungen). Dann entscheidet die ursprüngliche Tool-Belt-Prüfung. Das ist **keine generelle Werkzeugsperre**: Ein Werkzeug kann nach den ursprünglichen Regeln weiterhin erlaubt sein.

![Ohne beide automatischen Freigaben verbleiben dieselben Werkzeuge im Spielerinventar](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-disabled.png)

*Kontrollierter Vergleich: In beiden Werkzeugbildern ist `allowAllNonStackableItems` von Tool Belt ausgeschaltet und die ursprüngliche Whitelist leer. Bei deaktivierter Zusatzfreigabe werden dieselben zwölf Werkzeuge abgewiesen und liegen deshalb unten im Spielerinventar. Das ist kein Bild der unveränderten Tool-Belt-Standardeinstellungen.*

## Zusammenspiel mit Tool-Belt-Regeln

Die Zusatzfreigabe prüft `#c:tools` vor den ursprünglichen Zulassungsregeln. Bei eingeschalteter Zusatzfreigabe kann sie daher auch die ursprüngliche Blacklist für getaggte Werkzeuge übergehen. Nutze `false`, wenn ausschließlich Tool Belts eigene Regeln entscheiden sollen. Leere Stacks und Tool-Belt-Gegenstände werden vom Addon nicht zusätzlich freigegeben; für sie bleibt die ursprüngliche Prüfung zuständig.
