package com.openu.sadna.booklibrary.util;

import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.data.dao.BooksDao;
import com.openu.sadna.booklibrary.data.dao.UsersDao;
import com.openu.sadna.booklibrary.ui.LoginViewModelFactory;

public class InjectorUtils {

    private static Repository getRepository()  {
        //TODO return Repository.getInstance(FirestoreDatabase.getInstance().profilesDao());
        return Repository.getInstance(new BooksDao(), new UsersDao());
    }

    public static LoginViewModelFactory provideLoginViewModelFactory() {
        Repository repo = getRepository();
        return new LoginViewModelFactory(repo);
    }

}