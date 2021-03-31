package com.openu.sadna.booklibrary.util;

import android.app.Application;

import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.data.SharedPrefs;
import com.openu.sadna.booklibrary.network.RestClient;
import com.openu.sadna.booklibrary.ui.AddBookViewModelFactory;
import com.openu.sadna.booklibrary.ui.AdminLentBooksTrackingViewModelFactory;
import com.openu.sadna.booklibrary.ui.BookDetailsViewModelFactory;
import com.openu.sadna.booklibrary.ui.BooksCatalogViewModelFactory;
import com.openu.sadna.booklibrary.ui.LendingHistoryViewModelFactory;
import com.openu.sadna.booklibrary.ui.LoginViewModelFactory;

public class InjectorUtils {

    private static Repository getRepository(Application application)  {
        return Repository.getInstance(RestClient.getInstance().getApiService(), SharedPrefs.getInstance(application));
    }

    public static LoginViewModelFactory provideLoginViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new LoginViewModelFactory(repo);
    }

    public static AdminLentBooksTrackingViewModelFactory provideAdminLentBooksTrackingViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new AdminLentBooksTrackingViewModelFactory(repo);
    }

    public static BookDetailsViewModelFactory provideBookDetailsViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new BookDetailsViewModelFactory(repo);
    }

    public static BooksCatalogViewModelFactory provideBooksCatalogViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new BooksCatalogViewModelFactory(repo);
    }

    public static LendingHistoryViewModelFactory provideLendingHistoryViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new LendingHistoryViewModelFactory(repo);
    }

    public static AddBookViewModelFactory provideAddBookViewModelFactory(Application application) {
        Repository repo = getRepository(application);
        return new AddBookViewModelFactory(repo);
    }

}