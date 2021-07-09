package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.bookDetailsActivity.BookDetailsActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

import java.util.List;

public class LendingHistoryActivity  extends BaseActivity {

    private LendingHistoryViewModel viewModel;
    private RecyclerView lendingHistoryRecyclerView;
    private LendingHistoryRecyclerViewAdapter lendingHistoryRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lending_history);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideLendingHistoryViewModelFactory(getApplication())).get(LendingHistoryViewModel.class);

        final ProgressBar loadingProgressBar = findViewById(R.id.loading);
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isLoading) {
                if(isLoading != null && isLoading){
                    loadingProgressBar.setVisibility(View.VISIBLE);
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }
                else{
                    loadingProgressBar.setVisibility(View.GONE);
                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                }

            }
        });

        viewModel.getShowError().observe(this, new Observer<Event<Integer>>() {
            @Override
            public void onChanged(@Nullable Event<Integer> event) {
                if (event != null && !event.hasBeenHandled())
                    Toast.makeText(LendingHistoryActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        lendingHistoryRecyclerView = findViewById(R.id.lending_history_recycler_view);
        lendingHistoryRecyclerViewAdapter = new LendingHistoryRecyclerViewAdapter(this);
        lendingHistoryRecyclerViewAdapter.setClickListener(new LendingHistoryRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(LendingHistoryActivity.this, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.BOOK_ID_EXTRA, lendingHistoryRecyclerViewAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        lendingHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lendingHistoryRecyclerView.setAdapter(lendingHistoryRecyclerViewAdapter);

        viewModel.getLendingHistory().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> lendingHistory) {
                if(lendingHistory != null) {
                    lendingHistoryRecyclerViewAdapter.setData(lendingHistory);
                    lendingHistoryRecyclerViewAdapter.notifyDataSetChanged();
                    if (lendingHistory.size() == 0)
                        Toast.makeText(LendingHistoryActivity.this, R.string.no_lending_history_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
