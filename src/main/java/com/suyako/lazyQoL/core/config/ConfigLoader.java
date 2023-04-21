package com.suyako.lazyQoL.core.config;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.FunctionManager.FunctionManager;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ConfigLoader {
    private static Configuration config;
    public ConfigLoader(FMLPreInitializationEvent event) {
        config = new Configuration(new File(LazyQoL.LazyQoLDirectory,"config.cfg"));
        config.load();
    }
    //save current enabled functions
    public static void setFunctionStateStorage(){
        List<String> list = new ArrayList<String>();
        for(LazyQoLFunction function : LazyQoLRegister.FunctionList){
            if(function.getStatus()){
                list.add(function.getName());
            }
        }
        config.get(Configuration.CATEGORY_GENERAL, "FunctionStorage",list.toArray(new String[0]), "FunctionOpenStorage").set(list.toArray(new String[0]));
        config.save();
        config.load();
    }
    //read enabled function in config and apply it
    public static void applyFunctionState(){
        String[] functions = config.get(Configuration.CATEGORY_GENERAL, "FunctionStorage",new String[0], "FunctionOpenStorage").getStringList();
        for(String function : functions){
            FunctionManager.setStatus(function,true);
        }
        //config.save();
        config.load();
    }
    //Common settings
    public static void setBoolean(String key,Boolean value,Boolean defaultValue) {
        config.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        config.save();
        removeConfigCache(key);
        config.load();
    }
    public static boolean getBoolean(String key,Boolean defaultValue) {
        boolean status = config.get(Configuration.CATEGORY_GENERAL,key, defaultValue,"").getBoolean();
        //config.save();
        config.load();
        return status;
    }
    public static void setString(String key,String value,String defaultValue) {
        config.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        config.save();
        removeConfigCache(key);
        config.load();
    }
    public static String getString(String key,String defaultValue) {
        String str = config.get(Configuration.CATEGORY_GENERAL,key, defaultValue,"").getString();
        //config.save();
        config.load();
        return str;
    }
    public static void setInt(String key,int value,int defaultValue) {
        config.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        config.save();
        removeConfigCache(key);
        config.load();
    }
    public static int getInt(String key,int defaultValue) {
        int number = config.get(Configuration.CATEGORY_GENERAL,key, defaultValue,"").getInt();
        //config.save();
        config.load();
        return number;
    }
    public static void setDouble(String key,double value,double defaultValue) {
        config.get(Configuration.CATEGORY_GENERAL, key, defaultValue, "").set(value);
        config.save();
        removeConfigCache(key);
        config.load();
    }
    public static double getDouble(String key,double defaultValue) {
        double number = config.get(Configuration.CATEGORY_GENERAL,key, defaultValue,"").getDouble();
        //config.save();
        config.load();
        return number;
    }

    private static void removeConfigCache(String key){
        getConfigByFunctionName.cache.remove(key);
    }
}
