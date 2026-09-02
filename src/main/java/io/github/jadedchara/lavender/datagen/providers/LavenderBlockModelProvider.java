package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.client.model.Model;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TexturedModel;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LavenderBlockModelProvider extends FabricModelProvider {
    public LavenderBlockModelProvider(FabricDataGenerator g) {
        super(g);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        BlockModelGenerators.BlockEntityModelGenerator storedGen = generators.blockEntityModels(
                ModelLocationUtils.decorateBlockModelLocation("banner"),
                Blocks.OAK_PLANKS
        );
        for(Block block: LavenderBlocks.LAVENDER_BLOCKS) {
            //System.out.println(block);
            if (
                    block.getName().getString().endsWith("concrete")
                    || (block.getName().getString().endsWith("_terracotta") && !block.getName().getString().contains(
                            "glazed"))
                    || block.getName().getString().endsWith("_concrete_powder")
             
            ) {
                generators.createTrivialCube(block);
            } else if (
                    block.getName().getString().endsWith("stained_glass")
            ) {
                for(Block b : LavenderBlocks.LAVENDER_BLOCKS){
                    if(b.getName().getString().equals(block.getName().getString()+"_pane")){
                        generators.createGlassBlocks(block,b);
                    }
                }
            } else if (
                    block.getName().getString().endsWith("_candle")
            ) {
                //System.out.println("Candle:" + block.getName().getString());
                for(Block b : LavenderBlocks.LAVENDER_BLOCKS){
                    if(b.getName().getString().equals(block.getName().getString()+"_cake")){
                        //System.out.println(block.getName().getString() + " : " + b.getName().getString());
                        generators.createCandleAndCandleCake(block,b);
                    }
                }
            } else if (
                    block.getName().getString().endsWith("_bed")
            ) {
                for(Block b : LavenderBlocks.LAVENDER_BLOCKS){
                    if(b.getName().getString().equals(block.getName().getString().replaceAll("_bed","")+"_wool")){
                        generators.blockEntityModels(
                                Registry.BLOCK.getKey(block),
                                Blocks.OAK_PLANKS
                        ).createWithoutBlockItem(block);
                        generators.createBedItem(block,b);


                    }
                }
            } else if (
                    block.getName().getString().endsWith("_banner") && !block.getName().getString().contains("wall")
            ) {
                        for(Block wallBanner : LavenderBlocks.LAVENDER_BLOCKS){
                            if(
                                    wallBanner.getName().getString().endsWith("_wall_banner") &&
                                            wallBanner
                                                    .getName()
                                                    .getString()
                                                    .replaceAll("_wall","")
                                                    .equals(block.getName().getString())
                            ){
                                generators
                                        .blockEntityModels(
                                                Registry.BLOCK.getKey(block),
                                                Blocks.OAK_PLANKS
                                        )
                                        .createWithCustomBlockItemModel(ModelTemplates.BANNER_INVENTORY,block)
                                        .createWithoutBlockItem(wallBanner);
                            }
                        }
            } else if (
                    block.getName().getString().endsWith("_shulker_box")
            ) {
                generators.createShulkerBox(block);
            } else if (
                    block.getName().getString().endsWith("_wool")
            ){
                for(Block b : LavenderBlocks.LAVENDER_BLOCKS){
                    if(b.getName().getString().equals(block.getName().getString().replaceAll("_wool","")+"_carpet")){
                        generators.createFullAndCarpetBlocks(block,b);
                    }
                }
            } else if(
                    block.getName().getString().endsWith("_glazed_terracotta") && block instanceof GlazedTerracottaBlock
            ){
                generators.createColoredBlockWithStateRotations(TexturedModel.GLAZED_TERRACOTTA, block);
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        for (Block block : LavenderBlocks.LAVENDER_BLOCKS){
            if (
                    block.getName().getString().endsWith("_banner")
            ) {
                /*ModelTemplates.BANNER_INVENTORY.create(ModelLocationUtils.getModelLocation(block.asItem()),
                        TextureMapping.particle(Blocks.OAK_PLANKS),
                        generators.output);*/
            }
        }
    }
}
