package de.teutonstudio.toolbeltunlimited.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/** A rotating window over enabled inventory rows, keeping their original row identities. */
public final class RingWindow {
    private List<Integer> rows = List.of();
    private int first;

    public void update(int total, Set<Integer> hidden) {
        int anchor = rows.isEmpty() ? 0 : rows.get(first);
        var next = new ArrayList<Integer>();
        for (int row = 0; row < total; row++) if (!hidden.contains(row)) next.add(row);
        if (next.equals(rows)) return;
        rows = List.copyOf(next);
        first = 0;
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i) >= anchor) { first = i; break; }
        }
    }

    public int count() { return rows.size(); }
    public boolean contains(int row) { return rows.contains(row); }
    public int visibleCount(int capacity) { return Math.min(rows.size(), capacity); }
    public int rowAt(int local) { return rows.get(Math.floorMod(first + local, rows.size())); }

    public int localIndex(int row, int capacity) {
        int index = rows.indexOf(row);
        if (index < 0) return -1;
        int local = Math.floorMod(index - first, rows.size());
        return local < visibleCount(capacity) ? local : -1;
    }

    public boolean scroll(double amount, int capacity) {
        if (amount == 0 || rows.size() <= capacity) return false;
        first = Math.floorMod(first - (int) Math.signum(amount), rows.size());
        return true;
    }

    public void focus(int row, int capacity) {
        int index = rows.indexOf(row);
        if (index >= 0 && localIndex(row, capacity) < 0) first = index;
    }
}
