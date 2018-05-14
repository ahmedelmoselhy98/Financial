package com.e.k.m.a.financial.models;

/**
 * Created by ahmedelmoselhy on 3/25/2018.
 */

public class MoneyQuntaties {
    private String userEmail;
    private long incomeDate;
    private double fullIncome,savings,financialDues,expenses;

    public MoneyQuntaties() {
    }


    public String getUserEmail() {
        return userEmail;
    }

    public double getFullIncome() {
        return fullIncome;
    }

    public void setFullIncome(double fullIncome) {
        this.fullIncome = fullIncome;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public long getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(long incomeDate) {
        this.incomeDate = incomeDate;
    }

    public double getSavings() {
        return savings;
    }

    public void setSavings(double savings) {
        this.savings = savings;
    }

    public double getFinancialDues() {
        return financialDues;
    }

    public void setFinancialDues(double financialDues) {
        this.financialDues = financialDues;
    }

    public double getExpenses() {
        return expenses;
    }

    public void setExpenses(double expenses) {
        this.expenses = expenses;
    }
}
