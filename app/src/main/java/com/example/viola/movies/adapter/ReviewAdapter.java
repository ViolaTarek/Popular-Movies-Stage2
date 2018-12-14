package com.example.viola.movies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.viola.movies.R;
import com.example.viola.movies.Response.fetchReviews;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.Reviewholder> {
    private Context mContext;
    private List<fetchReviews> reviewResults;

    public ReviewAdapter(Context mContext, List<fetchReviews> reviewResults) {
        this.reviewResults = reviewResults;
    }

    @NonNull
    @Override
    public Reviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new Reviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Reviewholder holder, int position) {
        holder.title.setText(reviewResults.get(position).getAuthor());
        holder.url.setText(reviewResults.get(position).getContent());

    }

    @Override
    public int getItemCount() {
        return reviewResults.size();
    }

    public class Reviewholder extends RecyclerView.ViewHolder {
        public TextView url;
        public TextView title;

         Reviewholder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.review_writer);
            url =  itemView.findViewById(R.id.review_link);

        }
    }
}
