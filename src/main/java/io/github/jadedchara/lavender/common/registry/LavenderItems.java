package io.github.jadedchara.lavender.common.registry;

import io.github.jadedchara.lavender.Lavender;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;

import java.util.ArrayList;
import java.util.List;

public class LavenderItems {

    public static List<DyeItem> DYES = new ArrayList<>();

    //DYES
    //public static final DyeItem LAVENDER_DYE = registerDye("lavender_dye",DyeColor.byId(16));
    //public static final DyeItem FOLLY_DYE = registerDye("folly_dye",DyeColor.byId(17));

    //

    //UTILS
    public static DyeItem registerDye(String name, DyeColor color) {
        return Registry.register(
                Registry.ITEM,
                new ResourceLocation(Lavender.MOD_ID, name),
                new DyeItem(color,new Item.Properties().stacksTo(64).tab(CreativeModeTab.TAB_MISC))
        );
    }
    public static void init(){
        for (LavenderColors color : LavenderColors.values()) {
            DYES.add(registerDye(String.join("",color.getName(),"_dye"),DyeColor.byId(color.getId())));
        }
        //System.out.println("Registered " + LAVENDER_DYE.asItem().toString());
    }
}
