package io.github.jadedchara.ashfall.client.render.block.model.item;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.item.util.AnimatableBlockItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ControlAnchorItemModel extends AnimatedGeoModel<AnimatableBlockItem> {
    @Override
    public ResourceLocation getModelResource(AnimatableBlockItem spatialAnchorBlockEntity) {
        return (Ashfall.id("geo/block/spatial_anchor.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(AnimatableBlockItem spatialAnchorBlockEntity) {
        return (Ashfall.id("textures/block/control_anchor.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(AnimatableBlockItem spatialAnchorBlockEntity) {
        return (Ashfall.id("animations/block/spatial_anchor.animation.json"));
    }
}
