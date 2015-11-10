package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class AddToItDialog1 extends DialogFragment {


    private ListView dialogList;

    private String[] dialogArray = {"Existing itinerary", "New itinerary"}

    public AddToItDialog1 newInstance() {
        AddToItDialog1 f = new AddToItDialog1();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_to_it_dialog1, container, false);

        //Assign the List view
        dialogList = (ListView) root.findViewById(R.id.addtoitdialog1_listview);
        getDialog().setTitle("Add to...");

        return root;
    }

}
