package com.e.k.m.a.financial.calendar;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.TextView;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.home.MainHomeSubRecyclerAdapter;
import com.e.k.m.a.financial.models.MoneyQuntaties;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarActivity extends AppCompatActivity {

    MoneyQuntaties quntaties;
    String userEmail;
    DatabaseAdapter databaseAdapter;
    MainHomeSubRecyclerAdapter mainHomeSubRecyclerAdapter;
    RecyclerView calendarRecyclerView;
    CalendarView calendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.commit();
        userEmail = sp.getString("useremail", "");
        Log.e("useremail",userEmail);
        databaseAdapter = new DatabaseAdapter(getBaseContext());
        quntaties = databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        setMoneyQuantitieData();

        calendarRecyclerView  = findViewById(R.id.calendar_recyclerview);
        databaseAdapter = new DatabaseAdapter(this);
        LinearLayoutManager linearLayout = new LinearLayoutManager(this);
        calendarRecyclerView.setLayoutManager(linearLayout);
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy",new Locale("ar"));
        String d = dateFormat.format(Calendar.getInstance().getTimeInMillis());
        mainHomeSubRecyclerAdapter = new MainHomeSubRecyclerAdapter(CalendarActivity.this,databaseAdapter.readSubElementItemFromDatebase(d,userEmail));
        calendarRecyclerView.setAdapter(mainHomeSubRecyclerAdapter);
        calendarView = findViewById(R.id.calender_view);
        calendarView.setDate(Calendar.getInstance().getTimeInMillis());


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy",new Locale("ar"));
        Date date = new Date();
        date.setYear(year);
        date.setMonth(month);
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month,dayOfMonth);
        String d = dateFormat.format(calendar.getTime());
        mainHomeSubRecyclerAdapter = new MainHomeSubRecyclerAdapter(CalendarActivity.this,databaseAdapter.readSubElementItemFromDatebase(d,userEmail));
        mainHomeSubRecyclerAdapter.notifyDataSetChanged();
        calendarRecyclerView.setAdapter(mainHomeSubRecyclerAdapter);
    }
});
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

}
