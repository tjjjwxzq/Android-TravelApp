package com.example.aqi.travelapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddToItDialog2E extends DialogFragment {

   public static AddToItDialog2E newInstance(String[] saveditineraries, CharSequence placename){
        AddToItDialog2E fragment = new AddToItDialog2E();
        Bundle args = new Bundle();
        args.putCharSequenceArray("saved itineraries",saveditineraries );
        args.putCharSequence("placename", placename);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //Get arguments
        final String[] saveditineraries = (String[]) getArguments().getCharSequenceArray("saved itineraries");
        final String placename = (String) getArguments().getCharSequence("placename");

        //Create builder with simple list
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Choose existing itinerary");
        builder.setItems(saveditineraries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int status = ItineraryManager.updateSavedItinerary(saveditineraries[which],
                        placename);

                //Toast to let user know that the placename has been successfully added
                //or that it was duplicate
                Toast toast;
                if(status == 1)
                {
                    toast = Toast.makeText(getActivity(), "Added " + placename + " to " +
                            saveditineraries[which], Toast.LENGTH_SHORT);
                }
                else
                {
                    toast = Toast.makeText(getActivity(), placename +
                            " is already in " + saveditineraries[which], Toast.LENGTH_SHORT);
                }
                LinearLayout layout = (LinearLayout) toast.getView();
                if (layout.getChildCount() > 0) {
                    TextView tv = (TextView) layout.getChildAt(0);
                    tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                }
                toast.show();

            }
        });
        return builder.create();

    }

}
