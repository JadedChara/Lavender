package io.github.jadedchara.ashfall.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ashfall.client.render.block.model.SpatialAnchorModel;
import io.github.jadedchara.ashfall.common.block.entity.SpatialAnchorBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class SpatialAnchorRenderer extends GeoBlockRenderer<SpatialAnchorBlockEntity> {
    public SpatialAnchorRenderer(BlockEntityRendererProvider.Context c) {
        super(new SpatialAnchorModel());
    }
    @Override
    public RenderType getRenderType(SpatialAnchorBlockEntity animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }
}
