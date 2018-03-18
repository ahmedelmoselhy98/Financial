package com.e.k.m.a.financial.models;

/**
 * Created by Mahmoud Sadek on 3/15/2018.
 */

public class Account {

    String email, password, firstName, LastName, telephone, incomeValue, IncomeDate;
    int ID;

    public Account() {
    }

    public Account(String email, String password, String firstName, String lastName, String telephone, String incomeValue, String incomeDate, int ID) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        LastName = lastName;
        this.telephone = telephone;
        this.incomeValue = incomeValue;
        IncomeDate = incomeDate;
        this.ID = ID;
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

    public void setIncomeValue(String incomeValue) {
        this.incomeValue = incomeValue;
    }

    public void setIncomeDate(String incomeDate) {
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

    public String getIncomeValue() {
        return incomeValue;
    }

    public String getIncomeDate() {
        return IncomeDate;
    }

    public int getID() {
        return ID;
    }
}
