package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity.AdminLentBooksTrackingViewModel;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class BookDetailsActivity  extends BaseActivity {

    private BookDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideBookDetailsViewModelFactory(getApplication())).get(BookDetailsViewModel.class);
    }
}
