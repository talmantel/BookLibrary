package com.openu.sadna.booklibrary.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

class BookDetailsActivity  extends AppCompatActivity {

    private BookDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        viewModel = ViewModelProviders.of(this, InjectorUtils.provideBookDetailsViewModelFactory(getApplication())).get(BookDetailsViewModel.class);
    }
}
