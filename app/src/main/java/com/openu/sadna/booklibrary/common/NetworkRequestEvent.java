package com.openu.sadna.booklibrary.common;

public class NetworkRequestEvent extends Event<NetworkRequestEvent.NetworkResult>{

    public enum NetworkResult {
        SUCCESS, NETWORK_ERROR, SERVER_ERROR;
    }

    public NetworkRequestEvent(NetworkResult content) {
        super(content);
    }

    public static NetworkRequestEvent success(){
        return new NetworkRequestEvent(NetworkResult.SUCCESS);
    }

    public static NetworkRequestEvent networkError(){
        return new NetworkRequestEvent(NetworkResult.NETWORK_ERROR);
    }

    public static NetworkRequestEvent serverError(){
        return new NetworkRequestEvent(NetworkResult.SERVER_ERROR);
    }
}
