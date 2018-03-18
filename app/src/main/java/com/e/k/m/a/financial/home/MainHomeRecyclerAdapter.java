package com.e.k.m.a.financial.home;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.models.MainHomeRecyclerViewModel;

import java.util.ArrayList;

/**
 * Created by ahmedelmoselhy on 7/30/2017.
 */

public class MainHomeRecyclerAdapter extends RecyclerView.Adapter<MainHomeRecyclerAdapter.RecyclerViewHolder>{
    private RecyclerView subRecycler;
    private MainHomeSubRecyclerAdapter subRecyclerAdapter;
    private static int NUM_OF_ITEMS;
    private static Context mContext;
    private ArrayList<MainHomeRecyclerViewModel> mainHomeRecyclerViewModel;

    public MainHomeRecyclerAdapter(Context mContext) {
        this.mContext = mContext;
        NUM_OF_ITEMS = 5;
    }

    public MainHomeRecyclerAdapter(Context mContext, ArrayList<MainHomeRecyclerViewModel> mainHomeRecyclerViewModel) {
        this.mContext = mContext;
        this.mainHomeRecyclerViewModel = mainHomeRecyclerViewModel;
            NUM_OF_ITEMS = 5;
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.main_home_recycler_item ,parent,false);
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
//        if (mainHomeRecyclerViewModel.size() >0){
//            return mainHomeRecyclerViewModel.size();
//        }else
        return NUM_OF_ITEMS;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView itemDate,itemValue;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemDate = itemView.findViewById(R.id.main_home_recycler_item_date);
            itemValue = itemView.findViewById(R.id.main_home_recycler_item_value);
            buildMainHomeSubRecyclerView(itemView);
        }

        public void bind(int itemPosition){


        }


    }
    public void buildMainHomeSubRecyclerView(View v){
        subRecycler =v.findViewById(R.id.main_home_sub_recycler_view);
        subRecyclerAdapter = new MainHomeSubRecyclerAdapter(mContext);
        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        subRecycler.setLayoutManager(linearLayout);
        subRecycler.setAdapter(subRecyclerAdapter);
    }
}
