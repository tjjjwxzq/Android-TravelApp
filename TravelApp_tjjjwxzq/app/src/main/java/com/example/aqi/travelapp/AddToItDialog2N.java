package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

import java.util.ArrayList;


/**
 * This class defines the dialog that opens when a user chooses
 * to add a place to a new itinerary
 * The dialog prompts for a name for the itinerary
 * If the user presses OK without adding a name
 * the dialog persists until he inputs a proper name
 */
public class AddToItDialog2N extends DialogFragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "AddToItDia2N";
    private static final String ARG_PLACENAME = "placename";

    public static AddToItDialog2N newInstance(CharSequence placename) {
        AddToItDialog2N fragment = new AddToItDialog2N();
        Bundle args = new Bundle();
        args.putCharSequence(ARG_PLACENAME, placename);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Get arguments
        final String placename = (String) getArguments().getCharSequence(ARG_PLACENAME);

        //Layout inflater for custom view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View v = inflater.inflate(R.layout.fragment_add_to_it_dialog2_n, null);

        //Create builder
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Name your new itinerary");
        builder.setView(v)
            .setPositiveButton(R.string.alert_dialog_ok, null) //override behavior in onShow() below
            .setNegativeButton(R.string.alert_dialog_cancel, null); //just close the dialog

        // Prevent dialog from closing if bad input is given and button is pressed
        final AlertDialog dialog = builder.create();
        dialog.setOnShowListener(new DialogInterface.OnShowListener()
        {
            @Override
            public void onShow(DialogInterface dialogint)
            {
                Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View view)
                    {
                        //Get the name input by user
                        EditText itname = (EditText) v.findViewById(R.id.itinerary_name);
                        final String str_itname = itname.getText().toString();

                        Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);
                        // Center toast text
                        LinearLayout layout = (LinearLayout) toast.getView();
                                if (layout.getChildCount() > 0) {
                                    TextView tv = (TextView) layout.getChildAt(0);
                                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                                }

                        //If itinerary name is empty, prompt the user to enter a proper name
                        if(str_itname.length() == 0)
                        {
                            //Toast to let the user know that the itinerary has been saved
                            Log.d(TAG, "toast to input prpoer name");
                            toast.setText("Please input an itinerary name");
                            toast.show();
                        }
                        else
                        {
                            //Filter saveditineraries according to the input name to check
                            //if any names clash
                            ArrayList<SavedItinerary> arrsavedit = new ArrayList<SavedItinerary>(
                            Collections2.filter(ItineraryManager.saveditineraries,
                                new Predicate<SavedItinerary>() {
                                    @Override
                                    public boolean apply(SavedItinerary input) {
                                        return input.name.equals(str_itname);
                                }
                            }));

                            if(arrsavedit.size()>0)
                            {
                                toast.setText("Itinerary " + str_itname + " already exists");
                                toast.show();
                            }
                            else
                            {
                                //Save the new itinerary
                                ItineraryManager.saveNewItinerary(str_itname, placename);
                                //Toast to let the user know that the itinerary has been saved
                                toast.setText("Added " + placename + " to " + str_itname);
                                toast.show();

                                //All is well; dismiss the dialog
                                dialog.dismiss();

                            }
                        }
                    }
                });
            }
        });

        return dialog;

    }

}
