package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.common.mixins.EntityPlayerSPAccessor;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;

import java.util.List;

public class SynthesizerAura {
    static Boolean hasCharm = false;
    static Boolean hasAtom = false;
    Utils utils = new Utils();
    double range = 2.9;
    public static long latest;
    public static EntityLivingBase entityTarget;
    static EntityLivingBase LastClickedEntity;
    public SynthesizerAura(){
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onEnable(CustomEventHandler.FunctionEnableEvent event){
        if(event.function.getName().equals("SynthesizerAura")){
            if(Minecraft.getMinecraft().theWorld != null) {
                if (onInit()) {
                    event.setCanceled(true);
                }
            }
        }
    }
    @SubscribeEvent
    public void onSwitch(CustomEventHandler.FunctionSwitchEvent event){
        if(event.function.getName().equals("SynthesizerAura")){
            if(Minecraft.getMinecraft().theWorld != null) {
                if (event.status) {
                    if (onInit()) {
                        event.setCanceled(true);
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void MotionChangePreEvent(CustomEventHandler.MotionChangeEvent.Pre event){
        if(Minecraft.getMinecraft().theWorld != null) {
            if (FunctionManager.getStatus("SynthesizerAura")) {
                if (Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    if (StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("charminizer") || StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("atominizer")) {
                        entityTarget = getTarget();
                        if (entityTarget != null) {
                            float[] angles = Utils.getServerAngles(entityTarget);
                            event.yaw = ((EntityPlayerSPAccessor) Minecraft.getMinecraft().thePlayer).getLastReportedYaw() - MathHelper.wrapAngleTo180_float((float) Math.max(-180, Math.min((double) (((EntityPlayerSPAccessor) Minecraft.getMinecraft().thePlayer).getLastReportedYaw() - angles[0]), 180)));
                            event.pitch = ((EntityPlayerSPAccessor) Minecraft.getMinecraft().thePlayer).getLastReportedPitch() - MathHelper.wrapAngleTo180_float((float) Math.max(-90, Math.min((double) (((EntityPlayerSPAccessor) Minecraft.getMinecraft().thePlayer).getLastReportedPitch() - angles[1]), 90)));
                        }
                    } else {
                        entityTarget = null;
                    }
                }
            }
        }
    }
    @SubscribeEvent
    public void MotionChangePostEvent(CustomEventHandler.MotionChangeEvent event) {
        if(Minecraft.getMinecraft().theWorld != null) {
            if (FunctionManager.getStatus("SynthesizerAura")) {
                if (entityTarget != null) {
                    //utils.devLog("target:" + entityTarget);
                    //Minecraft.getMinecraft().playerController.interactWithEntitySendPacket(Minecraft.getMinecraft().thePlayer, entityTarget);
                    //Minecraft.getMinecraft().playerController.sendUseItem(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, Minecraft.getMinecraft().thePlayer.getHeldItem());
                    //KeyBinding.setKeyBindState(Minecraft.getMinecraft().gameSettings.keyBindUseItem.getKeyCode(),true);
                    C02PacketUseEntity c02 = new C02PacketUseEntity(entityTarget, C02PacketUseEntity.Action.INTERACT);
                    Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(c02);
                    LastClickedEntity = entityTarget;
                }
            }
        }
    }
    @SubscribeEvent
    public void onMouse(InputEvent.MouseInputEvent event){
        if(Minecraft.getMinecraft().theWorld != null) {
            if (FunctionManager.getStatus("SynthesizerAura")) {
                if(Minecraft.getMinecraft().thePlayer.getHeldItem() != null) {
                    if (StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("charminizer") || StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("atominizer")) {
                        if (Minecraft.getMinecraft().gameSettings.keyBindAttack.isPressed()) {
                            try {
                                if (System.currentTimeMillis() - latest >= 0) {
                                    latest = System.currentTimeMillis();
                                    for (int i = 0; i < 8; ++i) {
                                        ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
                                        if (stack != null && (StringUtils.stripControlCodes(stack.getDisplayName().toLowerCase()).contains("aspect of the end") || StringUtils.stripControlCodes(stack.getDisplayName().toLowerCase()).contains("aspect of the void"))) {
                                            int currentSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
                                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = i;
                                            Minecraft.getMinecraft().playerController.sendUseItem(Minecraft.getMinecraft().thePlayer, Minecraft.getMinecraft().theWorld, stack);
                                            Minecraft.getMinecraft().thePlayer.inventory.currentItem = currentSlot;
                                            break;
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }
    private EntityLivingBase getTarget(){
        if ((!(Minecraft.getMinecraft().currentScreen instanceof GuiContainer) && Minecraft.getMinecraft().theWorld != null)) {
            double x = Minecraft.getMinecraft().thePlayer.posX;
            double y = Minecraft.getMinecraft().thePlayer.posY;
            double z = Minecraft.getMinecraft().thePlayer.posZ;
            List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - (range*2 / 2d), y - (range*2 / 2d), z - (range*2 / 2d), x + (range*2 / 2d), y + (range*2 / 2d), z + (range*2 / 2d)), null);
            for(EntityArmorStand entity : entities){
                if(EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains("Exe") || EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains("Wai") || EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains("Zee")){
                    if((LastClickedEntity == null) || (LastClickedEntity != entity && StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("charminizer")) || StringUtils.stripControlCodes(Minecraft.getMinecraft().thePlayer.getHeldItem().getDisplayName().toLowerCase()).contains("atominizer")){
                        return entity;
                    }
                }
            }
        }
        return null;
    }
    private boolean onInit(){
        checkItem();
        if(!hasCharm && !hasAtom){
            utils.print("cannot find Charminizer or Atominizer in your hotbar");
            return true;
        }
       utils.print("hand Charminizer or Atominizer left click to use Aspect of the End/Void teleport function");
        entityTarget = null;
        LastClickedEntity = null;
        return false;
    }
    private void checkItem(){
        boolean charm = false;
        boolean atom = false;
        for (int i = 0; i < 8; ++i) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (stack != null && StringUtils.stripControlCodes(stack.getDisplayName().toLowerCase()).contains("charminizer")) {
                charm = true;
                break;
            }
        }
        hasCharm = charm;
        for (int i = 0; i < 8; ++i) {
            ItemStack stack = Minecraft.getMinecraft().thePlayer.inventory.mainInventory[i];
            if (stack != null && StringUtils.stripControlCodes(stack.getDisplayName().toLowerCase()).contains("atominizer")) {
                atom = true;
                break;
            }
        }
        hasAtom = atom;
    }
}
