package com.suyako.lazyQoL.common.mixins;

import net.minecraft.client.renderer.entity.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RendererLivingEntity.class)
public interface RenderLivingEntityAccessor {
    @Accessor("renderOutlines")
    void setOutline(boolean outline);
}
