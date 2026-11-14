package de.teutonstudio.toolbeltunlimited.mixin;

import de.teutonstudio.toolbeltunlimited.SlotLayoutAccess;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Slot.class)
public abstract class SlotMixin implements SlotLayoutAccess {
    @Unique private boolean toolbeltunlimited$visible = true;

    @Mutable @Accessor("x")
    public abstract void toolbeltunlimited$setX(int x);

    @Mutable @Accessor("y")
    public abstract void toolbeltunlimited$setY(int y);

    @Override
    public void toolbeltunlimited$setVisible(boolean visible) {
        toolbeltunlimited$visible = visible;
    }

    @Inject(method = "isActive", at = @At("HEAD"), cancellable = true)
    private void toolbeltunlimited$hideScrolledSlots(CallbackInfoReturnable<Boolean> cir) {
        if (!toolbeltunlimited$visible) cir.setReturnValue(false);
    }
}
