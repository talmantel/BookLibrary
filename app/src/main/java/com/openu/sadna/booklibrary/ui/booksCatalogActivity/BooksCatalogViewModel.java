package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Books;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.User;


public class BooksCatalogViewModel extends ViewModel {

    private LiveData<User> currentUser;
    private MediatorLiveData<Event<Integer>> showError;
    private MutableLiveData<Boolean> isLoading;
    private Repository repository;
    private MutableLiveData<Books> books;
    private MutableLiveData<Categories> categories;
    private int loadingCount;

    public BooksCatalogViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MediatorLiveData<>();
        isLoading = new MutableLiveData<>();
        books = new MutableLiveData<>();
        categories = new MutableLiveData<>();
        loadBooks(null, null);
        loadCategories();
    }

    private void loadCategories(){
        addToLoad();
        repository.getCategories(new RequestCallback<Categories>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Categories data) {
                switch (event) {
                    case SUCCESS:
                        categories.setValue(data);
                        break;
                    case NETWORK_ERROR:
                        showError.setValue(new Event<>(R.string.network_error));
                        break;
                    case SERVER_ERROR:
                        showError.setValue(new Event<>(R.string.server_error));
                        break;
                }
                itemLoaded();
            }
        });
    }

    public void loadBooks(String textQuery, String category){
        addToLoad();
        repository.getBooks(textQuery, category, new RequestCallback<Books>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Books data) {
                switch (event) {
                    case SUCCESS:
                        books.setValue(data);
                        break;
                    case NETWORK_ERROR:
                        showError.setValue(new Event<>(R.string.network_error));
                        break;
                    case SERVER_ERROR:
                        showError.setValue(new Event<>(R.string.server_error));
                        break;
                }
                itemLoaded();
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

    public LiveData<Books> getBooks() {
        return books;
    }

    public MutableLiveData<Categories> getCategories() {
        return categories;
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