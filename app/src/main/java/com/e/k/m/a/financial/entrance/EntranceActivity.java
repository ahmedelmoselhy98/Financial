package com.e.k.m.a.financial.entrance;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.e.k.m.a.financial.FinancialConsultActivity;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.RepotsActivity.AddReport;
import com.e.k.m.a.financial.stats.StatsActivity;
import com.e.k.m.a.financial.buyelement.BuyElement;
import com.e.k.m.a.financial.discounts.DiscountsActivity;
import com.e.k.m.a.financial.home.MainActivity;

public class EntranceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrance);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userEmail = sp.getString("useremail", "");
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("isLogin", true);
        spe.commit();
    }

    public void openMain(View v){
        startActivity(new Intent(this, MainActivity.class));
    }
    public void openGoal(View v){
        startActivity(new Intent(this, FinancialConsultActivity.class));
    }
    public void openstats(View v){
        startActivity(new Intent(this, StatsActivity.class));
    }
    public void openDiscounts(View v){
        startActivity(new Intent(this, DiscountsActivity.class));
    }
    public void openReports(View v){
        startActivity(new Intent(this, AddReport.class));
    }

}
