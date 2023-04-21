package com.suyako.lazyQoL.common.function;

import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.InventoryClicker;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.utils.thread.ThreadPool;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.awt.*;
public class HarpBot {
    static boolean isInHarp = false;
    static Slot slot;
    static long timestamp;
    static int guiLeft;
    static int guiTop;
    Minecraft mc = Minecraft.getMinecraft();
    public HarpBot() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }
    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        if(event.gui instanceof GuiChest) {
            Container container = ((GuiChest) event.gui).inventorySlots;
            if(container instanceof ContainerChest) {
                ContainerChest containerChest = (ContainerChest) container;
                if (containerChest.getLowerChestInventory().getDisplayName().getUnformattedText().startsWith("Harp -")) {
                    isInHarp = true;
                }
            }
        }
    }
    @SubscribeEvent
    public void onGuiRender(GuiScreenEvent.DrawScreenEvent.Post event) {
        if (!isInHarp) return;
        if (slot != null && System.currentTimeMillis() - timestamp < (Integer) getConfigByFunctionName.get("HarpBot","delay") * 0.75) {
            GlStateManager.disableLighting();
            GlStateManager.disableDepth();
            GlStateManager.disableBlend();
            ScaledResolution resolution = new ScaledResolution(mc);
            mc.fontRendererObj.drawStringWithShadow(
                    "click slot, slot id: " + slot.slotNumber,
                    (resolution.getScaledWidth() / 2.0F) - (mc.fontRendererObj.getStringWidth("click slot, slot id: " + slot.slotNumber) / 2.0F),
                    resolution.getScaledHeight() / 4.0F,
                    Color.CYAN.getRGB()
            );
            GlStateManager.enableLighting();
            GlStateManager.enableDepth();
        }
    }
    @SubscribeEvent
    public void onPacket(CustomEventHandler.PacketReceivedEvent event) throws InterruptedException {
        if (FunctionManager.getStatus("HarpBot")) {
            if (isInHarp) {
                if (!(Minecraft.getMinecraft().currentScreen instanceof GuiChest)) {
                    isInHarp = false;
                }
            } else {
                return;
            }
            if (event.packet instanceof S2FPacketSetSlot) {
                S2FPacketSetSlot packetSetSlot = (S2FPacketSetSlot) event.packet;
                ItemStack itemStack = packetSetSlot.func_149174_e();
                int windowId = packetSetSlot.func_149175_c();
                if (itemStack != null) {
                    int slotNumber = packetSetSlot.func_149173_d();
                    if (slotNumber > 26 && slotNumber < 36 &&
                            itemStack.getItem() instanceof ItemBlock &&
                            ((ItemBlock) itemStack.getItem()).getBlock() == Blocks.wool) {
                        ThreadPool.submit(() -> {
                            try {
                                Thread.sleep(50L);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            int clicksNeeded = 0;
                            for (int i = slotNumber; i >= 0; i -= 9) {
                                ItemStack stackInSlot = mc.thePlayer.openContainer.inventorySlots.get(i).getStack();
                                if (((ItemBlock) stackInSlot.getItem()).getBlock() == ((ItemBlock) itemStack.getItem()).getBlock()) {
                                    clicksNeeded++;
                                } else {
                                    break;
                                }
                            }
                            for (int i = 1; i <= clicksNeeded; i++) {
                                try {
                                    Thread.sleep((Integer) getConfigByFunctionName.get("HarpBot","delay"));
                                } catch (InterruptedException e) {
                                    throw new RuntimeException(e);
                                }
                                slot = mc.thePlayer.openContainer.inventorySlots.get(slotNumber);
                                timestamp = System.currentTimeMillis();
                                InventoryClicker.ClickSlot(mc.thePlayer.openContainer.inventorySlots.get(slotNumber + 9), InventoryClicker.Type.DROP);
                            }
                        });
                    }
                }
            }
        }
    }
}
