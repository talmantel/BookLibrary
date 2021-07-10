package com.openu.sadna.booklibrary.ui.booksCatalogActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.network.pojo.Book;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BooksRecyclerViewAdapter extends RecyclerView.Adapter<BooksRecyclerViewAdapter.ViewHolder> {

    private List<Book> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    public BooksRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mData.get(position);
        holder.bookNameTextView.setText(book.getName());
        holder.authorNameTextView.setText(holder.itemView.getContext().getString(R.string.book_author_full_name_text, book.getAuthorFName(), book.getAuthorLName()));
        if(book.getDescription() != null && !book.getDescription().isEmpty()) {
            holder.bookDescriptionTextView.setText(book.getDescription());
            holder.bookDescriptionTextView.setVisibility(View.VISIBLE);
        }
        else
            holder.bookDescriptionTextView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView bookNameTextView;
      private final TextView authorNameTextView;
      private final TextView bookDescriptionTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.book_name);
            authorNameTextView = itemView.findViewById(R.id.author_name);
            bookDescriptionTextView = itemView.findViewById(R.id.book_description);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(getAdapterPosition());
        }
    }

    public Book getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setData(List<Book> mData) {
        this.mData = mData;
    }
}