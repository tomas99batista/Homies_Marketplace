package com.example.homiesmarketplaceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder> {
    private List<Review> dataList;
    private Context context;
    private ReviewAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ReviewAdapter.OnItemClickListener listener){
        mListener=listener;
    }


    public ReviewAdapter(Context context, List<Review> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        TextView userNameReview;
        TextView CommentReview;
        RatingBar ratingBar;

        ReviewViewHolder(View itemView, final ReviewAdapter.OnItemClickListener mListener) {

            super(itemView);
            mView = itemView;
            userNameReview = mView.findViewById(R.id.userNameReview);
            CommentReview = mView.findViewById(R.id.CommentReview);
            ratingBar=mView.findViewById(R.id.userRating);
        }
    }

    @NonNull
    @Override
    public ReviewAdapter.ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.review, parent, false);
        return new ReviewViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ReviewViewHolder holder, int position) {
        holder.userNameReview.setText(dataList.get(position).getEmail());
        holder.CommentReview.setText(dataList.get(position).getComment());
        holder.ratingBar.setRating((float) dataList.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

}
