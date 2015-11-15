package com.example.aqi.travelapp;

/**
 * Class to contain expenditure into
 */

public class ExpItem {
    private String exp;
    private String amt;

    public ExpItem(String exp, String amt)
    {
        this.exp = exp;
        this.amt = amt;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String location) {
        this.exp = location;
    }

    public String getAmt() {
        return amt;
    }

    public void setAmt(String address) {
        this.amt=address;
    }

    @Override
    public String toString(){
        return exp+","+amt;
    }
}

