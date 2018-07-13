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

import com.e.k.m.a.financial.calendar.CalendarActivity;
import com.e.k.m.a.financial.FinancialConsultActivity;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.updatequantity.UpdateQuantaties;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.e.k.m.a.financial.registration.Login;
import com.e.k.m.a.financial.buyelement.BuyElement;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {


    RecyclerView mainRecycler;
    MainHomeRecyclerAdapter mainRecyclerAdapter;
    private SharedPreferences sp;
    private DatabaseAdapter databaseAdapter;
    private MoneyQuntaties quntaties;
    private String userEmail;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("ar"));
        String ar = dateFormat.format(Calendar.getInstance().getTime());
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd", new Locale("ar"));
        String ar2 = dateFormat2.format(Calendar.getInstance().getTime());
        Log.e("dddd", ar);
        TextView cc = findViewById(R.id.current_month);
        TextView aa = findViewById(R.id.current_day);
        aa.setText(ar2);
        cc.setText(ar);

        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("isLogin", true);
        spe.commit();
        userEmail = sp.getString("useremail", "");
        Log.e("useremail", userEmail);
        databaseAdapter = new DatabaseAdapter(getBaseContext());
        quntaties = databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        account = databaseAdapter.readAccountFromDatebase(userEmail);

        spe.putBoolean("resetsalary", false);
        spe.commit();

        boolean resetsalary = sp.getBoolean("resetsalary", false);
        Date date = new Date(account.getIncomeDate());
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("dd MMMM yyyy", new Locale("ar"));
        String a = dateFormat3.format(date);
        Log.e("data base Date", a);
        Date date1 = new Date(Calendar.getInstance().getTimeInMillis());
        Log.e("Current Date", "Day " + date1.getDay() + "  Month " + date1.getMonth() + "  year " + date1.getYear());
        boolean day = date.getDay() <= date1.getDay();
        boolean month = date.getMonth() == date1.getMonth();
        boolean year = date.getYear() == date1.getYear();
        if (day & month & year) {
            date.setMonth(date.getMonth() + 1);
            double mainSalary = account.getIncomeValue();
            double currentSalary = quntaties.getFullIncome();
            quntaties.setFullIncome(mainSalary + currentSalary);
            quntaties.setFinancialDues(0.0);
            quntaties.setExpenses(0.0);
            quntaties.setSavings(0.0);
            quntaties.setIncomeDate(date.getTime());
            databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
            account.setIncomeDate(date.getTime());
            databaseAdapter.updateAccountsToDatabase(account);
        } else  {

        }
        setMoneyQuantitieData();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BuyElement.class));
                finish();
            }
        });
    }
    public void financial(View view){
        buildMainHomeRecyclerView("مستحقات");
    }
    public void expenses(View view) {
        buildMainHomeRecyclerView("نفقات");
    }




    private String getCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("ar"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private void setMoneyQuantitieData() {
        TextView salaryTxt = findViewById(R.id.slaryTxt);
        salaryTxt.setText(String.valueOf(quntaties.getFullIncome()) + " RS");
        TextView cheabTxt = findViewById(R.id.cheabTxt);
        cheabTxt.setText(String.valueOf(quntaties.getSavings()) + " RS");
        TextView primaryTxt = findViewById(R.id.primaryTxt);
        primaryTxt.setText(String.valueOf(quntaties.getFinancialDues()) + " RS");
        TextView secondTxt = findViewById(R.id.secondTxt);
        secondTxt.setText(String.valueOf(quntaties.getExpenses()) + " RS");
    }

    public void buildMainHomeRecyclerView(String kind) {
        mainRecycler = findViewById(R.id.main_home_recycler_view);
        mainRecyclerAdapter = new MainHomeRecyclerAdapter(this, getMainListItemsData(),kind);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        mainRecycler.setLayoutManager(linearLayout);
        mainRecycler.setAdapter(mainRecyclerAdapter);
    }


    public ArrayList<MainElementItem> getMainListItemsData() {
        Log.e("Main recycler", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
        ArrayList<MainElementItem> temp = databaseAdapter.readMainElementItemFromDatebase();
        ArrayList<MainElementItem> result = new ArrayList<MainElementItem>();
        for (int i = 0; i < temp.size(); i++) {
            if (temp.get(i).getEmail().equals(userEmail)) {
                result.add(temp.get(i));
                Log.e("Main recycler", temp.get(i).getEmail());
            }
        }
        return databaseAdapter.readMainElementItemFromDatebaseByEmail(userEmail);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logout) {
            SharedPreferences.Editor spe = sp.edit();
            spe.putBoolean("isLogin", false);
            spe.commit();
            Intent logoutIntent = new Intent(MainActivity.this, Login.class);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            logoutIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logoutIntent);
            finish();
        }
        if (item.getItemId() == R.id.calender_menu) {
            startActivity(new Intent(MainActivity.this, CalendarActivity.class));
        }
        if (item.getItemId() == R.id.updateSalary) {
            startActivity(new Intent(MainActivity.this, UpdateQuantaties.class));
            finish();
        }
        return true;
    }
}
