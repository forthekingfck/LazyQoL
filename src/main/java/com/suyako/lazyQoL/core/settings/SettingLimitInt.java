package com.suyako.lazyQoL.core.settings;

import com.suyako.lazyQoL.core.config.ConfigLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SettingLimitInt extends AbstractSettingOptionTextField {
    int DefaultValue;
    int max;
    int min;
    public SettingLimitInt(String name, String ID, int DefaultValue,int MaxValue,int MinValue) {
        this.name = name;
        this.ID = ID;
        this.DefaultValue = DefaultValue;
        this.max = MaxValue;
        this.min = MinValue;
    }
    @Override
    public void init(){
        this.setText(String.valueOf(ConfigLoader.getInt(parentFunction + "_" + ID,DefaultValue)));
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
        return ConfigLoader.getInt(parentFunction + "_" + ID,DefaultValue);
    }

    @Override
    public void setValue() {
        try {
            if(Integer.parseInt(this.getText()) > max){
                ConfigLoader.setInt(parentFunction + "_" + ID,max, DefaultValue);
            } else if(Integer.parseInt(this.getText()) < min){
                ConfigLoader.setInt(parentFunction + "_" + ID,min, DefaultValue);
            } else {
                ConfigLoader.setInt(parentFunction + "_" + ID,Integer.parseInt(this.getText()), DefaultValue);
            }


        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
