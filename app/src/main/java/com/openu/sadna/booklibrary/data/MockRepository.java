package com.openu.sadna.booklibrary.data;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Books;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.ArrayList;
import java.util.List;

public class MockRepository implements Repository{


    private static final long LOADING_DELAY = 1000;
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
        books.add(new Book("Author", "1", "Book name 1 Very Very Very Long ", "Category 1", "Description 1", 1, true));
        books.add(new Book("Author", "2", "Book name 2 Very Very Very Long ", "Category 2", "Description 2", 2, true));
        books.add(new Book("Author", "3", "Book name 3 Very Very Very Long ", "Category 3", "Description 3", 3, true));
        books.add(new Book("Author", "4", "Book name 4 Very Very Very Long ", "Category 4", "Description 4", 4, true));
        books.add(new Book("Author", "5", "Book name 5 Very Very Very Long ", "Category 5", "Description 5", 5, true));
        books.add(new Book("Author", "6", "Book name 6 Very Very Very Long ", "Category 6", "Description 6", 6, false));
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 1, true));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 2, true));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 3, true));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 4, true));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 5, true));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 6, false));
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 1, true));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 2, true));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 3, true));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 4, true));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 5, true));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 6, false));
        books.add(new Book("Author", "1 Very Long Name", "Book name 1", "Category 1", "Description 1", 1, true));
        books.add(new Book("Author", "2 Very Long Name", "Book name 2", "Category 2", "Description 2", 2, true));
        books.add(new Book("Author", "3 Very Long Name", "Book name 3", "Category 3", "Description 3", 3, true));
        books.add(new Book("Author", "4 Very Long Name", "Book name 4", "Category 4", "Description 4", 4, true));
        books.add(new Book("Author", "5 Very Long Name", "Book name 5", "Category 5", "Description 5", 5, true));
        books.add(new Book("Author", "6 Very Long Name", "Book name 6", "Category 6", "Description 6", 6, false));
        books.add(new Book("Author", "1 Very Long Name", "Book name 1 Very Very Very Long", "Category 1", "Description 1", 1, true));
        books.add(new Book("Author", "2 Very Long Name", "Book name 2 Very Very Very Long", "Category 2", "Description 2", 2, true));
        books.add(new Book("Author", "3 Very Long Name", "Book name 3 Very Very Very Long", "Category 3", "Description 3", 3, true));
        books.add(new Book("Author", "4 Very Long Name", "Book name 4 Very Very Very Long", "Category 4", "Description 4", 4, true));
        books.add(new Book("Author", "5 Very Long Name", "Book name 5 Very Very Very Long", "Category 5", "Description 5", 5, true));
        books.add(new Book("Author", "6 Very Long Name", "Book name 6 Very Very Very Long", "Category 6", "Description 6", 6, false));
    }


    public synchronized static MockRepository getInstance(SharedPrefs sharedPrefs){
        if(instance == null)
            instance = new MockRepository(sharedPrefs);
        return instance;
    }

    @Override
    public void login(String username, String password, final RequestCallback<Void> callback) {
        User user = new User("Tal", "Mantelmakher", "token", true);
        currentUser.setValue(user);
        sharedPrefs.setUser(user);
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void register(String username, String password, String fname, String lname, final RequestCallback<Void> callback) {
        User user = new User(fname, lname, "token", false);
        currentUser.setValue(user);
        sharedPrefs.setUser(user);
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
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
    public void addBook(String bookName, String authorName, String authorFamily, String description, String category, final RequestCallback<Void> callback) {
        books.add(new Book(authorName, authorFamily, bookName, category, description, books.size() + 1, true));
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getBooks(String textQuery, String category, final RequestCallback<Books> callback) {
        final ArrayList<Book> list = new ArrayList<>();
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
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, new Books(list));
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void orderBook(int bookID, final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void returnBook(int bookID, final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getBookReviews(int bookID, final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void addBookReview(int bookID, String review, final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getUserOrderHistory(final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getLentBooks(final RequestCallback<Void> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getCategories(final RequestCallback<Categories> callback) {
        final ArrayList<String> categories = new ArrayList<>();
        for(Book book : books){
            if(book.getCategory() != null && !categories.contains(book.getCategory()))
                categories.add(book.getCategory());
        }
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, new Categories(categories));
                }
            }, LOADING_DELAY);
        }
    }
}
