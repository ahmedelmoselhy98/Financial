package com.e.k.m.a.financial.models;

/**
 * Created by ahmedelmoselhy on 3/15/2018.
 */

public class HeadModel {
    String incomedate, incomevalue, incomecheap, incomeprimary, incomesecond, spendMoney;
    int ID;

    public HeadModel(String incomedate, String incomevalue, String incomecheap, String incomeprimary, String incomesecond, String spendMoney, int ID) {
        this.incomedate = incomedate;
        this.incomevalue = incomevalue;
        this.incomecheap = incomecheap;
        this.incomeprimary = incomeprimary;
        this.incomesecond = incomesecond;
        this.spendMoney = spendMoney;
        this.ID = ID;
    }

    public String getIncomedate() {
        return incomedate;
    }

    public String getIncomevalue() {
        return incomevalue;
    }

    public String getIncomecheap() {
        return incomecheap;
    }

    public String getIncomeprimary() {
        return incomeprimary;
    }

    public String getIncomesecond() {
        return incomesecond;
    }

    public String getSpendMoney() {
        return spendMoney;
    }

    public int getID() {
        return ID;
    }
}
