package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;


public class AdminLentBooksTrackingViewModel extends ViewModel {

    private final LiveData<User> currentUser;
    private final MutableLiveData<Event<Integer>> showError = new MutableLiveData<>();
    private final Repository repository;

    public AdminLentBooksTrackingViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }
}