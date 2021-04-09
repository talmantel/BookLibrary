package com.openu.sadna.booklibrary.ui;

import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.openu.sadna.booklibrary.util.InjectorUtils;

import org.jetbrains.annotations.NotNull;

public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return InjectorUtils.provideOptionsMenuHandler(getApplication()).onCreateOptionsMenu(menu, getMenuInflater());
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item) {
        return InjectorUtils.provideOptionsMenuHandler(getApplication()).onOptionsItemSelected(this, item);
    }

}
