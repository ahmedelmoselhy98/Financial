package com.e.k.m.a.financial.registration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.e.k.m.a.financial.DatabaseAdapter;
import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.home.MainActivity;
import com.e.k.m.a.financial.models.Account;

public class RegisterActivity extends AppCompatActivity {


    private EditText inputEmail, inputPassword, inputFirstName, inputLastName, inputPhone, inputIncomeValue, inputIncomeDate;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword, inputLayoutFirstName, inputLayoutLastName, inputLayoutPhone, inputLayoutIncomeValue, inputLayoutIncomeDate;
    private Button btnRegisterNewAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


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
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputFirstName.addTextChangedListener(new MyTextWatcher(inputFirstName));
        inputLastName.addTextChangedListener(new MyTextWatcher(inputLastName));
        inputPhone.addTextChangedListener(new MyTextWatcher(inputPhone));
        inputIncomeValue.addTextChangedListener(new MyTextWatcher(inputIncomeValue));
        inputIncomeDate.addTextChangedListener(new MyTextWatcher(inputIncomeDate));

        btnRegisterNewAccount = findViewById(R.id.btn_register_new_account);
        btnRegisterNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });


    }


    private void getAccountData(){
    Account account =new Account();
    account.setEmail(inputEmail.getText().toString());
    account.setPassword(inputPassword.getText().toString());
    account.setFirstName(inputFirstName.getText().toString());
    account.setLastName(inputLastName.getText().toString());
    account.setTelephone(inputPhone.getText().toString());
    account.setIncomeValue(inputIncomeValue.getText().toString());
    account.setIncomeDate(inputIncomeDate.getText().toString());

    DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        long insterAccountData= databaseAdapter.SignUpData(account);
        if (insterAccountData > -1){
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor spe = sp.edit();
            spe.putInt("ID", (int) insterAccountData).apply();
            spe.commit();
            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
            finish();
            Toast.makeText(this, "success", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "faild", Toast.LENGTH_SHORT).show();

    }
    /**
     * Validating form
     */
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
}

