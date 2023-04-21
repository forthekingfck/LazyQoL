package com.suyako.lazyQoL.core.FunctionManager;

import com.suyako.lazyQoL.core.config.ConfigLoader;
import com.suyako.lazyQoL.core.event.CustomEventHandler;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.core.ui.FunctionNotice;
import com.suyako.lazyQoL.core.settings.AbstractSettingOptionButton;
import com.suyako.lazyQoL.core.settings.AbstractSettingOptionTextField;
import com.suyako.lazyQoL.core.settings.ISettingOption;
import com.suyako.lazyQoL.utils.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public class FunctionManager {
    //The function that is currently bound
    private static String currentFunction = "";
    //get a function's status
    public static Boolean getStatus(String Name){
        /*Optional<LazyQoLFunction> optional = LazyQoLRegister.FunctionList.stream().filter(it -> it.getName().equals(Name)).findFirst();
        if(optional.isPresent()){
            return optional.get().getStatus();
        } else {
            return false;
        }*/
        for(int i = 0; i < LazyQoLRegister.FunctionList.size(); ++i){
            if(LazyQoLRegister.FunctionList.get(i).getName().equals(Name)){
                return LazyQoLRegister.FunctionList.get(i).getStatus();
            }
        }
        /*for(LazyQoLFunction function : LazyQoLRegister.FunctionList){

        }*/
        return false;
    }
    //set a funtion's status
    public static void setStatus(String Name,Boolean status){
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            if(function.getName().equals(Name)){
                if(status){
                    CustomEventHandler.FunctionEnableEvent event;
                    event = new CustomEventHandler.FunctionEnableEvent(function);
                    CustomEventHandler.EVENT_BUS.post(event);
                    if(!event.isCanceled()){
                        //function.color = new Random().nextInt(4);
                        function.setStatus(true);
                        FunctionNotice.ShowNotice(function.getName(),function.getStatus());
                        ConfigLoader.setFunctionStateStorage();
                        Utils.print(Name + EnumChatFormatting.WHITE + " enabled");
                    }
                }
                if(!status){
                    CustomEventHandler.FunctionDisabledEvent event;
                    event = new CustomEventHandler.FunctionDisabledEvent(function);
                    CustomEventHandler.EVENT_BUS.post(event);
                    if(!event.isCanceled()){
                        //function.color = new Random().nextInt(4);
                        function.setStatus(false);
                        FunctionNotice.ShowNotice(function.getName(),function.getStatus());
                        ConfigLoader.setFunctionStateStorage();
                        Utils.print(Name + EnumChatFormatting.WHITE + " disabled");
                    }
                }
                break;
            }
        }
    }
    //switch a function's status without any notice
    public static void switchStatusNoNotice(String Name) {
        
        for (LazyQoLFunction function : LazyQoLRegister.FunctionList) {
            if (function.getName().equals(Name)) {


                CustomEventHandler.FunctionSwitchEvent event;
                event = new CustomEventHandler.FunctionSwitchEvent(function, !function.getStatus());
                CustomEventHandler.EVENT_BUS.post(event);
                if (!event.isCanceled()) {
                    //function.color = new Random().nextInt(4);
                    function.SwtichStatus();
                    ConfigLoader.setFunctionStateStorage();
                }
                break;

            }
        }
    }
    //set a function's status without any notice
    public static void setStatusNoNotice(String Name,Boolean status){
        
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            if(function.getName().equals(Name)){
                if(status){
                    CustomEventHandler.FunctionEnableEvent event;
                    event = new CustomEventHandler.FunctionEnableEvent(function);
                    CustomEventHandler.EVENT_BUS.post(event);
                    if(!event.isCanceled()){
                        //function.color = new Random().nextInt(4);
                        function.setStatus(true);
                        ConfigLoader.setFunctionStateStorage();
                    }
                }
                if(!status){
                    CustomEventHandler.FunctionDisabledEvent event;
                    event = new CustomEventHandler.FunctionDisabledEvent(function);
                    CustomEventHandler.EVENT_BUS.post(event);
                    if(!event.isCanceled()){
                        //function.color = new Random().nextInt(4);
                        function.setStatus(false);
                        ConfigLoader.setFunctionStateStorage();
                    }
                }
                break;
            }
        }
    }
    //switch a function's status
    public static void switchStatus(String Name){
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            if(function.getName().equals(Name)){
                    CustomEventHandler.FunctionSwitchEvent event;
                    event = new CustomEventHandler.FunctionSwitchEvent(function,!function.getStatus());
                    CustomEventHandler.EVENT_BUS.post(event);
                    if(!event.isCanceled()){
                        //function.color = new Random().nextInt(4);
                        function.SwtichStatus();
                        if(!Name.equals("Pathfinding")) {
                        } else {
                            FunctionNotice.ShowNotice(Name,function.getStatus());
                        }
                        ConfigLoader.setFunctionStateStorage();
                        if(function.getStatus()){
                            Utils.print(Name + EnumChatFormatting.WHITE + " enabled");
                        } else {
                            Utils.print(Name + EnumChatFormatting.WHITE + " disabled");
                        }
                    }
                break;
            }
        }
    }
    //get the longest function name and add 20 more
    public static int getLongestTextWidthAdd20(){
        int width = 0;
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
           width = Math.max(Minecraft.getMinecraft().fontRendererObj.getStringWidth(function.getName()),width);
        }
        return width + 20;
    }
    //get the longest function name and add 5 more
    public static int getLongestTextWidthAdd5(){
        int width = 0;
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            width = Math.max(Minecraft.getMinecraft().fontRendererObj.getStringWidth(function.getName()),width);
        }
        return width + 5;
    }
    //use function name to get a LazyQoLFunction
    public static LazyQoLFunction getFunctionByName(String name){
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList) {
            if(function.getName().equals(name)){
                return function;
            }
        }
        return null;
    }
    //bind a function
    public static void bindFunction(String Name){
        currentFunction = Name;
    }
    //add a config to bound function
    public static void addConfiguration(ISettingOption option){
        try {
            if(option instanceof AbstractSettingOptionButton) {
                ((AbstractSettingOptionButton)option).parentFunction = currentFunction;
                FunctionManager.getFunctionByName(currentFunction).addConfigurationOption(option);
            } else if(option instanceof AbstractSettingOptionTextField){
                ((AbstractSettingOptionTextField)option).parentFunction = currentFunction;
                FunctionManager.getFunctionByName(currentFunction).addConfigurationOption(option);
            }
        } catch(NullPointerException e){
            System.err.println("[LazyQoL] Unable to add Configuration " + option + " to " + currentFunction + ".");
        }
    }
}
