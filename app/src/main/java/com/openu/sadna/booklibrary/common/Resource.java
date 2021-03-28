package com.openu.sadna.booklibrary.common;


public class Resource<T> {

    private T data;
    private Exception error;
    private boolean isSuccessful;

    public Resource(T data, Exception error, boolean isSuccessful) {
        this.data = data;
        this.error = error;
        this.isSuccessful = isSuccessful;
    }

    public T getData(){
       return data;
    }

    public Exception getError(){
        return error;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }
}