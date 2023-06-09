package com.suyako.lazyQoL.common.mixins;

import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(ShaderGroup.class)
public interface ShaderGroupAccessor {
    @Accessor("mainFramebuffer")
    Framebuffer getBuffer();
    @Accessor("listShaders")
    List<Shader> getShaders();
}
