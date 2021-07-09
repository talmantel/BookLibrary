package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Book;

import java.util.ArrayList;
import java.util.List;


public class AdminLentBooksTrackingViewModel extends ViewModel {

    protected enum Events{
        RETURN_BOOK_SUCCESS
    }

    private final MutableLiveData<Event<Integer>> showError;
    private final MutableLiveData<Event<AdminLentBooksTrackingViewModel.Events>> handleEvent;
    private final MutableLiveData<Boolean> isLoading;
    private final Repository repository;
    private final MutableLiveData<List<Book>> lentBooks;

    public AdminLentBooksTrackingViewModel(Repository repository) {
        this.repository = repository;
        showError = new MutableLiveData<>();
        handleEvent = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        lentBooks = new MutableLiveData<>();
        loadLentBooks();
    }

    public void loadLentBooks(){
        isLoading.setValue(true);
        repository.getLentBooks(new RequestCallback<List<Book>>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, List<Book> data) {
                switch (event) {
                    case SUCCESS:
                        lentBooks.setValue(data);
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

    public void returnBook(final int bookID){
        isLoading.setValue(true);
        repository.returnBook(bookID, new RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Void data) {
                switch (event) {
                    case SUCCESS:
                        removeBookFromLentBooks(bookID);
                        handleEvent.setValue(new Event<>(Events.RETURN_BOOK_SUCCESS));
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

    private void removeBookFromLentBooks(int bookID){
        List<Book> oldLentBooks = lentBooks.getValue();
        if(oldLentBooks != null) {
            ArrayList<Book> newList = new ArrayList<>();
            for (Book book : oldLentBooks) {
                if (book.getId() != bookID)
                    newList.add(book);
            }
            lentBooks.setValue(newList);
        }
    }

    public LiveData<Event<Integer>> getShowError() {
        return showError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<List<Book>> getLentBooks() {
        return lentBooks;
    }
}