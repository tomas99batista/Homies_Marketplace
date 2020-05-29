package com.example.homiesmarketplaceapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.Place;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.FeedViewHolder> {

    private List<Place> dataList;
    private Context context;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onItemClick(int position);
        void onAddToFavorites(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
    }
    public FeedAdapter(Context context, List<Place> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    static class FeedViewHolder extends RecyclerView.ViewHolder {

        public final View mView;

        ImageView placeImage;
        TextView placeName;
        TextView placeDetails;
        ImageButton addToFavorites;


        FeedViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mView = itemView;

            placeImage=mView.findViewById(R.id.placeImage);
            placeName = mView.findViewById(R.id.place_name);
            placeDetails=mView.findViewById(R.id.place_details);
            addToFavorites=mView.findViewById(R.id.add_to_favorites);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener !=null){
                        int position=getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onItemClick(position);
                        }
                    }
                }
            });

            addToFavorites.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position=getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onAddToFavorites(position);
                        }
                    }
                }
            });
        }
    }

    @Override
    public FeedViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_feed, parent, false);
        return new FeedViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(FeedViewHolder holder, int position) {

        //holder.txtId.setText(dataList.get(position).getEvent_id().toString());
        holder.placeName.setText(dataList.get(position).getTitle());

        holder.placeDetails.setText(dataList.get(position).getCity());

        Glide.with(context).load(dataList.get(position).getPhotos()).into(holder.placeImage);
    }

    @Override
    public int getItemCount() {

        return dataList.size();
    }
}
