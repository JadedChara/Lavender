package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.GlazedTerracottaBlock;
import net.minecraft.world.level.block.WallBannerBlock;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class LavenderRecipeProvider extends FabricRecipeProvider {

    public LavenderRecipeProvider(FabricDataGenerator g) {
        super(g);
    }

    @Override
    protected void generateRecipes(Consumer<FinishedRecipe> consumer) {

        //Non-dye-based
        for(Block product : LavenderBlocks.LAVENDER_BLOCKS){

            //TERRACOTTA
            if(product.getName().getString().endsWith("_glazed_terracotta")){
                for(Block uncooked : LavenderBlocks.LAVENDER_BLOCKS){
                    if(
                            uncooked.getName().getString().equals(
                                    product.getName().getString().replaceAll("_glazed","")
                            )
                    ){
                        RecipeProvider.smeltingResultFromBase(consumer,uncooked,product);
                    }
                }
            }

            //GLASS
            else if(product.getName().getString().endsWith("_pane")){
                for(Block solid : LavenderBlocks.LAVENDER_BLOCKS){

                    //
                    if(
                            solid.getName().getString().equals(
                                    product.getName().getString().replaceAll("_pane","")
                            )
                    ){
                        RecipeProvider.stainedGlassPaneFromStainedGlass(consumer,product,solid);
                    }
                }
            }

            //BED
            else if(product.getName().getString().endsWith("_bed")){
                for(Block wool : LavenderBlocks.LAVENDER_BLOCKS){
                    if(
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_bed","_wool")
                                    )
                    ){
                        RecipeProvider.bedFromPlanksAndWool(consumer,product,wool);
                    }
                }
            }

            //BANNER
            else if(product.getName().getString().endsWith("_banner") && !(product instanceof WallBannerBlock)){
                for(Block wool : LavenderBlocks.LAVENDER_BLOCKS){
                    if(
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_banner","_wool")
                                    )
                    ){
                        RecipeProvider.banner(consumer,product,wool);
                    }
                }
            }

            //CARPET
            else if(product.getName().getString().endsWith("_carpet")){
                for(Block wool : LavenderBlocks.LAVENDER_BLOCKS){
                    if(
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_carpet","_wool")
                                    )
                    ){
                        RecipeProvider.carpet(consumer,product,wool);
                    }
                }
            }
        }
        //dye-based


    }
}
