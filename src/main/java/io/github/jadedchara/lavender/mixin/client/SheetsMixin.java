package io.github.jadedchara.lavender.mixin.client;

import com.google.common.collect.ImmutableList;
import io.github.jadedchara.lavender.common.util.LavenderColors;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.resources.model.Material;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;
import org.spongepowered.asm.mixin.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Debug
@Mixin(Sheets.class)
public class SheetsMixin {

    private static List<String> stored = new ArrayList<>();
    private static List<String> getList(){
        for(DyeColor color : DyeColor.values()){
            stored.add(color.getName());
        }
        for(LavenderColors color : LavenderColors.values()){
            if (!stored.contains(color.getName())){
                stored.add(color.getName());
            }
        }
        return stored;
    }

    @Shadow
    @Final
    @Mutable
    public static final List<Material> SHULKER_TEXTURE_LOCATION =
            getList().stream().map((p_110784_) -> {
        return new Material(Sheets.SHULKER_SHEET, new ResourceLocation("entity/shulker/shulker_" + p_110784_));
    }).collect(ImmutableList.toImmutableList());

}
