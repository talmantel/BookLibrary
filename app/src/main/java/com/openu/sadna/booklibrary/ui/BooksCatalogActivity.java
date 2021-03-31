package com.openu.sadna.booklibrary.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class BooksCatalogActivity extends AppCompatActivity {

    private  BooksCatalogViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_catalog);
        viewModel = ViewModelProviders.of(this, InjectorUtils.provideBooksCatalogViewModelFactory(getApplication())).get( BooksCatalogViewModel.class);
    }
}