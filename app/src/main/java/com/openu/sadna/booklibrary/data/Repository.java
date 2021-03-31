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

    public LiveData<NetworkRequestEvent> login(String username, String password) {
        final MutableLiveData<NetworkRequestEvent> result = new MutableLiveData<>();
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

                      result.setValue(NetworkRequestEvent.success());
                  }
                  else
                      result.setValue(NetworkRequestEvent.serverError());
              }

              @Override
              public void onFailure(Call<User> call, Throwable t) {
                  result.setValue(NetworkRequestEvent.networkError());
              }
          });

        return result;
    }

    public LiveData<User> getCurrentUser(){
        return currentUser;
    }

    public void logout(){
        sharedPrefs.clearUser();
        currentUser.setValue(null);
    }
}
