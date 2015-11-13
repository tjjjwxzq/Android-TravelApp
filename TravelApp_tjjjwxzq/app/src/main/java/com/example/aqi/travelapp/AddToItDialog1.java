package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentTransaction;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 */
public class AddToItDialog1 extends DialogFragment {


    private ListView dialogList;

    private ArrayAdapter<String> mAdapter;

    private String[] dialogArray = {"Existing itinerary", "New itinerary"};

    static AddToItDialog1 newInstance(CharSequence placename) {
        AddToItDialog1 f = new AddToItDialog1();
        Bundle args = new Bundle();
        args.putCharSequence("placename", placename);
        f.setArguments(args);
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get arguments
        final CharSequence placename = getArguments().getCharSequence("placename");

        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_add_to_it_dialog1, container, false);

        //Assign the List view and set the adapter
        dialogList = (ListView) root.findViewById(R.id.addtoitdialog1_listview);
        mAdapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,dialogArray);
        dialogList.setAdapter(mAdapter);

        //Set the listener for the list view
        dialogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                  Fragment fragment = null;

                  //Loads saved itineraries from file if not already stored in memory
                  ItineraryManager.loadSavedItineraries(getActivity());

                  switch(position)
                  {
                      case 0://Existing itinerary

                          if( ItineraryManager.saveditineraries.size() ==0){
                              // No saved itineraries
                              Toast toast = Toast.makeText(getActivity(), "No saved itineraries. Create a new one instead.",
                                      Toast.LENGTH_SHORT);
                              LinearLayout layout = (LinearLayout) toast.getView();
                              if (layout.getChildCount() > 0) {
                                  TextView tv = (TextView) layout.getChildAt(0);
                                  tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                              }
                              toast.show();

                              //Don't dismiss the dialog in this case

                          }
                          else
                          {
                              //Get the itinerary names to display in subsequent dialog list
                              String[] itineraries = ItineraryManager.extractSavedItineraries();
                              FragmentTransaction ft = getFragmentManager().beginTransaction();
                              DialogFragment newFragment = AddToItDialog2E.newInstance(itineraries,placename);
                              newFragment.show(ft,"dialog");

                              //Dismiss dialog fragment
                              dismiss();

                          }
                          break;
                      case 1: //New itinerary
                          FragmentTransaction ft = getFragmentManager().beginTransaction();
                          DialogFragment newFragment = AddToItDialog2N.newInstance(placename);
                          newFragment.show(ft,"dialog");

                          //Dismiss dialog fragment
                          dismiss();
                          break;
                  }



              }
          });

        //Set the title
        getDialog().setTitle("Add to...");

        return root;
    }

}
