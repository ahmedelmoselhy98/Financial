package com.e.k.m.a.financial.buyelement;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.home.MainHomeSubRecyclerAdapter;

import java.util.ArrayList;
import java.util.Arrays;

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
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                String category = arrayList.get(position);
                createSubCategory(category);
                BuyElement.buyElementSubRecycler.setLayoutManager(linearLayoutManager);
                BuyElement.buyElementSubRecycler.setAdapter(BuyElement.buyElementSubRecyclerAdapter);
            }
        });
    }
    private void createSubCategory(String s){
        ArrayList<String> restaurant = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.restaurant)));
        ArrayList<String> me = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.me)));
        ArrayList<String> home = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.home)));
        ArrayList<String> car = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.car)));
        ArrayList<String> education = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.education)));
        ArrayList<String> entertainment = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.entertainment)));
        ArrayList<String> devices = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.devices)));
        ArrayList<String> mykids = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.mykids)));
        ArrayList<String> occation = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.occation)));
        ArrayList<String> participate = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.participate)));
        ArrayList<String> charity = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.charity)));
        ArrayList<String> travel = new ArrayList<>( Arrays.asList(mContext.getResources().getStringArray(R.array.travel)));
        switch (s){
            case "مطعم":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, restaurant,"مطعم");
                break;
            case "انا":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, me,"انا");
                break;
            case "البيت":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, home,"البيت");
                break;
            case "سيارة":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, car,"سيارة");
                break;
            case "تعليم":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, education,"تعليم");
                break;
            case "ترفية":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, entertainment,"ترفية");
                break;
            case "اجهزة":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, devices,"اجهزة");
                break;
            case "الابناء":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, mykids,"الابناء");
                break;
            case "مناسبات":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, occation,"مناسبات");
                break;

            case "اشتراكات":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, participate,"اشتراكات");
                break;
            case "خيرى":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, charity,"خيرى");
                break;
            case "سفر":
                BuyElement.buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(mContext, travel,"سفر");
                break;
            default:
                Toast.makeText(mContext, "مفيش تصنيفات", Toast.LENGTH_SHORT).show();
                break;
        }
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
