package com.openu.sadna.booklibrary.ui.addBookActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.loginActivity.LoginViewModel;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class AddBookActivity  extends BaseActivity {

    private AddBookViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideAddBookViewModelFactory(getApplication())).get(AddBookViewModel.class);
    }
}