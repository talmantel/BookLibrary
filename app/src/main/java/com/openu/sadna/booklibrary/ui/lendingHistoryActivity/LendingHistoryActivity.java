package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.bookDetailsActivity.BookDetailsViewModel;
import com.openu.sadna.booklibrary.util.InjectorUtils;

public class LendingHistoryActivity  extends BaseActivity {

    private LendingHistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_history);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideLendingHistoryViewModelFactory(getApplication())).get(LendingHistoryViewModel.class);
    }
}
