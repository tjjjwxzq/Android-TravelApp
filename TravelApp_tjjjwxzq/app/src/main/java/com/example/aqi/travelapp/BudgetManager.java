package com.example.aqi.travelapp;

import java.util.ArrayList;

/**
 * Class for storing budget info during an app session
 * Class fields are written to file when the app stops
 * and are read from file when the app starts
 */
public class BudgetManager {

    //File name for saving budget info
    public static final String BUDGET_FILENAME = "BudgetFile";

    public static ArrayList<ExpItem> expItemsArr;

    public static double totalBudget;

    public static double totalSpent;

    public static double totalRemaining;
}
