package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.network.pojo.User;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

import java.util.List;

public class BookDetailsActivity  extends BaseActivity {

    public static final String BOOK_ID_EXTRA = "book_id";

    private BookDetailsViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        int bookID = getIntent().getIntExtra(BOOK_ID_EXTRA, -1);

        viewModel = new ViewModelProvider(this, InjectorUtils.provideBookDetailsViewModelFactory(getApplication())).get(BookDetailsViewModel.class);
        viewModel.loadBook(bookID);

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
                    Toast.makeText(BookDetailsActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEventsToHandle().observe(this, new Observer<Event<BookDetailsViewModel.Events>>() {
            @Override
            public void onChanged(@Nullable Event<BookDetailsViewModel.Events> event) {
                if (event != null && !event.hasBeenHandled()) {
                    switch (event.getContentIfNotHandled()) {
                        case RETURN_BOOK_SUCCESS:
                            Toast.makeText(BookDetailsActivity.this, R.string.book_returned_message, Toast.LENGTH_SHORT).show();
                            break;
                        case ORDER_BOOK_SUCCESS:
                            Toast.makeText(BookDetailsActivity.this, R.string.book_ordered_message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });

        viewModel.getCurrentUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                //TODO
            }
        });

        viewModel.getBook().observe(this, new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                //TODO
            }
        });

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                //TODO
            }
        });
    }
}
