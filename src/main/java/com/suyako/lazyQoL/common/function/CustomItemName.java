package com.suyako.lazyQoL.common.function;

import java.util.HashMap;

public class CustomItemName {
    public static HashMap<String,String> CustomName = new HashMap<String,String>();
    public static Boolean hasCustomName(String UUID){
        return CustomName.containsKey(UUID);
    }
    public static String getCustomName(String UUID) {
        return CustomName.get(UUID);
    }
}
