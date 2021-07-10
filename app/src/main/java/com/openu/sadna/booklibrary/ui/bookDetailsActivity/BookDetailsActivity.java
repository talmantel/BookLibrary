package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.Book;
import com.openu.sadna.booklibrary.network.pojo.Review;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class BookDetailsActivity  extends BaseActivity {

    public static final String BOOK_ID_EXTRA = "book_id";

    private BookDetailsViewModel viewModel;
    private BookReviewsRecyclerViewAdapter bookReviewsRecyclerViewAdapter;
    private EditText reviewEditText;

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
                        case ADD_REVIEW_SUCCESS:
                            reviewEditText.setText("");
                            hideKeyboard();
                            Toast.makeText(BookDetailsActivity.this, R.string.review_added_message, Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });

        final Button orderBookButton = findViewById(R.id.order_button);
        final Button returnBookButton = findViewById(R.id.return_button);
        final TextView bookNameTextView = findViewById(R.id.book_name);
        final TextView bookAuthorTextView = findViewById(R.id.book_author);
        final TextView bookCategoryTextView = findViewById(R.id.book_category);
        final TextView bookDescriptionTextView = findViewById(R.id.book_description);

        bookDescriptionTextView.setMovementMethod(new ScrollingMovementMethod());
        reviewEditText = findViewById(R.id.review_edit_text);

        viewModel.getBook().observe(this, new Observer<Book>() {
            @Override
            public void onChanged(Book book) {
                bookNameTextView.setText(book.getName());
                bookAuthorTextView.setText(getString(R.string.author_name_text, book.getAuthorFName(), book.getAuthorLName()));
                bookCategoryTextView.setText(getString(R.string.category_text, book.getCategory()));

                if(book.getDescription() != null && !book.getDescription().isEmpty())
                    bookDescriptionTextView.setText(book.getDescription());
                else
                    bookDescriptionTextView.setText(R.string.no_book_description_message);

                if(book.isAvailable()) {
                    orderBookButton.setText(R.string.action_order);
                    orderBookButton.setEnabled(true);
                }
                else if(book.getLendDetails() == null){
                    orderBookButton.setText(R.string.action_order_unavailable);
                    orderBookButton.setEnabled(false);
                }
                else{
                    SimpleDateFormat sdf = new SimpleDateFormat(getString(R.string.date_format), Locale.getDefault());
                    orderBookButton.setText(getString(R.string.action_order_already_ordered, sdf.format(book.getLendDetails().getLentTime())));
                    orderBookButton.setEnabled(false);
                }
            }
        });


        RecyclerView bookReviewsRecyclerView = findViewById(R.id.books_reviews_recycler_view);
        bookReviewsRecyclerViewAdapter = new BookReviewsRecyclerViewAdapter(this);
        bookReviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookReviewsRecyclerView.setAdapter(bookReviewsRecyclerViewAdapter);

        viewModel.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(List<Review> reviews) {
                if(reviews != null) {
                    bookReviewsRecyclerViewAdapter.setData(reviews);
                    bookReviewsRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        });

        viewModel.getShowReturnBookOption().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean shouldShowReturnButton) {
                returnBookButton.setVisibility(shouldShowReturnButton ? View.VISIBLE : View.GONE);
            }
        });

        findViewById(R.id.add_review_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reviewText = reviewEditText.getText().toString();
                if(!reviewText.isEmpty())
                    viewModel.submitReview(reviewText);
            }
        });

        orderBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.orderBook();
            }
        });

        returnBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.returnBook();
            }
        });
    }

    public void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = getCurrentFocus();
        if (view == null)
            view = new View(this);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
