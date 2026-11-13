# Troubleshooting

[Deutsch](Fehlerbehebung) · **English** · [Tool Belt Unlimited](English)

| Problem | Check |
| --- | --- |
| Minecraft will not start | Minecraft 1.21.1, NeoForge and all [dependencies](Installation-en) on client and server; use matching NeoForge builds. |
| No added recipe | Belt has at least nine slots and fewer than `maxSlots`; belt, pouch and string are in material slots, matching needle in the tool slot. |
| No original recipe below nine slots | Check Tool Belt's Sewing Kit integration and modpack recipe changes; the addon does not replace those recipes. |
| Preview always shows ten slots | The fixed preview is ten; the actual Sewing Table result is computed from the input belt. |
| Recipe stops at 20 slots | Expected by default. The server must change `maxSlots` for higher limits. |
| Item is rejected | Check actual `#c:tools` membership, `allowCommonTools` and original Tool Belt rules. `false` disables only the extra permission. |
| Blacklist appears ignored | Enabled extra permission for tagged tools runs before the original blacklist; see [Allowing tools](Werkzeuge-en). |
| Config change seems ineffective | Check the active server file and possible world override; restart the world/server. |
| Radial menu is crowded | Check GUI scale and occupied slots; extremely large belts have not been comprehensively tested. |

![Empty output at the default maximum](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-maximum-reached.png)

*A missing recipe at the configured maximum is expected behavior.*

Report reproducible problems in the [issue tracker](https://github.com/TeutonStudio/ToolBeltUnlimited/issues), including mod versions, active `maxSlots`, `allowCommonTools`, belt slots, ingredients, GUI scale and relevant log excerpts.
