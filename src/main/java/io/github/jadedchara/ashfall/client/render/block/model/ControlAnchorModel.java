package io.github.jadedchara.ashfall.client.render.block.model;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.ControlAnchorBlockEntity;
import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class ControlAnchorModel extends AnimatedGeoModel<ControlAnchorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(ControlAnchorBlockEntity c) {
        return (Ashfall.id("geo/block/spatial_anchor.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(ControlAnchorBlockEntity c) {
        return (Ashfall.id("textures/block/control_anchor.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(ControlAnchorBlockEntity c) {
        return (Ashfall.id("animations/block/spatial_anchor.animation.json"));
    }
}
