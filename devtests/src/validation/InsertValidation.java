package validation;

import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedClientConfig;
import dev.gigaherz.toolbelt.BeltFinder;
import dev.gigaherz.toolbelt.ConfigData;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import dev.gigaherz.toolbelt.client.radial.ItemStackRadialMenuItem;
import net.minecraft.client.Minecraft;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Files;
import static validation.UiValidation.*;

final class InsertValidation {
    private static int time;
    private static int frame = -1;

    private static void client(String key, Object value) {
        ((ModConfigSpec.ConfigValue) ToolBeltUnlimitedClientConfig.SPEC.getValues().get(key)).set(value);
    }

    private static void setup(Minecraft mc, boolean displayEmpty, boolean held, boolean full) {
        prepare(mc, 20, !full, false);
        ConfigData.displayEmptySlots = displayEmpty;
        ConfigData.releaseToSwap = true;
        if (held) server(mc, p -> {
            var item = new ItemStack(Items.GOLDEN_PICKAXE);
            item.set(DataComponents.CUSTOM_NAME, Component.literal("Insertion probe"));
            p.setItemInHand(InteractionHand.MAIN_HAND, item);
            p.inventoryMenu.broadcastChanges();
        });
    }

    private static void inserted(Minecraft mc, int slot) {
        server(mc, p -> {
            var belt = BeltFinder.findBelt(p).orElseThrow().getBelt();
            var inventory = ToolBeltItem.getComponentItemHandler(belt);
            require(p.getMainHandItem().isEmpty(), "insertion empties hand for selected slot " + slot);
            require(inventory.getStackInSlot(slot).is(Items.GOLDEN_PICKAXE)
                    && inventory.getStackInSlot(slot).getHoverName().getString().equals("Insertion probe"),
                    "server inserts original item into exact slot " + slot);
            require(inventory.getStackInSlot(0).getHoverName().getString().equals("Tool 0"), "insertion preserves occupied slot 0");
            int count = 0;
            for (int i = 0; i < inventory.getSlots(); i++) if (inventory.getStackInSlot(i).is(Items.GOLDEN_PICKAXE)) count++;
            require(count == 1, "insertion creates no duplicate items");
        });
    }

    static void tick(Minecraft mc) throws Exception {
        if (mc.screen instanceof HeldRadialScreen held) {
            if (frame == held.renderSerial) return;
            frame = held.renderSerial;
            int next = time + 1;
            if ((next == 30 || next == 70 || next == 100 || next == 140 || next == 180 || next == 220 || next == 280)
                    && (!menu(mc).isReady() || menu(mc).getVisibleItemCount() == 0)) return;
        } else frame = -1;
        time++;
        if (time == 10) {
            mc.options.guiScale().set(2); mc.resizeDisplay();
            config("itemsPerRing", 9); config("maxSlots", 20); client("useRings", true); client("maxVisibleRings", 3);
            setup(mc, false, true, false);
        }
        if (time == 20) openRings(mc);
        if (time == 30) pointAt(mc, 1, 0);
        if (time == 35) { hovered(mc, 1); shot(mc, "insert-empty-slot-selected"); }
        if (time == 40) mc.screen.mouseReleased(0, 0, 0);
        if (time == 45) inserted(mc, 1);
        if (time == 50) setup(mc, false, false, false);
        if (time == 60) openRings(mc);
        if (time == 70) pointAt(mc, 1, 0);
        if (time == 75) require(menu(mc).getHoveredItem() == null, "empty hand still respects hidden empty slots");
        if (time == 80) setup(mc, true, true, false);
        if (time == 90) openRings(mc);
        if (time == 100) pointAt(mc, 10, 0);
        if (time == 105) hovered(mc, 10);
        if (time == 110) ((HeldRadialScreen) mc.screen).releaseKey();
        if (time == 115) inserted(mc, 10);
        if (time == 120) setup(mc, false, true, false);
        if (time == 130) openRings(mc);
        if (time == 140) move(mc, mc.screen.width / 2, mc.screen.height / 2);
        if (time == 145) require(menu(mc).getHoveredItem() != null && !(menu(mc).getHoveredItem() instanceof ItemStackRadialMenuItem), "center insert remains available with held item");
        if (time == 150) mc.screen.mouseReleased(0, 0, 0);
        if (time == 155) inserted(mc, 1);
        if (time == 160) setup(mc, false, true, false);
        if (time == 170) openRings(mc);
        if (time == 180) pointAt(mc, 9, 0);
        if (time == 185) hovered(mc, 9);
        if (time == 190) mc.screen.mouseReleased(0, 0, 0);
        if (time == 195) server(mc, p -> {
            require(p.getMainHandItem().getHoverName().getString().equals("Tool 9"), "occupied-slot swap still returns old tool");
            require(ToolBeltItem.getComponentItemHandler(BeltFinder.findBelt(p).orElseThrow().getBelt()).getStackInSlot(9).is(Items.GOLDEN_PICKAXE), "occupied-slot swap stores held item");
        });
        if (time == 200) setup(mc, false, true, true);
        if (time == 210) openRings(mc);
        if (time == 220) move(mc, mc.screen.width / 2, mc.screen.height / 2);
        if (time == 225) require(menu(mc).getHoveredItem() == null, "full belt has no center insert action");
        if (time == 260) {
            config("itemsPerRing", 6); config("maxSlots", 30); client("maxVisibleRings", 2);
            setup(mc, false, true, false);
        }
        if (time == 270) openRings(mc);
        if (time == 280) { mc.screen.mouseScrolled(1, 1, 0, -1); mc.screen.mouseScrolled(1, 1, 0, -1); pointAt(mc, 13, 2); }
        if (time == 285) hovered(mc, 13);
        if (time == 290) mc.screen.mouseReleased(0, 0, 0);
        if (time == 295) inserted(mc, 13);
        if (time == 300) {
            config("itemsPerRing", 9); config("maxSlots", 20); client("maxVisibleRings", 3);
            Files.writeString(mc.gameDirectory.toPath().resolve("ui-validation-success.txt"), "UI_VALIDATION_COMPLETE\n");
            System.out.println("INSERT_UI_VALIDATION_COMPLETE"); System.out.println("UI_VALIDATION_COMPLETE"); mc.stop();
        }
    }
}
