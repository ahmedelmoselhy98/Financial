package com.e.k.m.a.financial.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.SubElementItem;

import java.util.ArrayList;

public class MainHomeRecyclerAdapter extends RecyclerView.Adapter<MainHomeRecyclerAdapter.RecyclerViewHolder> {
    private RecyclerView subRecycler;
    private MainHomeSubRecyclerAdapter subRecyclerAdapter;
    private static int NUM_OF_ITEMS;
    private static Context mContext;
    private ArrayList<MainElementItem> mainElementItems;
    private DatabaseAdapter databaseAdapter;
    private SharedPreferences sp;
    private String userEmail;
    private String kind;
    private ArrayList<SubElementItem> temp;
    private int flag = 0;
    public MainHomeRecyclerAdapter(Context mContext, ArrayList<MainElementItem> mainElementItems) {
        this.mContext = mContext;
        this.mainElementItems = mainElementItems;
        flag = 1;
        for (int i = 0; i < mainElementItems.size(); i++) {
            Log.e("MainHomeRecyclerAdapter", mainElementItems.get(i).getItemDate());
            Log.e("MainHomeRecyclerAdapter", mainElementItems.get(i).getMoneySpentInMonth() + "");
        }
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        userEmail = sp.getString("useremail", "");

        NUM_OF_ITEMS = mainElementItems.size();
        databaseAdapter = new DatabaseAdapter(mContext);

    }
public MainHomeRecyclerAdapter(Context mContext, ArrayList<MainElementItem> mainElementItems,String kind) {
        this.mContext = mContext;
        this.kind = kind;
        this.mainElementItems = mainElementItems;
        flag = 2;
        for (int i = 0; i < mainElementItems.size(); i++) {
            Log.e("MainHomeRecyclerAdapter", mainElementItems.get(i).getItemDate());
            Log.e("MainHomeRecyclerAdapter", mainElementItems.get(i).getMoneySpentInMonth() + "");
        }
        sp = PreferenceManager.getDefaultSharedPreferences(mContext);
        userEmail = sp.getString("useremail", "");

        NUM_OF_ITEMS = mainElementItems.size();
        databaseAdapter = new DatabaseAdapter(mContext);

    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View v = layoutInflater.inflate(R.layout.main_home_recycler_item, parent, false);
        RecyclerViewHolder holder = new RecyclerViewHolder(v);

        return holder;
    }


    @Override
    public void onBindViewHolder(final RecyclerViewHolder holder, final int position) {
        setScaleAnimation(holder.itemView);
        holder.bind(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    // functions to Animate Recycler Items
    private void setFadeAnimation(View view) {
        AlphaAnimation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    private final static int FADE_DURATION = 1000;
    private void setScaleAnimation(View view) {
        ScaleAnimation anim = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        anim.setDuration(FADE_DURATION);
        view.startAnimation(anim);
    }
    @Override
    public int getItemCount() {
        if (mainElementItems.size() > 0) {
            return NUM_OF_ITEMS;
        } else
            return 0;
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView itemDate, itemValue;

        public RecyclerViewHolder(View itemView) {
            super(itemView);
            itemDate = itemView.findViewById(R.id.main_home_recycler_item_date);
            itemValue = itemView.findViewById(R.id.main_home_recycler_item_value);
        }

        public void bind(int itemPosition) {
            Log.e("bindMain Date", mainElementItems.get(itemPosition).getItemDate());
            itemDate.setText(mainElementItems.get(itemPosition).getItemDate());
            Log.e("bindMain Price", String.valueOf(mainElementItems.get(itemPosition).getMoneySpentInMonth()));
            itemValue.setText(String.valueOf(mainElementItems.get(itemPosition).getMoneySpentInMonth()) + " RS");
            buildMainHomeSubRecyclerView(itemView, getSubListItemsData(itemPosition));
        }


    }

    public void buildMainHomeSubRecyclerView(View v, ArrayList<SubElementItem> subElementItems) {
        subRecycler = v.findViewById(R.id.main_home_sub_recycler_view);
        subRecyclerAdapter = new MainHomeSubRecyclerAdapter(mContext, subElementItems);
        LinearLayoutManager linearLayout = new LinearLayoutManager(mContext);
        subRecycler.setLayoutManager(linearLayout);
        subRecycler.setAdapter(subRecyclerAdapter);
    }


    public ArrayList<SubElementItem> getSubListItemsData(int position) {
        Log.e("getSubListItemsData", mainElementItems.get(position).getItemDate());

if (flag == 1){
    temp = databaseAdapter.readSubElementItemFromDatebase(mainElementItems.get(position).getItemDate(), userEmail);
}else if (flag == 2) {
        temp = databaseAdapter.readSubElementItemFromDatebase(mainElementItems.get(position).getItemDate(), userEmail, kind);
}
        ArrayList<SubElementItem> result = new ArrayList<SubElementItem>();
        for (int i = 0; i < temp.size(); i++) {
            if (userEmail.equals(temp.get(i).getEmail())) {
                result.add(i, temp.get(i));
            }
        }
        return temp;
    }


}
