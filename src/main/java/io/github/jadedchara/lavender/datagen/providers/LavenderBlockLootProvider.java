package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.world.level.block.Block;

public class LavenderBlockLootProvider extends FabricBlockLootTableProvider {
    public LavenderBlockLootProvider(FabricDataGenerator g) {
        super(g);
    }

    @Override
    protected void generateBlockLootTables() {
        for(Block block : LavenderBlocks.LAVENDER_BLOCKS){
            if(
                    block.getName().getString().endsWith("shulker")
            ){
                createShulkerBoxDrop(block);
            }else if(
                    block.getName().getString().endsWith("terracotta") ||
                            block.getName().getString().endsWith("wool") ||
                            block.getName().getString().endsWith("bed") ||
                            block.getName().getString().endsWith("concrete") ||
                            block.getName().getString().endsWith("powder")
            ){
                dropSelf(block);
            }else if(
                    block.getName().getString().endsWith("candle")
            ){
                createCandleDrops(block);
            }else if(
                    block.getName().getString().endsWith("banner")
            ){
                createBannerDrop(block);
            }else if(
                    block.getName().getString().endsWith("cake")
            ){
                createCandleCakeDrops(block);
            }else if(
                    block.getName().getString().endsWith("glass") ||
                            block.getName().getString().endsWith("pane")
            ){
                dropWhenSilkTouch(block);
            }
        }
    }
}
