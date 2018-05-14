package com.e.k.m.a.financial.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.models.SubElementItem;

import java.util.ArrayList;

public class MainHomeSubRecyclerAdapter extends RecyclerView.Adapter<MainHomeSubRecyclerAdapter.RecyclerViewHolder>{

    private static int NUM_OF_ITEMS;
    private static Context mContext;
    private ArrayList<SubElementItem> subElementItems;

    public MainHomeSubRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        NUM_OF_ITEMS = 5;
    }

    public MainHomeSubRecyclerAdapter(Context mContext, ArrayList<SubElementItem> subElementItems) {
        this.mContext = mContext;
        this.subElementItems = subElementItems;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.main_home_sub_recycler_item ,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.bind(position);
//        holder.itemMovieImage.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Toast.makeText(mContext,filmsRate[position], Toast.LENGTH_SHORT).show();
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        if (subElementItems.size() >0){
            return subElementItems.size();
        }else
        return NUM_OF_ITEMS;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        ImageView itemImage;
        TextView itemKind,itemValue,itemKind2;
        public RecyclerViewHolder(View itemView) {
            super(itemView);

            itemImage = itemView.findViewById(R.id.main_home_sub_recycler_item_image);
            itemKind = itemView.findViewById(R.id.main_home_sub_recycler_item_kind);
            itemKind2 = itemView.findViewById(R.id.main_home_sub_recycler_item_kind2);
            itemValue = itemView.findViewById(R.id.main_home_sub_recycler_item_value);
        }

        public void bind(int itemPosition){
            if (subElementItems.size() >0){
            itemKind.setText(subElementItems.get(itemPosition).getItemTitle());
            itemKind2.setText(subElementItems.get(itemPosition).getKind());
            itemValue.setText(String.valueOf(subElementItems.get(itemPosition).getItemPrice())+" RS");
        }
        }
    }

}
