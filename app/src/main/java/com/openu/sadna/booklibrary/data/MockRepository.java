package com.openu.sadna.booklibrary.data;

import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.BookLendDetails;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Objects;

public class MockRepository implements Repository{


    private static final long LOADING_DELAY = 1000;
    private static MockRepository instance = null;

    private final MutableLiveData<User> currentUser;
    private final SharedPrefs sharedPrefs;

    private final List<Book> books;
    private final List<Book> booksLendHistory;
    private final Hashtable<Integer, List<Review>> reviews;

    private MockRepository(SharedPrefs sharedPrefs){
        this.sharedPrefs = sharedPrefs;
        currentUser = new MutableLiveData<>(this.sharedPrefs.getUser());

        reviews = new Hashtable<>();
        ArrayList<Review> bookReviews = new ArrayList<>();
        bookReviews.add(new Review("Review 1 short", System.currentTimeMillis()));
        bookReviews.add(new Review("Review 2 A little longer", System.currentTimeMillis()));
        bookReviews.add(new Review("Review 3 VERY VERY VERY VERY VERY  VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY LONG LING", System.currentTimeMillis()));
        bookReviews.add(new Review("Review 4 Multi line: \n MULTI LINE: \n ANOTHER LINE \n ANOTHER LINE", System.currentTimeMillis()));
        bookReviews.add(new Review("2 Review 1 short", System.currentTimeMillis() - 1000*60*60));
        bookReviews.add(new Review("2 Review 2 A little longer", System.currentTimeMillis() - 1000*60*60));
        bookReviews.add(new Review("2 Review 3 VERY VERY VERY VERY VERY  VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY LONG LING", System.currentTimeMillis() - 1000*60*60));
        bookReviews.add(new Review("2 Review 4 Multi line: \n MULTI LINE: \n ANOTHER LINE \n ANOTHER LINE", System.currentTimeMillis() - 1000*60*60));
        bookReviews.add(new Review("3 Review 1 short", System.currentTimeMillis() - 1000*60*60*10));
        bookReviews.add(new Review("3 Review 2 A little longer", System.currentTimeMillis() - 1000*60*60*10));
        bookReviews.add(new Review("3 Review 3 VERY VERY VERY VERY VERY  VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY VE VERY VERY VERY LONG LING", System.currentTimeMillis() - 1000*60*60*10));
        bookReviews.add(new Review("3 Review 4 Multi line: \n MULTI LINE: \n ANOTHER LINE \n ANOTHER LINE", System.currentTimeMillis() - 1000*60*60*10));

        reviews.put(1, bookReviews);
        reviews.put(2, bookReviews);
        reviews.put(3, bookReviews);
        reviews.put(4, bookReviews);
        reviews.put(5, bookReviews);
        reviews.put(6, bookReviews);
        reviews.put(11, new ArrayList<Review>());
        reviews.put(12, new ArrayList<Review>());
        reviews.put(13, new ArrayList<Review>());
        reviews.put(14, new ArrayList<Review>());
        reviews.put(15, new ArrayList<Review>());
        reviews.put(16, new ArrayList<Review>());
        reviews.put(21, new ArrayList<Review>());
        reviews.put(22, new ArrayList<Review>());
        reviews.put(23, new ArrayList<Review>());
        reviews.put(24, new ArrayList<Review>());
        reviews.put(25, new ArrayList<Review>());
        reviews.put(26, new ArrayList<Review>());
        reviews.put(31, new ArrayList<Review>());
        reviews.put(32, new ArrayList<Review>());
        reviews.put(33, new ArrayList<Review>());
        reviews.put(34, new ArrayList<Review>());
        reviews.put(35, new ArrayList<Review>());
        reviews.put(36, new ArrayList<Review>());
        reviews.put(41, new ArrayList<Review>());
        reviews.put(42, new ArrayList<Review>());
        reviews.put(43, new ArrayList<Review>());
        reviews.put(44, new ArrayList<Review>());
        reviews.put(45, new ArrayList<Review>());
        reviews.put(46, new ArrayList<Review>());

        books = new ArrayList<>();
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1", 1, true, null));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2", 2, true, null));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3", 3, true, null));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4", 4, true, null));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5", 5, true, null));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6", 6, true, null));
        books.add(new Book("Author", "1", "Book name 1 Very Very Very Long ", "Category 1", "Description 1", 11, true, null));
        books.add(new Book("Author", "2", "Book name 2 Very Very Very Long ", "Category 2", "Description 2", 12, true, null));
        books.add(new Book("Author", "3", "Book name 3 Very Very Very Long ", "Category 3", "Description 3", 13, true, null));
        books.add(new Book("Author", "4", "Book name 4 Very Very Very Long ", "Category 4", "Description 4", 14, true, null));
        books.add(new Book("Author", "5", "Book name 5 Very Very Very Long ", "Category 5", "Description 5", 15, true, null));
        books.add(new Book("Author", "6", "Book name 6 Very Very Very Long ", "Category 6", "Description 6", 16, true, null));
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 21, true, null));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 22, true, null));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 23, true, null));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 24, true, null));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 25, true, null));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6 Very Very Very Very Long Very Very Long Very Very Long Very Very Long", 26, true, null));
        books.add(new Book("Author", "1", "Book name 1", "Category 1", "Description 1 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 21, true, null));
        books.add(new Book("Author", "2", "Book name 2", "Category 2", "Description 2 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 22, true, null));
        books.add(new Book("Author", "3", "Book name 3", "Category 3", "Description 3 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 23, true, null));
        books.add(new Book("Author", "4", "Book name 4", "Category 4", "Description 4 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 24, true, null));
        books.add(new Book("Author", "5", "Book name 5", "Category 5", "Description 5 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 25, true, null));
        books.add(new Book("Author", "6", "Book name 6", "Category 6", "Description 6 Mega Very Very Very Long Very Very Long Very Very Long Very Very Long Mega Very Mega Very Mega Very Mega Very Mega Very Mega Very", 26, true, null));
        books.add(new Book("Author", "1 Very Long Name", "Book name 1", "Category 1", "Description 1", 31, true, null));
        books.add(new Book("Author", "2 Very Long Name", "Book name 2", "Category 2", "Description 2", 32, true, null));
        books.add(new Book("Author", "3 Very Long Name", "Book name 3", "Category 3", "Description 3", 33, true, null));
        books.add(new Book("Author", "4 Very Long Name", "Book name 4", "Category 4", "Description 4", 34, true, null));
        books.add(new Book("Author", "5 Very Long Name", "Book name 5", "Category 5", "Description 5", 35, true, null));
        books.add(new Book("Author", "6 Very Long Name", "Book name 6", "Category 6", "Description 6", 36, true, null));
        books.add(new Book("Author", "1 Very Long Name", "Book name 1 Very Very Very Long", "Category 1", "Description 1", 41, true, null));
        books.add(new Book("Author", "2 Very Long Name", "Book name 2 Very Very Very Long", "Category 2", "Description 2", 42, true, null));
        books.add(new Book("Author", "3 Very Long Name", "Book name 3 Very Very Very Long", "Category 3", "Description 3", 43, true, null));
        books.add(new Book("Author", "4 Very Long Name", "Book name 4 Very Very Very Long", "Category 4", "Description 4", 44, true, null));
        books.add(new Book("Author", "5 Very Long Name", "Book name 5 Very Very Very Long", "Category 5", "Description 5", 45, true, null));
        books.add(new Book("Author", "6 Very Long Name", "Book name 6 Very Very Very Long", "Category 6", "Description 6", 46, true, null));

        booksLendHistory = new ArrayList<>();


        orderBook(1, null);
        orderBook(11, null);
        orderBook(21, null);
        orderBook(31, null);
        orderBook(41, null);

        returnBook(1, null);
        returnBook(21, null);
        returnBook(41, null);
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
        books.add(new Book(authorName, authorFamily, bookName, category, description, books.size() + 1, true, null));
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
    public void getBooks(String textQuery, String category, final RequestCallback<List<Book>> callback) {
        final ArrayList<Book> list = new ArrayList<>();
        for(Book book : books){
            if((category == null || book.getCategory().equals(category))
                && ( textQuery == null || book.getAuthorFName().contains(textQuery)
                || book.getAuthorLName().contains(textQuery)
                || book.getName().contains(textQuery)
                || book.getDescription().contains(textQuery))
            ){
                list.add(new Book(book.getAuthorFName(), book.getAuthorLName(), book.getName(), book.getCategory(), book.getDescription(), book.getId(), true, null));
            }
        }
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, list);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getBook(int bookID, final RequestCallback<Book> callback) {
        for(final Book book : books){
            if(book.getId() == bookID) {
                if (callback != null) {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, book);
                        }
                    }, LOADING_DELAY);
                }
                break;
            }
        }
    }

    @Override
    public void orderBook(int bookID, final RequestCallback<Void> callback) {
        for(Book book : books){
            if(book.getId() == bookID){
                if(book.isAvailable()){
                    BookLendDetails lendDetails = new BookLendDetails(System.currentTimeMillis(), null, currentUser.getValue());
                    Book newBook = new Book(book.getAuthorFName(), book.getAuthorLName(), book.getName(), book.getCategory(), book.getDescription(), book.getId(), false, lendDetails);
                    books.remove(book);
                    books.add(newBook);
                    booksLendHistory.add(newBook);

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
                break;
            }
        }
    }

    @Override
    public void returnBook(int bookID, final RequestCallback<Void> callback) {
        for(Book book : books){
            if(book.getId() == bookID && !book.isAvailable()){
                BookLendDetails lendDetails = new BookLendDetails(book.getLendDetails().getLentTime(), System.currentTimeMillis(), book.getLendDetails().getLentTo());
                Book newBook = new Book(book.getAuthorFName(), book.getAuthorLName(), book.getName(), book.getCategory(), book.getDescription(), book.getId(), true, lendDetails);
                books.remove(book);
                books.add(newBook);
                booksLendHistory.remove(book);
                booksLendHistory.add(newBook);

                if(callback != null) {
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                        }
                    }, LOADING_DELAY);
                }
                break;
            }
        }
    }

    @Override
    public void getBookReviews(final int bookID, final RequestCallback<List<Review>> callback) {
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, new ArrayList<>(Objects.requireNonNull(reviews.get(bookID))));
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void addBookReview(int bookID, String review, final RequestCallback<Void> callback) {
        Objects.requireNonNull(reviews.get(bookID)).add(new Review(review, System.currentTimeMillis()));
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
    public void getUserOrderHistory(final RequestCallback<List<Book>> callback) {
        final ArrayList<Book> list = new ArrayList<>();
        for(Book book : booksLendHistory){
            if(book.getLendDetails() != null
                    && book.getLendDetails().getLentTo().getFirstName().equals(Objects.requireNonNull(currentUser.getValue()).getFirstName())
                    && book.getLendDetails().getLentTo().getLastName().equals(currentUser.getValue().getLastName())
            ){
                BookLendDetails lendDetails = new BookLendDetails(book.getLendDetails().getLentTime(), book.getLendDetails().getReturnTime(), null);
                list.add(new Book(book.getAuthorFName(), book.getAuthorLName(), book.getName(), book.getCategory(), book.getDescription(), book.getId(), true, lendDetails));
            }
        }
        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, list);
                }
            }, LOADING_DELAY);
        }
    }

    @Override
    public void getLentBooks(final RequestCallback<List<Book>> callback) {
        final ArrayList<Book> list = new ArrayList<>();
        for(Book book : books){
            if(!book.isAvailable())
                list.add(book);
        }

        if(callback != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, list);
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
