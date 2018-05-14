package com.e.k.m.a.financial.registration;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.e.k.m.a.financial.models.SubElementItem;
import com.e.k.m.a.financial.moneyQuantity.MonyQuantityActivity;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.models.Account;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword, inputFirstName, inputLastName, inputPhone, inputIncomeValue, inputIncomeDate;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutFirstName, inputLayoutLastName, inputLayoutPhone, inputLayoutIncomeValue, inputLayoutIncomeDate;
    private Button btnRegisterNewAccount;
    private Account account;
    private DatabaseAdapter databaseAdapter;
    private Calendar myCalendar = Calendar.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputFirstName.addTextChangedListener(new MyTextWatcher(inputFirstName));
        inputLastName.addTextChangedListener(new MyTextWatcher(inputLastName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputIncomeValue.addTextChangedListener(new MyTextWatcher(inputIncomeValue));
        inputIncomeDate.addTextChangedListener(new MyTextWatcher(inputIncomeDate));
        btnRegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        databaseAdapter = new DatabaseAdapter(this);
        createDatePicker();
    }

    public void initViews(){
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputLayoutFirstName = findViewById(R.id.input_layout_firstName);
        inputLayoutLastName = findViewById(R.id.input_layout_lastName);
        inputLayoutPhone = findViewById(R.id.input_layout_telephone);
        inputLayoutIncomeValue =  findViewById(R.id.input_layout_incomeValue);
        inputLayoutIncomeDate = findViewById(R.id.input_layout_incomeValue);
        inputEmail = findViewById(R.id.edt_register_input_email);
        inputPassword = findViewById(R.id.edt_register_input_password);
        inputFirstName = findViewById(R.id.edt_register_input_firstName);
        inputLastName = findViewById(R.id.edt_register_input_lastName);
        inputPhone = findViewById(R.id.edt_register_input_telephone);
        inputIncomeValue = findViewById(R.id.edt_register_input_incomeValue);
        inputIncomeDate = findViewById(R.id.edt_register_input_incomeDate);
        btnRegisterNewAccount = findViewById(R.id.btn_register_new_account);

    }
    Calendar mCalendar1;
    private void createDatePicker(){
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear+1);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        inputIncomeDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(RegisterActivity.this,onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("ar"));
        mCalendar1 = myCalendar;
        inputIncomeDate.setText(dateFormat.format(myCalendar.getTime()));
    }
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        if (!validateFirstName()) {
            return;
        }
        if (!validateLastName()) {
            return;
        }
        if (!validatePhone()) {
            return;
        }
        if (!validateIncomeValue()) {
            return;
        }
        if (!validateIncomeDate()) {
            return;
        }

        getAccountData();
    }
    private void getAccountData(){
        account =new Account();
        account.setEmail(inputEmail.getText().toString());
        account.setPassword(inputPassword.getText().toString());
        account.setFirstName(inputFirstName.getText().toString());
        account.setLastName(inputLastName.getText().toString());
        account.setTelephone(inputPhone.getText().toString());
        account.setIncomeValue(Double.parseDouble(inputIncomeValue.getText().toString()));
        account.setIncomeDate(mCalendar1.getTimeInMillis());

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.putString("useremail", account.getEmail());
        Log.e("useremail",account.getEmail());

        spe.commit();

        MoneyQuntaties quntaties = new MoneyQuntaties();
        quntaties.setUserEmail(inputEmail.getText().toString());
        quntaties.setIncomeDate(myCalendar.getTimeInMillis());
        quntaties.setFullIncome(Double.parseDouble(inputIncomeValue.getText().toString()));
        quntaties.setFinancialDues(0.0);
        quntaties.setExpenses(0.0);
        quntaties.setSavings(0.0);

        MainElementItem mainElementItem = new MainElementItem();
        mainElementItem.setEmail(account.getEmail());
        mainElementItem.setItemDate(getCurrentDate());
        mainElementItem.setMoneySpentInMonth(0.0);
        Log.e("Register","setEmail: " + mainElementItem.getEmail() + " / " +"  setItemDate"+ mainElementItem.getItemDate() + " / "+ "  setMoney"+ mainElementItem.getMoneySpentInMonth());
        try {
            databaseAdapter.saveMainElementItemToDatabase(mainElementItem);
        }catch (Exception e){
            Log.e("exception",e.getMessage());
        }
        long insertAccountData= databaseAdapter.saveAccountsToDatabase(account);
        long insertMoneyQuantityData = databaseAdapter.saveMoneyQuantitiesToDatabase(quntaties);
        if (insertAccountData > -1){
            if (insertMoneyQuantityData > -1){
            startActivity(new Intent(RegisterActivity.this,MonyQuantityActivity.class));
            Toast.makeText(this, "تم", Toast.LENGTH_SHORT).show();
            finish();
        }
        }else
            Toast.makeText(this, "الحساب موجود", Toast.LENGTH_SHORT).show();

    }

    public void initDatabase(){

    }
    private boolean validateEmail() {
        String email = inputEmail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError(getString(R.string.err_msg_email));
            requestFocus(inputEmail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateFirstName() {
        if (inputFirstName.getText().toString().trim().isEmpty()) {
            inputLayoutFirstName.setError(getString(R.string.err_msg_first_name));
            requestFocus(inputFirstName);
            return false;
        } else {
            inputLayoutFirstName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateLastName() {
        if (inputLastName.getText().toString().trim().isEmpty()) {
            inputLayoutLastName.setError(getString(R.string.err_msg_last_name));
            requestFocus(inputLastName);
            return false;
        } else {
            inputLayoutLastName.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validatePhone() {
        if (inputPhone.getText().toString().trim().isEmpty()) {
            inputLayoutPhone.setError(getString(R.string.err_msg_phone));
            requestFocus(inputPhone);
            return false;
        } else {
            inputLayoutPhone.setErrorEnabled(false);
        }
        if (inputPhone.getText().toString().trim().length()<10){
            inputLayoutPhone.setError(getString(R.string.err_msg_phone1));
            requestFocus(inputPhone);
            return false;
        }
        return true;
    }
    private boolean validateIncomeValue() {
        if (inputIncomeValue.getText().toString().trim().isEmpty()) {
            inputLayoutIncomeValue.setError(getString(R.string.err_msg_income_value));
            requestFocus(inputIncomeValue);
            return false;
        } else {
            inputLayoutIncomeValue.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateIncomeDate() {
        if (inputIncomeDate.getText().toString().trim().isEmpty()) {
            inputLayoutIncomeDate.setError(getString(R.string.err_msg_income_date));
            requestFocus(inputIncomeDate);
            return false;
        } else {
            inputLayoutIncomeDate.setErrorEnabled(false);
        }

        return true;
    }
    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                case R.id.edt_register_input_email:
                    validateEmail();
                    break;
                case R.id.edt_register_input_password:
                    validatePassword();
                    break;
                case R.id.edt_register_input_firstName:
                    validateFirstName();
                    break;
                case R.id.edt_register_input_lastName:
                    validateLastName();
                    break;
                case R.id.edt_register_input_telephone:
                    validatePhone();
                    break;
                case R.id.edt_register_input_incomeValue:
                    validateIncomeValue();
                    break;
                case R.id.edt_register_input_incomeDate:
                    validateIncomeDate();
                    break;
            }
        }
    }
    private String getCurrentDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy",new Locale("ar"));
        return dateFormat.format(Calendar.getInstance().getTime());
    }

private long handleDate(){
           return myCalendar.getTimeInMillis();
}

}

