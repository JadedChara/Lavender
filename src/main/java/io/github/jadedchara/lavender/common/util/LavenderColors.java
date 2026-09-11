package io.github.jadedchara.lavender.common.util;

import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;

public enum LavenderColors implements StringRepresentable {

    /*
    LAVENDER; 0xd8a8ff
    CHARTREUSE;
    MUSTARD;
    FOLLY; 0xfd004d
    */

    LAVENDER(16,"lavender",0xd8a8ff, MaterialColor.TERRACOTTA_PURPLE,0xd8a8ff,0xd8a8ff),
    FOLLY(17,"folly",0xfd004d,MaterialColor.COLOR_PINK,0xfd004d,0xfd004d),
    CHARTREUSE(18, "chartreuse",0xbfbf6c,MaterialColor.TERRACOTTA_GREEN,0xbfbf6c,0xbfbf6c),
    TURQUOISE(19,"turquoise",0x40d8d8,MaterialColor.TERRACOTTA_CYAN,0x40d8d8,0x40d8d8),
    DARK_GRAY(20,"dark_gray",0x2e2e2e,MaterialColor.TERRACOTTA_BLACK,0x2e2e2e,0x2e2e2e),
    CRIMSON(21,"crimson",0x551c1d,MaterialColor.NETHER,0x551c1d,0x551c1d),
    MAROON(22,"maroon",0x800000,MaterialColor.NETHER,0x800000,0x800000),
    COPPER(23,"copper",0xa44322,MaterialColor.TERRACOTTA_ORANGE,0xa44322,0xa44322),
    DESERT_TAN(24,"desert_tan",0xd3b17d,MaterialColor.SAND,0xd3b17d,0xd3b17d),
    PERIWINKLE(25,"periwinkle",0xc2ecf9,MaterialColor.COLOR_LIGHT_BLUE,0xc2ecf9,0xc2ecf9),
    MUSTARD(26,"mustard",0xdcc066,MaterialColor.TERRACOTTA_YELLOW,0xdcc066,0xdcc066),
    ORANGE_CREAM(27,"orange_cream",0xffb95a,MaterialColor.TERRACOTTA_ORANGE,0xffb95a,0xffb95a),
    COFFEE(28,"coffee",0xb7794d,MaterialColor.TERRACOTTA_BROWN,0xb7794d,0xb7794d),
    RASPBERRY(29,"raspberry",0x7f1734,MaterialColor.COLOR_PURPLE,0x7f1734,0x7f1734);
    private final int id;
    private final String name;
    private final MaterialColor mapColor;
    private final float[] textureDiffuseColors;
    private final int color;
    private final int fireworkColor;
    private final int textColor;

    LavenderColors(int j, String string2, int k, MaterialColor mapColor, int l, int m) {
        this.id = j;
        this.name = string2;
        this.mapColor = mapColor;
        this.textColor = m;
        this.color = k;
        int n = (k & 0xFF0000) >> 16;
        int o = (k & 0xFF00) >> 8;
        int p = (k & 0xFF) >> 0;
        this.textureDiffuseColors = new float[]{(float)n / 255.0f, (float)o / 255.0f, (float)p / 255.0f};
        this.fireworkColor = l;
    }
    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public float[] getTextureDiffuseColors() {
        return this.textureDiffuseColors;
    }

    public MaterialColor getMapColor() {
        return this.mapColor;
    }

    public int getFireworkColor() {
        return this.fireworkColor;
    }

    public int getTextColor() {
        return this.textColor;
    }

    public int getColor() {
        return color;
    }

    public DyeColor get() {
        return DyeColor.valueOf(this.name());
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String getSerializedName() {
        return "";
    }
}
