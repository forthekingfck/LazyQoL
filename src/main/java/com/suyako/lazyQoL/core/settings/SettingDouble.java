package com.suyako.lazyQoL.core.settings;

import com.suyako.lazyQoL.core.config.ConfigLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SettingDouble extends AbstractSettingOptionTextField {
    double DefaultValue;
    public SettingDouble(String name, String ID, double DefaultValue) {
        this.name = name;
        this.ID = ID;
        this.DefaultValue = DefaultValue;
    }
    @Override
    public void init(){
        this.setText(String.valueOf(ConfigLoader.getDouble(parentFunction + "_" + ID,DefaultValue)));
    }
    @Override
    public void update() {
        if(this.visible) {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());
            Minecraft.getMinecraft().fontRendererObj.drawString(name, this.xPosition + (this.width / 2) - (Minecraft.getMinecraft().fontRendererObj.getStringWidth(name) / 2), yPosition - 10, 0xFFFFFF);
        }
        this.drawTextBox();
    }

    @Override
    public Object getValue() {
        return ConfigLoader.getDouble(parentFunction + "_" + ID,DefaultValue);
    }

    @Override
    public void setValue() {
        try {
            ConfigLoader.setDouble(parentFunction + "_" + ID,Double.parseDouble(this.getText()), DefaultValue);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
