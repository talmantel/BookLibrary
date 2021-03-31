package com.openu.sadna.booklibrary.common;


public class Resource<T> {

    private T data;
    private Throwable error;

    public Resource(T data) {
        this.data = data;
        this.error = null;
    }

    public Resource(Throwable error) {
        this.data = null;
        this.error = error;
    }

    public T getData(){
       return data;
    }

    public Throwable getError(){
        return error;
    }

    public boolean isSuccessful() {
        return error != null;
    }
}