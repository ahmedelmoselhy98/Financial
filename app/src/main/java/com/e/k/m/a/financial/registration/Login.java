package com.e.k.m.a.financial.registration;

import android.content.Intent;
import android.content.SharedPreferences;
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

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.buyelement.BuyElement;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.entrance.EntranceActivity;
import com.e.k.m.a.financial.home.MainActivity;
import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.moneyQuantity.MonyQuantityActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Login extends AppCompatActivity {

    private static final String TAG = Login.class.getSimpleName();
    private EditText inputEmail, inputPassword;
    private TextInputLayout inputLayoutEmail, inputLayoutPassword;
    Button btnLogin,btnNewAccount;
    private DatabaseAdapter databaseAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.commit();
        boolean isLogin = sp.getBoolean("isLogin",false);
        if (isLogin){
            startActivity(new Intent(Login.this,EntranceActivity.class));
        finish();
        }
        initViews();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd EEEE MMMM yyyy",new Locale("ar"));
        Log.e(TAG, dateFormat.format(Calendar.getInstance(TimeZone.getDefault()).getTime()));

        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));
        inputEmail.addTextChangedListener(new MyTextWatcher(inputEmail));

        databaseAdapter =  new DatabaseAdapter(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });
        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,RegisterActivity.class));
            }
        });
    }







    public void initViews(){
        inputLayoutEmail =  findViewById(R.id.input_layout_email);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_activity_login_sign_in);
        btnNewAccount = findViewById(R.id.btn_activity_login_sign_up);
    }
    private void submitForm() {
        if (!validateEmail()) {
            return;
        }

        if (!validatePassword()) {

            return;
        }
        String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();
        try {
            Account account = databaseAdapter.readAccountFromDatebase(email);
            if (email.equals(account.getEmail())){
                if (password.equals(account.getPassword())){
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor spe = sp.edit();
                spe.putString("useremail", email);
                spe.commit();
                startActivity(new Intent(Login.this,MainActivity.class));
                finish();
                }else
                    Toast.makeText(this, "كلمة المرور غير صحيحة ", Toast.LENGTH_SHORT).show();
            }else
                Toast.makeText(this, "الحساب غير موجود سجل حساب جديد", Toast.LENGTH_SHORT).show();
        }
        catch (Exception e){Log.e(TAG,e.getMessage());}

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
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
