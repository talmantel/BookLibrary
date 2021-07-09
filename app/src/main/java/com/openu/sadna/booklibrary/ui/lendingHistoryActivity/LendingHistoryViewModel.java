package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;


public class LendingHistoryViewModel extends ViewModel {

    private final LiveData<User> currentUser;
    private final MutableLiveData<Event<Integer>> showError;
    private final MutableLiveData<Boolean> isLoading;
    private final Repository repository;
    private final MutableLiveData<List<Book>> lendingHistory;

    public LendingHistoryViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        lendingHistory = new MutableLiveData<>();
        loadLendingHistory();
    }

    public void loadLendingHistory(){
        isLoading.setValue(true);
        repository.getUserOrderHistory(new RequestCallback<List<Book>>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, List<Book> data) {
                switch (event) {
                    case SUCCESS:
                        lendingHistory.setValue(data);
                        break;
                    case NETWORK_ERROR:
                        showError.setValue(new Event<>(R.string.network_error));
                        break;
                    case SERVER_ERROR:
                        showError.setValue(new Event<>(R.string.server_error));
                        break;
                }
                isLoading.setValue(false);
            }
        });
    }



    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Book>> getLendingHistory() {
        return lendingHistory;
    }
}