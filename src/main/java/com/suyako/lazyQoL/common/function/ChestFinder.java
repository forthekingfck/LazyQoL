package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.utils.Chroma;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.util.List;

public class ChestFinder {
    public static int mode = 2;
    public ChestFinder() {
        MinecraftForge.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if (event.phase == TickEvent.Phase.START) {
            mode = (Integer) getConfigByFunctionName.get("ChestFinder","mode");
        }
    }
    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event){
        if(FunctionManager.getStatus("ChestFinder")){
            List<TileEntity> tileEntities = Minecraft.getMinecraft().theWorld.loadedTileEntityList;
            for(TileEntity entity : tileEntities){
                if(entity instanceof TileEntityChest){
                    if(mode == 0){
                        Utils.OutlinedBoxWithESP(entity.getPos(), Chroma.color,false,2);
                    }
                    if(mode == 1){
                        Utils.BoxWithESP(entity.getPos(), Chroma.color,true);
                    }
                    if(mode == 2){
                        Utils.OutlinedBoxWithESP(entity.getPos(), Chroma.color,false,2);
                        Utils.renderTrace(Minecraft.getMinecraft().thePlayer.getPosition(),entity.getPos(),Chroma.color,2);
                    }
                }
            }
        }
    }
}
