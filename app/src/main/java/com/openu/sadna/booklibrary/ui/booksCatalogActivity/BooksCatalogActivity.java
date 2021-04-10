package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.Books;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.ui.bookDetailsActivity.BookDetailsActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

import java.util.ArrayList;

public class BooksCatalogActivity extends BaseActivity {

    private BooksCatalogViewModel viewModel;
    private EditText textQueryEditText;
    private Spinner categorySpinner;
    private RecyclerView booksRecyclerView;
    private BooksRecyclerViewAdapter booksRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_catalog);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideBooksCatalogViewModelFactory(getApplication())).get(BooksCatalogViewModel.class);

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

        textQueryEditText = findViewById(R.id.queryEditText);
        categorySpinner = findViewById(R.id.category_spinner);
        booksRecyclerView = findViewById(R.id.books_recycler_view);
        booksRecyclerViewAdapter = new BooksRecyclerViewAdapter(this);
        booksRecyclerViewAdapter.setClickListener(new BooksRecyclerViewAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(BooksCatalogActivity.this, BookDetailsActivity.class);
                intent.putExtra(BookDetailsActivity.BOOK_ID_EXTRA, booksRecyclerViewAdapter.getItem(position).getId());
                startActivity(intent);
            }
        });
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        booksRecyclerView.setAdapter(booksRecyclerViewAdapter);

        viewModel.getBooks().observe(this, new Observer<Books>() {
            @Override
            public void onChanged(Books books) {
                if(books != null) {
                    booksRecyclerViewAdapter.setData(books.getBooks());
                    booksRecyclerViewAdapter.notifyDataSetChanged();
                    if (books.getBooks().size() == 0)
                        Toast.makeText(BooksCatalogActivity.this, R.string.no_matching_books_found_message, Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewModel.getShowError().observe(this, new Observer<Event<Integer>>() {
            @Override
            public void onChanged(@Nullable Event<Integer> event) {
                if (event != null && !event.hasBeenHandled())
                    Toast.makeText(BooksCatalogActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        ArrayList<String> categories = new ArrayList<>();
        categories.add(getString(R.string.all_categories_spinner_item));

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(BooksCatalogActivity.this, android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(spinnerAdapter);
        categorySpinner.setSelection(0);

        viewModel.getCategories().observe(this, new Observer<Categories>() {
            @Override
            public void onChanged(Categories categories) {
                if(categories != null) {
                    spinnerAdapter.clear();
                    spinnerAdapter.add(getString(R.string.all_categories_spinner_item));
                    spinnerAdapter.addAll(categories.getCategories());
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.search).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBooks();
            }
        });
    }

    private void searchBooks(){
        String textQuery = textQueryEditText.getText().toString();
        if(textQuery.isEmpty())
            textQuery = null;

        String category = null;
        //First spinner item is "All Categories"
        if(categorySpinner.getSelectedItemPosition() != 0)
            category = (String) categorySpinner.getSelectedItem();

        viewModel.loadBooks(textQuery, category);
    }

}