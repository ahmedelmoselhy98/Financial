package com.e.k.m.a.financial.buyelement;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.home.MainActivity;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.MoneyMonthlySpent;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.e.k.m.a.financial.models.SubElementItem;

import java.nio.channels.Selector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class BuyElement extends AppCompatActivity {
    RecyclerView buyElementRecycler;
    static RecyclerView buyElementSubRecycler;
    BuyElementMainRecyclerAdapter buyElementMainRecyclerAdapter;
    static BuyElementSubRecyclerAdapter buyElementSubRecyclerAdapter;
    ArrayList<String> arrayList;
    static Button addItemBtn;
    static EditText priceTxt;
    static ArrayList<String> arrayListSub;
    private Spinner spinner;
    private String choise;
    private DatabaseAdapter databaseAdapter;
    private MoneyQuntaties quntaties;
    private SharedPreferences sp;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_element);
        addItemBtn = findViewById(R.id.btn_add_price);
        priceTxt = findViewById(R.id.input_value);
        databaseAdapter = new DatabaseAdapter(getBaseContext());
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userEmail = sp.getString("useremail", "");
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("isLogin", true);
        spe.commit();
        databaseAdapter = new DatabaseAdapter(getBaseContext());
        quntaties = databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        spinerCode();
        buyElementMainList();
        buyElementSubList();
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (quntaties.getSavings() <= 0.0) {
                        Toast.makeText(BuyElement.this, "ليس لديك مال", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (choise.equals("سحب من النفقات")) {
                        if (getItemPrice() <= quntaties.getFullIncome())
                            addItemElementToList("نفقات");
                        double tempFullIncome = quntaties.getFullIncome() - getItemPrice();
                        double tempExpenses = quntaties.getExpenses() - getItemPrice();
                        quntaties.setFullIncome(tempFullIncome);
                        quntaties.setExpenses(tempExpenses);
                        quntaties.setSavings(quntaties.getSavings());
                        if (quntaties.getExpenses() < 0.0 && quntaties.getSavings() > 0.0) {
                            Toast.makeText(BuyElement.this, "ليس لديك نفقات كافية وسيتم السحب المدخرات", Toast.LENGTH_SHORT).show();
                            quntaties.setSavings(quntaties.getExpenses() + quntaties.getSavings());
                            quntaties.setExpenses(0.0);
                            if (quntaties.getSavings() < 0.0) {
                                Toast.makeText(BuyElement.this, "ليس لديك مال", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
                        } else
                            databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
                        startActivity(new Intent(BuyElement.this, MainActivity.class));
                        finish();
                    } else if (choise.equals("سحب من المستحقات")) {
                        if (getItemPrice() <= quntaties.getFullIncome())
                            addItemElementToList("مستحقات");
                        double tempFullIncome = quntaties.getFullIncome() - getItemPrice();
                        double tempFinancial = quntaties.getFinancialDues() - getItemPrice();
                        quntaties.setFullIncome(tempFullIncome);
                        quntaties.setFinancialDues(tempFinancial);
                        quntaties.setSavings(quntaties.getSavings());
                        if (quntaties.getFinancialDues() < 0.0 && quntaties.getSavings() > 0.0) {
                            Toast.makeText(BuyElement.this, "ليس لديك مستحقات كافية وسيتم السحب المدخرات", Toast.LENGTH_SHORT).show();
                            quntaties.setSavings(quntaties.getFinancialDues() + quntaties.getSavings());
                            quntaties.setFinancialDues(0.0);
                            if (quntaties.getSavings() < 0.0) {
                                Toast.makeText(BuyElement.this, "ليس لديك مال", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
                        } else
                            databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
                        startActivity(new Intent(BuyElement.this, MainActivity.class));
                        finish();
                    } else
                        Toast.makeText(BuyElement.this, "اختار نوع السحب", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void buyElementSubList() {
        buyElementSubRecycler = findViewById(R.id.buy_element_sub_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(this, new ArrayList<String>());
        buyElementSubRecycler.setLayoutManager(linearLayoutManager);
        buyElementSubRecycler.setAdapter(buyElementSubRecyclerAdapter);

    }

    private void buyElementMainList() {
        arrayList = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.mainCategory)));
        buyElementRecycler = findViewById(R.id.buy_element_main_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        buyElementMainRecyclerAdapter = new BuyElementMainRecyclerAdapter(this, arrayList);
        buyElementRecycler.setLayoutManager(linearLayoutManager);
        buyElementRecycler.setAdapter(buyElementMainRecyclerAdapter);
    }

    private void spinerCode() {
        String [] a = {"","",""};
        spinner = findViewById(R.id.spinner_withdraw_category);
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(BuyElement.this,
                R.array.withdrawCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choise = parent.getItemAtPosition(position).toString();
                if (!choise.equals("اختار نوع السحب"))
                spinner.setBackgroundColor(Color.GREEN);
                else
                    spinner.setBackgroundColor(Color.MAGENTA);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    private void addItemElementToList(String kind) {
        String itemDate = getCurrentDate();
        String itemMonth = getCurrentMonth();
        double itemPrice = getItemPrice();
        String category = buyElementSubRecyclerAdapter.category;
        if (!TextUtils.isEmpty(itemDate) && !TextUtils.isEmpty(category) && getItemPrice() != 0.0) {
            MainElementItem mainElementItem = databaseAdapter.readMainElementItemFromDatebase(itemDate);
            mainElementItem.setEmail(userEmail);
            mainElementItem.setItemDate(itemDate);
            mainElementItem.setMoneySpentInMonth(mainElementItem.getMoneySpentInMonth() + itemPrice);
            Log.e("price", mainElementItem.getMoneySpentInMonth() + "");
            Log.e("qqqqqqqqqqq insert", mainElementItem.getMoneySpentInMonth() + "");
            long d = databaseAdapter.saveMainElementItemToDatabase(mainElementItem);
            if (d == -1) {
                databaseAdapter.updateMainElementItemToDatabase(mainElementItem);
                Log.e("qqqqqqqqqqq update", "");
            }
            try {
            } catch (Exception e) {
                databaseAdapter.updateMainElementItemToDatabase(mainElementItem);
            }
//            if (itemDate.equals(mainElementItem.getItemDate())){
//            }else {
//                Log.e("qqqqqqqqqqqqqqqqqqqq","insert");
//                Log.e("qqqqqqqqqqqqqqqqqqqq","end");
//
//            }
            SubElementItem subElementItem = new SubElementItem();
            subElementItem.setEmail(userEmail);
            subElementItem.setItemDate(getCurrentDate());
            subElementItem.setItemTitle(category);
            subElementItem.setKind(kind);
            subElementItem.setItemPrice(itemPrice);
            long s = databaseAdapter.saveSubElementItemToDatabase(subElementItem);
            Log.e("saveSubElementDatabase", s + "");
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy", new Locale("ar"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private String getCurrentMonth() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM yyyy", new Locale("ar"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

    private double getItemPrice() {
        if (!TextUtils.isEmpty(priceTxt.getText().toString()))
            return Double.parseDouble(priceTxt.getText().toString());
        else
            return 0.0;
    }

}
