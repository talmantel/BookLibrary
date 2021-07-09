package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;


public class BookDetailsViewModel extends ViewModel {

    private final LiveData<User> currentUser;
    private final MutableLiveData<Event<Integer>> showError;
    private final Repository repository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<Book> book;
    private MutableLiveData<List<Review>> reviews;
    private int loadingCount;

    public BookDetailsViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MutableLiveData<>();
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

    public LiveData<Book> getBook() {
        return book;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    private void addToLoad(){
        loadingCount++;
        updateLoading();
    }

    private void itemLoaded(){
        loadingCount--;
        updateLoading();
    }

    private void updateLoading(){
        isLoading.setValue(loadingCount > 0);
    }
}