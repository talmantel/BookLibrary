package com.openu.sadna.booklibrary.ui.loginActivity;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;

import org.jetbrains.annotations.NotNull;

public class LoginViewModel extends ViewModel {

    private final LiveData<User> currentUser;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<Event<Integer>> showError;
    private final Repository repository;

    public LoginViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
    }

    public void login(@NotNull String username, @NotNull String password) {
        Integer credentialsError = validateLogin(username, password);
        if(credentialsError != null)
            showError.setValue(new Event<>(credentialsError));
        else {
            isLoading.setValue(true);
            repository.login(username, password, new RequestCallback<Void>() {
                @Override
                public void onNetworkResponse(@NotNull NetworkRequestEvent event, Void data) {
                    switch (event) {
                        case SUCCESS:
                            if (currentUser.getValue() == null)
                                showError.setValue(new Event<>(R.string.invalid_credentials));
                            break;
                        case NETWORK_ERROR:
                            showError.setValue(new Event<>(R.string.network_error));
                            break;
                        case SERVER_ERROR:
                            showError.setValue(new Event<>(R.string.invalid_credentials));
                            break;
                    }
                    isLoading.setValue(false);
                }
            });
        }
    }

    public void register(@NotNull String username, @NotNull String password, @NotNull String repeatPassword, @NotNull String fname, @NotNull String lname) {
        Integer credentialsError = validateRegistration(username, password, repeatPassword, fname, lname);
        if(credentialsError != null)
            showError.setValue(new Event<>(credentialsError));
        else {
            isLoading.setValue(true);
            repository.register(username, password, fname, lname, new RequestCallback<Void>() {
                @Override
                public void onNetworkResponse(@NotNull NetworkRequestEvent event, Void data) {
                    switch (event) {
                        case SUCCESS:
                            if (currentUser.getValue() == null)
                                showError.setValue(new Event<>(R.string.server_error));
                            break;
                        case NETWORK_ERROR:
                            showError.setValue(new Event<>(R.string.network_error));
                            break;
                        case SERVER_ERROR:
                            showError.setValue(new Event<>(R.string.server_error));
                            break;
                    }
                    isLoading.setValue(false);
                }
            });
        }
    }

    private @StringRes Integer validateRegistration(@NotNull String username, @NotNull String password, @NotNull String repeatPassword, @NotNull String fname, @NotNull String lname){
        if(fname.isEmpty())
            return R.string.first_name_empty_error;
        if(lname.isEmpty())
            return R.string.last_name_empty_error;
        if(username.length() < 4)
            return R.string.username_illegal_error;
        if(password.length() < 4)
            return R.string.password_illegal_error;
        if(!password.equals(repeatPassword))
            return R.string.passwords_dont_match_error;
        return null;
    }

    private @StringRes Integer validateLogin(@NotNull String username, @NotNull String password){
        if(username.length() < 4)
            return R.string.username_illegal_error;
        if(password.length() < 4)
            return R.string.password_illegal_error;
        return null;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
}