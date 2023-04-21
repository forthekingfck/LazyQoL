package com.suyako.lazyQoL.common;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.common.key.KeyLoader;
import com.suyako.lazyQoL.common.test.Screenshot;
import com.suyako.lazyQoL.core.EtherwarpTeleport;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.FunctionManager.SelectGuiFunctionExecutant;
import com.suyako.lazyQoL.core.config.EtherwarpWaypoints;
import com.suyako.lazyQoL.core.config.getConfigByFunctionName;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.core.gui.ClickGUI;
import com.suyako.lazyQoL.core.gui.KeyBindsGUI;
import com.suyako.lazyQoL.core.gui.SettingsGUI;
import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.core.type.SelectTable;
import com.suyako.lazyQoL.core.ui.QSF.QSFFunctionList;
import com.suyako.lazyQoL.core.ui.QSF.QSFSelectGUI;
import com.suyako.lazyQoL.core.ui.classic.ClassicFunctionList;
import com.suyako.lazyQoL.core.ui.classic.ClassicSelectGUI;
import com.suyako.lazyQoL.core.ui.transparent.FunctionList;
import com.suyako.lazyQoL.core.ui.transparent.SelectGUI;
import com.suyako.lazyQoL.core.ui.white.NewFunctionList;
import com.suyako.lazyQoL.core.ui.white.NewSelectGUI;
import com.suyako.lazyQoL.develop.Console;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import org.lwjgl.input.Keyboard;

import java.util.Map;

public class EventLoader {
    ClassicSelectGUI classicSelectGui = new ClassicSelectGUI();
    NewSelectGUI newSelectGui = new NewSelectGUI();
    NewFunctionList newFunctionList = new NewFunctionList();
    ClassicFunctionList classicFunctions = new ClassicFunctionList();
    SelectGUI transparentSelectGUI = new SelectGUI();
    FunctionList transparentFunctionList = new FunctionList();
    QSFSelectGUI qsfSelectGUI = new QSFSelectGUI();
    QSFFunctionList qsfFunctionList = new QSFFunctionList();
    SelectGuiFunctionExecutant exec = new SelectGuiFunctionExecutant();

    int KeyTime = 0;

    Boolean NowFunctionKeyStatus = true;

    int index = 0;

