package de.teutonstudio.toolbeltunlimited.client;

import dev.gigaherz.toolbelt.common.BeltScreen;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = "toolbeltunlimited", value = Dist.CLIENT)
public final class ClientScreens {
    @SubscribeEvent
    public static void open(ScreenEvent.Opening event) {
        if (event.getNewScreen() instanceof BeltScreen screen && Minecraft.getInstance().player != null) {
            event.setNewScreen(new BeltGridScreen(screen.getMenu(), Minecraft.getInstance().player.getInventory(), screen.getTitle()));
        }
    }
}
