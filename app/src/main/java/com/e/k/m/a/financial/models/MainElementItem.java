package com.e.k.m.a.financial.models;

import java.util.ArrayList;


public class MainElementItem {

    private String itemDate,email;
    private double moneySpentInMonth;

    public MainElementItem() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getItemDate() {
        return itemDate;
    }

    public void setItemDate(String itemDate) {
        this.itemDate = itemDate;
    }

    public double getMoneySpentInMonth() {
        return moneySpentInMonth;
    }

    public void setMoneySpentInMonth(double moneySpentInMonth) {
        this.moneySpentInMonth = moneySpentInMonth;
    }
}
