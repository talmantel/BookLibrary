package com.openu.sadna.booklibrary.common;

public class Event<T> {
    private boolean hasBeenHandled = false;

    private final T content;

    public Event(T content){
        this.content = content;
    }

    public T getContentIfNotHandled(){
        if(hasBeenHandled)
            return null;
        hasBeenHandled = true;
        return content;
    }

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
