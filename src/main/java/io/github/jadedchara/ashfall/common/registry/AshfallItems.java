package io.github.jadedchara.ashfall.common.registry;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.item.warpstone.AnchorWarpstoneItem;
import io.github.jadedchara.ashfall.common.item.warpstone.DimensionalWarpstoneItem;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AshfallItems {
    //REGISTRATION METHODS
    private static <T extends Item> T registerItem(T item, String internalName) {

        return Registry.register(Registry.ITEM,Ashfall.id(internalName),item);
    }

    //ITEMS
    public static final AnchorWarpstoneItem ANCHOR_WARPSTONE = registerItem(
            new AnchorWarpstoneItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)),
            "anchor_warpstone"
    );
    public static final DimensionalWarpstoneItem DIMENSIONAL_WARPSTONE = registerItem(
            new DimensionalWarpstoneItem(new Item.Properties().tab(CreativeModeTab.TAB_TOOLS)),
            "dimensional_warpstone"
    );
    //~~~~~~~~~~~~
    public static void init(){}
}
