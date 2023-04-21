package com.suyako.lazyQoL.core.config;

import com.suyako.lazyQoL.core.cache.CachePool;
import com.suyako.lazyQoL.core.register.LazyQoLRegister;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;

public class getConfigByFunctionName {
    public static CachePool<String,Object> cache;
    public getConfigByFunctionName(){
        //Instantiate a cache pool
        cache = new CachePool<>(5, 20, 2);
    }
    //get a config value by function name
    //will get it at cache first, if cannot find in cache,then will get it at config file and put it into cache pool
    public static Object get(String FunctionName,String ConfigID){
        Object value;
        value = cache.get(FunctionName + "_" + ConfigID);
        if(value == null){
            for (LazyQoLFunction function : LazyQoLRegister.FunctionList) {
                if (function.getName().equals(FunctionName)) {
                    value = function.getConfigurationValue(ConfigID);
                    if(value != null) {
                        cache.put(FunctionName + "_" + ConfigID, value);
                    } else {
                        break;
                    }
                    break;
                }
            }
        }
        return value;
    }
}
