package com.openu.sadna.booklibrary.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

class AddBookActivity  extends AppCompatActivity {

    private AddBookViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        viewModel = ViewModelProviders.of(this, InjectorUtils.provideAddBookViewModelFactory(getApplication())).get(AddBookViewModel.class);
    }
}
