package com.e.k.m.a.financial.models;

import com.e.k.m.a.financial.models.Expense;

import java.util.ArrayList;

/**
 * Created by Mahmoud Sadek on 3/15/2018.
 */

public class MainHomeRecyclerViewModel {
    String date, value;
    ArrayList<Expense> expenses;

    public MainHomeRecyclerViewModel() {
    }

    public MainHomeRecyclerViewModel(String date, String value, ArrayList<Expense> expenses) {
        this.date = date;
        this.value = value;
        this.expenses = expenses;
    }

    public String getDate() {
        return date;
    }

    public String getValue() {
        return value;
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }
}
