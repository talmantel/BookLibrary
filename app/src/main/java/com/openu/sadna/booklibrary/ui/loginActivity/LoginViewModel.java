package com.openu.sadna.booklibrary.ui.loginActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private MutableLiveData<Boolean> showProgressBar;
    private MediatorLiveData<Event<Integer>> showError;
    private Repository repository;

    public LoginViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MediatorLiveData<>();
        showProgressBar = new MutableLiveData<>();
    }

    public void login(String username, String password) {
        showProgressBar.setValue(true);
        repository.login(username, password, new Repository.RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(@NotNull NetworkRequestEvent event, Void data) {
                switch (event){
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
                showProgressBar.setValue(false);
            }
        });
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }
}