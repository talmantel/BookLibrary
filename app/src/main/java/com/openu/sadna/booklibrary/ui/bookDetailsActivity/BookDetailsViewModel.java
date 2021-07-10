package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;


public class BookDetailsViewModel extends ViewModel {

    protected enum Events{
        RETURN_BOOK_SUCCESS, ORDER_BOOK_SUCCESS, ADD_REVIEW_SUCCESS
    }

    private final LiveData<User> currentUser;
    private final MutableLiveData<Event<Integer>> showError;
    private final Repository repository;
    private final MutableLiveData<Event<Events>> handleEvent;
    private final MutableLiveData<Boolean> isLoading;
    private final MutableLiveData<Book> book;
    private final MutableLiveData<List<Review>> reviews;
    private final MediatorLiveData<Boolean> showReturnBookOption;
    private int loadingCount;
    private int bookID;

    public BookDetailsViewModel(Repository repository) {
        this.repository = repository;
        currentUser = repository.getCurrentUser();
        showError = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        handleEvent = new MutableLiveData<>();
        book = new MutableLiveData<>();
        reviews = new MutableLiveData<>();
        showReturnBookOption = new MediatorLiveData<>();

        showReturnBookOption.addSource(currentUser, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                updateShouldReturnBookOption();
            }
        });

        showReturnBookOption.addSource(book, new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                updateShouldReturnBookOption();
            }
        });
    }

    private void updateShouldReturnBookOption(){
        if(currentUser.getValue() == null || !currentUser.getValue().isAdmin() || book.getValue() == null || book.getValue().isAvailable())
            showReturnBookOption.setValue(false);
        else
            showReturnBookOption.setValue(true);
    }

    public void loadBook(int bookID){
        this.bookID = bookID;
        loadBookDetails();
        loadBookReviews();
    }

    private void loadBookDetails(){
        addToLoad();
        repository.getBook(bookID, new RequestCallback<Book>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Book data) {
                switch (event) {
                    case SUCCESS:
                        book.setValue(data);
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

    private void loadBookReviews(){
        addToLoad();
        repository.getBookReviews(bookID, new RequestCallback<List<Review>>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, List<Review> data) {
                switch (event) {
                    case SUCCESS:
                        reviews.setValue(data);
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

    public void returnBook(){
        addToLoad();
        repository.returnBook(bookID, new RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Void data) {
                switch (event) {
                    case SUCCESS:
                        loadBookDetails();
                        handleEvent.setValue(new Event<>(Events.RETURN_BOOK_SUCCESS));
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

    public void orderBook(){
        addToLoad();
        repository.orderBook(bookID, new RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Void data) {
                switch (event) {
                    case SUCCESS:
                        loadBookDetails();
                        handleEvent.setValue(new Event<>(Events.ORDER_BOOK_SUCCESS));
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

    public void submitReview(String review){
        addToLoad();
        repository.addBookReview(bookID, review, new RequestCallback<Void>() {
            @Override
            public void onNetworkResponse(NetworkRequestEvent event, Void data) {
                switch (event) {
                    case SUCCESS:
                        loadBookReviews();
                        handleEvent.setValue(new Event<>(Events.ADD_REVIEW_SUCCESS));
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

    public LiveData<Event<Events>> getEventsToHandle() {
        return handleEvent;
    }

    public LiveData<Boolean> getShowReturnBookOption() {
        return showReturnBookOption;
    }
}