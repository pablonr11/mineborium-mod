package com.mundobachata.mineborium.datagen;

import com.mundobachata.mineborium.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output) {
        super(output);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        // TODO: Test int parameter in shaped() method. It should be the amount.
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CIGARETTE_FILTER.get())
                .define('#', Items.STRING)
                .pattern("#")
                .pattern("#")
                .pattern("#")
                .unlockedBy("has_string", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.STRING).build()))
                .save(consumer);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CIGARETTE.get())
                .define('P', Items.PAPER)
                .define('F', ModItems.CIGARETTE_FILTER.get())
                .define('M', ModItems.MALBORIUM.get())
                .pattern("PMP")
                .pattern("PMP")
                .pattern("PFP")
                .unlockedBy("has_malborium", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.MALBORIUM.get()).build()))
                .save(consumer);
    }
}
