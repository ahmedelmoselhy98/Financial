package com.e.k.m.a.financial.updatequantity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.models.MoneyQuntaties;

public class UpdateQuantaties extends AppCompatActivity {
    MoneyQuntaties quntaties;
    String userEmail;
    DatabaseAdapter databaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_quantaties);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.commit();
        userEmail = sp.getString("useremail", "");
        Log.e("useremail",userEmail);
        databaseAdapter = new DatabaseAdapter(getBaseContext());
        quntaties = databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        setMoneyQuantitieData();
    }

    public void newSalary(View view) {
        try{
        Account account = databaseAdapter.readAccountFromDatebase(userEmail);
        double mainSalary = account.getIncomeValue();
        double currentSalary = quntaties.getFullIncome();
        quntaties.setFullIncome(mainSalary + currentSalary);
        quntaties.setFinancialDues(0.0);
        quntaties.setExpenses(0.0);
        quntaties.setSavings(0.0);
        databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
        setMoneyQuantitieData();
            Toast.makeText(this, "تم", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, MonyQuantityActivity.class));
        }catch (Exception e){
            Toast.makeText(this, "خطأ", Toast.LENGTH_SHORT).show();
            Log.e("updateActivity","aaaaaaaaaaasdqwravzxvczxcasd");
        }
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

    public void updateSalary(View view) {
        startActivity(new Intent(this, UpdateSubActivity.class));

    }
}
