package com.e.k.m.a.financial.database;

import android.provider.BaseColumns;

public class FinConsultContract {

    public FinConsultContract() {
    }

    public static class AccountTable implements BaseColumns {
        public final static String TABLE_NAME = "accounttable";
        public final static String COLUMN_NAME_UID = "_id";
        public final static String COLUMN_NAME_FIRST_NAME = "firstname";
        public final static String COLUMN_NAME_LAST_NAME = "secondname";
        public final static String COLUMN_NAME_TELEPHONE = "telephone";
        public final static String COLUMN_NAME_EMAIL = "email";
        public final static String COLUMN_NAME_PASSWORD = "password";
        public final static String COLUMN_NAME_INCOME_DATE = "incomedate";
        public final static String COLUMN_NAME_INCOME_VALUE = "incomevalue";


    }
    public static class MoneyQuatitiesTable implements BaseColumns {
        public final static String TABLE_NAME = "moneyquentitytable";
        public final static String COLUMN_NAME_EMAIL = "email";
        public final static String COLUMN_NAME_INCOME_DATE = "incomedate";
        public final static String COLUMN_NAME_FULL_INCOME = "fullincome";
        public final static String COLUMN_NAME_SAVINGS = "savings";
        public final static String COLUMN_NAME_FINANCIAL_DUES = "financialdues";
        public final static String COLUMN_NAME_EXPENSES = "expenses";
    }
    public static class MainElementItem implements BaseColumns {
        public final static String TABLE_NAME = "mainitemslist";
        public final static String COLUMN_NAME_EMAIL = "email";
        public final static String COLUMN_NAME_ITEM_DATE = "itemdate";
        public final static String COLUMN_NAME_ITEM_PRICE = "itemprice";
    }
    public static class SubElementItem implements BaseColumns {
        public final static String TABLE_NAME = "subitemslist";
        public final static String COLUMN_NAME_EMAIL = "email";
        public final static String COLUMN_NAME_ITEM_DATE = "itemdate";
        public final static String COLUMN_NAME_ITEM_TITLE = "itemtitle";
        public final static String COLUMN_NAME_ITEM_KIND = "itemkind";
        public final static String COLUMN_NAME_ITEM_PRICE = "itemprice";
    }
    public static class MoneyMonthlySpent implements BaseColumns {
        public final static String TABLE_NAME = "moneymothe";
        public final static String COLUMN_NAME_ITEM_DATE = "itemdate";
        public final static String COLUMN_NAME_ITEM_PRICE = "itemprice";
    }
}
