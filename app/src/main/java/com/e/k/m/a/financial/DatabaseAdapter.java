package com.e.k.m.a.financial;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.models.Expense;
import com.e.k.m.a.financial.models.HeadModel;
import com.e.k.m.a.financial.models.MainHomeRecyclerViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mahmoud on 5/1/2016.
 */
public class DatabaseAdapter {
    DatabaseHelper databaseHelper;
    Context context;
    private static final String TAG = DatabaseAdapter.class.getSimpleName();
    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public int SignIn(String email, String password) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.USER_EMAIL, DatabaseHelper.USER_PASSWORD};
        Cursor cursor = db.query(DatabaseHelper.TABLE_ACCOUNT, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String USER_EMAIL = cursor.getString(1);
            String USER_PASSWORD = cursor.getString(2);
            if (!email.equals("")) {
                if (email.equals(USER_EMAIL) && password.equals(USER_PASSWORD)) {
                    return ID;
                }
            }
        }
        return -1;
    }

    public long SignUpData(Account account) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValuesACCOUNT = new ContentValues();
        contentValuesACCOUNT.put(DatabaseHelper.USER_EMAIL, account.getEmail());
        contentValuesACCOUNT.put(DatabaseHelper.USER_PASSWORD, account.getPassword());
        contentValuesACCOUNT.put(DatabaseHelper.USER_FIRST_NAME, account.getFirstName());
        contentValuesACCOUNT.put(DatabaseHelper.USER_SECOND_NAME, account.getLastName());
        contentValuesACCOUNT.put(DatabaseHelper.USER_TELEPHONE, account.getTelephone());
        contentValuesACCOUNT.put(DatabaseHelper.INCOME_DATE, account.getIncomeDate());
        contentValuesACCOUNT.put(DatabaseHelper.INCOME_VALUE, account.getIncomeValue());
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_ACCOUNT, null, contentValuesACCOUNT);
        ContentValues contentValuesHEAD = new ContentValues();
        Date date = new Date();
        Date date2 = new Date(date.getYear(), date.getMonth()+1, Integer.parseInt(account.getIncomeDate()));

        Log.e(TAG,""+Double.parseDouble(account.getIncomeValue()));
