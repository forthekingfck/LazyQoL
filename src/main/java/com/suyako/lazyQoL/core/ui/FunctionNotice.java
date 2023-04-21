package com.suyako.lazyQoL.core.ui;

import com.suyako.lazyQoL.core.notice.FunctionNoticeManager;

public class FunctionNotice {
    public static void ShowNotice(String message,boolean isEnable){
        FunctionNoticeManager.add(message,isEnable);
    }
}
