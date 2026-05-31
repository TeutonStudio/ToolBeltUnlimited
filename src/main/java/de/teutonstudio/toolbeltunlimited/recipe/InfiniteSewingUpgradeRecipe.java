package de.teutonstudio.toolbeltunlimited.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimited;
import de.teutonstudio.toolbeltunlimited.ToolBeltUnlimitedConfig;
import dev.gigaherz.sewingkit.SewingKitMod;
import dev.gigaherz.sewingkit.api.SewingRecipe;
import dev.gigaherz.sewingkit.table.SewingInput;
import dev.gigaherz.toolbelt.ToolBelt;
import dev.gigaherz.toolbelt.belt.ToolBeltItem;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public final class InfiniteSewingUpgradeRecipe extends SewingRecipe {
    public static final MapCodec<InfiniteSewingUpgradeRecipe> CODEC =
            RecordCodecBuilder.mapCodec(instance -> instance.group(
                    Codec.BOOL.optionalFieldOf("show_notification", true)
                            .forGetter(InfiniteSewingUpgradeRecipe::showNotification)
            ).apply(instance, InfiniteSewingUpgradeRecipe::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, InfiniteSewingUpgradeRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.BOOL,
                    InfiniteSewingUpgradeRecipe::showNotification,
                    InfiniteSewingUpgradeRecipe::new
            );

    public InfiniteSewingUpgradeRecipe(boolean showNotification) {
        super(
                "toolbelt_belt_upgrade",
                sewingMaterials(),
                null,
                Ingredient.of(SewingKitMod.NETHERITE_OR_HIGHER),
                new ItemStack(ToolBelt.BELT.get()),
                showNotification
        );
    }

    private static NonNullList<SewingRecipe.Material> sewingMaterials() {
        NonNullList<SewingRecipe.Material> materials = NonNullList.create();
        materials.add(SewingRecipe.Material.of(Ingredient.of(ToolBelt.BELT.get()), 1));
        materials.add(SewingRecipe.Material.of(Ingredient.of(ToolBelt.POUCH.get()), 1));
        materials.add(SewingRecipe.Material.of(Ingredient.of(Items.STRING), 1));
        return materials;
    }

    @Override
    public boolean matches(SewingInput input, Level level) {
        if (!super.matches(input, level)) {
            return false;
        }

        ItemStack belt = findBelt(input);
        int slots = ToolBeltItem.getSlotsCount(belt);
        return !belt.isEmpty() && slots >= 9 && slots < ToolBeltUnlimitedConfig.maxSlots();
    }

    @Override
    public ItemStack assemble(SewingInput input, HolderLookup.Provider provider) {
        ItemStack belt = findBelt(input);
        if (belt.isEmpty()) {
            return ItemStack.EMPTY;
        }

        ItemStack upgraded = belt.copy();
        upgraded.setCount(1);
        ToolBeltItem.setSlotsCount(upgraded, ToolBeltItem.getSlotsCount(belt) + 1);
        return upgraded;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider provider) {
        return ToolBeltItem.of(10);
    }

    private static ItemStack findBelt(SewingInput input) {
        for (int slot = 2; slot < 6; slot++) {
            ItemStack stack = input.getItem(slot);
            if (stack.getItem() instanceof ToolBeltItem) {
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public RecipeSerializer<InfiniteSewingUpgradeRecipe> getSerializer() {
        return ToolBeltUnlimited.INFINITE_SEWING_UPGRADE.get();
    }

    public static final class Serializer implements RecipeSerializer<InfiniteSewingUpgradeRecipe> {
        @Override
        public MapCodec<InfiniteSewingUpgradeRecipe> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, InfiniteSewingUpgradeRecipe> streamCodec() {
            return STREAM_CODEC;
        }
    }
}
