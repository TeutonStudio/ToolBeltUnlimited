package de.teutonstudio.toolbeltunlimited.mixin;

import de.teutonstudio.toolbeltunlimited.BeltLayout;
import de.teutonstudio.toolbeltunlimited.SlotLayoutAccess;
import de.teutonstudio.toolbeltunlimited.BeltMenuAccess;
import de.teutonstudio.toolbeltunlimited.BeltRows;
import dev.gigaherz.toolbelt.BeltFinder;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import dev.gigaherz.toolbelt.common.BeltContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeltContainer.class, remap = false)
public abstract class BeltContainerMixin extends AbstractContainerMenu implements BeltMenuAccess {
    @Shadow @Final private int blockedSlot;
    @Unique private ItemStack toolbeltunlimited$initialBelt;

    protected BeltContainerMixin(MenuType<?> type, int id) { super(type, id); }

    @Override
    public ItemStack toolbeltunlimited$getBelt(Player player) {
        return blockedSlot >= 0 ? player.getInventory().getItem(blockedSlot)
                : BeltFinder.findBelt(player).map(BeltFinder.BeltGetter::getBelt).orElse(toolbeltunlimited$initialBelt);
    }

    @Override
    public boolean clickMenuButton(Player player, int row) {
        BeltContainer menu = (BeltContainer) (Object) this;
        if (player.containerMenu != menu || row < 0 || row >= BeltLayout.rows(menu.beltSlots)) return false;
        ItemStack belt = toolbeltunlimited$getBelt(player);
        if (!(belt.getItem() instanceof ToolBeltItem) || ToolBeltItem.getSlotsCount(belt) != menu.beltSlots) return false;
        BeltRows.toggle(belt, row);
        player.getInventory().setChanged();
        menu.broadcastChanges();
        if (!player.level().isClientSide) BeltFinder.sendSync(player);
        return true;
    }
    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;ILnet/minecraft/world/item/ItemStack;)V",
            at = @At("RETURN"))
    private void toolbeltunlimited$grid(int id, Inventory inventory, int blockedSlot, ItemStack belt, CallbackInfo ci) {
        BeltContainer menu = (BeltContainer) (Object) this;
        toolbeltunlimited$initialBelt = belt;
        int extraHeight = (BeltLayout.rows(menu.beltSlots) - 1) * BeltLayout.SLOT_SIZE;
        for (int i = 0; i < menu.slots.size(); i++) {
            Slot slot = menu.slots.get(i);
            SlotLayoutAccess layout = (SlotLayoutAccess) slot;
            if (i < menu.beltSlots) {
                layout.toolbeltunlimited$setX(8 + BeltLayout.column(i) * BeltLayout.SLOT_SIZE);
                layout.toolbeltunlimited$setY(20 + BeltLayout.row(i) * BeltLayout.SLOT_SIZE);
            } else {
                layout.toolbeltunlimited$setY(slot.y + extraHeight);
            }
        }
    }
}
