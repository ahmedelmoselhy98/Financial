package com.e.k.m.a.financial.models;


public class MoneyMonthlySpent {

    private String itemDate;
    private double moneySpentInMonth;

    public MoneyMonthlySpent() {
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
