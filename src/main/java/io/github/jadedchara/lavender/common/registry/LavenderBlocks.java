package io.github.jadedchara.lavender.common.registry;

import io.github.jadedchara.lavender.Lavender;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;

public class LavenderBlocks {

    public static List<Block> LAVENDER_BLOCKS = new ArrayList<>();

    //WOOLS

    //UTIL
    public static boolean returnFalse(BlockState bs, BlockGetter bg, BlockPos bp, EntityType<?> et){
        return false;
    }
    private static boolean returnFalse(BlockState bs, BlockGetter bg, BlockPos bp) {
        return false;
    }
    public static Block registerBlockItem(Block block, String internalName, CreativeModeTab tab) {
        BlockItem be = new BlockItem(block, new Item.Properties().tab(tab));
        Registry.register(Registry.ITEM, Lavender.id(internalName),be);
        LAVENDER_BLOCKS.add(block);
        System.out.println("Registered " + Lavender.id(internalName));
        return Registry.register(Registry.BLOCK,Lavender.id(internalName),block);
    }
    public static Block registerOnlyBlock(Block block, String internalName){
        LAVENDER_BLOCKS.add(block);
        System.out.println("Registered " + Lavender.id(internalName));
        return Registry.register(Registry.BLOCK,Lavender.id(internalName),block);
    }

    public static void init(){
        for (LavenderColors color : LavenderColors.values()) {

            //WOOL INIT
            registerBlockItem(
                    new Block(
                            FabricBlockSettings
                                    .of(Material.WOOL,color.getMapColor())
                                    .sounds(SoundType.WOOL)
                    ),
                    String.join("",color.getName(),"_wool"),
                    CreativeModeTab.TAB_BUILDING_BLOCKS
            );
            //CARPET INIT
            registerBlockItem(
                    new WoolCarpetBlock(
                            color.get(),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.CLOTH_DECORATION, color.getMapColor())
                                    .strength(0.1F)
                                    .sound(SoundType.WOOL)
                    ),
                    String.join("",color.getName(),"_carpet"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //STAINED GLASS INIT
            registerBlockItem(
                    new StainedGlassBlock(
                            color.get(),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.GLASS, color.get())
                                    .strength(0.3F)
                                    .sound(SoundType.GLASS)
                                    .noOcclusion()
                                    .isValidSpawn(LavenderBlocks::returnFalse)
                                    .isRedstoneConductor(LavenderBlocks::returnFalse)
                                    .isSuffocating(LavenderBlocks::returnFalse)
                                    .isViewBlocking(LavenderBlocks::returnFalse)
                    ),
                    String.join("",color.getName(),"_stained_glass"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //STAINED GLASS PANE INIT
            registerBlockItem(
                    new StainedGlassPaneBlock(
                            color.get(),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.GLASS).strength(0.3F)
                                    .sound(SoundType.GLASS)
                                    .noOcclusion()
                    ),
                    String.join("",color.getName(),"_stained_glass_pane"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //SHULKER BOX INIT
            registerBlockItem(
                    new ShulkerBoxBlock(
                            color.get(),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.SHULKER_SHELL,color.get())
                                    .strength(2.0F)
                                    .dynamicShape()
                                    .noOcclusion()
                                    .isSuffocating((bs, bg, bp) -> {
                                        BlockEntity be = bg.getBlockEntity(bp);
                                        if (!(be instanceof ShulkerBoxBlockEntity sbbe)) {
                                            return true;
                                        } else {
                                            return sbbe.isClosed();
                                        }
                                    })
                                    .isViewBlocking((bs, bg, bp) -> {
                                        BlockEntity be = bg.getBlockEntity(bp);
                                        if (!(be instanceof ShulkerBoxBlockEntity sbbe)) {
                                            return true;
                                        } else {
                                            return sbbe.isClosed();
                                        }
                                    })
                    ),
                    String.join("",color.getName(),"_shulker_box"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //TERRACOTTA INIT
            registerBlockItem(
                    new Block(
                            BlockBehaviour
                                    .Properties
                                    .of(Material.STONE, color.getMapColor())
                                    .requiresCorrectToolForDrops()
                                    .strength(1.25F, 4.2F)),
                    String.join("",color.getName(),"_terracotta"),
                    CreativeModeTab.TAB_BUILDING_BLOCKS
            );
            //GLAZED TERRACOTTA INIT
            registerBlockItem(
                    new GlazedTerracottaBlock(
                            BlockBehaviour
                                    .Properties
                                    .of(Material.STONE, color.get())
                                    .requiresCorrectToolForDrops()
                                    .strength(1.4F)),
                    String.join("",color.getName(),"_glazed_terracotta"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //CONCRETE AND CONCRETE POWDER INIT
            registerBlockItem(
                    new ConcretePowderBlock(
                            registerBlockItem(
                                    new Block(
                                            BlockBehaviour
                                                    .Properties
                                                    .of(Material.STONE, color.get())
                                                    .requiresCorrectToolForDrops()
                                                    .strength(1.8F)),
                                    String.join("",color.getName(),"_concrete"),
                                    CreativeModeTab.TAB_BUILDING_BLOCKS

                            ),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.SAND, color.get())
                                    .strength(0.5F)
                                    .sound(SoundType.SAND)),
                    String.join("",color.getName(),"_concrete_powder"),
                    CreativeModeTab.TAB_BUILDING_BLOCKS
            );
            //BED INIT
            registerBlockItem(
                    new BedBlock(
                            color.get(),
                            BlockBehaviour.Properties.of(
                                    Material.WOOL,
                                    (blockState) -> blockState.getValue(BedBlock.PART) == BedPart.FOOT ?
                                            color.getMapColor() :
                            MaterialColor.WOOL).sound(SoundType.WOOD).strength(0.2F).noOcclusion()),
                    String.join("",color.getName(),"_bed"),
                    CreativeModeTab.TAB_DECORATIONS

            );
            //BANNER INIT
            registerBlockItem(
                    new BannerBlock(
                            color.get(),
                            BlockBehaviour
                                    .Properties
                                    .of(Material.WOOD)
                                    .noCollission()
                                    .strength(1.0F)
                                    .sound(SoundType.WOOD)
                    ),
                    String.join("",color.getName(),"_banner"),
                    CreativeModeTab.TAB_DECORATIONS
            );
            //CANDLE INIT
            ;
            //CANDLE AND CANDLE CAKE INIT
            registerOnlyBlock(
                    new CandleCakeBlock(
                            registerBlockItem(
                                    new CandleBlock(
                                            BlockBehaviour
                                                    .Properties
                                                    .of(Material.DECORATION, color.getMapColor())
                                                    .noOcclusion()
                                                    .strength(0.1F)
                                                    .sound(SoundType.CANDLE)
                                                    .lightLevel(CandleBlock.LIGHT_EMISSION)),
                                    String.join("",color.getName(),"_candle"),
                                    CreativeModeTab.TAB_DECORATIONS
                            ),
                            BlockBehaviour.Properties.copy(Blocks.CANDLE_CAKE)
                    ),
                    String.join("",color.getName(),"_candle_cake")
            );
        }
    }



}
