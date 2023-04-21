package com.suyako.lazyQoL.core.FunctionManager;

import com.suyako.LazyQoL;
import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.SelectTable;


public class SelectGuiFunctionExecutant {
    Boolean FunctionStatus = true;
    //If the state of the function is different from the specified state, switch the state of the function
    public void RunFunction(String FunctionName){
        if(FunctionStatus) {
            FunctionManager.switchStatus(FunctionName);
        }
    }
    //enter a table
    public void EnterTable(String TableName){
        LazyQoL.PresentGUI = TableName;
        for (SelectTable table : SelectGUIStorage.TableStorage) {
            if (table.getID().equals(LazyQoL.PresentGUI)) {
                LazyQoL.PresentFunction = table.getList().get(0).getName();
            }
        }
    }
    //Specifies the desired function state
    public void SetRunFunctionStatus(Boolean status){
        FunctionStatus = status;
    }
    //Get the currently required function status
    public Boolean getRunFunctionStatus(){
        return FunctionStatus;
    }
}
