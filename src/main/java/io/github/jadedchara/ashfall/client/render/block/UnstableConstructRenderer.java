package io.github.jadedchara.ashfall.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.jadedchara.ashfall.client.render.block.model.UnstableConstructModel;
import io.github.jadedchara.ashfall.common.block.entity.UnstableConstructBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.renderers.geo.GeoBlockRenderer;

public class UnstableConstructRenderer extends GeoBlockRenderer<UnstableConstructBlockEntity> {
    public UnstableConstructRenderer(BlockEntityRendererProvider.Context c) {
        super(new UnstableConstructModel());
    }

    @Override
    public RenderType getRenderType(UnstableConstructBlockEntity animatable, float partialTick, PoseStack poseStack,
                                    MultiBufferSource bufferSource, VertexConsumer buffer, int packedLight, ResourceLocation texture) {
        return RenderType.entityCutoutNoCull(texture);
    }

    @Override
    public void renderRecursively(GeoBone bone, PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        /*if(bone.getName().equals("anchor") && this.animatable.getState() == 1){
            red = 0.3F;
            green = 0.9F;
            blue = 0.9F;
        }*/
        super.renderRecursively(bone, poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);

    }
}
