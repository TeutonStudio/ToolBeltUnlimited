# Allowing tools

[Deutsch](Werkzeuge) · **English** · [Tool Belt Unlimited](English)

With `allowCommonTools = true`, the addon accepts items with the common tool tag **`#c:tools`**. The default is `true`. Tag membership matters, rather than an item's displayed name or appearance.

![Twelve tagged tools stored in the belt with the extra permission enabled](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-enabled.png)

*Twelve actual tagged tools in the belt: pickaxe, axe, shovel, hoe, sword, shears, brush, shield, trident, bow, crossbow and fishing rod.*

## Disable the extra permission

Set `allowCommonTools = false` in [Settings](Einstellungen-en). Tool Belt's original acceptance check then decides. This **does not ban every tool**: the original rules may still allow an item.

![The same tools remain in player inventory when both automatic permissions are disabled](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-disabled.png)

*Controlled comparison: both tool captures disable Tool Belt's `allowAllNonStackableItems` and leave its whitelist empty. With the addon's extra permission also disabled, the same twelve tools are rejected and remain in the player inventory below. This does not show unchanged Tool Belt defaults.*

## Interaction with Tool Belt rules

The extra permission checks `#c:tools` before the original acceptance rules. When enabled, it can therefore also override Tool Belt's original blacklist for tagged tools. Use `false` if only Tool Belt's own rules should decide. The addon does not grant extra permission to empty stacks or Tool Belt items; these continue through the original check.
