package com.e.k.m.a.financial.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.e.k.m.a.financial.models.Account;
import com.e.k.m.a.financial.models.MainElementItem;
import com.e.k.m.a.financial.models.MoneyMonthlySpent;
import com.e.k.m.a.financial.models.MoneyQuntaties;
import com.e.k.m.a.financial.models.SubElementItem;

import java.util.ArrayList;


public class DatabaseAdapter {
    private DatabaseHelper databaseHelper;
    private Context context;
    private static final String TAG = DatabaseAdapter.class.getSimpleName();
    public DatabaseAdapter(Context context) {
        databaseHelper = new DatabaseHelper(context);
        this.context = context;
    }

    public long saveAccountsToDatabase(Account account){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_EMAIL, account.getEmail());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_PASSWORD, account.getPassword());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_FIRST_NAME, account.getFirstName());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_LAST_NAME, account.getLastName());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_TELEPHONE, account.getTelephone());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_INCOME_VALUE, account.getIncomeValue());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_INCOME_DATE, account.getIncomeDate());
        long newRowId = db.insert(FinConsultContract.AccountTable.TABLE_NAME, null, values);
        return newRowId;
    }
    public long updateAccountsToDatabase(Account account){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String selection = FinConsultContract.AccountTable.COLUMN_NAME_EMAIL + " LIKE ?";
        String[] selectionArgs = {account.getEmail()};

        values.put(FinConsultContract.AccountTable.COLUMN_NAME_EMAIL, account.getEmail());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_PASSWORD, account.getPassword());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_FIRST_NAME, account.getFirstName());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_LAST_NAME, account.getLastName());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_TELEPHONE, account.getTelephone());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_INCOME_VALUE, account.getIncomeValue());
        values.put(FinConsultContract.AccountTable.COLUMN_NAME_INCOME_DATE, account.getIncomeDate());
        long newRowId = db.update(FinConsultContract.AccountTable.TABLE_NAME,values,selection,selectionArgs);
        return newRowId;
    }
    public Account readAccountFromDatebase(String email){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.AccountTable.COLUMN_NAME_UID,FinConsultContract.AccountTable.COLUMN_NAME_EMAIL, FinConsultContract.AccountTable.COLUMN_NAME_PASSWORD
                ,FinConsultContract.AccountTable.COLUMN_NAME_FIRST_NAME,FinConsultContract.AccountTable.COLUMN_NAME_LAST_NAME,FinConsultContract.AccountTable.COLUMN_NAME_TELEPHONE
                ,FinConsultContract.AccountTable.COLUMN_NAME_INCOME_VALUE,FinConsultContract.AccountTable.COLUMN_NAME_INCOME_DATE
        };
        String selection = FinConsultContract.AccountTable.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(
                FinConsultContract.AccountTable.TABLE_NAME,   // The table to query
                columns,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        Account account = new Account();
        while (cursor.moveToNext()){
            account.setID(cursor.getInt(0));
            account.setEmail(cursor.getString(1));
            account.setPassword(cursor.getString(2));
            account.setFirstName(cursor.getString(3));
            account.setLastName(cursor.getString(4));
            account.setTelephone(cursor.getString(5));
            account.setIncomeValue(cursor.getDouble(6));
            account.setIncomeDate(cursor.getLong(7));
        }
        return account;
    }


    public long saveMoneyQuantitiesToDatabase(MoneyQuntaties quntaties){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EMAIL, quntaties.getUserEmail());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_INCOME_DATE, quntaties.getIncomeDate());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FULL_INCOME, quntaties.getFullIncome());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FINANCIAL_DUES, quntaties.getFinancialDues());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EXPENSES, quntaties.getExpenses());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_SAVINGS, quntaties.getSavings());
        long newRowId = db.insert(FinConsultContract.MoneyQuatitiesTable.TABLE_NAME, null, values);
        return newRowId;
    }
    public int updateMoneyQuantitiesToDatabase(MoneyQuntaties quntaties){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = FinConsultContract.AccountTable.COLUMN_NAME_EMAIL + " LIKE ?";
        String[] selectionArgs = {quntaties.getUserEmail()};
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EMAIL, quntaties.getUserEmail());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_INCOME_DATE, quntaties.getIncomeDate());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FULL_INCOME, quntaties.getFullIncome());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FINANCIAL_DUES, quntaties.getFinancialDues());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EXPENSES, quntaties.getExpenses());
        values.put(FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_SAVINGS, quntaties.getSavings());
        int newRowId = db.update(FinConsultContract.MoneyQuatitiesTable.TABLE_NAME, values, selection, selectionArgs);
        return newRowId;
    }
    public MoneyQuntaties readMoneyQuantitiesFromDatebase(String email){
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EMAIL,
                FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_INCOME_DATE,
                FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FULL_INCOME,
                FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FINANCIAL_DUES,
                FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EXPENSES,
                FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_SAVINGS
        };
        String selection = FinConsultContract.AccountTable.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(
                FinConsultContract.MoneyQuatitiesTable.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        MoneyQuntaties quntaties = new MoneyQuntaties();
        while (cursor.moveToNext()){
            quntaties.setUserEmail(cursor.getString(0));
            quntaties.setIncomeDate(cursor.getLong(1));
            quntaties.setFullIncome(cursor.getDouble(2));
            quntaties.setFinancialDues(cursor.getDouble(3));
            quntaties.setExpenses(cursor.getDouble(4));
            quntaties.setSavings(cursor.getDouble(5));
        }
        return quntaties;
    }


    public long saveMainElementItemToDatabase(MainElementItem elementItem){
        String s = elementItem.getItemDate() +" "+ elementItem.getMoneySpentInMonth() + " " + elementItem.getEmail();
        Log.e("saveMainElement" ,s);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL, elementItem.getEmail());
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE, elementItem.getItemDate());
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE, elementItem.getMoneySpentInMonth());
        long newRowId = db.insert(FinConsultContract.MainElementItem.TABLE_NAME, null, values);
        return newRowId;
    }
    public int updateMainElementItemToDatabase(MainElementItem elementItem){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE + " LIKE ? and "+
                FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL + " LIKE ?";
        String[] selectionArgs = {elementItem.getItemDate(),elementItem.getEmail()};
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL, elementItem.getEmail());
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE, elementItem.getItemDate());
        values.put(FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE, elementItem.getMoneySpentInMonth());
        int newRowId = db.update(FinConsultContract.MainElementItem.TABLE_NAME, values, selection, selectionArgs);
        return newRowId;
    }
    public ArrayList<MainElementItem> readMainElementItemFromDatebase(){
        ArrayList<MainElementItem> mainElementItems =  new ArrayList<MainElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
                String[] columns = {FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL,
                        FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE,
                        FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        Cursor cursor = db.query(
                FinConsultContract.MainElementItem.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null
        );
        MainElementItem mainElementItem= new MainElementItem();
        while (cursor.moveToNext()){
            mainElementItem = new MainElementItem();
            mainElementItem.setEmail(cursor.getString(0));
//            Log.e(TAG,cursor.getString(0));
            mainElementItem.setItemDate(cursor.getString(1));
//            Log.e(TAG,cursor.getString(1));
            mainElementItem.setMoneySpentInMonth(cursor.getDouble(2));
//            Log.e(TAG,cursor.getDouble(2)+"");
            mainElementItems.add(mainElementItem);
        }
        ArrayList<MainElementItem> itemArrayList = new ArrayList<>();
        for (int i = mainElementItems.size()-1; i >= 0; i--  ) {
            itemArrayList.add(mainElementItems.get(i));
        }
        return itemArrayList;
    }
    public MainElementItem readMainElementItemFromDatebase(String date){
        ArrayList<MainElementItem> mainElementItems =  new ArrayList<MainElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                FinConsultContract.MainElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        MainElementItem mainElementItem= new MainElementItem();
        while (cursor.moveToNext()){
            mainElementItem.setEmail(cursor.getString(0));
            mainElementItem.setItemDate(cursor.getString(1));
            mainElementItem.setMoneySpentInMonth(cursor.getDouble(2));
        }
        return mainElementItem;
    }
    public ArrayList<MainElementItem> readMainElementItemFromDatebaseByEmail(String email){
        ArrayList<MainElementItem> mainElementItems =  new ArrayList<MainElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL+ " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(
                FinConsultContract.MainElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        MainElementItem mainElementItem= new MainElementItem();
        while (cursor.moveToNext()){
            mainElementItem = new MainElementItem();
            mainElementItem.setEmail(cursor.getString(0));
//            Log.e(TAG,cursor.getString(0));
            mainElementItem.setItemDate(cursor.getString(1));
//            Log.e(TAG,cursor.getString(1));
            mainElementItem.setMoneySpentInMonth(cursor.getDouble(2));
//            Log.e(TAG,cursor.getDouble(2)+"");
            mainElementItems.add(mainElementItem);
        }

        ArrayList<MainElementItem> itemArrayList = new ArrayList<>();
        for (int i = mainElementItems.size()-1; i >= 0; i--  ) {
            itemArrayList.add(mainElementItems.get(i));
        }
        return itemArrayList;
    }

    public long saveSubElementItemToDatabase(SubElementItem elementItem){
        String s = elementItem.getItemDate() +" "+elementItem.getItemTitle() +" "+elementItem.getItemPrice()+" "+elementItem.getEmail();
        Log.e("saveMainElement" ,s);
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL, elementItem.getEmail());
        values.put(FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE, elementItem.getItemDate());
        values.put(FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE, elementItem.getItemTitle());
        values.put(FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND, elementItem.getKind());
        values.put(FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE, elementItem.getItemPrice());
        long newRowId = db.insert(FinConsultContract.SubElementItem.TABLE_NAME, null, values);
        return newRowId;
    }
    public ArrayList<SubElementItem> readSubElementItemFromDatebase(String date){
        ArrayList<SubElementItem> subElementItems =  new ArrayList<SubElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE + " = ?";
//                FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL+" = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                FinConsultContract.SubElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        SubElementItem subElementItem;
        while (cursor.moveToNext()){
            subElementItem =  new SubElementItem();
            subElementItem.setEmail(cursor.getString(0));
            subElementItem.setItemDate(cursor.getString(1));
            subElementItem.setItemTitle(cursor.getString(2));
            subElementItem.setKind(cursor.getString(3));
//            Log.e("546846854658465465",cursor.getString(3));
            subElementItem.setItemPrice(cursor.getDouble(4));
            subElementItems.add(subElementItem);
        }
        return subElementItems;
    }
    public ArrayList<SubElementItem> readSubElementItemFromDatebase(String date,String email){
        ArrayList<SubElementItem> subElementItems =  new ArrayList<SubElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE + " = ? and " +
                FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL+" = ?";
        String[] selectionArgs = {date,email};
        Cursor cursor = db.query(
                FinConsultContract.SubElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        SubElementItem subElementItem;
        while (cursor.moveToNext()){
            subElementItem =  new SubElementItem();
            subElementItem.setEmail(cursor.getString(0));
            subElementItem.setItemDate(cursor.getString(1));
            subElementItem.setItemTitle(cursor.getString(2));
            subElementItem.setKind(cursor.getString(3));
//            Log.e("546846854658465465",cursor.getString(3));
            subElementItem.setItemPrice(cursor.getDouble(4));
            subElementItems.add(subElementItem);
        }
        return subElementItems;
    }
    public ArrayList<SubElementItem> readSubElementItemFromDatebase(String date,String email,String kind){
        ArrayList<SubElementItem> subElementItems =  new ArrayList<SubElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE + " = ? and " +
                FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL+" = ? and "+FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND+" = ?";
        String[] selectionArgs = {date,email,kind};
        Cursor cursor = db.query(
                FinConsultContract.SubElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        SubElementItem subElementItem;
        while (cursor.moveToNext()){
            subElementItem =  new SubElementItem();
            subElementItem.setEmail(cursor.getString(0));
            subElementItem.setItemDate(cursor.getString(1));
            subElementItem.setItemTitle(cursor.getString(2));
            subElementItem.setKind(cursor.getString(3));
//            Log.e("546846854658465465",cursor.getString(3));
            subElementItem.setItemPrice(cursor.getDouble(4));
            subElementItems.add(subElementItem);
        }
        return subElementItems;
    }
    public ArrayList<SubElementItem> readSubElementItemFromDatebaseByEmail(String email){
        ArrayList<SubElementItem> subElementItems =  new ArrayList<SubElementItem>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND,
                FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(
                FinConsultContract.SubElementItem.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        SubElementItem subElementItem;
        while (cursor.moveToNext()){
            subElementItem =  new SubElementItem();
            subElementItem.setEmail(cursor.getString(0));
            subElementItem.setItemDate(cursor.getString(1));
            subElementItem.setItemTitle(cursor.getString(2));
            subElementItem.setKind(cursor.getString(3));
//            Log.e("546846854658465465",cursor.getString(3));
            subElementItem.setItemPrice(cursor.getDouble(4));
            subElementItems.add(subElementItem);
        }
        return subElementItems;
    }

    public MoneyMonthlySpent readMoneyMonthlySpentToDatabase(String date){
        ArrayList<MoneyMonthlySpent> monthlySpents =  new ArrayList<MoneyMonthlySpent>();
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        String[] columns = {FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE,
                FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_PRICE,
        };
        String selection = FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE + " = ?";
        String[] selectionArgs = {date};
        Cursor cursor = db.query(
                FinConsultContract.MoneyMonthlySpent.TABLE_NAME,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );
        MoneyMonthlySpent moneyMonthlySpent= new MoneyMonthlySpent();
        while (cursor.moveToNext()){
            moneyMonthlySpent.setItemDate(cursor.getString(0));
            Log.e("qqweqwe",cursor.getString(1));
            moneyMonthlySpent.setMoneySpentInMonth(cursor.getDouble(1));
            Log.e("qqweqwe",cursor.getString(0));
        }
        return moneyMonthlySpent;
    }
    public int updateMoneyMonthlySpentToDatabase(MoneyMonthlySpent moneyMonthlySpent){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        String selection = FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE + " LIKE ?";
        String[] selectionArgs = {moneyMonthlySpent.getItemDate()};
        ContentValues values = new ContentValues();
        values.put(FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE, moneyMonthlySpent.getItemDate());
        values.put(FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_PRICE, moneyMonthlySpent.getMoneySpentInMonth());
        int newRowId = db.update(FinConsultContract.MoneyMonthlySpent.TABLE_NAME, values, selection, selectionArgs);
        return newRowId;
    }
    public long saveMoneyCostToDatabase(MoneyMonthlySpent moneyMonthlySpent){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Log.e("zxczxczxc",moneyMonthlySpent.getItemDate());
        Log.e("zxczxczxc",moneyMonthlySpent.getMoneySpentInMonth()+"");
        values.put(FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE, moneyMonthlySpent.getItemDate());
        values.put(FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_PRICE, moneyMonthlySpent.getMoneySpentInMonth());
        long newRowId = db.insert(FinConsultContract.MoneyMonthlySpent.TABLE_NAME, null, values);
        return newRowId;
    }

    class DatabaseHelper extends SQLiteOpenHelper {
        private final static String DATABASE_NAME = "financialdatabase";
        private final static int DATABASE_VERSION = 1;
        private Context context;
        private static final String SQL_CREATE_ACCOUNTS =
                "CREATE TABLE " + FinConsultContract.AccountTable.TABLE_NAME + " (" +
                        FinConsultContract.AccountTable._ID + " INTEGER," +
                        FinConsultContract.AccountTable.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                        FinConsultContract.AccountTable.COLUMN_NAME_PASSWORD + " TEXT," +
                        FinConsultContract.AccountTable.COLUMN_NAME_FIRST_NAME + " TEXT," +
                        FinConsultContract.AccountTable.COLUMN_NAME_LAST_NAME + " TEXT," +
                        FinConsultContract.AccountTable.COLUMN_NAME_TELEPHONE + " TEXT," +
                        FinConsultContract.AccountTable.COLUMN_NAME_INCOME_VALUE + " DOUBLE," +
                        FinConsultContract.AccountTable.COLUMN_NAME_INCOME_DATE + " LONG)";
        private static final String SQL_DELETE_ACCOUNTS =
                "DROP TABLE IF EXISTS " + FinConsultContract.AccountTable.TABLE_NAME;




        private static final String SQL_CREATE_MONEY_QUANTITIES =
                "CREATE TABLE " + FinConsultContract.MoneyQuatitiesTable.TABLE_NAME + " (" +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EMAIL + " TEXT PRIMARY KEY," +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_INCOME_DATE + " LONG," +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FULL_INCOME + " DOUBLE," +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_SAVINGS + " DOUBLE," +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_FINANCIAL_DUES + " DOUBLE," +
                        FinConsultContract.MoneyQuatitiesTable.COLUMN_NAME_EXPENSES + " DOUBLE)";
        private static final String SQL_DELETE_MONEY_QUANTITIES =
                "DROP TABLE IF EXISTS " + FinConsultContract.MoneyQuatitiesTable.TABLE_NAME;



        private static final String SQL_CREATE_MAIN_LIST_ITEMS =
                "CREATE TABLE " + FinConsultContract.MainElementItem.TABLE_NAME + " (" +
                        FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE + " TEXT," +
                        FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL + " TEXT," +
                        FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_PRICE + " DOUBLE, PRIMARY KEY ( " +
                        FinConsultContract.MainElementItem.COLUMN_NAME_ITEM_DATE  +","
                        +FinConsultContract.MainElementItem.COLUMN_NAME_EMAIL+
                        " ))";
        private static final String SQL_DELETE_MAIN_LIST_ITEMS =
                "DROP TABLE IF EXISTS " + FinConsultContract.MainElementItem.TABLE_NAME;


        private static final String SQL_CREATE_MONEY_MONTH =
                "CREATE TABLE " + FinConsultContract.MoneyMonthlySpent.TABLE_NAME + " (" +
                        FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_DATE + " TEXT PRIMARY KEY," +
                        FinConsultContract.MoneyMonthlySpent.COLUMN_NAME_ITEM_PRICE + " DOUBLE)";
        private static final String SQL_DELETE_MONEY_MONTH =
                "DROP TABLE IF EXISTS " + FinConsultContract.MoneyMonthlySpent.TABLE_NAME;



        private static final String SQL_CREATE_SUB_LIST_ITEMS =
                "CREATE TABLE " + FinConsultContract.SubElementItem.TABLE_NAME + " (" +
                        FinConsultContract.SubElementItem.COLUMN_NAME_EMAIL + " TEXT," +
                        FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_DATE + " TEXT," +
                        FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_TITLE + " TEXT," +
                        FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_KIND + " TEXT," +
                        FinConsultContract.SubElementItem.COLUMN_NAME_ITEM_PRICE + " DOUBLE)";
        private static final String SQL_DELETE_SUB_LIST_ITEMS =
                "DROP TABLE IF EXISTS " + FinConsultContract.SubElementItem.TABLE_NAME;

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            try {
                sqLiteDatabase.execSQL(SQL_CREATE_ACCOUNTS);
                sqLiteDatabase.execSQL(SQL_CREATE_MONEY_QUANTITIES);
                sqLiteDatabase.execSQL(SQL_CREATE_MAIN_LIST_ITEMS);
                sqLiteDatabase.execSQL(SQL_CREATE_SUB_LIST_ITEMS);
                sqLiteDatabase.execSQL(SQL_CREATE_MONEY_MONTH);
            } catch (SQLException e) {
                Log.e("DATABASE CREATE",e.getMessage());
                Toast.makeText(context, "Sorry there is Error in create your database ", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
            try {
                sqLiteDatabase.execSQL(SQL_DELETE_ACCOUNTS);
                sqLiteDatabase.execSQL(SQL_DELETE_MONEY_QUANTITIES);
                sqLiteDatabase.execSQL(SQL_DELETE_MAIN_LIST_ITEMS);
                sqLiteDatabase.execSQL(SQL_DELETE_SUB_LIST_ITEMS);
                sqLiteDatabase.execSQL(SQL_DELETE_MONEY_MONTH);
                onCreate(sqLiteDatabase);
            } catch (SQLException e) {
                Log.e("DATABASE DELETE",e.getMessage());
                Toast.makeText(context, "Sorry there is Error in create your database ", Toast.LENGTH_SHORT).show();
            }
        }

    }
}