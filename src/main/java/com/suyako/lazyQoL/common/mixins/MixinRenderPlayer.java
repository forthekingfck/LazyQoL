package com.suyako.lazyQoL.common.mixins;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.suyako.lazyQoL.core.config.getConfigByFunctionName.get;

@Mixin(RenderPlayer.class)
public class MixinRenderPlayer {
    @Inject(
            method = {"preRenderCallback(Lnet/minecraft/client/entity/AbstractClientPlayer;F)V"},
            at = {@At("HEAD")}
    )
    public void onPreRenderCallback(AbstractClientPlayer entitylivingbaseIn, float partialTickTime, CallbackInfo ci) {
        if(FunctionManager.getStatus("Giant")) {
            double scaleValue = (Double) get("Giant", "allScale");
            GlStateManager.scale(scaleValue, scaleValue, scaleValue);
        }
    }
}
