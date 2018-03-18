package com.e.k.m.a.financial.buyelement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.e.k.m.a.financial.DatabaseAdapter;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.home.MainActivity;
import com.e.k.m.a.financial.models.Expense;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class BuyElement extends AppCompatActivity {
    RecyclerView buyElementRecycler;
    static RecyclerView buyElementSubRecycler;
    BuyElementMainRecyclerAdapter buyElementMainRecyclerAdapter;
    static BuyElementSubRecyclerAdapter buyElementSubRecyclerAdapter;
    ArrayList<String> arrayList;
    static ArrayList<String> arrayListSub;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_element);
        Button addItemBtn = findViewById(R.id.btn_add_price);
        final EditText priceTxt = findViewById(R.id.input_value);
        addItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                Expense expense = new Expense(priceTxt.getText().toString(), "انا/كوره", date.getTime()+"");
                DatabaseAdapter databaseAdapter = new DatabaseAdapter(getBaseContext());
                databaseAdapter.AddFinancialDues(expense, MainActivity.model);
                startActivity(new Intent(BuyElement.this, MainActivity.class));
                finish();
            }
        });
        spinerCode();
        buyElementMainList();
        buyElementSubList();
    }

    private void buyElementSubList() {
        arrayListSub = new ArrayList<>(Arrays.asList(this.getResources().getStringArray(R.array.restaurant)));
        buyElementSubRecycler = findViewById(R.id.buy_element_sub_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        buyElementSubRecyclerAdapter = new BuyElementSubRecyclerAdapter(this, arrayListSub);
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
        Spinner spinner = (Spinner) findViewById(R.id.spinner_withdraw_category);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.withdrawCategory, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

}
