package com.e.k.m.a.financial.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.e.k.m.a.financial.DatabaseAdapter;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.registration.Login;
import com.e.k.m.a.financial.buyelement.BuyElement;
import com.e.k.m.a.financial.models.HeadModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    RecyclerView mainRecycler;
    MainHomeRecyclerAdapter mainRecyclerAdapter;
    SharedPreferences sp;
    public static HeadModel model;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy",new Locale("ar"));
        String ar = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd",new Locale("ar"));
        String ar2 = dateFormat2.format(Calendar.getInstance().getTime());
        Log.e("dddd",ar);
        TextView cc = findViewById(R.id.current_month);
        TextView aa = findViewById(R.id.current_day);
        aa.setText(ar2);
        cc.setText(ar);
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        buildMainHomeRecyclerView();
        try{
        if (sp.getInt("ID", -1) == -1) {
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        }else {
            setHeadData(sp.getInt("ID", -1));
        }
        }catch (Exception e){

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            startActivity(new Intent(MainActivity.this,BuyElement.class));
            finish();
            }
        });

    }

    private void setHeadData(int ID) {
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        Date d = new Date();
        model = databaseAdapter.HeadData(d.getTime()+"" , ID);
        TextView salaryTxt = findViewById(R.id.slaryTxt);
        salaryTxt.setText(model.getIncomevalue());
        TextView cheabTxt = findViewById(R.id.cheabTxt);
        cheabTxt.setText(model.getIncomecheap());
        TextView primaryTxt = findViewById(R.id.primaryTxt);
        primaryTxt.setText(model.getIncomeprimary());
        TextView secondTxt = findViewById(R.id.secondTxt);
        secondTxt.setText(model.getIncomesecond());
        TextView spendTxt = findViewById(R.id.month_total_money_tv);
        spendTxt.setText(model.getSpendMoney());
    }


    public void buildMainHomeRecyclerView() {
        mainRecycler = findViewById(R.id.main_home_recycler_view);
        mainRecyclerAdapter = new MainHomeRecyclerAdapter(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(linearLayout);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout){
            sp.edit().putInt("ID", -1).apply();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }
        return true;
    }
}
