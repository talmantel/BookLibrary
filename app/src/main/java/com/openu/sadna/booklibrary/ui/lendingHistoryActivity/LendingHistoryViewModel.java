package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;


public class LendingHistoryViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private MutableLiveData<Event<Integer>> showError = new MutableLiveData<>();
    private Repository repository;

    public LendingHistoryViewModel(Repository repository) {
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