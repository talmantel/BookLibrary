package com.openu.sadna.booklibrary.ui;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class AdminLentBooksTrackingViewModelFactory implements ViewModelProvider.Factory {

    private Repository repo;

    public AdminLentBooksTrackingViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminLentBooksTrackingViewModel.class)) {
            return (T) new AdminLentBooksTrackingViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}