package com.openu.sadna.booklibrary.data;

import com.openu.sadna.booklibrary.common.Resource;
import com.openu.sadna.booklibrary.data.dao.BooksDao;
import com.openu.sadna.booklibrary.data.dao.UsersDao;
import com.openu.sadna.booklibrary.data.model.User;

public class Repository {

    private static Repository instance;

    private Repository(){ }

    public synchronized static Repository getInstance(BooksDao booksDao, UsersDao usersDao){
        if(instance == null)
            instance = new Repository();
        return instance;
    }

    public Resource<User> login(String username, String password) {
        //TODO
        return new Resource<User>(null, null, false);
    }
}
