package com.inovaitsys.inozest.adapters;

/**
 * Created by Bagya on 5/3/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.inovaitsys.inozest.ImageResponse;
import com.inovaitsys.inozest.R;
import com.inovaitsys.inozest.listeners.GridItemClickListener;

import java.util.List;


public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.ImageViewHolder> {

    private LayoutInflater inflater;
    private Context context;
    private List<ImageResponse> restaurantList;
    GridItemClickListener gridItemClickListener;

    public RestaurantAdapter(Context context, List<ImageResponse> restaurantList, GridItemClickListener gridItemClickListener){

        inflater=LayoutInflater.from(context);
        this.restaurantList = restaurantList;
        this.context = context;
        this.gridItemClickListener = gridItemClickListener;
    }
    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view=inflater.inflate(R.layout.restaurant_card,parent,false);
        ImageViewHolder holder=new ImageViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {

        String url = restaurantList.get(position).getUrl();
        String restaurant_name = restaurantList.get(position).getName();
        Glide.with(context).load(url).into(holder.banner);
        holder.title.setText(restaurant_name);



    }

    @Override
    public int getItemCount() {

        return restaurantList.size();
    }


    class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView banner;
        TextView title;

        public ImageViewHolder(View itemView) {
            super(itemView);
            banner = (ImageView) itemView.findViewById(R.id.subs_imageview);
            title = (TextView) itemView.findViewById(R.id.subs_res_name);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();


            gridItemClickListener.onItemClick("1");


        }
    }


}

