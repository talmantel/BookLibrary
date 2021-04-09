package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.addBookActivity.AddBookViewModel;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class AdminLentBooksTrackingActivity extends BaseActivity {

    private AdminLentBooksTrackingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lent_books_tracking);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideAdminLentBooksTrackingViewModelFactory(getApplication())).get(AdminLentBooksTrackingViewModel.class);

    }
}
