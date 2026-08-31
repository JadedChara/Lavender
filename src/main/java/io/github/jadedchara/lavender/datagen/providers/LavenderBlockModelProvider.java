package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ConcretePowderBlock;
import net.minecraft.world.level.block.WoolCarpetBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class LavenderBlockModelProvider extends FabricModelProvider {
    public LavenderBlockModelProvider(FabricDataGenerator g) {
        super(g);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generators) {
        for(Block block: LavenderBlocks.LAVENDER_BLOCKS) {
            //System.out.println(block);
            if (
                    block.getName().getString().endsWith("concrete")
                    || block.getName().getString().endsWith("terracotta")
                    || block.getName().getString().endsWith("glazed_terracotta")
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
                    if(b.getName().getString().equals(block.getName().replaceAll("_bed","").getString()+"_wool")){
                        generators.createBedItem(block,b);
                    }
                }
            } else if (
                    block.getName().getString().endsWith("_banner")
            ) {
                ModelTemplates.BANNER_INVENTORY.create(block,TextureMapping.particle(Blocks.OAK_PLANKS),
                        generators.modelOutput);
            } else if (
                    block.getName().getString().endsWith("_shulker_box")
            ) {
                generators.createShulkerBox(block);
            } else if (
                    block.getName().getString().endsWith("_wool")
            ){
                for(Block b : LavenderBlocks.LAVENDER_BLOCKS){
                    if(b.getName().getString() == block.getName().getString().replaceAll("_wool","")+"_carpet"){
                        generators.createFullAndCarpetBlocks(block,b);
                    }
                }
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerators generators) {
        //Not needed atm...
    }
}
