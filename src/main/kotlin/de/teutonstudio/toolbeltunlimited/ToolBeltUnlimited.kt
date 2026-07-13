package de.teutonstudio.toolbeltunlimited

import de.teutonstudio.toolbeltunlimited.recipe.InfiniteSewingUpgradeRecipe
import net.minecraft.core.registries.BuiltInRegistries
import net.minecraft.world.item.crafting.RecipeSerializer
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.ModContainer
import net.neoforged.fml.common.Mod
import net.neoforged.fml.config.ModConfig
import net.neoforged.neoforge.registries.DeferredHolder
import net.neoforged.neoforge.registries.DeferredRegister
import java.util.function.Supplier

@Mod(ToolBeltUnlimited.MOD_ID)
class ToolBeltUnlimited(modBus: IEventBus, modContainer: ModContainer) {
    init {
        RECIPE_SERIALIZERS.register(modBus)
        modContainer.registerConfig(ModConfig.Type.SERVER, ToolBeltUnlimitedConfig.SPEC)
    }

    companion object {
        const val MOD_ID = "toolbeltunlimited"

        private val RECIPE_SERIALIZERS: DeferredRegister<RecipeSerializer<*>> =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, MOD_ID)

        @JvmField
        val INFINITE_SEWING_UPGRADE:
            DeferredHolder<RecipeSerializer<*>, RecipeSerializer<InfiniteSewingUpgradeRecipe>> =
            RECIPE_SERIALIZERS.register(
                "infinite_sewing_upgrade",
                Supplier { InfiniteSewingUpgradeRecipe.Serializer() },
            )
    }
}
