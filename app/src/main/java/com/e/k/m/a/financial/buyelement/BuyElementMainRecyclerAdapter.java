package com.e.k.m.a.financial.buyelement;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.home.MainHomeSubRecyclerAdapter;
import com.e.k.m.a.financial.models.MainHomeRecyclerViewModel;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by ahmedelmoselhy on 7/30/2017.
 */

public class BuyElementMainRecyclerAdapter extends RecyclerView.Adapter<BuyElementMainRecyclerAdapter.RecyclerViewHolder>{
    private RecyclerView subRecycler;
    private MainHomeSubRecyclerAdapter subRecyclerAdapter;
    private static int NUM_OF_ITEMS;
    private static Context mContext;
    private ArrayList<String> arrayList;

//    public BuyElementMainRecyclerAdapter(Context mContext) {
//        this.mContext = mContext;
//        NUM_OF_ITEMS = arrayList.size();
//    }

    public BuyElementMainRecyclerAdapter(Context mContext, ArrayList<String> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;
            NUM_OF_ITEMS = arrayList.size();

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.buy_element_recycler_item ,parent,false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BuyElement.arrayListSub = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.me)));
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, BuyElement.arrayListSub);
                BuyElement.buyElementSubRecycler.setLayoutManager(linearLayoutManager);
                BuyElement.buyElementSubRecycler.setAdapter(BuyElement.buyElementSubRecyclerAdapter);
                Toast.makeText(mContext, "Position " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList.size() >0){
            return arrayList.size();
        }else
        return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        ImageView itemimage;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.buy_element_textview);
            itemimage = itemView.findViewById(R.id.buy_element_imageview);
        }

        public void bind(int itemPosition){
            itemName.setText(arrayList.get(itemPosition));
        }


    }
}
