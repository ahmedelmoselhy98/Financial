package com.e.k.m.a.financial.moneyQuantity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.k.m.a.financial.buyelement.BuyElement;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.entrance.EntranceActivity;
import com.e.k.m.a.financial.home.MainActivity;
import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.models.MoneyQuntaties;

public class MonyQuantityActivity extends AppCompatActivity {


    private EditText inputFinancialDues, inputExpenses;
    private TextInputLayout inputLayoutFinancialDues, inputLayoutExpenses;
    private Button btnSubmit;
    private Account account;
    private double fullIncome;
    private double financialDuesMax,expenseMax;
    private DatabaseAdapter databaseAdapter;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mony_quantity);

        inputLayoutFinancialDues =  findViewById(R.id.input_layout_financial_dues);
        inputLayoutExpenses = findViewById(R.id.input_layout_expenses);
        inputFinancialDues = findViewById(R.id.edt_mony_quantity_input_financial_dues);
        inputExpenses = findViewById(R.id.edt_mony_quantity_input_expenses);
        inputFinancialDues.addTextChangedListener(new MyTextWatcher(inputFinancialDues));
        inputExpenses.addTextChangedListener(new MyTextWatcher(inputExpenses));


        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String userEmail = sp.getString("useremail","");
        databaseAdapter =  new DatabaseAdapter(getBaseContext());

        account= databaseAdapter.readAccountFromDatebase(userEmail);
        fullIncome =  account.getIncomeValue();
        financialDuesMax = fullIncome*(50.0/100.0);
        Log.e("financialDuesMax",financialDuesMax+"");
        expenseMax = fullIncome*(30.0/100.0);
        Log.e("fullIncome",fullIncome+"");
        final String financialDuesHint = this.getResources().getString(R.string.hint_financial_dues) + " "+financialDuesMax + "RS";
        final String expensesHint = this.getResources().getString(R.string.hint_expenses) + " "+expenseMax + "RS";
        inputFinancialDues.setHint(financialDuesHint);
        inputFinancialDues.setHintTextColor(Color.WHITE);
        inputExpenses.setHint(expensesHint);
        inputExpenses.setHintTextColor(Color.WHITE);


        btnSubmit = findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (!TextUtils.isEmpty(inputFinancialDues.getText().toString())&&!TextUtils.isEmpty(inputExpenses.getText().toString())){
        double inputFinancialDuesValue = Double.parseDouble(inputFinancialDues.getText().toString());
        double inputExpensesValue = Double.parseDouble(inputExpenses.getText().toString());
        if (inputFinancialDuesValue > financialDuesMax){
            Toast.makeText(MonyQuantityActivity.this, financialDuesHint, Toast.LENGTH_SHORT).show();
        return;
        }
        if (inputExpensesValue > expenseMax){
            Toast.makeText(MonyQuantityActivity.this, expensesHint, Toast.LENGTH_SHORT).show();
        return;
        }
        submitForm();
        }
    }
});
    }

    private void setMoneyQuantities(){
        double financial = Double.parseDouble(inputFinancialDues.getText().toString());
        double expenses = Double.parseDouble(inputExpenses.getText().toString());
        double fullIncome = account.getIncomeValue();
        double savings = fullIncome - (financial+expenses);
        MoneyQuntaties quntaties = new MoneyQuntaties();
        quntaties.setUserEmail(account.getEmail());
        quntaties.setFullIncome(fullIncome);
        quntaties.setFinancialDues(financial);
        quntaties.setExpenses(expenses);
        quntaties.setSavings(savings);
        databaseAdapter.updateMoneyQuantitiesToDatabase(quntaties);
        startActivity(new Intent(MonyQuantityActivity.this, EntranceActivity.class));
        finish();
    }




    private void submitForm() {
        if (!validateFinancialDues()) {
            return;
        }
        if (!validateExpenses()) {
            return;
        }
        setMoneyQuantities();
    }
    private boolean validateFinancialDues() {
        if (inputFinancialDues.getText().toString().trim().isEmpty()) {
            inputLayoutFinancialDues.setError(getString(R.string.err_msg_financialdues));
            requestFocus(inputFinancialDues);
            return false;
        } else {
            inputLayoutFinancialDues.setErrorEnabled(false);
        }
        return true;
    }
    private boolean validateExpenses() {
        if (inputExpenses.getText().toString().trim().isEmpty()) {
            inputLayoutExpenses.setError(getString(R.string.err_msg_enter_expenses));
            requestFocus(inputExpenses);
            return false;
        } else {
            inputLayoutExpenses.setErrorEnabled(false);
        }
        return true;
    }
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.edt_mony_quantity_input_financial_dues:
                    validateFinancialDues();
                    break;
                case R.id.edt_mony_quantity_input_expenses:
                    validateExpenses();
                    break;
            }
        }
    }
}
