package com.openu.sadna.booklibrary.util;

import android.app.Application;

import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.data.SharedPrefs;
import com.openu.sadna.booklibrary.network.RestClient;
import com.openu.sadna.booklibrary.ui.LoginViewModelFactory;

public class InjectorUtils {

    private static Repository getRepository(Application application)  {
        return Repository.getInstance(RestClient.getInstance().getApiService(), SharedPrefs.getInstance(application));
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new LoginViewModelFactory(repo);
    }

}