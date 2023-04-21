package com.suyako.lazyQoL.common.mixins;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(FontRenderer.class)
public class MixinFontRenderer {
    @ModifyVariable(
            method = "renderStringAtPos",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0)
    public String renderStringAtPos(String str){
        if(str == null){
            return "";
        }
        return StringFactory(str);
    }
    @ModifyVariable(
            method = "getStringWidth",
            at = @At("HEAD"),
            argsOnly = true,
            ordinal = 0)
    public String getStringWidth(String str){
        if(str == null){
            return "";
        }
        return StringFactory(str);
    }
    private String StringFactory(String str){
        if(str != null) {
            String originalString = str;
            String temp = originalString;
            temp = temp.replace("Chum", "Cum").replace("chum", "cum").replace("CHUM", "CUM");
            temp = temp.replace("Beast", "Breast");
            temp = temp.replace("e z", "ez").replace("e/z", "ez");
            if (FunctionManager.getStatus("NickHider") && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().thePlayer.getName() != null) {
                temp = temp.replace(Minecraft.getMinecraft().thePlayer.getName(), (String) getConfigByFunctionName.get("NickHider", "name"));
            }
            return temp;
        } else {
            return str;
        }
    }
}
