package net.voidatomicx.karmaticton.entity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.voidatomicx.karmaticton.entity.custom.KarmatictonScytheEntity;
import net.voidatomicx.karmaticton.entity.render.state.ScytheRenderState;
import net.voidatomicx.karmaticton.item.ModItems;


public class KarmatictonScytheRenderer extends EntityRenderer<KarmatictonScytheEntity, ScytheRenderState> {

    private final ItemModelResolver modelResolver;

    public KarmatictonScytheRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.modelResolver = context.getItemModelResolver();
    }

    @Override
    public void extractRenderState(KarmatictonScytheEntity entity, ScytheRenderState entityRenderState, float f) {
        this.modelResolver.updateForNonLiving(entityRenderState.itemRenderState,new ItemStack(ModItems.KARMATICTON_SCYTHE), ItemDisplayContext.THIRD_PERSON_RIGHT_HAND,entity);
        super.extractRenderState(entity, entityRenderState, f);
    }

    @Override
    public ScytheRenderState createRenderState() {
        return new ScytheRenderState();
    }

    @Override
    public void submit(ScytheRenderState entityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {

        entityRenderState.itemRenderState.submit(poseStack, submitNodeCollector, entityRenderState.lightCoords, OverlayTexture.NO_OVERLAY, entityRenderState.outlineColor);

        super.submit(entityRenderState, poseStack, submitNodeCollector, cameraRenderState);
    }
}
