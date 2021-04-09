package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class BooksCatalogActivity extends BaseActivity {

    private BooksCatalogViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_catalog);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideBooksCatalogViewModelFactory(getApplication())).get(BooksCatalogViewModel.class);
    }

}