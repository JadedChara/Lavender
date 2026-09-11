package io.github.jadedchara.ashfall.common.registry;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.SpatialAnchorBlock;
import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import io.github.jadedchara.lavender.Lavender;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.Material;

public class AshfallBlocks {

    //REGISTRATION METHODS
    private static <T extends Block> T registerBlockItem(T block, String internalName, CreativeModeTab tab) {
        BlockItem be = new BlockItem(block, new Item.Properties().tab(tab));
        Registry.register(Registry.ITEM, Ashfall.id(internalName),be);
        return Registry.register(Registry.BLOCK,Ashfall.id(internalName),block);
    }
    private static <T extends Block> T registerOnlyBlock(T block, String internalName){
        return Registry.register(Registry.BLOCK, Ashfall.id(internalName),block);
    }
    public static <T extends BlockEntityType<?>> T registerBlockEntity(String internalName, T type) {
        return Registry.register(Registry.BLOCK_ENTITY_TYPE, Ashfall.id(internalName),type);
    }

    //BLOCKS
    public static final SpatialAnchorBlock SPATIAL_ANCHOR_BLOCK = registerBlockItem(new SpatialAnchorBlock(
            FabricBlockSettings.of(Material.METAL).strength(5f,5f)
    ),"spatial_anchor",CreativeModeTab.TAB_REDSTONE);

    //BLOCKENTITIES
    public static final BlockEntityType<SpatialAnchorBlockEntity> SPATIAL_ANCHOR_BLOCK_ENTITY = registerBlockEntity(
            "spatial_anchor_blockentity",BlockEntityType.Builder.of(SpatialAnchorBlockEntity::new,
                    SPATIAL_ANCHOR_BLOCK).build(null));


    //~~~~~~~~~~~
    public static void init(){
    }
}
