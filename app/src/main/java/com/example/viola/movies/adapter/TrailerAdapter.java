package com.example.viola.movies.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.viola.movies.R;
import com.example.viola.movies.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerHolder> {
private List<Trailer> mTrailers;
private Context mContext;

    public TrailerAdapter(List<Trailer> mTrailers, Context mContext) {
        this.mTrailers = mTrailers;
        this.mContext = mContext;
    }

    @Override
    public TrailerAdapter.TrailerHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item,parent,false);
        return new TrailerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TrailerHolder holder, int position) {
        holder.title.setText(mTrailers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mTrailers.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView thumbnail;
        public TrailerHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.trailer_title);
            thumbnail = itemView.findViewById(R.id.moviePoster_iv);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Trailer clickedItem = mTrailers.get(pos);
                        String videoId = mTrailers.get(pos).getKey();
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+videoId));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("VIDEO_ID", videoId);
                        mContext.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedItem.getName(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
