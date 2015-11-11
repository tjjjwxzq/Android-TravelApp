package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BudgetAdapterCallback
    {

    private static final String TAG = "MainActivity";

    private ArrayAdapter<String> mDrawerAdapter;
    private ListView mDrawerList;

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private String mActivityTitle;

    final String[] osArray = { "Attraction Locator", "Itinerary Planner", "Budget Manager"};

    public double budget = Math.round(0.00*100)/100.0;
    public double addedBudget;
    public double spent = Math.round(0.00*100)/100.0;
    public double remaining = Math.round(0.00*100)/100.0;

    public String expTitle;
    public double expAmt;

    public ArrayList<String> expenditure = new ArrayList<>();
    public ArrayList<String> expList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        //Set up navigation drawer
        mDrawerList = (ListView)findViewById(R.id.left_drawer);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        mActivityTitle = getTitle().toString();

        addDrawerItems();
        setupDrawer();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //Initalize attraction locator fragment
        Fragment fragment = new AttractionLocatorFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.relative, fragment);
        transaction.addToBackStack(null);
        transaction.commit();



    }

    private void addDrawerItems(){

        mDrawerAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, osArray);
        mDrawerList.setAdapter(mDrawerAdapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Fragment fragment = null;
                switch (position) {
                    case 0:
                        fragment = new AttractionLocatorFragment();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction transaction = fragmentManager.beginTransaction();
                        transaction.replace(R.id.relative, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        break;
                    case 2:
                        fragment = new BudgetManagerFragment();
                        FragmentManager fragmentManager1 = getFragmentManager();
                        FragmentTransaction transaction1 = fragmentManager1.beginTransaction();
                        transaction1.replace(R.id.relative, fragment);
                        transaction1.addToBackStack(null);
                        transaction1.commit();
                        break;
                    case 1:
                        Intent intent = new Intent(MainActivity.this, ItineraryActivity.class);
                        MainActivity.this.startActivity(intent);
                        break;
                }
                setTitle(osArray[position]);
                mDrawerLayout.closeDrawer(mDrawerList);
            }
        });
    }

    private void setupDrawer() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getSupportActionBar().setTitle("Navigation");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getSupportActionBar().setTitle(mActivityTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    //Save itinerary to file when activity is stopped
    @Override
    protected void onStop()
    {
        ItineraryActivity.writeSavedItineraries(this);
        Log.d(TAG, "Stoppping main");
        super.onStop();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//        public void search(View view){
//            Button search = (Button) findViewById(R.id.button);
//
//            ArrayList image_details = getListData();
//            final ListView lv1 = (ListView) findViewById(R.id.listView);
//            lv1.setAdapter(new CustomListAdapter(this, image_details));
//        }
//
//        private ArrayList getListData() {
//            ArrayList<NewsItem> results = new ArrayList<>();
//            NewsItem newsData = new NewsItem();
//            newsData.setLocation("Sentosa");
//            newsData.setAddress("1 Sentosa Cove");
//            results.add(newsData);
//
//            NewsItem newsData1 = new NewsItem();
//            newsData1.setLocation("Singapore Zoo");
//            newsData1.setAddress("80 Mandai Lake Rd, 729826");
//            results.add(newsData1);
//
//            NewsItem newsData2 = new NewsItem();
//            newsData2.setLocation("Gardens by the Bay");
//            newsData2.setAddress("18 Marina Gardens Dr, 018953");
//            results.add(newsData2);
//            // Add some more dummy data for testing
//            return results;
//        }

        public void showlist(View view){
            TextView place = (TextView)findViewById(R.id.place_details);
            Button btn = (Button)findViewById(R.id.btn_addtoit);
            boolean state = ((ToggleButton)view).isChecked();

            if (state){
                place.setVisibility(View.VISIBLE);
                btn.setVisibility(View.VISIBLE);
            }
            else {
                place.setVisibility(View.GONE);
                btn.setVisibility(View.GONE);
            }
        }

        //BEGIN BUDGET PART
        public void updateBudgetText(){
            TextView addToBudget = (TextView) findViewById(R.id.budgetDouble);
            budget = Math.round(budget*100)/100;
            addToBudget.setText(Double.toString(budget));
        }

        public void updateSpentText() {
            TextView spentAmt = (TextView) findViewById(R.id.spentDouble);
            spent = Math.round(spent*100)/100;
            spentAmt.setText(Double.toString(spent));
        }

        public void updateRemainingText() {
            TextView remainingAmt = (TextView) findViewById(R.id.remainingDouble);
            remaining = Math.round((budget-spent)*100)/100;
            remainingAmt.setText(Double.toString(remaining));
        }

        public void addtobudget(View view) {
            Button btn2 = (Button) findViewById(R.id.addBudgetButton);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.addtobudget, null);
            final EditText meow = (EditText) dialogView.findViewById(R.id.additionalBudget);
            builder.setView(dialogView);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //user clicked ok button
                    if((meow.getText().toString()).equals("")) {
                        Toast.makeText(MainActivity.this, "Please enter an amount!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        addedBudget = Double.parseDouble((meow).getText().toString());
                        budget += Math.round(addedBudget * 100) / 100;
                        updateBudgetText();
                        updateRemainingText();
                        Toast.makeText(MainActivity.this, "Budget updated successfully!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        public void addnewexpenditure(View view) {
            Button btn3 = (Button) findViewById(R.id.addExpenditureButton);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.addexpenditure, null);
            final EditText title = (EditText) dialogView.findViewById(R.id.expenditureTitle);
            final EditText amt = (EditText) dialogView.findViewById(R.id.expenditureAmount);
            builder.setView(dialogView);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if ((amt.getText().toString()).equals("")) {
                        Toast.makeText(MainActivity.this, "Please enter an amount", Toast.LENGTH_SHORT).show();
                    } else {
                        expTitle = title.getText().toString();
                        expAmt = Double.parseDouble((amt).getText().toString());
                        if (budget == 0.00) {
                            Toast.makeText(MainActivity.this, "Please enter your budget first!", Toast.LENGTH_SHORT).show();
                        } else if (remaining <= expAmt) {
                            Toast.makeText(MainActivity.this, "Not enough money to make purchase", Toast.LENGTH_SHORT).show();
                        } else if (expTitle.equals("")) {
                            Toast.makeText(MainActivity.this, "Please enter a title.", Toast.LENGTH_SHORT).show();
                        } else {
                            expenditure.add(expTitle);
                            expList.add(Double.toString(expAmt));
                            spent += Math.round(expAmt * 100) / 100;
                            remaining = budget - spent;
                            updateSpentText();
                            updateRemainingText();

//                        ArrayAdapter<String> expAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1 ,expList);
//
//                        ListView expList = (ListView) findViewById(R.id.expenditureList);
//                        expList.setAdapter(expAdapter);

                            ArrayList exp_details = getExpData();
                            final ListView lv1 = (ListView) findViewById(R.id.expenditureList);
                            BudgetListAdapter budgetAdapter = new BudgetListAdapter(getApplicationContext(), exp_details);
                            budgetAdapter.setCallback(MainActivity.this);
                            lv1.setAdapter(budgetAdapter);

                        }

                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        public void editExpenditure(View view, int position) {
            ImageButton btn = (ImageButton) findViewById(R.id.editExpenditure);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.editexpenditure, null);
            final EditText title = (EditText) dialogView.findViewById(R.id.editExpenditureTitle);
            final EditText amt = (EditText) dialogView.findViewById(R.id.editExpenditureAmount);
            final int position1 = position;
            final Double originalAmt = Double.parseDouble(expList.get(position1));
            title.setText(expenditure.get(position1), TextView.BufferType.EDITABLE);
            amt.setText(expList.get(position1), TextView.BufferType.EDITABLE);
            builder.setView(dialogView);
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    expAmt = Double.parseDouble(amt.getText().toString());

                    if(remaining<(expAmt-originalAmt)) {
                        Toast.makeText(MainActivity.this, "Not enough money to make purchase!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        expTitle = title.getText().toString();
                        expList.set(position1, Double.toString(expAmt));
                        expenditure.set(position1, expTitle);

                        spent = spent - originalAmt + expAmt;

                        updateSpentText();
                        updateRemainingText();

                        ArrayList exp_details = getExpData();
                        final ListView lv1 = (ListView) findViewById(R.id.expenditureList);
                        BudgetListAdapter budgetAdapter = new BudgetListAdapter(getApplicationContext(), exp_details);
                        budgetAdapter.setCallback(MainActivity.this);
                        lv1.setAdapter(budgetAdapter);
                    }

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    //User cancelled the dialog
                }

            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }

        private ArrayList getExpData() {
            ArrayList<ExpItem> results = new ArrayList<>();
            for (int i = 0; i < expenditure.size(); i++){
                ExpItem exp = new ExpItem();
                exp.setExp(expenditure.get(i));
                exp.setAmt(expList.get(i));
                results.add(exp);
            }
            return results;
        }


}
