package com.suyako.lazyQoL.core.register;

import com.suyako.lazyQoL.core.storage.SelectGUIStorage;
import com.suyako.lazyQoL.core.type.LazyQoLFunction;
import com.suyako.lazyQoL.core.type.SelectObject;
import com.suyako.lazyQoL.core.type.SelectTable;
import com.suyako.lazyQoL.utils.FontManager;

import java.util.ArrayList;
import java.util.List;

public class LazyQoLRegister {
    //All registered functions are stored
    public static List<LazyQoLFunction> FunctionList = new ArrayList<LazyQoLFunction>();
    //GUI list storage
    SelectGUIStorage SelectGuiStorage = new SelectGUIStorage();
    //Register a function
    public void RegisterFunction(LazyQoLFunction function) {
        FunctionList.add(function);
    }
    //Reorder the list of functions to make the FunctionList more beautiful
    public static void ReList(){
        List<LazyQoLFunction> originalList = FunctionList;
        LazyQoLFunction originalArray[] = originalList.toArray(new LazyQoLFunction[0]);
        for (int i = 0; i < originalArray.length; i++) {
            LazyQoLFunction insertValue=originalArray[i];
            int insertIndex=i-1;
            while (insertIndex>=0 && FontManager.QuicksandBoldFont.getStringWidth(insertValue.getName()) < FontManager.QuicksandBoldFont.getStringWidth(originalArray[insertIndex].getName())) {
                originalArray[insertIndex+1]=originalArray[insertIndex];
                insertIndex--;
            }
            originalArray[insertIndex+1]=insertValue;
        }
        List<LazyQoLFunction> newList = new ArrayList<>();
        for(int i=originalArray.length - 1; i >= 0; i--){
            newList.add(originalArray[i]);
        }
        FunctionList.clear();
        FunctionList.addAll(newList);
    }
    //Register an object that you can select
    public void RegisterSelectObject(SelectObject object){
        List<SelectTable> NewList = new ArrayList<SelectTable>();
        for(SelectTable table : SelectGUIStorage.TableStorage){
            if(table.getID().equals(object.getParentID())){
                table.add(object);
            }
            NewList.add(table);
            SelectGuiStorage.SetWholeList(NewList);

        }
    }
    //Register a empty list,you still need to invoke RegisterSelectObject to add SelectObject into list
    public void RegisterTable(SelectTable table) {
        SelectGUIStorage.TableStorage.add(table);
    }
}
