package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetManagerFragment extends Fragment implements BudgetAdapterCallback
{
    private static final String TAG = "BudgetFrag";

    private View root;

    private ImageButton btnaddbudget;

    private ImageButton btnaddexpenditure;

    private TextView budgetfield;

    private TextView spentfield;

    private TextView remainingfield;

    private ListView expenditurelist;

    private BudgetListAdapter budgetAdapter;

    public static Fragment newInstance(Context context) {
        BudgetManagerFragment frag = new BudgetManagerFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_budget_manager, container, false);

        //Get the add budget button
        btnaddbudget = (ImageButton) root.findViewById(R.id.addBudgetButton);

        //Get the add expenditure button
        btnaddexpenditure = (ImageButton) root.findViewById(R.id.addExpenditureButton);

        //Set their onClickListeners
        btnaddbudget.setOnClickListener(new addToBudget());
        btnaddexpenditure.setOnClickListener(new addNewExpenditure());

        //Get the budget field
        budgetfield = (TextView) root.findViewById(R.id.budgetDouble);

        //Get the spent field
        spentfield = (TextView) root.findViewById(R.id.spentDouble);

        //Get the remaining field
        remainingfield = (TextView) root.findViewById(R.id.remainingDouble);

        //Get the expenditure list view
        expenditurelist = (ListView) root.findViewById(R.id.expenditureList);

        //Initialize the budget list view adapter
        budgetAdapter = new BudgetListAdapter(getActivity(), BudgetManager.expItemsArr);

        //Set the callback for the budgetAdapter
        budgetAdapter.setCallback(this);

        //Set the adapter for the list view
        expenditurelist.setAdapter(budgetAdapter);

        //Set the text views
        budgetfield.setText(String.format("%.2f",BudgetManager.totalBudget));
        spentfield.setText(String.format("%.2f",BudgetManager.totalSpent));
        remainingfield.setText(String.format("%.2f",BudgetManager.totalRemaining));

        return root;
    }

    @Override
    public void onStop()
    {
        //Write budget info to file
        FileUtils.writeBudgetToFile(getActivity(),BudgetManager.BUDGET_FILENAME,
                BudgetManager.totalBudget, BudgetManager.totalSpent, BudgetManager.totalRemaining, BudgetManager.expItemsArr);
        super.onStop();
    }

    public class addToBudget implements View.OnClickListener{

        @Override
        public void onClick(View view){

          //Build dialog to input budget
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.addtobudget, null);
            builder.setView(dialogView)
                    .setTitle("Amount to add")
                    .setPositiveButton("OK", null) //Override below
                    .setNegativeButton("Cancel", null);

            //Only dismiss dialog after user has given a valid input
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogint) {
                    Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            EditText text_dialogaddbudget = (EditText) dialogView.findViewById(R.id.additionalBudget);

                            Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

                            //If the field is empty, prompt user
                            if (text_dialogaddbudget.length() == 0) {
                                toast.setText("Please enter an amount");
                                toast.show();
                            } else {
                                double budget = Math.round(
                                        Double.parseDouble(text_dialogaddbudget.getText().toString()));
                                BudgetManager.totalBudget += budget;
                                BudgetManager.totalRemaining += budget;
                                Log.d(TAG, "remaininig " + BudgetManager.totalRemaining);
                                budgetfield.setText(String.format("%.2f",BudgetManager.totalBudget));
                                remainingfield.setText(String.format("%.2f",BudgetManager.totalRemaining));
                                dialog.dismiss();
                            }
                        }
                    });

                }
            });

            dialog.show();

        }
          }

    public class addNewExpenditure implements View.OnClickListener {

        @Override
        public void onClick(View view)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.addexpenditure, null);
            builder.setView(dialogView)
                    .setPositiveButton("OK", null) //Override below
                    .setNegativeButton("Cancel", null)
                    .setTitle("Add new expenditure");

            //Persist dialog until user gives valid input
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogint) {
                    Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            EditText text_title = (EditText) dialogView.findViewById(R.id.expenditureTitle);
                            EditText text_amount = (EditText) dialogView.findViewById(R.id.expenditureAmount);
                            String exptitle = text_title.getText().toString();
                            String expamount = text_amount.getText().toString();

                            Toast toast = Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);

                            if (text_amount.length() == 0){
                                toast.setText("Please enter an amount");
                                toast.show();
                            } else if (text_title.length() == 0) {
                                toast.setText("Please enter a title");
                                toast.show();
                            }else{
                                if (BudgetManager.totalBudget == 0.00) {
                                    toast.setText("Please enter your budget first");
                                    toast.show();
                                } else if (BudgetManager.totalRemaining <= Double.parseDouble(expamount)) {
                                    toast.setText("Not enough money to make purchase");
                                    toast.show();
                                } else {
                                    BudgetManager.totalSpent +=  Double.parseDouble(expamount);
                                    BudgetManager.totalRemaining = BudgetManager.totalBudget - BudgetManager.totalSpent;

                                    spentfield.setText(String.format("%.2f",BudgetManager.totalSpent));
                                    remainingfield.setText(String.format("%.2f",BudgetManager.totalRemaining));

                                    //Update the adapter view
                                    BudgetManager.expItemsArr.add(new ExpItem(exptitle,expamount));
                                    budgetAdapter.notifyDataSetChanged();

                                    dialog.dismiss();

                                }

                            }

                        }
                    });
                }
            });

            dialog.show();


        }

    }

    public void editExpenditure(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.editexpenditure, null);
        final EditText text_title = (EditText) dialogView.findViewById(R.id.editExpenditureTitle);
        final EditText text_amount = (EditText) dialogView.findViewById(R.id.editExpenditureAmount);
        final ExpItem expItem = BudgetManager.expItemsArr.get(position);

        //Set text to display current title and amount
        text_title.setText(expItem.getExp(), TextView.BufferType.EDITABLE);
        text_amount.setText(expItem.getAmt(), TextView.BufferType.EDITABLE);

        builder.setView(dialogView)
                .setPositiveButton("OK", null) //Override below
                .setNegativeButton("Cancel", null)
                .setTitle("Add new expenditure");

        //Persist dialog until user gives valid input
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogint) {
                Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String exptitle = text_title.getText().toString();
                        String expamount = text_amount.getText().toString();

                        Toast toast = Toast.makeText(getActivity(),"",Toast.LENGTH_SHORT);

                        if (text_amount.length() == 0){
                            toast.setText("Please enter an amount");
                            toast.show();
                        } else if (text_title.length() == 0) {
                            toast.setText("Please enter a title");
                            toast.show();
                        }else{
                            if (BudgetManager.totalBudget == 0.00) {
                                toast.setText("Please enter your budget first");
                                toast.show();
                            } else if (BudgetManager.totalRemaining <= Double.parseDouble(expamount)) {
                                toast.setText("Not enough money to make purchase");
                                toast.show();
                            } else {
                                expItem.setExp(exptitle);
                                expItem.setAmt(expamount);

                                BudgetManager.totalSpent +=  Double.parseDouble(expamount);
                                BudgetManager.totalRemaining += BudgetManager.totalBudget-BudgetManager.totalSpent;

                                spentfield.setText(String.format("%.2f",
                                        BudgetManager.totalSpent));
                                remainingfield.setText(String.format("%.2f",
                                        BudgetManager.totalRemaining));

                                //Update the adapter view
                                budgetAdapter.notifyDataSetChanged();

                                dialog.dismiss();

                            }

                        }

                    }
                });
            }
        });

        dialog.show();
   }

}
