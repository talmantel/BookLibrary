package com.openu.sadna.booklibrary.ui.adminLentBooksTrackingActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.network.pojo.Book;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class LentBooksRecyclerViewAdapter extends RecyclerView.Adapter<LentBooksRecyclerViewAdapter.ViewHolder> {

    private List<Book> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private ItemClickListener mReturnBookClickListener;
    private final SimpleDateFormat sdf;

    public LentBooksRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
        sdf = new SimpleDateFormat(context.getString(R.string.date_time_format), Locale.getDefault());
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_lent_book_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mData.get(position);
        holder.bookNameTextView.setText(book.getName());
        holder.lentTimeTextView.setText(holder.itemView.getContext().getString(R.string.book_lent_at_time_text, sdf.format(new Date(book.getLendDetails().getLentTime()))));
        holder.lentToTextView.setText(holder.itemView.getContext().getString(R.string.book_lent_to_text, book.getLendDetails().getLentTo().getFirstName(), book.getLendDetails().getLentTo().getLastName()));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView bookNameTextView;
        private final TextView lentToTextView;
        private final TextView lentTimeTextView;

        ViewHolder(final View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.book_name);
            lentToTextView = itemView.findViewById(R.id.book_lent_to);
            lentTimeTextView = itemView.findViewById(R.id.book_lent_time);
            Button returnBookButton = itemView.findViewById(R.id.return_book);
            returnBookButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mClickListener != null) mReturnBookClickListener.onItemClick(getAdapterPosition());
                }
            });
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

    public void setReturnBookClickListener(ItemClickListener returnBookClickListener) {
        this.mReturnBookClickListener = returnBookClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(int position);
    }

    public void setData(List<Book> mData) {
        this.mData = mData;
    }
}