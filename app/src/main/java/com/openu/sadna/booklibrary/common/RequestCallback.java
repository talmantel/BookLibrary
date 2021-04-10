package com.openu.sadna.booklibrary.common;

public interface RequestCallback<T>{
    void onNetworkResponse(NetworkRequestEvent event, T data);
}