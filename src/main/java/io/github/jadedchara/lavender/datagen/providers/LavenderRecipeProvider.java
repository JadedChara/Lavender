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
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
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
        for (Block product : LavenderBlocks.LAVENDER_BLOCKS) {

            //TERRACOTTA
            if (product.getName().getString().endsWith("_glazed_terracotta")) {
                for (Block uncooked : LavenderBlocks.LAVENDER_BLOCKS) {
                    if (
                            uncooked.getName().getString().equals(
                                    product.getName().getString().replaceAll("_glazed", "")
                            )
                    ) {
                        RecipeProvider.smeltingResultFromBase(consumer, uncooked, product);
                    }
                }
            }

            //GLASS
            else if (product.getName().getString().endsWith("_pane")) {
                for (Block solid : LavenderBlocks.LAVENDER_BLOCKS) {

                    //
                    if (
                            solid.getName().getString().equals(
                                    product.getName().getString().replaceAll("_pane", "")
                            )
                    ) {
                        RecipeProvider.stainedGlassPaneFromStainedGlass(consumer, product, solid);
                    }
                }
            }

            //BED
            else if (product.getName().getString().endsWith("_bed")) {
                for (Block wool : LavenderBlocks.LAVENDER_BLOCKS) {
                    if (
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_bed", "_wool")
                                    )
                    ) {
                        RecipeProvider.bedFromPlanksAndWool(consumer, product, wool);
                    }
                }
            }

            //BANNER
            else if (product.getName().getString().endsWith("_banner") && !(product instanceof WallBannerBlock)) {
                for (Block wool : LavenderBlocks.LAVENDER_BLOCKS) {
                    if (
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_banner", "_wool")
                                    )
                    ) {
                        RecipeProvider.banner(consumer, product, wool);
                    }
                }
            }

            //CARPET
            else if (product.getName().getString().endsWith("_carpet")) {
                for (Block wool : LavenderBlocks.LAVENDER_BLOCKS) {
                    if (
                            wool.getName().getString().endsWith("wool") &&
                                    wool.getName().getString().equals(
                                            product.getName().getString().replaceAll("_carpet", "_wool")
                                    )
                    ) {
                        RecipeProvider.carpet(consumer, product, wool);
                    }
                }
            }
        }
        //dye-based
        for (DyeItem dye : LavenderItems.DYES) {
            for (Block block : LavenderBlocks.LAVENDER_BLOCKS) {
                //CARPET
                if (
                        block.getName().getString().endsWith("_carpet")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "carpet")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.coloredCarpetFromWhiteCarpetAndDye(consumer, block, dye);
                }
                //WOOL
                else if (
                        block.getName().getString().endsWith("_wool")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "wool")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.coloredWoolFromWhiteWoolAndDye(consumer, block, dye);
                }
                //TERRACOTTA
                else if (
                        block.getName().getString().endsWith("_terracotta")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "terracotta")
                                .equals(
                                        block.getName().getString()
                                )
                                && !(block instanceof GlazedTerracottaBlock)
                ) {
                    RecipeProvider.coloredTerracottaFromTerracottaAndDye(consumer, block, dye);
                }
                //BED
                else if (
                        block.getName().getString().endsWith("_bed")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "bed")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.bedFromWhiteBedAndDye(consumer, block, dye);
                }
                //CONCRETE POWDER
                else if (
                        block.getName().getString().endsWith("_concrete_powder")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "concrete_powder")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.concretePowder(consumer, block, dye);
                }
                //CANDLE
                else if (
                        block.getName().getString().endsWith("_candle")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "candle")
                                .replaceAll("item", "block")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.candle(consumer, block.asItem(), dye);
                }
                //STAINED GLASS
                else if (
                        block.getName().getString().endsWith("_stained_glass")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "stained_glass")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    RecipeProvider.stainedGlassFromGlassAndDye(consumer, block, dye);
                }
                //STAINED GLASS
                else if (
                        block.getName().getString().endsWith("_shulker")
                                && dye
                                .getName(dye.getDefaultInstance())
                                .getString()
                                .replaceAll("dye", "shulker")
                                .equals(
                                        block.getName().getString()
                                )
                ) {
                    ShapelessRecipeBuilder.shapeless(block, 1).requires(Blocks.SHULKER_BOX).requires(dye).save(consumer);
                }
                //================
                //NON DYE BLOCKS
                else if (
                        block.getName().getString().endsWith("iron_mesh")
                ) {
                    try {
                        ShapelessRecipeBuilder
                                .shapeless(block, 1)
                                .requires(Blocks.IRON_BARS, 6)
                                .requires(Items.IRON_NUGGET)
                                .unlockedBy("has_nugget", FabricRecipeProvider.has(Items.IRON_NUGGET))
                                .save(consumer);
                    } catch (Exception e) {
                    }

                }
            }

            String dyeName = dye.getName(dye.getDefaultInstance()).getString().replaceFirst("lavender:", "");
            if (dyeName.contains("lavender")) {
                try {
                    ShapelessRecipeBuilder
                            .shapeless(dye, 2)
                            .requires(Items.PURPLE_DYE)
                            .requires(Items.LIGHT_GRAY_DYE)
                            .unlockedBy("has_purple", FabricRecipeProvider.has(Items.PURPLE_DYE))
                            .save(consumer);
                } catch (Exception e) {
                    //ignore
                }
            } else if (dyeName.contains("folly")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.RED_DYE)
                        .requires(Items.PINK_DYE)
                        .unlockedBy("has_red", FabricRecipeProvider.has(Items.RED_DYE))
                        .save(consumer, "folly_dye_1");
                ShapelessRecipeBuilder
                        .shapeless(dye, 3)
                        .requires(Items.RED_DYE, 2)
                        .requires(Items.WHITE_DYE)
                        .unlockedBy("has_red", FabricRecipeProvider.has(Items.RED_DYE))
                        .save(consumer, "folly_dye_2");
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.SWEET_BERRIES)
                        .unlockedBy("has_berries", FabricRecipeProvider.has(Items.SWEET_BERRIES))
                        .save(consumer,"folly_dye_berries");
            } else if (dyeName.contains("chartreuse")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.LIME_DYE)
                        .requires(Items.GREEN_DYE)
                        .unlockedBy("has_green", FabricRecipeProvider.has(Items.GREEN_DYE))
                        .save(consumer);
            } else if (dyeName.contains("turquoise")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.BLUE_DYE)
                        .requires(Items.CYAN_DYE)
                        .unlockedBy("has_cyan", FabricRecipeProvider.has(Items.CYAN_DYE))
                        .save(consumer,"turquoise_dye_normal");
                ShapelessRecipeBuilder
                        .shapeless(dye, 1)
                        .requires(Items.WARPED_ROOTS)
                        .unlockedBy("has_roots", FabricRecipeProvider.has(Items.WARPED_ROOTS))
                        .save(consumer,"turquoise_dye_roots");
            } else if (dyeName.contains("dark_gray")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.BLACK_DYE)
                        .requires(Items.GRAY_DYE)
                        .unlockedBy("has_gray", FabricRecipeProvider.has(Items.GRAY_DYE))
                        .save(consumer);
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.CHARCOAL)
                        .unlockedBy("has_charcoal", FabricRecipeProvider.has(Items.CHARCOAL))
                        .save(consumer,"dark_gray_dye_charcoal");
            } else if (dyeName.contains("crimson")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.RED_DYE)
                        .requires(Items.BLACK_DYE)
                        .unlockedBy("has_red", FabricRecipeProvider.has(Items.RED_DYE))
                        .save(consumer);
                ShapelessRecipeBuilder
                        .shapeless(dye, 1)
                        .requires(Items.CRIMSON_ROOTS)
                        .unlockedBy("has_roots", FabricRecipeProvider.has(Items.CRIMSON_ROOTS))
                        .save(consumer, "crimson_dye_roots");
            } else if (dyeName.contains("maroon")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.RED_DYE)
                        .requires(Items.BROWN_DYE)
                        .unlockedBy("has_brown", FabricRecipeProvider.has(Items.BROWN_DYE))
                        .save(consumer);
            } else if (dyeName.contains("copper")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.ORANGE_DYE)
                        .requires(Items.BROWN_DYE)
                        .unlockedBy("has_orange", FabricRecipeProvider.has(Items.ORANGE_DYE))
                        .save(consumer);
                ShapelessRecipeBuilder
                        .shapeless(dye, 1)
                        .requires(Items.PUMPKIN_SEEDS, 4)
                        .save(consumer,"copper_dye_pumpkin_seeds");
            } else if (dyeName.contains("desert_tan")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.BROWN_DYE)
                        .requires(Items.WHITE_DYE)
                        .unlockedBy("has_brown", FabricRecipeProvider.has(Items.BROWN_DYE))
                        .save(consumer);
            } else if (dyeName.contains("periwinkle")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.LIGHT_BLUE_DYE)
                        .requires(Items.WHITE_DYE)
                        .unlockedBy("has_light_blue", FabricRecipeProvider.has(Items.LIGHT_BLUE_DYE))
                        .save(consumer);
            } else if (dyeName.contains("mustard")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.YELLOW_DYE)
                        .requires(Items.GRAY_DYE)
                        .unlockedBy("has_yellow", FabricRecipeProvider.has(Items.YELLOW_DYE))
                        .save(consumer);
            } else if (dyeName.contains("orange_cream")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.ORANGE_DYE)
                        .requires(Items.WHITE_DYE)
                        .unlockedBy("has_orange", FabricRecipeProvider.has(Items.ORANGE_DYE))
                        .save(consumer);
            } else if (dyeName.contains("coffee")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.BROWN_DYE)
                        .requires(Items.GRAY_DYE)
                        .unlockedBy("has_brown", FabricRecipeProvider.has(Items.BROWN_DYE))
                        .save(consumer);
            } else if (dyeName.contains("raspberry")) {
                ShapelessRecipeBuilder
                        .shapeless(dye, 2)
                        .requires(Items.RED_DYE)
                        .requires(Items.PURPLE_DYE)
                        .unlockedBy("has_red", FabricRecipeProvider.has(Items.RED_DYE))
                        .save(consumer);
            }
        }
    }

}
