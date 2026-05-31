package de.teutonstudio.toolbeltunlimited;

import de.teutonstudio.toolbeltunlimited.recipe.InfiniteSewingUpgradeRecipe;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(ToolBeltUnlimited.MODID)
public final class ToolBeltUnlimited {
    public static final String MODID = "toolbeltunlimited";

    private static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MODID);

    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<InfiniteSewingUpgradeRecipe>>
            INFINITE_SEWING_UPGRADE = RECIPE_SERIALIZERS.register(
                    "infinite_sewing_upgrade",
                    InfiniteSewingUpgradeRecipe.Serializer::new
            );

    public ToolBeltUnlimited(IEventBus modBus, ModContainer modContainer) {
        RECIPE_SERIALIZERS.register(modBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, ToolBeltUnlimitedConfig.SPEC);
    }
}
