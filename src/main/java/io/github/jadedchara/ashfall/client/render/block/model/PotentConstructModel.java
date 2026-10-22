package io.github.jadedchara.ashfall.client.render.block.model;

import io.github.jadedchara.ashfall.Ashfall;
import io.github.jadedchara.ashfall.common.block.entity.PotentConstructBlockEntity;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public class PotentConstructModel extends AnimatedGeoModel<PotentConstructBlockEntity> {
    public PotentConstructModel() {
        super();

    }



    @Override
    public ResourceLocation getModelResource(PotentConstructBlockEntity potentConstructBlockEntity) {
        return (Ashfall.id("geo/block/potent_construct.geo.json"));
    }

    @Override
    public ResourceLocation getTextureResource(PotentConstructBlockEntity potentConstructBlockEntity) {
        return (Ashfall.id("textures/block/potent_construct.png"));
    }

    @Override
    public ResourceLocation getAnimationResource(PotentConstructBlockEntity potentConstructBlockEntity) {
        return (Ashfall.id("animations/block/potent_construct.animation.json"));
    }
}
