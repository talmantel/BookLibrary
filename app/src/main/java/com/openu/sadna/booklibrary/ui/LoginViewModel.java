package com.openu.sadna.booklibrary.ui;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.common.Resource;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.data.model.User;


public class LoginViewModel extends ViewModel {

    private MutableLiveData<Resource<User>> loginResult = new MutableLiveData<>();
    private Repository repository;

    LoginViewModel(Repository repository) {
        this.repository = repository;
    }

    LiveData<Resource<User>> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        //TODO can be launched in a separate asynchronous job
        Resource<User> result = repository.login(username, password);
        loginResult.setValue(result);
    }

    //TODO A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    //TODO A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}