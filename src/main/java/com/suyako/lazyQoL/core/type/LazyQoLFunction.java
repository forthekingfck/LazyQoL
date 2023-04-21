package com.suyako.lazyQoL.core.type;

import com.suyako.lazyQoL.core.settings.AbstractSettingOptionButton;
import com.suyako.lazyQoL.core.settings.AbstractSettingOptionTextField;
import com.suyako.lazyQoL.core.settings.ISettingOption;

import java.util.ArrayList;
import java.util.List;

public class LazyQoLFunction {
    String Name;
    Boolean Status = false;
    Boolean Configurable = false;
    List<ISettingOption> configurationOptions = new ArrayList<>();

    public LazyQoLFunction(String FunctionName){
        Name = FunctionName;
    }
    @Deprecated
    public int color;
    public String getName(){
        return Name;
    }
    public void setStatus(Boolean FunctionStatus){
        Status = FunctionStatus;
    }
    public Boolean getStatus(){
        return Status;
    }
    public void SwtichStatus(){
        Status = !Status;
    }
    public LazyQoLFunction addConfigurationOption(ISettingOption option){
        if(!Configurable){
           Configurable = true;
        }
        configurationOptions.add(option);
        return this;
    }
    public Object getConfigurationValue(String ID){
        for(ISettingOption option : configurationOptions){
            if(option instanceof AbstractSettingOptionButton) {
                if(((AbstractSettingOptionButton) option).ID.equals(ID)) {
                    return option.getValue();
                }
            }
            if(option instanceof AbstractSettingOptionTextField) {
                if(((AbstractSettingOptionTextField) option).ID.equals(ID)) {
                    return option.getValue();
                }
            }
        }
        return null;
    }
    public List<ISettingOption> getConfigurationList(){
        return configurationOptions;
    }
    public Boolean isConfigurable(){
        return Configurable;
    }
}
