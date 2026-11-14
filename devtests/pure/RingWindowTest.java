import de.teutonstudio.toolbeltunlimited.client.RingWindow;
import java.util.Set;
import java.util.Arrays;

public final class RingWindowTest {
    private static void order(RingWindow w, int capacity, int... expected) {
        int[] actual = java.util.stream.IntStream.range(0, w.visibleCount(capacity)).map(w::rowAt).toArray();
        if (!Arrays.equals(actual, expected)) throw new AssertionError(Arrays.toString(actual) + " != " + Arrays.toString(expected));
    }
    public static void main(String[] args) {
        var w = new RingWindow();
        w.update(5, Set.of(1));
        order(w, 2, 0, 2);
        w.scroll(-1, 2); order(w, 2, 2, 3);
        w.scroll(1, 2); order(w, 2, 0, 2);
        w.scroll(1, 2); order(w, 2, 4, 0);
        w.scroll(-1, 2); order(w, 2, 0, 2);
        w.focus(3, 2); order(w, 2, 3, 4);
        w.update(5, Set.of(1, 3)); order(w, 2, 4, 0);
        if (w.localIndex(1, 2) != -1 || w.localIndex(4, 2) != 0) throw new AssertionError("hidden row hit testing");
        w.update(5, Set.of(0, 1, 2, 3, 4)); order(w, 2);
        if (w.scroll(-1, 2) || w.localIndex(0, 2) != -1) throw new AssertionError("all hidden");
        w.update(1, Set.of()); order(w, 1, 0);
        if (w.scroll(-1, 1)) throw new AssertionError("all rows already visible");
        w.update(5, Set.of(1)); w.scroll(-1, 1); order(w, 1, 2);
        for (int i = 0; i < 1000; i++) { w.scroll(-1, 2); if (w.rowAt(0) == 1) throw new AssertionError("hidden ring returned"); }
        System.out.println("RING_WINDOW_TESTS_PASSED: both directions, wrap, hidden rows, all hidden, focus and capacity");
    }
}
