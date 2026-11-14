package de.teutonstudio.toolbeltunlimited;

import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;

import java.util.Set;
import java.util.TreeSet;

/** Namespaced custom data travels with the belt and is copied by its upgrade recipes. */
public final class BeltRows {
    private static final String KEY = "toolbeltunlimited:hidden_rows";
    private BeltRows() {}

    public static Set<Integer> hidden(ItemStack belt) {
        Set<Integer> rows = new TreeSet<>();
        for (int row : belt.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY).copyTag().getIntArray(KEY)) {
            if (row >= 0) rows.add(row);
        }
        return rows;
    }

    public static void toggle(ItemStack belt, int row) {
        Set<Integer> rows = hidden(belt);
        if (!rows.remove(row)) rows.add(row);
        CustomData.update(DataComponents.CUSTOM_DATA, belt, tag -> {
            if (rows.isEmpty()) tag.remove(KEY);
            else tag.putIntArray(KEY, rows.stream().mapToInt(Integer::intValue).toArray());
        });
    }
}
