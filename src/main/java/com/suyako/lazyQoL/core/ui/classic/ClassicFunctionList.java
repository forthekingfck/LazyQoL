package com.suyako.lazyQoL.core.ui.classic;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class ClassicFunctionList {
    Minecraft mc = Minecraft.getMinecraft();

    public void draw() {
        int height = (Integer) getConfigByFunctionName.get("HUD","FunctionListHeight");
        for (LazyQoLFunction function : LazyQoLRegister.FunctionList) {
            if(function.getStatus()) {
                mc.fontRendererObj.drawString(function.getName(), new ScaledResolution(mc).getScaledWidth() - mc.fontRendererObj.getStringWidth(function.getName()), height, LazyQoL.Color);
                height = height + 10;
            }
        }
    }
}
