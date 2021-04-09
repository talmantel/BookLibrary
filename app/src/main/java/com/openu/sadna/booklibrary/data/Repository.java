package com.openu.sadna.booklibrary.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openu.sadna.booklibrary.common.NetworkRequestEvent;
import com.openu.sadna.booklibrary.network.APIInterface;
import com.openu.sadna.booklibrary.network.pojo.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static Repository instance = null;

    private MutableLiveData<User> currentUser;
    private APIInterface apiService;
    private SharedPrefs sharedPrefs;


    public interface RequestCallback<T>{
        void onNetworkResponse(NetworkRequestEvent event, T data);
    }


    private Repository(APIInterface apiService, SharedPrefs sharedPrefs){
        this.apiService = apiService;
        this.sharedPrefs = sharedPrefs;
        currentUser = new MutableLiveData<>();
        currentUser.setValue(this.sharedPrefs.getUser());
    }

    public synchronized static Repository getInstance(APIInterface apiService, SharedPrefs sharedPrefs){
        if(instance == null)
            instance = new Repository(apiService, sharedPrefs);
        return instance;
    }

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

    public LiveData<User> getCurrentUser(){
        return currentUser;
    }

    public void logout(){
        sharedPrefs.clearUser();
        currentUser.setValue(null);
    }


    public void addBook(String bookName, String authorName, String authorFamily, String description, String category, final RequestCallback<Void> callback){
        //TODO
    }


}
