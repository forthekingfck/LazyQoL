package com.suyako.lazyQoL.core.gui;

import com.suyako.lazyQoL.core.config.ConfigLoader;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import org.lwjgl.input.Keyboard;

public class KeyBindButton extends GuiClickGUIButton{
    public LazyQoLFunction function;
    public KeyBindButton(int p_i46323_1_, int x, int y, int p_i46323_4_, int p_i46323_5_, LazyQoLFunction function) {
        super(p_i46323_1_,x,y,p_i46323_4_,p_i46323_5_,"");
        this.function = function;
    }
    public void setWait(){
        this.displayString = "Waiting For Input...";
    }
    public void refresh(){
        this.yPosition = this.OriginalYPos + this.Excursion;
        int keyValue = (Integer) ConfigLoader.getInt(function.getName() + "_KeyBindValue",-114514);
        this.displayString = function.getName() + " -> " + (keyValue == -114514 ? "[NONE]" : "[" + Keyboard.getKeyName(keyValue) + "]");
    }
    public void setKey(int KeyCode){
        ConfigLoader.setInt(function.getName() + "_KeyBindValue",KeyCode,-114514);
    }
}
