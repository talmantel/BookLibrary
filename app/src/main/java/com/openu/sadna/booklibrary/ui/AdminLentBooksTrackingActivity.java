package com.openu.sadna.booklibrary.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

class AdminLentBooksTrackingActivity extends AppCompatActivity {

    private AdminLentBooksTrackingViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lent_books_tracking);
        viewModel = ViewModelProviders.of(this, InjectorUtils.provideAdminLentBooksTrackingViewModelFactory(getApplication())).get(AdminLentBooksTrackingViewModel.class);
    }
}
