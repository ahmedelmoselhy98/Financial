package com.e.k.m.a.financial.models;

import java.util.Calendar;
import java.util.Date;

public class Account {

    private String email, password, firstName, LastName, telephone;
    double incomeValue;
    int ID;
    long IncomeDate;
    public Account() {
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        LastName = lastName;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public void setIncomeValue(double incomeValue) {
        this.incomeValue = incomeValue;
    }
    public void setIncomeDate(long incomeDate) {
        IncomeDate = incomeDate;
    }
    public void setID(int ID) {
        this.ID = ID;
    }
    public String getEmail() {
        return email;
    }
    public String getPassword() {
        return password;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return LastName;
    }
    public String getTelephone() {
        return telephone;
    }
    public double getIncomeValue() {
        return incomeValue;
    }
    public long getIncomeDate() {
        return IncomeDate;
    }
    public int getID() {
        return ID;
    }
}
