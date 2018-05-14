package com.e.k.m.a.financial;

import android.app.DatePickerDialog;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.e.k.m.a.financial.registration.RegisterActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FinancialConsultActivity extends AppCompatActivity {
    private TextView goalTextView;
    private TextInputLayout goalTextInputLayout,dateTextInputLayout,priceTextInputLayout;
    private EditText goalEditText,dateEditText,priceEditText;
    private Button goalButton;
    private Calendar myCalendar = Calendar.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_financial_consult);
        goalTextInputLayout = findViewById(R.id.input_layout_goal);
        goalEditText =  findViewById(R.id.edt_input_goal);
//        dateTextInputLayout = findViewById(R.id.input_layout_goal_date);
//        dateEditText =  findViewById(R.id.edt_goal_date);
        priceTextInputLayout= findViewById(R.id.input_layout_goal_price);
        priceEditText =  findViewById(R.id.edt_input_goal_price);
        goalTextView =  findViewById(R.id.goal_result);
        goalEditText.addTextChangedListener(new MyTextWatcher(goalEditText));
//        dateEditText.addTextChangedListener(new MyTextWatcher(dateEditText));
        priceEditText.addTextChangedListener(new MyTextWatcher(priceEditText));
//        createDatePicker();
        goalButton = findViewById(R.id.btn_goal);
        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            submitForm();
            }
        });

    }
    private void createDatePicker(){
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(FinancialConsultActivity.this,onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();


            }
        });

    }
    private void updateLabel() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("ar"));
        dateEditText.setText(dateFormat.format(myCalendar.getTime()));
    }
    private String  pricePerMothe(){
        Date goalDate = new Date(myCalendar.getTimeInMillis());
        double goalYear = goalDate .getYear()+1900;
        double goalMonth = goalDate .getMonth()+1;
        Date currentDate = new Date(Calendar.getInstance().getTimeInMillis());
        double currentYear = currentDate .getYear()+1900;
        double currentMonth = currentDate .getMonth()+1;
        double numOfYears = goalYear - currentYear;
        double numOfMonthes = goalMonth - currentMonth;
        double inputPrice = Double.parseDouble(priceEditText.getText().toString());
        double result = (12*numOfYears)+numOfMonthes;


        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor spe = sp.edit();
        spe.putBoolean("isLogin", true);
        spe.commit();
        String userEmail = sp.getString("useremail","");

        MoneyQuntaties quntaties =databaseAdapter.readMoneyQuantitiesFromDatebase(userEmail);
        double month = inputPrice/quntaties.getSavings();
        if (inputPrice < quntaties.getSavings() | inputPrice == quntaties.getSavings())
            return "يمكنك تحقيق هدفك في الوقت الحالى";
        if (inputPrice > quntaties.getSavings())
            return "بناء على مدخراتك الحاليه تحتاج الى" + Math.round(month) + " شهر لتحقيق هدفك";
//            return "لكي تحقق هدفك عليك ادخار مبلغ " + Math.round(inputPrice/result)+" RS فى كل شهر "+ " لمدة "+ result + " شهر ";
        else
            return "لقد ادخلت التاريخ بشكل خاطئ";
    }
    private void submitForm() {

        if (!validateGoal()) {
            return;
        }
//        if (!validateGoalDate()) {
//            return;
//        }
        if (!validateGoalPrice()) {
            return;
        }
            goalTextView.setText(pricePerMothe());
    }
    private boolean validateGoal() {
        if (goalEditText.getText().toString().trim().isEmpty()) {
            goalTextInputLayout.setError(getString(R.string.err_msg_goal));
            requestFocus(goalEditText);
            return false;
        } else {
            goalTextInputLayout.setErrorEnabled(false);
        }

        return true;
    }
//    private boolean validateGoalDate() {
//        if (dateEditText.getText().toString().trim().isEmpty()) {
//            dateTextInputLayout.setError(getString(R.string.err_msg_goal_Date));
//            requestFocus(dateEditText);
//            return false;
//        } else {
//            dateTextInputLayout.setErrorEnabled(false);
//        }
//
//        return true;
//    }
    private boolean validateGoalPrice() {
        if (priceEditText.getText().toString().trim().isEmpty()) {
            priceTextInputLayout.setError(getString(R.string.err_msg_goal_price));
            requestFocus(priceEditText);
            return false;
        } else {
            priceTextInputLayout.setErrorEnabled(false);
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
                case R.id.edt_input_goal:
                    validateGoal();
                    break;
//                case R.id.edt_goal_date:
//                    validateGoalDate();
//                    break;
                case R.id.edt_input_goal_price:
                    validateGoalPrice();
                    break;

            }
        }
    }
}
