package com.openu.sadna.booklibrary.common;

import android.content.Context;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.data.Repository;
import com.openu.sadna.booklibrary.network.pojo.User;
import com.openu.sadna.booklibrary.ui.addBookActivity.AddBookActivity;
import com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity.AdminLentBooksTrackingActivity;
import com.openu.sadna.booklibrary.ui.booksCatalogActivity.BooksCatalogActivity;
import com.openu.sadna.booklibrary.ui.lendingHistoryActivity.LendingHistoryActivity;
import com.openu.sadna.booklibrary.ui.loginActivity.LoginActivity;

public class OptionsMenuHandler {

    private static OptionsMenuHandler instance = null;
    private Repository repository;

    private OptionsMenuHandler(Repository repository){
        this.repository = repository;
    }

    public synchronized static OptionsMenuHandler getInstance(Repository repository){
        if(instance == null)
            instance = new OptionsMenuHandler(repository);
        return instance;
    }


    public boolean onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(R.menu.menu, menu);
        User user = repository.getCurrentUser().getValue();
        if(user != null && user.isAdmin()) {
            menu.findItem(R.id.menu_add_book).setVisible(true);
            menu.findItem(R.id.menu_lending_tracking).setVisible(true);
        }
        return true;
    }

    public boolean onOptionsItemSelected(Context context, MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_add_book:
                intent = new Intent(context, AddBookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                return true;
            case R.id.menu_book_catalog:
                intent = new Intent(context, BooksCatalogActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                return true;
            case R.id.menu_lending_history:
                intent = new Intent(context, LendingHistoryActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                return true;
            case R.id.menu_lending_tracking:
                intent = new Intent(context, AdminLentBooksTrackingActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                context.startActivity(intent);
                return true;
            case R.id.menu_logout:
                repository.logout();
                intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
                return true;
        }
        return false;
    }
}
