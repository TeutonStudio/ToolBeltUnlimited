# Sewing Table upgrades

[Deutsch](Upgrades) · **English** · [Tool Belt Unlimited](English)

## Up to nine slots

Tool Belt's original upgrades remain available. The added recipe starts with belts that already have at least nine slots.

![Original Sewing Table recipe upgrading eight slots to nine](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-8-to-9.png)

*Tool Belt's original recipe turns the prepared eight-slot belt into a nine-slot belt.*

## From nine slots onward

Place these ingredients in the Sewing Kit table's material slots:

| Ingredient | Quantity |
| --- | --- |
| Tool Belt with at least nine slots and below the configured maximum | 1 |
| Tool Belt Pouch (`toolbelt:pouch`) | 1 |
| String (`minecraft:string`) | 1 |

Put a **Netherite Sewing Needle or better**, tagged `#sewingkit:needles/netherite_or_higher`, in the tool slot. The added recipe does not require a pattern. Select the belt recipe and take the output. Repeat to add further slots.

![Dynamic Sewing Table recipe upgrading nine slots to ten](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-9-to-10.png)

*The first added step produces ten slots. The recipe always computes input slots + 1.*

![Dynamic Sewing Table recipe upgrading 19 slots to 20](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-upgrade-19-to-20.png)

*The same recipe type produces 20 slots when given the prepared 19-slot belt.*

## Contents are preserved

The output copies the input belt and its components, then increases its slot count by one. Fresh captures use a belt already containing a diamond pickaxe; the capture run checked that the output retained it. The custom name is also retained.

![Ten-slot output belt retaining its diamond pickaxe](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-contents-retained.png)

*Opened nine-to-ten recipe output: the previously stored pickaxe remains in the belt.*

## At the maximum

![No further upgrade output for a twenty-slot belt](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-maximum-reached.png)

*With `maxSlots = 20`, a 20-slot belt has no further added recipe. Read this alongside the previous image's “20 Plätze” (20 slots) tooltip.*

The recipe type's fixed preview shows ten slots; the actual Sewing Table output depends on your input belt. The addon raises the configurable maximum; upgrades still require materials.
