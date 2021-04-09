package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;


public class AdminLentBooksTrackingViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private MediatorLiveData<Event<Integer>> showError = new MediatorLiveData<>();
    private Repository repository;

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