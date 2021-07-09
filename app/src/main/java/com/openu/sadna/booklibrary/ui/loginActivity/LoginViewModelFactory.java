package com.openu.sadna.booklibrary.ui.loginActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class LoginViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repo;

    public LoginViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LoginViewModel.class)) {
            return (T) new LoginViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}