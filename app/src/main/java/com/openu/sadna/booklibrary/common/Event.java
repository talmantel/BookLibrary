package com.openu.sadna.booklibrary.common;

public class Event<T> {
    private boolean hasBeenHandled = false;

    private T content;

    public Event(T content){
        this.content = content;
    }

    public T getContentIfNotHandled(){
        if(hasBeenHandled)
            return null;
        hasBeenHandled = true;
        return content;
    }

    public boolean hasBeenHandled() {
        return hasBeenHandled;
    }
}
