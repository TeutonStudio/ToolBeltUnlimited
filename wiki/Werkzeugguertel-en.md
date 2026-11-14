# Using the belt

[Deutsch](Werkzeugguertel) · **English** · [Tool Belt Unlimited](English)

The addon raises the previous nine-slot limit. **20 is the default maximum**, not an automatically fully upgraded belt. Each successful upgrade adds one slot. Below nine slots, use Tool Belt's existing recipes; after that, continue at the [Sewing Table](Upgrades-en).

## Store and select

- Use Tool Belt's normal controls; sneak and right-click while holding the belt to open its inventory.
- Place allowed tools into the belt slots.
- Use Tool Belt's radial menu to switch tools. Check **Options → Controls → Key Binds → Tool Belt** for the actual bindings and conflicts.

## Grid and ring layers since 1.4.0

The belt inventory defaults to nine columns. Since 1.5.0, `itemsPerRing` sets both the column and ring-slot count; see [Settings](Einstellungen-en). Each row corresponds to exactly one selection ring: row 1 is the innermost ring, row 2 the second ring, and so on. The first column sits at the top; the remaining columns proceed clockwise. Empty slots keep their angles even when hidden by Tool Belt. A partial final row produces a partial ring with the same angular positions.

When the screen cannot fit every row or ring, use the mouse wheel to scroll through the layers. Ring numbers still refer to the original inventory rows. Tool Belt's mouse click, release-to-swap and cycle controls remain available.

![Twenty belt slots arranged in three rows](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/grid-20.png)

*Local development build 1.4.0: nine, nine and two slots. The numbers on the right identify their ring layers.*

![Three ring layers with sparse contents](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-20-sparse.png)

*Four tools in their actual slots 1, 10, 19 and 20. Hidden empty slots keep dimmed positions and cannot be selected.*

![An empty slot selected in the second ring](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-20-empty-slots.png)

*When Tool Belt displays empty slots, the empty slot in row 2, column 2 can also be selected.*

### Many layers

![Last rows of a ninety-slot belt](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/grid-90-scrolled.png)

![Last ring layers of a ninety-slot belt](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/rings-90-scrolled.png)

*Prepared test belt with the limit raised to 90, requested GUI scale 4 and Minecraft's actual available scaling. The mouse wheel reaches the final row and tenth ring. This is a controlled UI test with an integrated server.*

## Eyes and rotating ring layers since 1.5.0

Each inventory row has an eye. Click it to hide that row from ring selection; click again to show it. Items remain accessible in the inventory. Scrolling and keyboard cycling skip hidden rows. All rows may be hidden; the inventory eyes can always restore them.

![Second inventory row hidden using its eye](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-grid-six-eyes.png)

![Ring selection skips the hidden row](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-skip-row.png)

*With six slots per row and at most two visible rings, rows 1 and 3 appear first; row 2 is hidden.*

![One step shifts the rings inward](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-shift-inward.png)

*Scrolling down removes the innermost ring, shifts the second ring inward and introduces the next enabled row outside. Scrolling up reverses this: the outer ring leaves and the previous row appears inside.*

![Wrapping from the last enabled row to the first](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/config-rings-wrapped.png)

*After the final enabled row, the first returns. Displayed numbers still identify original inventory rows. When all enabled rows already fit on screen, scrolling leaves the arrangement in place.*

## Direct insertion since 1.5.1

Hold an allowed item and select an empty ring slot to store it in that exact position. Empty slots become selectable even with Tool Belt's empty-slot display disabled. With an empty hand, the original display option still applies. Row eyes remain active; center insertion and occupied-slot swapping continue to work.

![Selecting an empty ring slot for the held item](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/insert-empty-slot-selected.png)

*In-game check with empty-slot display disabled: slot 2 is selected for insertion. Clicking and release-to-swap were checked against actual server results, including an insertion target after scrolling.*

## Historical captures

The following images show the earlier interface before the grid and ring layers.

![Twenty-slot belt inventory containing twelve tools](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-common-tools-enabled.png)

*A 20-slot belt with twelve occupied slots. For this comparison, Tool Belt's original automatic permission for non-stackable items is disabled and the addon's extra permission is enabled.*

![Radial menu containing more than nine tools](https://raw.githubusercontent.com/wiki/TeutonStudio/ToolBeltUnlimited/assets/screenshots/wiki-radial-14.png)

*Existing in-game capture with 14 tools in the radial menu.*

In the old interface, the 20-slot row extends beyond the original inventory background. Since 1.4.0 the grid replaces that single row. These historical captures still document storage and the previous selection interface; they do not show the new layout.
