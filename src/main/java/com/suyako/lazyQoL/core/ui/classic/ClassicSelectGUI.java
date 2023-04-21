package com.suyako.lazyQoL.core.ui.classic;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.SelectObject;
import com.suyako.lazyQoL.core.type.SelectTable;
import net.minecraft.client.Minecraft;

public class ClassicSelectGUI {
    Minecraft mc = Minecraft.getMinecraft();
    public void draw(){
        int height = (Integer) getConfigByFunctionName.get("HUD","HUDHeight") + 10;
        int width = 0;
        mc.fontRendererObj.drawString("LazyQoL",0, (Integer) getConfigByFunctionName.get("HUD","HUDHeight"), LazyQoL.Color);
        for(SelectTable table : SelectGUIStorage.TableStorage){
            if(table.getID().equals(LazyQoL.PresentGUI)){
                for(SelectObject object : table.getList()){
                    if(object.getName().equals(LazyQoL.PresentFunction)) {
                        if(object.getType().equals("function")) {
                            String selectObjectName = "[function]" + object.getName() + " <-";
                            mc.fontRendererObj.drawString(selectObjectName, width, height, LazyQoL.Color);
                        } else {
                            String selectObjectName = "[list]" + object.getName() + " <-";
                            mc.fontRendererObj.drawString(selectObjectName, width, height, LazyQoL.Color);
                        }
                    } else {
                        if(object.getType().equals("function")) {
                            String selectObjectName = "[function]" + object.getName();
                            mc.fontRendererObj.drawString(selectObjectName, width, height, LazyQoL.Color);
                        } else {
                            String selectObjectName = "[list]" + object.getName();
                            mc.fontRendererObj.drawString(selectObjectName, width, height, LazyQoL.Color);
                        }
                    }
                    height = height + 10;
                }

            }
        }
    }
}
