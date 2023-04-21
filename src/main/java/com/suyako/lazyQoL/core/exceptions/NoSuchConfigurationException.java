package com.suyako.lazyQoL.core.exceptions;

public class NoSuchConfigurationException extends Exception{
    public NoSuchConfigurationException(String id) {
        super("cannot find the item that id is " + id);
    }
}
