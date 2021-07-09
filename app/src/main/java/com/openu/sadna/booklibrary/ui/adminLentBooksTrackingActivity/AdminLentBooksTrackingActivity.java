package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

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

public class AdminLentBooksTrackingActivity extends BaseActivity {

    private AdminLentBooksTrackingViewModel viewModel;
    private LentBooksRecyclerViewAdapter lentBooksRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_lent_books_tracking);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideAdminLentBooksTrackingViewModelFactory(getApplication())).get(AdminLentBooksTrackingViewModel.class);

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
                    Toast.makeText(AdminLentBooksTrackingActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEventsToHandle().observe(this, new Observer<Event<AdminLentBooksTrackingViewModel.Events>>() {
            @Override
            public void onChanged(@Nullable Event<AdminLentBooksTrackingViewModel.Events> event) {
                if (event != null && !event.hasBeenHandled() && event.getContentIfNotHandled() == AdminLentBooksTrackingViewModel.Events.RETURN_BOOK_SUCCESS)
                    Toast.makeText(AdminLentBooksTrackingActivity.this, R.string.book_returned_message, Toast.LENGTH_SHORT).show();
            }
        });

        RecyclerView lentBooksRecyclerView = findViewById(R.id.lent_books_recycler_view);
        lentBooksRecyclerViewAdapter = new LentBooksRecyclerViewAdapter(this);
        lentBooksRecyclerViewAdapter.setClickListener(new LentBooksRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(AdminLentBooksTrackingActivity.this, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.BOOK_ID_EXTRA, lentBooksRecyclerViewAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        lentBooksRecyclerViewAdapter.setReturnBookClickListener(new LentBooksRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(int position) {
                viewModel.returnBook(lentBooksRecyclerViewAdapter.getItem(position).getId());
            }
        });
        lentBooksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lentBooksRecyclerView.setAdapter(lentBooksRecyclerViewAdapter);

        viewModel.getLentBooks().observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> lendingHistory) {
                if(lendingHistory != null) {
                    lentBooksRecyclerViewAdapter.setData(lendingHistory);
                    lentBooksRecyclerViewAdapter.notifyDataSetChanged();
                    if (lendingHistory.size() == 0)
                        Toast.makeText(AdminLentBooksTrackingActivity.this, R.string.no_lent_books_found_message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
