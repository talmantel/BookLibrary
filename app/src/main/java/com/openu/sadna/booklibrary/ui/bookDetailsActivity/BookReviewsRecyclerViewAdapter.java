package com.openu.sadna.booklibrary.ui.bookDetailsActivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.openu.sadna.booklibrary.R;
import com.openu.sadna.booklibrary.network.pojo.Review;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BookReviewsRecyclerViewAdapter extends RecyclerView.Adapter<BookReviewsRecyclerViewAdapter.ViewHolder> {

    private List<Review> mData;
    private final LayoutInflater mInflater;
    private final SimpleDateFormat sdf;

    public BookReviewsRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = new ArrayList<>();
        sdf = new SimpleDateFormat(context.getString(R.string.date_format), Locale.getDefault());
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adapter_review_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mData.get(position);
        holder.reviewerNameTextView.setText(holder.itemView.getContext().getString(R.string.reviewer_name_text, review.getReviewer().getFirstName(), review.getReviewer().getLastName()));
        holder.reviewTimeTextView.setText(sdf.format(new Date(review.getTime())));
        holder.reviewTextView.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView reviewerNameTextView;
      private final TextView reviewTimeTextView;
      private final TextView reviewTextView;

        ViewHolder(View itemView) {
            super(itemView);
            reviewerNameTextView = itemView.findViewById(R.id.reviewer_name);
            reviewTimeTextView = itemView.findViewById(R.id.review_time);
            reviewTextView = itemView.findViewById(R.id.review);
        }
    }

    public void setData(List<Review> mData) {
        this.mData = mData;
    }
}