package de.teutonstudio.toolbeltunlimited.recipe

import com.mojang.serialization.Codec
import com.mojang.serialization.MapCodec
import com.mojang.serialization.codecs.RecordCodecBuilder
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimited
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig
import dev.gigaherz.sewingkit.SewingKitMod
import dev.gigaherz.sewingkit.api.SewingRecipe
import dev.gigaherz.sewingkit.table.SewingInput
import dev.gigaherz.toolbelt.ToolBelt
import dev.gigaherz.toolbelt.belt.ToolBeltItem
import net.minecraft.core.HolderLookup
import net.minecraft.core.NonNullList
import net.minecraft.network.RegistryFriendlyByteBuf
import net.minecraft.network.codec.ByteBufCodecs
import net.minecraft.network.codec.StreamCodec
import net.minecraft.world.item.ItemStack
import net.minecraft.world.item.Items
import net.minecraft.world.item.crafting.Ingredient
import net.minecraft.world.item.crafting.RecipeSerializer
import net.minecraft.world.level.Level

class InfiniteSewingUpgradeRecipe(private val notificationEnabled: Boolean) : SewingRecipe(
    "toolbelt_belt_upgrade",
    sewingMaterials(),
    null,
    Ingredient.of(SewingKitMod.NETHERITE_OR_HIGHER),
    ItemStack(ToolBelt.BELT.get()),
    notificationEnabled,
) {
    override fun matches(input: SewingInput, level: Level): Boolean {
        if (!super.matches(input, level)) {
            return false
        }

        val belt = findBelt(input)
        val slots = ToolBeltItem.getSlotsCount(belt)
        return !belt.isEmpty && slots >= 9 && slots < ToolBeltUnlimitedConfig.maxSlots()
    }

    override fun assemble(input: SewingInput, provider: HolderLookup.Provider): ItemStack {
        val belt = findBelt(input)
        if (belt.isEmpty) {
            return ItemStack.EMPTY
        }

        return belt.copy().also { upgraded ->
            upgraded.count = 1
            ToolBeltItem.setSlotsCount(upgraded, ToolBeltItem.getSlotsCount(belt) + 1)
        }
    }

    override fun getResultItem(provider: HolderLookup.Provider): ItemStack = ToolBeltItem.of(10)

    override fun getSerializer(): RecipeSerializer<InfiniteSewingUpgradeRecipe> =
        ToolBeltUnlimited.INFINITE_SEWING_UPGRADE.get()

    class Serializer : RecipeSerializer<InfiniteSewingUpgradeRecipe> {
        override fun codec(): MapCodec<InfiniteSewingUpgradeRecipe> = CODEC

        override fun streamCodec(): StreamCodec<RegistryFriendlyByteBuf, InfiniteSewingUpgradeRecipe> =
            STREAM_CODEC
    }

    companion object {
        @JvmField
        val CODEC: MapCodec<InfiniteSewingUpgradeRecipe> = RecordCodecBuilder.mapCodec { instance ->
            instance.group(
                Codec.BOOL.optionalFieldOf("show_notification", true)
                    .forGetter(InfiniteSewingUpgradeRecipe::notificationEnabled),
            ).apply(instance, ::InfiniteSewingUpgradeRecipe)
        }

        @JvmField
        val STREAM_CODEC: StreamCodec<RegistryFriendlyByteBuf, InfiniteSewingUpgradeRecipe> =
            StreamCodec.composite(
                ByteBufCodecs.BOOL,
                InfiniteSewingUpgradeRecipe::notificationEnabled,
                ::InfiniteSewingUpgradeRecipe,
            )

        private fun sewingMaterials(): NonNullList<Material> = NonNullList.create<Material>().apply {
            add(Material.of(Ingredient.of(ToolBelt.BELT.get()), 1))
            add(Material.of(Ingredient.of(ToolBelt.POUCH.get()), 1))
            add(Material.of(Ingredient.of(Items.STRING), 1))
        }

        private fun findBelt(input: SewingInput): ItemStack {
            for (slot in 2 until 6) {
                val stack = input.getItem(slot)
                if (stack.item is ToolBeltItem) {
                    return stack
                }
            }
            return ItemStack.EMPTY
        }
    }
}
