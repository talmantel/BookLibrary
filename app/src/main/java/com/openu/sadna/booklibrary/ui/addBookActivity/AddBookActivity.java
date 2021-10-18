package com.openu.sadna.booklibrary.ui.addBookActivity;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.common.Event;
import com.openu.sadna.booklibrary.network.pojo.Categories;
import com.openu.sadna.booklibrary.ui.BaseActivity;
import com.openu.sadna.booklibrary.util.InjectorUtils;

import java.util.ArrayList;

public class AddBookActivity  extends BaseActivity {

    private AddBookViewModel viewModel;
    private Spinner categorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);
        viewModel = new ViewModelProvider(this, InjectorUtils.provideAddBookViewModelFactory(getApplication())).get(AddBookViewModel.class);

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
                    Toast.makeText(AddBookActivity.this, event.getContentIfNotHandled(), Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getEventsToHandle().observe(this, new Observer<Event<AddBookViewModel.Events>>() {
            @Override
            public void onChanged(@Nullable Event<AddBookViewModel.Events> event) {
                if (event != null && !event.hasBeenHandled() && event.getContentIfNotHandled() == AddBookViewModel.Events.ADD_BOOK_SUCCESS){
                    Toast.makeText(AddBookActivity.this, R.string.book_added_message, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });


        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, new ArrayList<String>());
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner = findViewById(R.id.category_spinner);
        categorySpinner.setAdapter(spinnerAdapter);

        viewModel.getCategories().observe(this, new Observer<Categories>() {
            @Override
            public void onChanged(Categories categories) {
                if(categories != null) {
                    spinnerAdapter.clear();
                    spinnerAdapter.addAll(categories.getCategories());
                    spinnerAdapter.notifyDataSetChanged();
                }
            }
        });

        findViewById(R.id.add_book).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addBook();
            }
        });
    }

    private void addBook(){
        String bookISBNString = ((TextView) findViewById(R.id.book_isbn)).getText().toString();
        if(bookISBNString.isEmpty())
            Toast.makeText(this, R.string.book_isbn_empty_error, Toast.LENGTH_SHORT).show();
        else {
            int bookISBN = Integer.parseInt(bookISBNString);
            String bookName = ((TextView) findViewById(R.id.book_name)).getText().toString();
            String authorFirstName = ((TextView) findViewById(R.id.author_fname)).getText().toString();
            String authorLastName = ((TextView) findViewById(R.id.author_lname)).getText().toString();
            String bookDescription = ((TextView) findViewById(R.id.book_description)).getText().toString();
            String category = (String) categorySpinner.getSelectedItem();
            viewModel.addBook(bookISBN, bookName, authorFirstName, authorLastName, bookDescription, category);
        }
    }
}
