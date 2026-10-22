package io.github.jadedchara.ashfall.client.render.block.model;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class SpatialAnchorModel extends AnimatedGeoModel<SpatialAnchorBlockEntity> {
    @Override
    public ResourceLocation getModelResource(SpatialAnchorBlockEntity spatialAnchorBlockEntity) {
        return (Ashfall.id("geo/block/spatial_anchor.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(SpatialAnchorBlockEntity spatialAnchorBlockEntity) {
        return (Ashfall.id("textures/block/spatial_anchor.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(SpatialAnchorBlockEntity spatialAnchorBlockEntity) {
        return (Ashfall.id("animations/block/spatial_anchor.animation.json"));
    }
}
