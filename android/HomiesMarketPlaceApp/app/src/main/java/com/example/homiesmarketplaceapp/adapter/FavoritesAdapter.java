package com.example.homiesmarketplaceapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homiesmarketplaceapp.R;
import com.example.homiesmarketplaceapp.model.Place;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder> {
    private List<Place> dataList;
    private Context context;
    private FavoritesAdapter.OnItemClickListener mListener;


    public interface OnItemClickListener{
        void onItemClick(int position);
        void onRemovingFromFavoritesClick(int position);
    }

    public void setOnItemClickListener(FavoritesAdapter.OnItemClickListener listener){
        mListener=listener;
    }


    public FavoritesAdapter(Context context, List<Place> dataList) {
        this.context = context;
        this.dataList = dataList;
    }



    static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        ImageView favorite_place_image;
        TextView favorite_place_title;

        TextView favorite_place_location;
        TextView favorite_place_price;
        ImageButton remove;
        FavoritesViewHolder(View itemView,final FavoritesAdapter.OnItemClickListener listener) {
            super(itemView);
            mView = itemView;

            favorite_place_image=mView.findViewById(R.id.favorite_place_image);
            favorite_place_title=mView.findViewById(R.id.favorite_place_name);
            favorite_place_location=mView.findViewById(R.id.favorite_place_location);
            favorite_place_price=mView.findViewById(R.id.favorite_place_price);
            remove=mView.findViewById(R.id.remove_from_favorites);

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


            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener !=null){
                        int position=getAdapterPosition();
                        if (position !=RecyclerView.NO_POSITION){
                            listener.onRemovingFromFavoritesClick(position);
                        }
                    }
                }
            });

        }
    }

    @NonNull
    @Override
    public FavoritesAdapter.FavoritesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.place_favorites, parent, false);
        return new FavoritesViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoritesAdapter.FavoritesViewHolder holder, int position) {
        holder.favorite_place_title.setText(dataList.get(position).getTitle());

        holder.favorite_place_location.setText(dataList.get(position).getCity());
        holder.favorite_place_price.setText(((int)dataList.get(position).getPrice()) + " €/month");
        Glide.with(context).load(dataList.get(position).getPhotos()).into(holder.favorite_place_image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
