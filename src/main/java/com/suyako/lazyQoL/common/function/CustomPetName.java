package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class CustomPetName {
    Utils utils = new Utils();
    String OriginalName = null;
    String PetName;

    public CustomPetName() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void AutoEnableFunction(ClientChatReceivedEvent event) {
        if (EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText()).contains("Welcome") && EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText()).contains("Hypixel SkyBlock")) {
            if (FunctionManager.getStatus("CustomPetNameTag")) {
                FunctionManager.setStatus("CustomPetNameTag", false);
                FunctionManager.setStatus("CustomPetNameTag", true);
            }
            if (!FunctionManager.getStatus("CustomPetNameTag")) {
                FunctionManager.setStatus("CustomPetNameTag", true);
            }
            utils.print("SkyBlock joined, auto enabled CustomPetNameTag");
        }
        if (EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText()).contains("You summoned your")) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (FunctionManager.getStatus("CustomPetNameTag")) {
                        FunctionManager.setStatus("CustomPetNameTag", false);
                        OriginalName = null;
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        FunctionManager.setStatus("CustomPetNameTag", true);
                        utils.print("pet changed, auto reload CustomPetNameTag");
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
        if (EnumChatFormatting.getTextWithoutFormattingCodes(event.message.getFormattedText()).contains("Autopet equipped your")) {
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    if (FunctionManager.getStatus("CustomPetNameTag")) {
                        FunctionManager.setStatus("CustomPetNameTag", false);
                        OriginalName = null;
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        FunctionManager.setStatus("CustomPetNameTag", true);
                        utils.print("pet changed, auto reload CustomPetNameTag");
                    }
                }
            };
            Thread thread = new Thread(runnable);
            thread.start();
        }
    }

    @SubscribeEvent
    public void SwitchFunction(CustomEventHandler.FunctionSwitchEvent event) {
        if (event.function.getName().equals("CustomPetNameTag")) {
            boolean notFoundPet = true;
            if (event.status) {
                Double x = Minecraft.getMinecraft().thePlayer.posX;
                Double y = Minecraft.getMinecraft().thePlayer.posY;
                Double z = Minecraft.getMinecraft().thePlayer.posZ;
                List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200), null);
                for (EntityArmorStand entity : entities) {
                    if (EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains(EnumChatFormatting.getTextWithoutFormattingCodes(Minecraft.getMinecraft().thePlayer.getName() + "'s")) && (!entity.getCustomNameTag().contains("❤"))) {
                        OriginalName = entity.getCustomNameTag();
                        notFoundPet = false;
                        break;
                    }
                }
                if (notFoundPet) {
                    event.setCanceled(true);
                    utils.print("cannot find pet, function disabled");
                }

            } else {
                Double x = Minecraft.getMinecraft().thePlayer.posX;
                Double y = Minecraft.getMinecraft().thePlayer.posY;
                Double z = Minecraft.getMinecraft().thePlayer.posZ;
                List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200), null);
                for (EntityArmorStand entity : entities) {
                    if (EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains(EnumChatFormatting.getTextWithoutFormattingCodes(Minecraft.getMinecraft().thePlayer.getName() + "'s")) && (!entity.getCustomNameTag().contains("❤"))) {
                        entity.setCustomNameTag(OriginalName);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void EnableFunction(CustomEventHandler.FunctionEnableEvent event) {
        if (Minecraft.getMinecraft().theWorld != null) {
            if (event.function.getName().equals("CustomPetNameTag")) {
                boolean notFoundPet = true;
                Double x = Minecraft.getMinecraft().thePlayer.posX;
                Double y = Minecraft.getMinecraft().thePlayer.posY;
                Double z = Minecraft.getMinecraft().thePlayer.posZ;
                List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200), null);
                for (EntityArmorStand entity : entities) {
                    if (EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains(EnumChatFormatting.getTextWithoutFormattingCodes(Minecraft.getMinecraft().thePlayer.getName() + "'s")) && (!entity.getCustomNameTag().contains("❤"))) {
                        OriginalName = entity.getCustomNameTag();
                        notFoundPet = false;
                        break;
                    }
                }
                if (notFoundPet) {
                    event.setCanceled(true);
                    utils.print("cannot find pet, function disabled");
                }
            }
        }
    }

    @SubscribeEvent
    public void DisableFunction(CustomEventHandler.FunctionDisabledEvent event) {
        if (Minecraft.getMinecraft().theWorld != null) {
            if (event.function.getName().equals("CustomPetNameTag")) {
                Double x = Minecraft.getMinecraft().thePlayer.posX;
                Double y = Minecraft.getMinecraft().thePlayer.posY;
                Double z = Minecraft.getMinecraft().thePlayer.posZ;
                List<EntityArmorStand> entities = Minecraft.getMinecraft().theWorld.getEntitiesWithinAABB(EntityArmorStand.class, new AxisAlignedBB(x - 200, y - 200, z - 200, x + 200, y + 200, z + 200), null);
                for (EntityArmorStand entity : entities) {
                    if (EnumChatFormatting.getTextWithoutFormattingCodes(entity.getCustomNameTag()).contains(EnumChatFormatting.getTextWithoutFormattingCodes(Minecraft.getMinecraft().thePlayer.getName() + "'s"))) {
                        entity.setCustomNameTag(OriginalName);
                        break;
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void modifyPetName(RenderLivingEvent.Specials.Pre<EntityLivingBase> event) {
        if (Minecraft.getMinecraft().theWorld != null) {
            try {
                if (FunctionManager.getStatus("CustomPetNameTag")) {
                    Entity entity = event.entity;
                    if (entity instanceof EntityArmorStand) {
                        if (entity.hasCustomName()) {
                            if (entity.getCustomNameTag().contains(Minecraft.getMinecraft().thePlayer.getDisplayNameString())) {
                                if (OriginalName != null) {
                                    PetName = OriginalName;
                                    if ((Integer) getConfigByFunctionName.get("CustomPetNameTag", "petLevel") != 0) {
                                        String[] Temp1 = PetName.split("]");
                                        String[] Temp2 = Temp1[0].split("\\[");
                                        PetName = Temp2[0] + "[" + EnumChatFormatting.GRAY + "Lv" + (Integer) getConfigByFunctionName.get("CustomPetNameTag", "petLevel") + EnumChatFormatting.DARK_GRAY + "]" + Temp1[1];
                                    }
                                    String ruleStr = (String) getConfigByFunctionName.get("CustomPetNameTag", "petName");
                                    if(!ruleStr.equalsIgnoreCase("null")){
                                        String[] rules = ruleStr.split(",");
                                        for (String rule : rules) {
                                            String[] key2value = rule.split("=");
                                            PetName = PetName.replace(key2value[0], key2value[1]);
                                        }
                                        entity.setCustomNameTag(PetName);
                                    }
                                }


                            }
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
