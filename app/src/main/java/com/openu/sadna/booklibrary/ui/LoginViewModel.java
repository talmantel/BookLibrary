package com.openu.sadna.booklibrary.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;


public class LoginViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private MediatorLiveData<Event<Integer>> showError = new MediatorLiveData<>();
    private Repository repository;

    public LoginViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
    }

    public void login(String username, String password) {
        LiveData<NetworkRequestEvent> responseLiveData = repository.login(username, password);
        showError.addSource(responseLiveData, new Observer<NetworkRequestEvent>() {
            @Override
            public void onChanged(NetworkRequestEvent networkRequestEvent) {
                if(networkRequestEvent != null && !networkRequestEvent.hasBeenHandled()) {
                    switch (networkRequestEvent.getContentIfNotHandled()){
                        case SUCCESS:
                            if(currentUser.getValue() == null)
                                showError.setValue(new Event<>(R.string.invalid_credentials));
                            break;
                        case NETWORK_ERROR:
                            showError.setValue(new Event<>(R.string.network_error));
                            break;
                        case SERVER_ERROR:
                            showError.setValue(new Event<>(R.string.server_error));
                            break;
                    }
                }
            }
        });

    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }
}