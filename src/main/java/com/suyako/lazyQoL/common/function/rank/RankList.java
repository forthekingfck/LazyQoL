package com.suyako.lazyQoL.common.function.rank;

import net.minecraft.util.EnumChatFormatting;

import java.util.HashMap;

public class RankList {
    public static HashMap<String,String> rankMap = new HashMap<String, String>();
    public RankList() {
        rankMap.put("__Suyako__", EnumChatFormatting.LIGHT_PURPLE + "[" + EnumChatFormatting.WHITE + "Okay?" + EnumChatFormatting.LIGHT_PURPLE + "]" + "Suyako" + EnumChatFormatting.WHITE);
    }
}
