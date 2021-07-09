package com.openu.sadna.booklibrary.ui.lendingHistoryActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class LendingHistoryRecyclerViewAdapter extends RecyclerView.Adapter<LendingHistoryRecyclerViewAdapter.ViewHolder> {

    private List<Book> mData;
    private final LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private final SimpleDateFormat sdf;

    public LendingHistoryRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
        sdf = new SimpleDateFormat(context.getString(R.string.date_time_format), Locale.getDefault());
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_lending_history_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Book book = mData.get(position);
        holder.bookNameTextView.setText(book.getName());
        holder.lentTimeTextView.setText(holder.itemView.getContext().getString(R.string.book_lent_time_text, sdf.format(new Date(book.getLendDetails().getLentTime()))));
        if(book.getLendDetails().getReturnTime() != null) {
            holder.returnedTimeTextView.setText(holder.itemView.getContext().getString(R.string.book_returned_time_text, sdf.format(new Date(book.getLendDetails().getReturnTime()))));
            holder.returnedTimeTextView.setVisibility(View.VISIBLE);
        }
        else
            holder.returnedTimeTextView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final TextView bookNameTextView;
        private final TextView lentTimeTextView;
        private final TextView returnedTimeTextView;

        ViewHolder(View itemView) {
            super(itemView);
            bookNameTextView = itemView.findViewById(R.id.book_name);
            lentTimeTextView = itemView.findViewById(R.id.book_lent_time);
            returnedTimeTextView = itemView.findViewById(R.id.book_returned_time);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public Book getItem(int id) {
        return mData.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setData(List<Book> mData) {
        this.mData = mData;
    }
}