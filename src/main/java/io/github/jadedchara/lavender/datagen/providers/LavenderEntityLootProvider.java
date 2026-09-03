package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.Lavender;
import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.SimpleFabricLootTableProvider;
import net.minecraft.core.Registry;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootTableReference;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class LavenderEntityLootProvider extends SimpleFabricLootTableProvider {
    public LavenderEntityLootProvider(FabricDataGenerator g) {
        super(g, LootContextParamSets.ENTITY);
    }

    @Override
    public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
        for(LavenderColors color : LavenderColors.values()){
            for(Block block : LavenderBlocks.LAVENDER_BLOCKS){
                if(
                        block.getName().getString().endsWith("wool")
                                && block.getName().getString().contains(color.getName())
                ){
                    try{
                        consumer.accept(Lavender.id("entities/sheep/"+color.getName()),
                                EntityLoot.createSheepTable(block));
                    }catch(Exception e){
                        //ignore
                    }
                }
            }
        }
    }

    @Override
    public String getName() {
        return "Entity Loot Tables";
    }
}
