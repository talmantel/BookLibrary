package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.data.Repository;

public class LendingHistoryViewModelFactory implements ViewModelProvider.Factory {

    private Repository repo;

    public LendingHistoryViewModelFactory(Repository repo){
        this.repo = repo;
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(LendingHistoryViewModel.class)) {
            return (T) new LendingHistoryViewModel(repo);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}