package de.teutonstudio.toolbeltunlimited;

/** Shared slot order for the inventory grid and the concentric selection rings. */
public final class BeltLayout {
    public static final int SLOT_SIZE = 18;

    private BeltLayout() {}

    public static int columns() { return ToolBeltUnlimitedConfig.INSTANCE.itemsPerRing(); }

    public static int rows(int slots) {
        return slots <= 0 ? 0 : 1 + (slots - 1) / columns();
    }

    public static int row(int slot) {
        return slot / columns();
    }

    public static int column(int slot) {
        return slot % columns();
    }

    // A partial final row keeps the same angular positions as every full row.
    public static double angle(int slot) {
        return -Math.PI / 2 + column(slot) * Math.PI * 2 / columns();
    }

    public static int columnAt(double angle) {
        double turn = (angle + Math.PI / 2) / (Math.PI * 2);
        return Math.floorMod((int) Math.floor(turn * columns() + 0.5), columns());
    }
}