    public EventLoader() {
        MinecraftForge.EVENT_BUS.register(this);
        CustomEventHandler.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void RenderEvent(RenderGameOverlayEvent.Post event) {
        if (event.type == RenderGameOverlayEvent.ElementType.HELMET) {
            int hidePart = (Integer) getConfigByFunctionName.get("HUD", "hide");
            int style = (Integer) getConfigByFunctionName.get("HUD", "style");
            if (FunctionManager.getStatus("HUD")) {
                if (!(Minecraft.getMinecraft().currentScreen instanceof ClickGUI) && !(Minecraft.getMinecraft().currentScreen instanceof SettingsGUI)) {
                    if (style == 0) {
                        classicSelectGui.draw();
                        classicFunctions.draw();
                    }
                    if (style == 1) {
                        newSelectGui.draw();
                        newFunctionList.draw();
                    }
                    if (style == 2) {
                        transparentSelectGUI.draw();
                        transparentFunctionList.draw();
                    }
                    if (style == 3) {
                        qsfSelectGUI.draw();
                        qsfFunctionList.draw();
                    }
                }
            } else {
                if (hidePart == 0) {
                    if (style == 0) {
                        classicFunctions.draw();
                    }
                    if (style == 1) {
                        newFunctionList.draw();
                    }
                    if (style == 2) {
                        transparentFunctionList.draw();
                    }
                    if(style == 3){
                        qsfFunctionList.draw();
                    }
                }
                if (hidePart == 1) {
                    if (style == 0) {
                        classicSelectGui.draw();
                    }
                    if (style == 1) {
                        newSelectGui.draw();
                    }
                    if (style == 2) {
                        transparentSelectGUI.draw();
                    }
                    if (style == 3) {
                        qsfSelectGUI.draw();
                    }
                }
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public void RenderEvent(RenderGameOverlayEvent event) {
        if (!LazyQoL.shouldRenderBossBar) {
            if (event.type == RenderGameOverlayEvent.ElementType.BOSSHEALTH) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void OnKeyPressed(InputEvent.KeyInputEvent event) throws Exception {
        if (FunctionManager.getStatus("HUD") || (Integer) getConfigByFunctionName.get("HUD", "style") == 1) {
            if (KeyLoader.SelectGuiUP.isPressed()) {
                for (SelectTable table : SelectGUIStorage.TableStorage) {
                    if (table.getID().equals(LazyQoL.PresentGUI)) {
                        if (index - 1 < 0) {
                            index = table.getList().size() - 1;
                        } else {
                            index = index - 1;
                        }
                        if (index >= 0 && index < table.getList().size()) {
                            LazyQoL.PresentFunction = table.getList().get(index).getName();
                        }
                    }
                }
            }
            if (KeyLoader.SelectGuiDown.isPressed()) {
                for (SelectTable table : SelectGUIStorage.TableStorage) {
                    if (table.getID().equals(LazyQoL.PresentGUI)) {
                        if (index + 1 > table.getList().size() - 1) {
                            index = 0;
                        } else {
                            index = index + 1;
                        }
                        if (index >= 0 && index < table.getList().size()) {
                            LazyQoL.PresentFunction = table.getList().get(index).getName();
                        }
                    }
                }
            }
            if (KeyLoader.SelectGuiEnter.isPressed()) {
                if (KeyTime == 0) {
                    KeyTime = 1;
                    for (SelectTable table : SelectGUIStorage.TableStorage) {
                        if (table.getID().equals(LazyQoL.PresentGUI)) {
                            if (table.getList().get(index).getType().equals("table")) {
                                exec.EnterTable(table.getList().get(index).getName());
                                exec.SetRunFunctionStatus(false);
                                NowFunctionKeyStatus = false;
                                index = 0;
                            } else if (table.getList().get(index).getType().equals("function")) {

                                if (!exec.getRunFunctionStatus()) {
                                    if (NowFunctionKeyStatus) {
                                        exec.SetRunFunctionStatus(true);
                                        exec.RunFunction(table.getList().get(index).getName());
                                    } else {
                                        NowFunctionKeyStatus = true;
                                    }
                                } else {
                                    if (NowFunctionKeyStatus) {
                                        exec.RunFunction(table.getList().get(index).getName());
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                KeyTime = 0;
            }
            if (KeyLoader.SelectGuiBack.isPressed()) {
                LazyQoL.PresentGUI = "root";
                for (SelectTable table : SelectGUIStorage.TableStorage) {
                    if (table.getID().equals(LazyQoL.PresentGUI)) {
                        LazyQoL.PresentFunction = table.getList().get(0).getName();
                    }
                }
                index = 0;
            }
        }
        if (KeyLoader.HugeScreenshot.isPressed()) {
            Screenshot screenshot = new Screenshot();
            screenshot.CreateScreenshot(LazyQoL.ImageScaling);
        }
        if (KeyLoader.OpenClickGUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new ClickGUI(Minecraft.getMinecraft().currentScreen, "root"));
        }
        if (KeyLoader.OpenBindGUI.isPressed()) {
            Minecraft.getMinecraft().displayGuiScreen(new KeyBindsGUI(Minecraft.getMinecraft().currentScreen));
        }
        if (KeyLoader.OpenConsole.isPressed()) {
            Console.frame.setVisible(true);
        }
        if(KeyLoader.AddAOTV.isPressed()){
            EtherwarpTeleport.add(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector()).down());
        }
        if(KeyLoader.ClearAOTV.isPressed()){
            EtherwarpTeleport.clear();
        }
        if(KeyLoader.UndoAOTV.isPressed()){
            EtherwarpTeleport.undo();
        }
        if(KeyLoader.LoadAOTV.isPressed()){
            EtherwarpWaypoints.load();
        }
    }

    @SubscribeEvent
    public void handleFunctionKeyBinds(InputEvent.KeyInputEvent event) {
        for(Map.Entry<LazyQoLFunction,Integer> entry : LazyQoL.KeyBinding.entrySet()){
            if(Keyboard.getEventKey() == entry.getValue()) {
                if (Keyboard.getEventKeyState()) {
                    FunctionManager.switchStatus(entry.getKey().getName());
                }
            }
        }
    }
    /*@SubscribeEvent
    public void clientRender(RenderWorldLastEvent event) {
        Utils.OutlinedBoxWithESP(new BlockPos(Minecraft.getMinecraft().thePlayer.getPositionVector()).down(),Color.CYAN,false,2.5F);
    }*/
}

