package com.openu.sadna.booklibrary.ui.addBookActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;


public class AddBookViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private Repository repository;

    public AddBookViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
    }


    public void addBook(String bookName, String authorName, String authorFamily, String description, String category){
        repository.addBook(bookName, authorName, authorFamily, description, category, new RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Void data) {
                //TODO
            }
        });
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }
}