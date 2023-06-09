package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.utils.BlockScanner;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.block.BlockStone;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SapphireGrottoESP {
    static BlockScanner scanner = new BlockScanner();
    static boolean checked = false;
    List<BlockPos> pos = new ArrayList<BlockPos>();
    List<BlockPos> possibleCoord = new ArrayList<BlockPos>();
    public SapphireGrottoESP() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onEnable(CustomEventHandler.FunctionEnableEvent event){
        if (event.function.getName().equals("SapphireGrottoESP")) {
            init();
        }
    }
    @SubscribeEvent
    public void onSwitch(CustomEventHandler.FunctionSwitchEvent event){
        if (event.function.getName().equals("SapphireGrottoESP") && event.status) {
            init();
        }
    }
    public void init(){
        possibleCoord.clear();
        if(Minecraft.getMinecraft().theWorld != null) {
            checked = false;
            Utils.print("start scan, retry enable can scan again");
            scanner.start(
                    new BlockPos(824, 64, 824),
                    new BlockPos(515, 187, 514),
                    Blocks.stone_slab, (Integer) getConfigByFunctionName.get("SapphireGrottoESP", "step"), (Integer) getConfigByFunctionName.get("SapphireGrottoESP", "thread")
            );
        }
    }
    @SubscribeEvent
    public void onTick(TickEvent.ClientTickEvent event){
        if(FunctionManager.getStatus("SapphireGrottoESP")) {
            if (event.phase == TickEvent.Phase.END) {
                if (scanner.finished && !checked) {
                    pos = scanner.finalList;
                    try {
                        for (BlockPos position : pos) {
                            if (Minecraft.getMinecraft().theWorld.getBlockState(position.up()).getBlock() == Blocks.double_stone_slab) {
                                if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(2)).getBlock() == Blocks.stone && Minecraft.getMinecraft().theWorld.getBlockState(position.up(2)).getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE) {
                                    if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(3)).getBlock() == Blocks.stone && Minecraft.getMinecraft().theWorld.getBlockState(position.up(3)).getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE) {
                                        if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(4)).getBlock() == Blocks.stone && Minecraft.getMinecraft().theWorld.getBlockState(position.up(4)).getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE) {
                                            if (Minecraft.getMinecraft().theWorld.getBlockState(position.down()).getBlock() == Blocks.double_stone_slab) {
                                                if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(5)).getBlock() == Blocks.stone_slab) {
                                                    if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(6)).getBlock() == Blocks.double_stone_slab) {
                                                        if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(7)).getBlock() == Blocks.stone && Minecraft.getMinecraft().theWorld.getBlockState(position.up(7)).getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE) {
                                                            if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(2)).getBlock() == Blocks.double_stone_slab) {
                                                                possibleCoord.add(position);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (Minecraft.getMinecraft().theWorld.getBlockState(position.down()).getBlock() == Blocks.double_stone_slab) {
                                        if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(2)).getBlock() == Blocks.double_stone_slab) {
                                            if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(4)).getBlock() == Blocks.double_stone_slab) {
                                                if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(6)).getBlock() == Blocks.double_stone_slab) {
                                                    if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(3)).getBlock() == Blocks.air) {
                                                        if (Minecraft.getMinecraft().theWorld.getBlockState(position.down(5)).getBlock() == Blocks.stone && Minecraft.getMinecraft().theWorld.getBlockState(position.down(5)).getValue(BlockStone.VARIANT) == BlockStone.EnumType.DIORITE) {
                                                            if (Minecraft.getMinecraft().theWorld.getBlockState(position.up(3)).getBlock() == Blocks.air) {
                                                                possibleCoord.add(position);
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } catch(Exception e){

                    }
                    checked = true;
                }
            }
        }
    }
    @SubscribeEvent
    public void onRender(RenderWorldLastEvent event){
        if(FunctionManager.getStatus("SapphireGrottoESP")) {
            if (!possibleCoord.isEmpty()) {
                for (BlockPos position : possibleCoord) {
                    Utils.OutlinedBoxWithESP(position, Color.CYAN, false, 2.5F);
                    Utils.renderTrace(Minecraft.getMinecraft().thePlayer.getPositionVector(), new Vec3(position), Color.CYAN, 3.0F);
                }
            } else if(checked){
                FunctionManager.setStatus("SapphireGrottoESP", false);
                Utils.print("cannot Sapphire cord");
            }
        }
    }
}
