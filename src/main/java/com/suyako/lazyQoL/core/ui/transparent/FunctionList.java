package com.suyako.lazyQoL.core.ui.transparent;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.utils.FontManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;

public class FunctionList {
    Minecraft mc = Minecraft.getMinecraft();

    public void draw() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);
        FontManager.GothamRoundedFont.drawString("",0,0,0xFFFFFF);
        int height = (Integer) getConfigByFunctionName.get("HUD","FunctionListHeight");

        ResourceLocation resourceLocation = new ResourceLocation(LazyQoL.MODID, "hud2.png");
        for (LazyQoLFunction function : LazyQoLRegister.FunctionList) {
            if(function.getStatus()) {
                Minecraft.getMinecraft().renderEngine.bindTexture(resourceLocation);
                Gui.drawModalRectWithCustomSizedTexture((int) (scaledResolution.getScaledWidth() - FontManager.GothamRoundedFont.getStringWidth(function.getName()) - 6),height,0,0, (int) FontManager.GothamRoundedFont.getStringWidth(function.getName()) + 6,14, (float) FontManager.GothamRoundedFont.getStringWidth(function.getName()) + 6,28);
                FontManager.GothamRoundedFont.drawString(function.getName(),scaledResolution.getScaledWidth() - FontManager.GothamRoundedFont.getStringWidth(function.getName()) - 3,height + 5,0xFFFFFF);
                height = height + 14;
            }
        }
    }
}
