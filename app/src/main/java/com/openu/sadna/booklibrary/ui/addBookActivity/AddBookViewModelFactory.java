package com.openu.sadna.booklibrary.ui.addBookActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class AddBookViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repo;

    public AddBookViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AddBookViewModel.class)) {
            return (T) new AddBookViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}