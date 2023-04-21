package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.core.Pathfinder;
import com.suyako.lazyQoL.core.nukerCore;
import com.suyako.lazyQoL.utils.Chroma;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockNetherWart;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CropBot {
    nukerCore nuker = new nukerCore();
    static Utils utils = new Utils();
    int StartY;
    int StartX;
    int StartZ;
    EntityPlayer player;
    int EndX;
    int EndY;
    int EndZ;
    int NowX;
    int NowY;
    int NowZ;
    int nearlyStartY;
    int nearlyStartX;
    int nearlyStartZ;
    int nearlyEndX;
    int nearlyEndY;
    int nearlyEndZ;
    int nearlyNowX;
    int nearlyNowY;
    int nearlyNowZ;
    static int nearlyCooldownTick = 0;
    static BlockPos target;
    static BlockPos nearlyTarget;
    static int finderCount = 0;
    Minecraft mc = Minecraft.getMinecraft();
    List<BlockPos> ignoreList = new ArrayList<BlockPos>();

    public CropBot() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onEnable(CustomEventHandler.FunctionEnableEvent event) {
        if (event.function.getName().equals("CropBot")) {
            ignoreList.clear();
            if (getTarget(true) == null) {
                event.setCanceled(true);
                utils.print("cannot find chosen crop");
            }
        }
    }
    @SubscribeEvent
    public void onDisable(CustomEventHandler.FunctionDisabledEvent event) {
        if (event.function.getName().equals("CropBot")) {
            target = null;
            nearlyCooldownTick = 0;
            nearlyTarget = null;
            finderCount = 0;
            nuker.pos = null;
            FunctionManager.setStatus("Pathfinding", false);
        }
    }

    @SubscribeEvent
    public void onSwitch(CustomEventHandler.FunctionSwitchEvent event) {
        if (!event.status) {
            if (event.function.getName().equals("CropBot")) {
                target = null;
                nearlyCooldownTick = 0;
                nearlyTarget = null;
                finderCount = 0;
                nuker.pos = null;
                FunctionManager.setStatus("Pathfinding", false);
            }
        } else {
            if (event.function.getName().equals("CropBot")) {
                ignoreList.clear();
                if (getTarget(true) == null) {
                    event.setCanceled(true);
                    utils.print("cannot find chosen crop");
                }
            }
        }
    }

    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event) {
        if(FunctionManager.getStatus("CropBot")) {
            if (target == null) {
                target = getNearlyTarget();
                if(target == null) {
                    target = getTarget(false);
                    if (target != null) {
                        Pathfinder.setup(new BlockPos(Utils.floorVec(mc.thePlayer.getPositionVector().addVector(0.0D,0.2D,0.0D))), target, 0.0D);
                        if (Pathfinder.hasPath()) {
                            FunctionManager.setStatus("Pathfinding", true);
                        } else {
                            target = null;
                        }
                    }
                }
            }
            if(target == null){
                    target = getTarget(true);
                    Utils.print("cannot find chosen crop, enable large range finding");
                    if (target != null) {
                        Pathfinder.setup(new BlockPos(Utils.floorVec(mc.thePlayer.getPositionVector().addVector(0.0D,0.2D,0.0D))), target, 0.0D);
                        if (Pathfinder.hasPath()) {
                            FunctionManager.setStatus("Pathfinding", true);
                        } else {
                            target = null;
                        }
                    }
            }
            if(target == null){
                FunctionManager.setStatus("CropBot", false);
                Utils.print("cannot find chosen crop");
            }
            if (!FunctionManager.getStatus("Pathfinding")) {
                if (finderCount > 10) {
                    target = null;
                    finderCount = 0;
                } else {
                    finderCount = finderCount + 1;
                }
            }
            if(nearlyTarget == null || mc.theWorld.getBlockState(nearlyTarget).getBlock() == Blocks.air) {
                if (nearlyCooldownTick > 1) {
                    nearlyTarget = getNearlyTarget();
                    nearlyCooldownTick = 0;
                } else {
                    nearlyCooldownTick = nearlyCooldownTick + 1;
                }
            }
            try {
                nuker.nuke(new Vec3(Objects.requireNonNull(nearlyTarget)));
                if(nearlyTarget != null) {
                    if (ignoreList.size() + 1 > (Integer)getConfigByFunctionName.get("CropBot","listSize")) {
                        ignoreList.clear();
                    }
                    ignoreList.add(nearlyTarget);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private BlockPos getTarget(boolean large) {
        if (Minecraft.getMinecraft().theWorld != null) {
            this.player = Minecraft.getMinecraft().thePlayer;
            int bound;
            if(!large) {
                bound = (Integer) getConfigByFunctionName.get("CropBot", "radius");
            } else {
                bound = 80;
            }
            this.StartY = (int) (this.player.posY - 5);
            this.StartX = (int) (this.player.posX - bound);
            this.StartZ = (int) (this.player.posZ - bound);
            this.EndX = (int) (this.player.posX + bound);
            this.EndY = (int) (this.player.posY + 5);
            this.EndZ = (int) (this.player.posZ + bound);

            this.NowX = this.StartX;
            this.NowY = this.StartY;
            this.NowZ = this.StartZ;
            while (NowY != EndY) {
                if (this.NowX == this.EndX) {
                    if (this.NowZ == this.EndZ) {

                        this.NowZ = this.StartZ;
                        this.NowX = this.StartX;
                        NowY = NowY + 1;
                    } else {
                        this.NowX = this.StartX;
                        NowZ = NowZ + 1;
                    }
                } else {
                    NowX = NowX + 1;
                }
                BlockPos pos = new BlockPos(NowX, NowY, NowZ);
                if (isValid(pos)) {
                    return pos;
                }
            }
            this.NowX = this.StartX;
            this.NowY = this.StartY;
            this.NowZ = this.StartZ;
        }
        return null;
    }
    private BlockPos getNearlyTarget() {
        if (Minecraft.getMinecraft().theWorld != null) {
            this.player = Minecraft.getMinecraft().thePlayer;
            this.nearlyStartY = (int) (this.player.posY - 3.5D);
            this.nearlyStartX = (int) (this.player.posX - 3.5D);
            this.nearlyStartZ = (int) (this.player.posZ - 3.5D);
            this.nearlyEndX = (int) (this.player.posX + 3.5D);
            this.nearlyEndY = (int) (this.player.posY + 3.5D);
            this.nearlyEndZ = (int) (this.player.posZ + 3.5D);

            this.nearlyNowX = this.nearlyStartX;
            this.nearlyNowY = this.nearlyStartY;
            this.nearlyNowZ = this.nearlyStartZ;
            while (nearlyNowY != nearlyEndY) {
                if (this.nearlyNowX == this.nearlyEndX) {
                    if (this.nearlyNowZ == this.nearlyEndZ) {

                        this.nearlyNowZ = this.nearlyStartZ;
                        this.nearlyNowX = this.nearlyStartX;
                        nearlyNowY = nearlyNowY + 1;
                    } else {
                        this.nearlyNowX = this.nearlyStartX;
                        nearlyNowZ = nearlyNowZ + 1;
                    }
                } else {
                    nearlyNowX = nearlyNowX + 1;
                }
                BlockPos pos = new BlockPos(nearlyNowX, nearlyNowY, nearlyNowZ);
                if (isValid(pos)) {
                    return pos;
                }
            }
            this.nearlyNowX = this.nearlyStartX;
            this.nearlyNowY = this.nearlyStartY;
            this.nearlyNowZ = this.nearlyStartZ;
        }
        return null;
    }
    private Boolean isValid(BlockPos block) {
        if(!isIgnored(block)) {
            if (Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.potatoes && (Integer) getConfigByFunctionName.get("CropBot", "crop") == 0) {
                return Minecraft.getMinecraft().theWorld.getBlockState(block).getValue(BlockCrops.AGE) == 7;
            }
            if (Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.carrots && (Integer) getConfigByFunctionName.get("CropBot", "crop") == 1) {
                return Minecraft.getMinecraft().theWorld.getBlockState(block).getValue(BlockCrops.AGE) == 7;
            }
            if ((Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.brown_mushroom || Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.red_mushroom) && (Integer) getConfigByFunctionName.get("CropBot", "crop") == 2) {
                return true;
            }
            if (Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.nether_wart && (Integer) getConfigByFunctionName.get("CropBot", "crop") == 3) {
                return Minecraft.getMinecraft().theWorld.getBlockState(block).getValue(BlockNetherWart.AGE) == 3;
            }
            if (Minecraft.getMinecraft().theWorld.getBlockState(block).getBlock() == Blocks.wheat && (Integer) getConfigByFunctionName.get("CropBot", "crop") == 4) {
                return Minecraft.getMinecraft().theWorld.getBlockState(block).getValue(BlockCrops.AGE) == 7;
            }
        }
        return false;
    }
    private boolean isIgnored(BlockPos pos){
        boolean isIgnored = false;
        for(BlockPos ignored : ignoreList){
            if(pos.equals(ignored)){
                isIgnored = true;
                break;
            }
        }
        return isIgnored;
    }
    @SubscribeEvent
    public void Render(RenderWorldLastEvent event) {
        if(FunctionManager.getStatus("CropBot")) {
            if(nearlyTarget != null){
                Utils.BoxWithESP(nearlyTarget, Chroma.color,false);
                Utils.renderTrace(Minecraft.getMinecraft().thePlayer.getPosition(),nearlyTarget,Chroma.color,2);
            }
        }
    }
}
