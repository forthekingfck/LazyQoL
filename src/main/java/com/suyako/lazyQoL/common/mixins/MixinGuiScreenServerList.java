package com.suyako.lazyQoL.common.mixins;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.via.protocol.ProtocolCollection;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenServerList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiScreenServerList.class)
public abstract class MixinGuiScreenServerList extends GuiScreen {
    @Inject(method = "initGui", at = @At("RETURN"))
    public void injectInitGui(CallbackInfo ci) {
        buttonList.add(new GuiButton(114514, 5, 6, 98, 20,
                ProtocolCollection.getProtocolById(LazyQoL.getVersion()).getName()));
    }

    @Inject(method = "actionPerformed", at = @At("RETURN"))
    public void injectActionPerformed(GuiButton p_actionPerformed_1_, CallbackInfo ci) {
        if (p_actionPerformed_1_.id == 114514){
            if(LazyQoL.versionIndex + 1 > ProtocolCollection.values().length - 1){
                LazyQoL.versionIndex = 0;
            } else {
                LazyQoL.versionIndex = LazyQoL.versionIndex + 1;
            }
            LazyQoL.setVersion(ProtocolCollection.values()[LazyQoL.versionIndex].getVersion().getVersion());
        }
    }
    @Inject(method = "drawScreen", at = @At("HEAD"))
    public void injectDrawScreenHead(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        for(GuiButton button : this.buttonList){
            if(button.id == 114514){
                button.displayString = ProtocolCollection.getProtocolById(LazyQoL.getVersion()).getName();
            }
        }
    }
    @Inject(method = "drawScreen", at = @At("RETURN"))
    public void injectDrawScreen(int p_drawScreen_1_, int p_drawScreen_2_, float p_drawScreen_3_, CallbackInfo ci) {
        mc.fontRendererObj.drawStringWithShadow("<-- current version",
                104, 13, -1);
    }
}
