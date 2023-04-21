package com.suyako.lazyQoL.core.ui.QSF;


import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.utils.Blur;
import com.suyako.lazyQoL.utils.FontManager;
import com.suyako.lazyQoL.utils.render.Shadow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.HashMap;

public class QSFFunctionList {
    Minecraft mc = Minecraft.getMinecraft();
    static HashMap<String,Integer> enabledFunctions = new HashMap<>();
    int lastFunctionWidth = 0;
    public void draw() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        int height = (Integer) getConfigByFunctionName.get("HUD","FunctionListHeight");
        for (LazyQoLFunction function : LazyQoLRegister.FunctionList) {
            if(function.getStatus()) {
                GlStateManager.pushMatrix();
                Blur.renderBlur(scaledResolution.getScaledWidth() - FontManager.QuicksandBoldFont.getStringWidth(function.getName()) - 6,height,FontManager.QuicksandBoldFont.getStringWidth(function.getName()) + 6,13,10);
                GlStateManager.popMatrix();
                FontManager.QuicksandBoldFont.drawString(function.getName(), scaledResolution.getScaledWidth() - FontManager.QuicksandBoldFont.getStringWidth(function.getName()) - 3, height + 5, 0xFFFFFF);
                int currentWidth = FontManager.QuicksandBoldFont.getStringWidth(function.getName()) + 6;
                if(lastFunctionWidth != 0){
                    if(lastFunctionWidth - currentWidth > 0) {
                        Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth, height - 13, lastFunctionWidth - currentWidth, 13, Shadow.ShadowLocation.BOTTOM);
                        Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth, height - 13, lastFunctionWidth - currentWidth, 13, Shadow.ShadowLocation.BOTTOM_LEFT);
                    }
                    Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth,height - 13,lastFunctionWidth - currentWidth,13, Shadow.ShadowLocation.LEFT);
                }
                lastFunctionWidth = currentWidth;
                height = height + 13;
            }
        }
        Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth,height - 13,lastFunctionWidth,13, Shadow.ShadowLocation.BOTTOM);
        Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth,height - 13,lastFunctionWidth,13, Shadow.ShadowLocation.BOTTOM_LEFT);
        Shadow.drawShadow(scaledResolution.getScaledWidth() - lastFunctionWidth,height - 13,lastFunctionWidth,13, Shadow.ShadowLocation.LEFT);
    }
}
