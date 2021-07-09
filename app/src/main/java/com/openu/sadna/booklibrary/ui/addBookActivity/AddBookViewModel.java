package com.openu.sadna.booklibrary.ui.addBookActivity;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.User;

import org.jetbrains.annotations.NotNull;


public class AddBookViewModel extends ViewModel {

    protected enum Events{
        ADD_BOOK_SUCCESS
    }

    private final LiveData<User> currentUser;
    private final MutableLiveData<Event<Integer>> showError;
    private final MutableLiveData<Event<Events>> handleEvent;
    private final MutableLiveData<Boolean> isLoading;
    private final Repository repository;
    private final MutableLiveData<Categories> categories;

    public AddBookViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MutableLiveData<>();
        handleEvent = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        categories = new MutableLiveData<>();
        loadCategories();
    }

    private void loadCategories(){
        isLoading.setValue(true);
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
                isLoading.setValue(false);
            }
        });
    }

    public void addBook(String bookName, String authorName, String authorFamily, String description, String category){
        Integer bookDataError = validateAddBook(bookName, authorName, authorFamily, description, category);
        if(bookDataError != null)
            showError.setValue(new Event<>(bookDataError));
        else {
            isLoading.setValue(true);
            repository.addBook(bookName, authorName, authorFamily, description, category, new RequestCallback<Void>() {
                @Override
                public void onNetworkResponse(@NotNull NetworkRequestEvent event, Void data) {
                    switch (event) {
                        case SUCCESS:
                            handleEvent.setValue(new Event<>(Events.ADD_BOOK_SUCCESS));
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
    }

    private @StringRes Integer validateAddBook(String bookName, String authorName, String authorFamily, String description, String category){
        if(bookName == null || bookName.isEmpty())
            return R.string.book_name_empty_error;
        if(authorName == null || authorName.isEmpty())
            return R.string.book_author_first_name_empty_error;
        if(authorFamily == null || authorFamily.isEmpty())
            return R.string.book_author_last_name_empty_error;
        if(category == null || category.isEmpty())
            return R.string.book_category_empty_error;
        return null;
    }

    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Event<Events>> getEventsToHandle() {
        return handleEvent;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<Categories> getCategories() {
        return categories;
    }
}