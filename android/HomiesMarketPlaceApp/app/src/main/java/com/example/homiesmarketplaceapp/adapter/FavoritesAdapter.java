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
<<<<<<< HEAD
=======
        void onRemovingFromFavoritesClick(int position);
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
<<<<<<< HEAD
        TextView favorite_place_details;
=======
        TextView favorite_place_location;
        TextView favorite_place_price;
        ImageButton remove;
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        FavoritesViewHolder(View itemView,final FavoritesAdapter.OnItemClickListener listener) {
            super(itemView);
            mView = itemView;

<<<<<<< HEAD
            favorite_place_image=mView.findViewById(R.id.favorite_event_image);
            favorite_place_title=mView.findViewById(R.id.favorite_event_name);
            favorite_place_details=mView.findViewById(R.id.favorite_event_details);

=======
            favorite_place_image=mView.findViewById(R.id.favorite_place_image);
            favorite_place_title=mView.findViewById(R.id.favorite_place_name);
            favorite_place_location=mView.findViewById(R.id.favorite_place_location);
            favorite_place_price=mView.findViewById(R.id.favorite_place_price);
            remove=mView.findViewById(R.id.remove_from_favorites);
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76

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

<<<<<<< HEAD
=======
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

>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
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
<<<<<<< HEAD
        holder.favorite_place_details.setText(dataList.get(position).getCity());
=======
        holder.favorite_place_location.setText(dataList.get(position).getCity());
        holder.favorite_place_price.setText(((int)dataList.get(position).getPrice()) + " €/month");
>>>>>>> 22a6776091314bfb3f82246d2bd96801086a2a76
        Glide.with(context).load(dataList.get(position).getPhotos()).into(holder.favorite_place_image);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
