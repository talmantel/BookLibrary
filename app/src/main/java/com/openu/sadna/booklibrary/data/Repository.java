package com.openu.sadna.booklibrary.data;

import androidx.lifecycle.LiveData;

import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;

public interface Repository {
    void login(String username, String password, final RequestCallback<Void> callback);
    void register(String username, String password, String fname, String lname, final RequestCallback<Void> callback);
    LiveData<User> getCurrentUser();
    void logout();
    void addBook(String bookName, String authorName, String authorFamily, String description, String category, final RequestCallback<Void> callback);
    void getBooks(String textQuery, String category, RequestCallback<List<Book>> callback);
    void orderBook(int bookID, RequestCallback<Void> callback);
    void returnBook(int bookID, RequestCallback<Void> callback);
    void getBookReviews(int bookID, RequestCallback<List<Review>> callback);
    void addBookReview(int bookID, String review, RequestCallback<Void> callback);
    void getUserOrderHistory(RequestCallback<Void> callback);
    void getLentBooks(RequestCallback<Void> callback);
    void getCategories(RequestCallback<Categories> callback);
}
