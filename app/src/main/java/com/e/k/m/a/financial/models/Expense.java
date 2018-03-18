package com.e.k.m.a.financial.models;

/**
 * Created by Mahmoud Sadek on 3/15/2018.
 */
public class Expense {
    String value, category, date;
    int ID;

    public Expense() {
    }

    public Expense(String value, String category, String date) {
        this.value = value;
        this.category = category;
        this.date = date;
    }

    public String getValue() {
        return value;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
