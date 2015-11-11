package com.example.aqi.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * This class defines the dialog that opens when a user chooses
 * to add a place to a new itinerary
 * The dialog prompts for a name for the itinerary
 * If the user presses OK without adding a name
 * a default name is given
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
            .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //Get the name input by user
                    EditText itname = (EditText) v.findViewById(R.id.itinerary_name);
                    String str_itname = itname.getText().toString();

                    //If itinerary name is empty, set a default name
                    if(str_itname.length() == 0)
                        str_itname = "Itinerary " + (ItineraryActivity.saveditineraries != null?
                                ItineraryActivity.saveditineraries.size() + 1:1);

                    //Save the new itinerary
                    ItineraryActivity.saveNewItinerary(str_itname, placename);

                    //Toast to let the user know that the itinerary has been saved
                    Toast toast = Toast.makeText(getActivity(), "Added " + placename + " to " +
                           str_itname, Toast.LENGTH_SHORT);
                    LinearLayout layout = (LinearLayout) toast.getView();
                    if (layout.getChildCount() > 0) {
                        TextView tv = (TextView) layout.getChildAt(0);
                        tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                    }
                    toast.show();

                }
            })
            .setNegativeButton(R.string.alert_dialog_cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });


        return builder.create();



    }

}
