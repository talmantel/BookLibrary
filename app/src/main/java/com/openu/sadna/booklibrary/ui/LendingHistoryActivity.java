package com.openu.sadna.booklibrary.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.util.InjectorUtils;

class LendingHistoryActivity  extends AppCompatActivity {

    private LendingHistoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_history);
        viewModel = ViewModelProviders.of(this, InjectorUtils.provideLendingHistoryViewModelFactory(getApplication())).get(LendingHistoryViewModel.class);
    }
}
