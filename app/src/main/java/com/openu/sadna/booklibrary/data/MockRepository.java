package com.openu.sadna.booklibrary.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class MockRepository implements Repository{

    private static MockRepository instance = null;

    private MutableLiveData<User> currentUser;
    private SharedPrefs sharedPrefs;

    private List<Book> books;

    private MockRepository(SharedPrefs sharedPrefs){
        this.sharedPrefs = sharedPrefs;
        currentUser = new MutableLiveData<>(this.sharedPrefs.getUser());
        books = new ArrayList<>();
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1", 1, true));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2", 2, true));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3", 3, true));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4", 4, true));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5", 5, true));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6", 6, false));
    }


    public synchronized static MockRepository getInstance(SharedPrefs sharedPrefs){
        if(instance == null)
            instance = new MockRepository(sharedPrefs);
        return instance;
    }

    @Override
    public void login(String username, String password, RequestCallback<Void> callback) {
        User user = new User("Tal", "Mantelmakher", "token", true);
        currentUser.setValue(user);
        sharedPrefs.setUser(user);
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void register(String username, String password, String fname, String lname, RequestCallback<Void> callback) {
        User user = new User(fname, lname, "token", false);
        currentUser.setValue(user);
        sharedPrefs.setUser(user);
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public LiveData<User> getCurrentUser() {
        return currentUser;
    }

    @Override
    public void logout() {
        sharedPrefs.clearUser();
        currentUser.setValue(null);
    }

    @Override
    public void addBook(String bookName, String authorName, String authorFamily, String description, String category, RequestCallback<Void> callback) {
        books.add(new Book(authorName, authorFamily, bookName, category, description, books.size() + 1, true));
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void getBooks(String textQuery, String category, RequestCallback<List<Book>> callback) {
        ArrayList<Book> list = new ArrayList<>();
        for(Book book : books){
            if((category == null || book.getCategory().equals(category))
                && ( textQuery == null || book.getAuthorFName().contains(textQuery)
                || book.getAuthorLName().contains(textQuery)
                || book.getName().contains(textQuery)
                || book.getDescription().contains(textQuery))
            ){
                list.add(book);
            }
        }
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, list);
    }

    @Override
    public void orderBook(int bookID, RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void returnBook(int bookID, RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void getBookReviews(int bookID, RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void addBookReview(int bookID, String review, RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void getUserOrderHistory(RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void getLentBooks(RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }

    @Override
    public void getCategories(RequestCallback<Void> callback) {
        if(callback != null)
            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
    }
}
