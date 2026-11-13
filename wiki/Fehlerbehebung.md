# Fehlerbehebung

**Deutsch** · [English](Fehlerbehebung-en) · [Tool Belt Unlimited](Home)

| Problem | Prüfen |
| --- | --- |
| Minecraft startet nicht | Minecraft 1.21.1, NeoForge und sämtliche [Abhängigkeiten](Installation) auf Client und Server; keine Fabric-/Forge-Ausgabe verwenden. |
| Kein Zusatzrezept | Gürtel hat mindestens neun Plätze und weniger als `maxSlots`; Gürtel, Pouch und Faden liegen in Materialplätzen, passende Nadel im Werkzeugplatz. |
| Kein Originalrezept unter neun Plätzen | Tool Belts eigene Sewing-Kit-Integration und Modpack-Rezeptänderungen prüfen; das Addon ersetzt diese Rezepte nicht. |
| Rezeptvorschau zeigt immer zehn Plätze | Die feste Vorschau ist zehn; das tatsächliche Nähtischergebnis wird aus dem Eingabegürtel berechnet. |
| Bei 20 Plätzen endet das Rezept | Standardmäßig beabsichtigt. Für höhere Werte muss der Server `maxSlots` ändern. |
| Item wird abgewiesen | Tatsächlichen `#c:tools`-Tag, `allowCommonTools` und ursprüngliche Tool-Belt-Regeln prüfen. `false` deaktiviert nur die Zusatzfreigabe. |
| Blacklist scheint ignoriert | Die eingeschaltete Zusatzfreigabe für getaggte Werkzeuge läuft vor der ursprünglichen Blacklist; [Werkzeuge](Werkzeuge) erklärt die Reihenfolge. |
| Config-Änderung scheint wirkungslos | Aktive Serverdatei und mögliche Weltüberschreibung prüfen; Welt/Server neu starten. |
| Auswahlrad ist unübersichtlich | GUI-Größe und Anzahl belegter Plätze prüfen; sehr große Gürtel sind nicht umfassend erprobt. |

![Leeres Ergebnisfeld an der Standardobergrenze](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-maximum-reached.png)

*Ein fehlendes Rezept an der Obergrenze ist korrektes Verhalten.*

Melde reproduzierbare Probleme mit Modversionen, aktivem `maxSlots`, `allowCommonTools`, Gürtelplätzen, Zutaten, GUI-Größe und relevantem Logauszug im [Issue-Tracker](https://github.com/TeutonStudio/ToolBeltUnlimited/issues).
