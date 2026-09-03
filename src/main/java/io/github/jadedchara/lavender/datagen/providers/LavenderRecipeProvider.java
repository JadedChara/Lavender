package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.registry.LavenderItems;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
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
        for(DyeItem dye :  LavenderItems.DYES){
            for(Block block : LavenderBlocks.LAVENDER_BLOCKS){
                //CARPET
                if(
                        block.getName().getString().endsWith("_carpet")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","carpet")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.coloredCarpetFromWhiteCarpetAndDye(consumer,block,dye);
                }
                //WOOL
                else if(
                        block.getName().getString().endsWith("_wool")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","wool")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.coloredWoolFromWhiteWoolAndDye(consumer,block,dye);
                }
                //TERRACOTTA
                else if(
                        block.getName().getString().endsWith("_terracotta")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","terracotta")
                                .equals(
                                        block.getName().getString()
                                )
                                && !(block instanceof GlazedTerracottaBlock)
                ){
                    RecipeProvider.coloredTerracottaFromTerracottaAndDye(consumer,block,dye);
                }
                //BED
                else if(
                        block.getName().getString().endsWith("_bed")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","bed")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.bedFromWhiteBedAndDye(consumer,block,dye);
                }
                //CONCRETE POWDER
                else if(
                        block.getName().getString().endsWith("_concrete_powder")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","concrete_powder")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.concretePowder(consumer,block,dye);
                }
                //CANDLE
                else if(
                        block.getName().getString().endsWith("_candle")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","candle")
                                .replaceAll("item","block")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.candle(consumer,block.asItem(),dye);
                }
                //STAINED GLASS
                else if(
                        block.getName().getString().endsWith("_stained_glass")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","stained_glass")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    RecipeProvider.stainedGlassFromGlassAndDye(consumer,block,dye);
                }
                //STAINED GLASS
                else if(
                        block.getName().getString().endsWith("_shulker")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye","shulker")
                                .equals(
                                        block.getName().getString()
                                )
                ){
                    ShapelessRecipeBuilder.shapeless(block,1).requires(Blocks.SHULKER_BOX).requires(dye).save(consumer);
                }

            }
        }

    }
}
