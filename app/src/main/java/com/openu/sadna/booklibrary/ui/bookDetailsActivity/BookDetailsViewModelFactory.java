package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class BookDetailsViewModelFactory implements ViewModelProvider.Factory {

    private final Repository repo;

    public BookDetailsViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BookDetailsViewModel.class)) {
            return (T) new BookDetailsViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}