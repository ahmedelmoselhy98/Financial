package com.e.k.m.a.financial.models;

/**
 * Created by ahmedelmoselhy on 3/15/2018.
 */

public class FinancialDues {

    private String category;
    private int financialDuesValue;
    private String financialDuesDate;

    public int getFinancialDuesValue() {
        return financialDuesValue;
    }

    public void setFinancialDuesValue(int financialDuesValue) {
        this.financialDuesValue = financialDuesValue;
    }

    public String getFinancialDuesDate() {
        return financialDuesDate;
    }

    public void setFinancialDuesDate(String financialDuesDate) {
        this.financialDuesDate = financialDuesDate;
    }
}
