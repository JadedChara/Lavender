package io.github.jadedchara.lavender.datagen.providers;

import io.github.jadedchara.lavender.common.registry.LavenderBlocks;
import io.github.jadedchara.lavender.common.registry.LavenderItems;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.WallBannerBlock;

public class LavenderLangProvider extends FabricLanguageProvider {
    public LavenderLangProvider(FabricDataGenerator g) {
        super(g);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder) {
        for(Block block : LavenderBlocks.LAVENDER_BLOCKS){
            if(!(block instanceof WallBannerBlock)){
                builder.add(block, convertToName(block.getName().getString()));
            }
        }
        for(DyeItem dye : LavenderItems.DYES){
            builder.add(dye, convertToName(dye.getName(dye.getDefaultInstance()).getString()));
        }
    }
    public String convertToName(String translatable){
        translatable = translatable.replaceAll("block\\.lavender\\.","");
        translatable = translatable.replaceAll("item\\.lavender\\.","");
        String[] v = translatable.split("_");
        String returnable = "";
        for(String s : v){
            returnable+=(s.replaceFirst(String.valueOf(s.charAt(0)),(s.charAt(0)+"").toUpperCase())+" ");
        }
        return returnable.trim();
    }
}
