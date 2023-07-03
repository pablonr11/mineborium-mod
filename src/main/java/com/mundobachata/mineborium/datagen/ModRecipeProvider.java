package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.Mineborium;
import com.mundobachata.mineborium.block.ModBlocks;
import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CIGARETTE_FILTER.get())
                .define('#', Items.STRING)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_string", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STRING).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CIGARETTE.get())
                .define('P', ModItems.ROLLING_PAPER.get())
                .define('F', ModItems.CIGARETTE_FILTER.get())
                .define('M', ModItems.MARLBORIUM.get())
                .pattern("PMP")
                .pattern("PMP")
                .pattern("PFP")
                .unlockedBy("has_marlborium", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.MARLBORIUM.get()).build()))
                .save(consumer);

        twoByTwoPacker(consumer, RecipeCategory.MISC, ModItems.MARLBORIUM.get(),
                ModItems.MARLBORIUM_NUGGET.get());
        oneToOneConversionRecipe(consumer, ModItems.ROLLING_PAPER.get(), Items.PAPER, "rolling_paper", 4);
        nineBlockStorageRecipes(consumer, RecipeCategory.MISC, ModItems.MARLBORIUM.get(),
                RecipeCategory.MISC, ModBlocks.MARLBORIUM_BLOCK.get(), "marlborium2",
                null, "compact_marlborium_block", null);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_ARROW.get(), 8)
                .define('A', Items.ARROW)
                .define('C', ModItems.CIGARETTE.get())
                .pattern("AAA")
                .pattern("ACA")
                .pattern("AAA")
                .unlockedBy("has_cigarette", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CIGARETTE.get()).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_SWORD.get(), 1)
                .define('S', Items.STICK)
                .define('M', ModBlocks.MARLBORIUM_BLOCK.get())
                .pattern("M")
                .pattern("M")
                .pattern("S")
                .unlockedBy("has_compact_marlborium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MARLBORIUM_BLOCK.get()).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_PICKAXE.get(), 1)
                .define('S', Items.STICK)
                .define('M', ModBlocks.MARLBORIUM_BLOCK.get())
                .pattern("MMM")
                .pattern(" S ")
                .pattern(" S ")
                .unlockedBy("has_compact_marlborium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MARLBORIUM_BLOCK.get()).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_AXE.get(), 1)
                .define('S', Items.STICK)
                .define('M', ModBlocks.MARLBORIUM_BLOCK.get())
                .pattern("MM")
                .pattern("MS")
                .pattern(" S")
                .unlockedBy("has_compact_marlborium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MARLBORIUM_BLOCK.get()).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_SHOVEL.get(), 1)
                .define('S', Items.STICK)
                .define('M', ModBlocks.MARLBORIUM_BLOCK.get())
                .pattern("M")
                .pattern("S")
                .pattern("S")
                .unlockedBy("has_compact_marlborium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MARLBORIUM_BLOCK.get()).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.MARLBORIUM_HOE.get(), 1)
                .define('S', Items.STICK)
                .define('M', ModBlocks.MARLBORIUM_BLOCK.get())
                .pattern("MM")
                .pattern(" S")
                .pattern(" S")
                .unlockedBy("has_compact_marlborium_block", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.MARLBORIUM_BLOCK.get()).build()))
                .save(consumer);
    }

    protected static void twoByTwoPacker(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory, ItemLike itemLike, ItemLike itemLike1) {
        ShapedRecipeBuilder.shaped(recipeCategory, itemLike, 1).define('#', itemLike1).pattern("##").pattern("##").unlockedBy(getHasName(itemLike1), has(itemLike1)).save(consumer);
    }

    protected static void oneToOneConversionRecipe(Consumer<FinishedRecipe> consumer, ItemLike crafted, ItemLike materials, @Nullable String group, int quantity) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, crafted, quantity).requires(materials).group(group).unlockedBy(getHasName(materials), has(materials)).save(consumer, getConversionRecipeName(crafted, materials));
    }

    protected static void nineBlockStorageRecipes(Consumer<FinishedRecipe> consumer, RecipeCategory recipeCategory1,
                                                  ItemLike required, RecipeCategory recipeCategory2,
                                                  ItemLike crafted, String resource2,
                                                  @Nullable String group2, String resource1, @Nullable String group1) {
        ShapelessRecipeBuilder.shapeless(recipeCategory1, required, 9).requires(crafted).group(group1).unlockedBy(getHasName(crafted), has(crafted)).save(consumer, new ResourceLocation(Mineborium.MOD_ID, resource1));
        ShapedRecipeBuilder.shaped(recipeCategory2, crafted).define('#', required).pattern("###").pattern("###").pattern("###").group(group2).unlockedBy(getHasName(required), has(required)).save(consumer, new ResourceLocation(Mineborium.MOD_ID, resource2));
    }
}