//        Toast.makeText(context, date.getMonth() + "", Toast.LENGTH_SHORT).show();
        contentValuesHEAD.put(DatabaseHelper.INCOME_DATE, date2.getTime() + "");
        contentValuesHEAD.put(DatabaseHelper.INCOME_VALUE, account.getIncomeValue());
        contentValuesHEAD.put(DatabaseHelper.INCOME_CHEAP, (String.valueOf(Double.parseDouble(account.getIncomeValue()) * (20.0 / 100))));
        contentValuesHEAD.put(DatabaseHelper.INCOME_PRIMARY, (String.valueOf(Double.parseDouble(account.getIncomeValue()) * (50.0 / 100))));
        contentValuesHEAD.put(DatabaseHelper.INCOME_SECOND, (String.valueOf(Double.parseDouble(account.getIncomeValue()) * (30.0 / 100))));
        long id2 = sqLiteDatabase.insert(DatabaseHelper.TABLE_HEAD, null, contentValuesHEAD);
        return SignIn(account.getEmail(), account.getPassword());
    }

    public HeadModel HeadData(String searchDate, int searchId) {
        int i = 0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.INCOME_DATE, DatabaseHelper.INCOME_PRIMARY, DatabaseHelper.INCOME_SECOND, DatabaseHelper.INCOME_CHEAP, DatabaseHelper.INCOME_VALUE, DatabaseHelper.SPEND_MONEY};
        Cursor cursor = db.query(DatabaseHelper.TABLE_HEAD, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String INCOME_DATE = cursor.getString(1);
            String INCOME_PRIMARY = cursor.getString(2);
            String INCOME_SECOND = cursor.getString(3);
            String INCOME_CHEAP = cursor.getString(4);
            String INCOME_VALUE = cursor.getString(5);
            String SPEND_MONEY = cursor.getString(6);
            if (!searchDate.equals("") && searchId > -1) {
                if (Double.parseDouble(searchDate) < Double.parseDouble(INCOME_DATE)) {
                    return new HeadModel(INCOME_DATE, INCOME_VALUE, INCOME_CHEAP, INCOME_PRIMARY, INCOME_SECOND, SPEND_MONEY, ID);
                }
            }
        }
        return null;
    }

    public long AddFinancialDues(Expense expense, HeadModel headModel) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CATEGORY, expense.getCategory());
        contentValues.put(DatabaseHelper.VALUE, expense.getValue());
        contentValues.put(DatabaseHelper.DATE, expense.getDate());
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_FINANCIAL_DUES, null, contentValues);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(DatabaseHelper.INCOME_VALUE, Double.parseDouble(headModel.getIncomevalue())-Double.parseDouble(expense.getValue()));
        contentValues2.put(DatabaseHelper.INCOME_PRIMARY, Double.parseDouble(headModel.getIncomeprimary())-Double.parseDouble(expense.getValue()));
        String whereArgs[]={expense.getDate()};
        int count=sqLiteDatabase.update(DatabaseHelper.TABLE_HEAD, contentValues2,
                DatabaseHelper.INCOME_DATE + " >? " , whereArgs);
        return id;
    }

    public long AddExpenses(Expense expense, HeadModel headModel) {
        SQLiteDatabase sqLiteDatabase = databaseHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.CATEGORY, expense.getCategory());
        contentValues.put(DatabaseHelper.VALUE, expense.getValue());
        contentValues.put(DatabaseHelper.DATE, expense.getDate());
        long id = sqLiteDatabase.insert(DatabaseHelper.TABLE_EXPENSES, null, contentValues);
        ContentValues contentValues2 = new ContentValues();
        contentValues2.put(DatabaseHelper.INCOME_VALUE, Double.parseDouble(headModel.getIncomevalue())-Double.parseDouble(expense.getValue()));
        contentValues2.put(DatabaseHelper.INCOME_PRIMARY, Double.parseDouble(headModel.getIncomeprimary())-Double.parseDouble(expense.getValue()));
        String whereArgs[]={expense.getDate()};
        int count=sqLiteDatabase.update(DatabaseHelper.TABLE_HEAD, contentValues2,
                DatabaseHelper.INCOME_DATE + " >? " , whereArgs);
        return id;
    }


    public MainHomeRecyclerViewModel search(String searchDate) {
        int i = 0;
        Double totalValue = 0.0;
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String[] columns = {DatabaseHelper.UID, DatabaseHelper.VALUE, DatabaseHelper.CATEGORY, DatabaseHelper.DATE};
        Cursor cursor = db.query(DatabaseHelper.TABLE_FINANCIAL_DUES, columns, null, null, null, null, null);
        ArrayList<Expense> finincialDues = new ArrayList<Expense>();

        while (cursor.moveToNext()) {
            int ID = cursor.getInt(0);
            String value = cursor.getString(1);
            String category = cursor.getString(2);
            String date = cursor.getString(3);

            if (!searchDate.equals("")) {
                if (searchDate.equals(date)) {
                    finincialDues.add(new Expense());
                    finincialDues.get(i).setID(ID);
                    finincialDues.get(i).setValue(value);
                    finincialDues.get(i).setCategory(category);
                    finincialDues.get(i).setDate(date);
                    totalValue += Double.parseDouble(value);
                    i++;
                }
            }
        }
        cursor.close();
        SQLiteDatabase db2 = databaseHelper.getWritableDatabase();
        Cursor cursor2 = db2.query(DatabaseHelper.TABLE_EXPENSES, columns, null, null, null, null, null);
        ArrayList<Expense> expenses = new ArrayList<Expense>();

        while (cursor2.moveToNext()) {
            int ID = cursor2.getInt(0);
            String value = cursor2.getString(1);
            String category = cursor2.getString(2);
            String date = cursor2.getString(3);

            if (!searchDate.equals("")) {
                if (searchDate.equals(date)) {
                    expenses.add(new Expense());
                    expenses.get(i).setID(ID);
                    expenses.get(i).setValue(value);
                    expenses.get(i).setCategory(category);
                    expenses.get(i).setDate(date);
                    totalValue += Double.parseDouble(value);
                    i++;
                }
            }
        }
        cursor2.close();

        return new MainHomeRecyclerViewModel(searchDate, totalValue + "", expenses);
    }

    static class DatabaseHelper extends SQLiteOpenHelper {
        private final static String DATABASE_NAME = "financialdatabase";
        private final static int DATABASE_VERSION = 1;
        private final static String TABLE_ACCOUNT = "accounttable";
        private final static String TABLE_FINANCIAL_DUES = "financialdusetable";
        private final static String TABLE_EXPENSES = "expensestable";
        private final static String TABLE_HEAD = "headtable";
        private final static String UID = "_id";
        private final static String USER_FIRST_NAME = "firstname";
        private final static String USER_SECOND_NAME = "secondname";
        private final static String USER_TELEPHONE = "telephone";
        private final static String USER_EMAIL = "email";
        private final static String USER_PASSWORD = "password";
        private final static String INCOME_DATE = "incomedate";
        private final static String INCOME_VALUE = "incomevalue";
        private final static String INCOME_CHEAP = "incomecheap";
        private final static String INCOME_PRIMARY = "incomeprimary";
        private final static String INCOME_SECOND = "incomesecond";
        private final static String SPEND_MONEY = "spendmoney";
        private final static String CATEGORY = "category";
        private final static String VALUE = "value";
        private final static String DATE = "date";

        private final static String CREATE_ACCOUNT_TABLE = "CREATE TABLE " + TABLE_ACCOUNT + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + USER_EMAIL + " VARCHAR(255) ," + USER_PASSWORD + " VARCHAR(255) ," + USER_FIRST_NAME + " VARCHAR(255) ," + USER_SECOND_NAME + " VARCHAR(255) ," + USER_TELEPHONE + " VARCHAR(255) ," + INCOME_DATE + " VARCHAR(255) ," + INCOME_VALUE + " VARCHAR(255));";
        private final static String CREATE_HEAD_TABLE = "CREATE TABLE " + TABLE_HEAD + "(" + UID + " ," + INCOME_VALUE + " VARCHAR(255) ," + INCOME_DATE + " VARCHAR(255) ," + INCOME_PRIMARY + " VARCHAR(255) ," + INCOME_SECOND + " VARCHAR(255) ," + SPEND_MONEY + " VARCHAR(255) ," + INCOME_CHEAP + " VARCHAR(255));";
        private final static String CREATE_FINANCIAL_DUES_TABLE = "CREATE TABLE " + TABLE_FINANCIAL_DUES + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + CATEGORY + " VARCHAR(255) ," + VALUE + " VARCHAR(255) ," + DATE + " VARCHAR(255));";
        private final static String CREATE_EXPENSES_TABLE = "CREATE TABLE " + TABLE_EXPENSES + "(" + UID + " INTEGER PRIMARY KEY AUTOINCREMENT ," + CATEGORY + " VARCHAR(255) ," + VALUE + " VARCHAR(255) ," + DATE + " VARCHAR(255));";

        private final static String DROP_ACCOUNT_TABLE = "DROP TABLE IF EXISTS " + TABLE_ACCOUNT;
        private final static String DROP_HEAD_TABLE = "DROP TABLE IF EXISTS " + TABLE_HEAD;
        private final static String DROP_FINANCIAL_DUES_TABLE = "DROP TABLE IF EXISTS " + TABLE_FINANCIAL_DUES;
        private final static String DROP_EXPENSES_TABLE = "DROP TABLE IF EXISTS " + TABLE_EXPENSES;

        private Context context;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(CREATE_ACCOUNT_TABLE);
                sqLiteDatabase.execSQL(CREATE_HEAD_TABLE);
                sqLiteDatabase.execSQL(CREATE_FINANCIAL_DUES_TABLE);
                sqLiteDatabase.execSQL(CREATE_EXPENSES_TABLE);
                Toast.makeText(context, "database CREATED", Toast.LENGTH_SHORT).show();
            } catch (SQLException e) {
                Toast.makeText(context, "Sorry there is Error in create your database \n" + e, Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(DROP_ACCOUNT_TABLE);
                sqLiteDatabase.execSQL(DROP_HEAD_TABLE);
                sqLiteDatabase.execSQL(DROP_FINANCIAL_DUES_TABLE);
                sqLiteDatabase.execSQL(DROP_EXPENSES_TABLE);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Toast.makeText(context, "Sorry there is Error in upgrade your database \n" + e, Toast.LENGTH_SHORT).show();
            }
        }
    }
}