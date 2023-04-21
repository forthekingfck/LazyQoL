package com.suyako.lazyQoL.core.storage;

import com.suyako.lazyQoL.core.type.SelectTable;

import java.util.ArrayList;
import java.util.List;

public class SelectGUIStorage {
    //All SelectTables are stored
    public static List<SelectTable> TableStorage = new ArrayList<SelectTable>();
    //Replace the entire array (elements in )
    public void SetWholeList(List<SelectTable> table){
        TableStorage = table;
    }
}
