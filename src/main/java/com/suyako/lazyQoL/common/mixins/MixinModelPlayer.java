package com.suyako.lazyQoL.common.mixins;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelPlayer.class)
public class MixinModelPlayer extends ModelBiped {
    private ModelRenderer bipedSuyakoEar;
    private ModelRenderer bipedSuyakoEar2;
    @Inject(method = "<init>*",at = @At(value = "RETURN"))
    public void init(float p_i46304_1_, boolean p_i46304_2_, CallbackInfo ci){
        this.bipedSuyakoEar = new ModelRenderer(this, 24, 0);
        this.bipedSuyakoEar.addBox(-3.7F, -3.8F, -3.1F, 2, 2, 1);
        this.bipedSuyakoEar2 = new ModelRenderer(this, 30, 0);
        this.bipedSuyakoEar2.addBox(-7.3F, -3.8F, -3.1F, 2, 2, 1);
    }
    public void renderSuyakoEar(float f) {
        renderSuyakoEar1(f);
        renderSuyakoEar2(f);
    }
    public void renderSuyakoEar1(float f) {
        copyModelAngles(this.bipedHead, this.bipedSuyakoEar);
        this.bipedSuyakoEar.rotationPointX = 0.0F;
        this.bipedSuyakoEar.rotationPointY = 0.0F;
        this.bipedSuyakoEar.render(f);
    }
    public void renderSuyakoEar2(float f) {
        copyModelAngles(this.bipedHead, this.bipedSuyakoEar2);
        this.bipedSuyakoEar2.rotationPointX = 0.0F;
        this.bipedSuyakoEar2.rotationPointY = 0.0F;
        this.bipedSuyakoEar2.render(f);
    }
}
