package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class BooksCatalogViewModelFactory implements ViewModelProvider.Factory {

    private Repository repo;

    public BooksCatalogViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(BooksCatalogViewModel.class)) {
            return (T) new BooksCatalogViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}