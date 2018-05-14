package com.e.k.m.a.financial.RepotsActivity;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.k.m.a.financial.R;
import com.e.k.m.a.financial.database.DatabaseAdapter;
import com.e.k.m.a.financial.home.MainHomeRecyclerAdapter;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.SubElementItem;
import com.e.k.m.a.financial.registration.RegisterActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddReport extends AppCompatActivity {

    private EditText edtStartDate, edtEndDate;
    private TextInputLayout inputLayoutStartDate, inputLayoutEndDate;
    private Calendar myCalendar = Calendar.getInstance();
    RecyclerView reportRecyclerView;
    MainHomeRecyclerAdapter reportMainHomeRecyclerAdapter;
    private long startDate,endDate;
    private SharedPreferences sp;
    private String userEmail;
    private DatabaseAdapter databaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},00);
        inputLayoutStartDate = findViewById(R.id.input_layout_start_date);
        inputLayoutEndDate = findViewById(R.id.input_layout_end_date);
        edtStartDate = findViewById(R.id.edt_start_date);
        edtEndDate = findViewById(R.id.edt_end_date);
        databaseAdapter = new DatabaseAdapter(this);
        edtStartDate.addTextChangedListener(new MyTextWatcher(edtStartDate));
        edtEndDate.addTextChangedListener(new MyTextWatcher(edtEndDate));
        createDatePicker1();
        createDatePicker2();

    }

    public long converter(String s){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd EEEE MMMM yyyy",new Locale("ar"));
        Date date = new Date();
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("converter date",e.getMessage());
        }

        return date.getTime();
    }

    public void calculation(){
        double financial = 0.0;
        double expenses = 0.0;
        sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        userEmail = sp.getString("useremail", "");
        DatabaseAdapter databaseAdapter = new DatabaseAdapter(this);
        ArrayList<MainElementItem> mainElementItems = databaseAdapter.readMainElementItemFromDatebaseByEmail(userEmail);
        SubElementItem subElementItem;
        ArrayList<SubElementItem> subElementItems;
        ArrayList<MainElementItem> mainElementItems2 = new ArrayList<MainElementItem>();
        for (int i= 0;i < mainElementItems.size();i++){
            if (converter(mainElementItems.get(i).getItemDate()) >= startDate && converter(mainElementItems.get(i).getItemDate()) <= endDate) {
                mainElementItems2.add(mainElementItems.get(i));
                subElementItems = databaseAdapter.readSubElementItemFromDatebase(mainElementItems.get(i).getItemDate(),userEmail);
                Log.e("size","mainElementItems " +mainElementItems.size() +"   subElementItems" +subElementItems.size() );

                for (int a = 0;a < subElementItems.size();a++) {
                    subElementItem = subElementItems.get(a);
                    if (subElementItem.getKind().equals("مستحقات")){
                        financial +=subElementItem.getItemPrice();
                        Log.e("AddReport-financial",financial+"");
                    }
                    if (subElementItem.getKind().equals("نفقات"))
                    {
                        expenses +=subElementItem.getItemPrice();
                        Log.e("AddReport-expenses",expenses+"");
                    }
                }}

        }

            reportRecyclerView = findViewById(R.id.report_recycler_view);
            reportMainHomeRecyclerAdapter = new MainHomeRecyclerAdapter(this,mainElementItems2);
            LinearLayoutManager linearLayout = new LinearLayoutManager(this);
            reportRecyclerView.setLayoutManager(linearLayout);
            reportRecyclerView.setAdapter(reportMainHomeRecyclerAdapter);
            TextView tFinancial = findViewById(R.id.add_report_financial);
            tFinancial.setText(String.valueOf(financial));
            TextView tExpenses = findViewById(R.id.add_report_expenses);
            tExpenses.setText(String.valueOf(expenses));

    }
    public void saveReport(View view){
        if (startDate < endDate){
            calculation();
   }else if (startDate > endDate){
            Toast.makeText(this, "تاريخ البداية يجب ان يكون اقل من تاريخ النهاية", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(this, "عليك مراجعة التاريخ", Toast.LENGTH_SHORT).show();
    }
    private void createDatePicker1(){
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel1();
            }

        };
        edtStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddReport.this,onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

    }
    private void updateLabel1() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("ar"));
        edtStartDate.setText(dateFormat.format(myCalendar.getTime()));
        startDate = myCalendar.getTimeInMillis();
    }
    private void createDatePicker2(){
        final DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel2();
            }

        };
        edtEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(AddReport.this,onDateSetListener, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

    }
    private void updateLabel2() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy",new Locale("ar"));
        edtEndDate.setText(dateFormat.format(myCalendar.getTime()));
        endDate = myCalendar.getTimeInMillis();
    }
    private boolean validateStartDate() {
        if (edtStartDate.getText().toString().trim().isEmpty()) {
            inputLayoutStartDate.setError(getString(R.string.err_msg_start_date));
            requestFocus(edtStartDate);
            return false;
        } else {
            inputLayoutStartDate.setErrorEnabled(false);
        }

        return true;
    }
    private boolean validateEndDate() {
        if (edtEndDate.getText().toString().trim().isEmpty()) {
            inputLayoutEndDate.setError(getString(R.string.err_msg_end_date));
            requestFocus(edtEndDate);
            return false;
        } else {
            inputLayoutEndDate.setErrorEnabled(false);
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
                case R.id.edt_start_date:
                    validateStartDate();
                    break;
                case R.id.edt_end_date:
                    validateEndDate();
                    break;

            }
        }
    }
}
