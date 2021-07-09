package com.openu.sadna.booklibrary.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.common.RequestCallback;
import com.openu.sadna.booklibrary.network.APIInterface;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepositoryImpl implements Repository {

    private static Repository instance = null;

    private final MutableLiveData<User> currentUser;
    private final APIInterface apiService;
    private final SharedPrefs sharedPrefs;

    private RepositoryImpl(APIInterface apiService, SharedPrefs sharedPrefs){
        this.apiService = apiService;
        this.sharedPrefs = sharedPrefs;
        currentUser = new MutableLiveData<>(this.sharedPrefs.getUser());
    }

    public synchronized static Repository getInstance(APIInterface apiService, SharedPrefs sharedPrefs){
        if(instance == null)
            instance = new RepositoryImpl(apiService, sharedPrefs);
        return instance;
    }

    @Override
    public void login(String username, String password, final RequestCallback<Void> callback) {
        Call<User> loginCall = apiService.login(username, password);
        loginCall.enqueue(new Callback<User>() {
              @Override
              public void onResponse(Call<User> call, Response<User> response) {
                  if(response.code() == 200 || response.code() == 403) {
                      User user = response.body();
                      if(user.getToken() != null) {
                          currentUser.setValue(user);
                          sharedPrefs.setUser(user);
                      }
                      else
                          currentUser.setValue(null);

                      if(callback != null)
                          callback.onNetworkResponse(NetworkRequestEvent.SUCCESS, null);
                  }
                  else if(callback != null)
                      callback.onNetworkResponse(NetworkRequestEvent.SERVER_ERROR, null);
              }

              @Override
              public void onFailure(Call<User> call, Throwable t) {
                  if(callback != null)
                      callback.onNetworkResponse(NetworkRequestEvent.NETWORK_ERROR, null);
              }
          });
    }

    @Override
    public void register(String username, String password, String fname, String lname, RequestCallback<Void> callback) {

    }

    @Override
    public LiveData<User> getCurrentUser(){
        return currentUser;
    }

    @Override
    public void logout(){
        sharedPrefs.clearUser();
        currentUser.setValue(null);
    }


    @Override
    public void addBook(String bookName, String authorName, String authorFamily, String description, String category, final RequestCallback<Void> callback){
        //TODO
    }

    @Override
    public void getBooks(String textQuery, String category, RequestCallback<List<Book>> callback) {

    }

    @Override
    public void getBook(int bookID, RequestCallback<Book> callback) {

    }

    @Override
    public void orderBook(int bookID, RequestCallback<Void> callback) {

    }

    @Override
    public void returnBook(int bookID, RequestCallback<Void> callback) {

    }

    @Override
    public void getBookReviews(int bookID, RequestCallback<List<Review>> callback) {

    }

    @Override
    public void addBookReview(int bookID, String review, RequestCallback<Void> callback) {

    }

    @Override
    public void getUserOrderHistory(RequestCallback<List<Book>> callback) {

    }

    @Override
    public void getLentBooks(RequestCallback<List<Book>> callback) {

    }

    @Override
    public void getCategories(RequestCallback<Categories> callback) {

    }


}
