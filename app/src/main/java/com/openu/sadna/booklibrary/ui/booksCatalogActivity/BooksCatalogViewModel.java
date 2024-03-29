package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Categories;

import java.util.List;


public class BooksCatalogViewModel extends ViewModel {

    private final MutableLiveData<Event<Integer>> showError;
    private final MutableLiveData<Boolean> isLoading;
    private final Repository repository;
    private final MutableLiveData<List<Book>> books;
    private final MutableLiveData<Categories> categories;
    private int loadingCount;

    public BooksCatalogViewModel(Repository repository) {
        this.repository = repository;
        showError = new MutableLiveData<>();
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
        repository.getBooks(textQuery, category, new RequestCallback<List<Book>>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, List<Book> data) {
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

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Book>> getBooks() {
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