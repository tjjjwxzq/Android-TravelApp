package com.example.aqi.travelapp;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * ItineraryFragment displays the individual itineraries above a map view
 * where the user can view the planned route/ individual nodes
 * The itinerary title is displayed along with a plan itinerary button
 * and a list of nodes. When the button is pressed, and if the nodes are valid
 * (within our cost-time dataset), the optimal route will be planned,
 * the list view will update, and the route and markers will be drawn
 * onto the map.
 */
public class ItineraryFragment extends Fragment {

    private static final String TAG = "ItFrag";
    //Fragment initialization parameters
    private static final String ITINERARYPOS = "itposition";

    private View root;

    private TextView itineraryName;

    private ListView mListView;

    private ItineraryListAdapter mAdapter;

    private Button btnplanItinerary;

    private ImageButton btnshowItineraryMap;

    private TextView totalTime;

    private TextView totalCost;

    private int itinerarypos;

    private SavedItinerary itinerary;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * When we select an itinerary from the ItineraryPlannerFragment
     * we need to know which itinerary to pass to the ItineraryFragment,
     * so we pass the itineraries position in the arraylist ItineraryManager.saveditineraries
     *
     * @param  itinerarypos  the position of the selected itinerary as stored in
     *                       ItineraryManager.saveditineraries
     * @return A new instance of fragment ItineraryFragment.
     */
    public static ItineraryFragment newInstance(int itinerarypos){
        ItineraryFragment fragment = new ItineraryFragment();
        Bundle args = new Bundle();
        args.putInt(ITINERARYPOS, itinerarypos);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        //Set the map to visible
        getActivity().findViewById(R.id.myMapFragment).setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Get itinerary name
        itinerarypos = getArguments().getInt(ITINERARYPOS);

        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_itinerary, container, false);

        //Get the text view
        itineraryName = (TextView) root.findViewById(R.id.itinerary_name);
        itineraryName.setText(ItineraryManager.saveditineraries.get(itinerarypos).name);

        //Get the list view
        mListView = (ListView) root.findViewById(R.id.itinerary_list);

        //Iinitalize the ListView adapter
        mAdapter = new ItineraryListAdapter(itinerarypos,getActivity());

        //Set the ListView adapter
        mListView.setAdapter(mAdapter);

        //Get the button view
        btnplanItinerary = (Button) root.findViewById(R.id.btn_plan_itinerary);
        btnplanItinerary.setOnClickListener(new planItineraryListener());

        //Get the show itinerary button
        btnshowItineraryMap = (ImageButton) root.findViewById(R.id.btn_show_itinerary_map);
        btnshowItineraryMap.setOnClickListener(new showItineraryListener());

        //Get the total time and cost textviews
        totalTime = (TextView) root.findViewById(R.id.text_totaltime);
        totalCost = (TextView) root.findViewById(R.id.text_totalcost);

        //Get the itinerary
        itinerary = ItineraryManager.saveditineraries.get(itinerarypos);

        //If itinerary has already been planned, hide button and how total time and cost
        if(itinerary.destinations.size() == itinerary.itinerary.size()
                && itinerary.destinations.size() != 1)
        {
            setItineraryDescription();
            //Remove list view dividers
            mListView.setDividerHeight(0);
        }


        return root;
    }

    /**
     * Used to set up the itinerary views properly for an itienrary
     * that has already been planned
     */
    private void setItineraryDescription()
    {
        //Hides plan itinerary, shows total time and cost,
        //and show itinerary on map button
        btnplanItinerary.setVisibility(View.GONE);
        btnshowItineraryMap.setVisibility(View.VISIBLE);
        totalTime.setText(CostUtils.getTotalTime(itinerary.transportmodes, itinerary.itinerary)
                + "min");
        totalTime.setVisibility(View.VISIBLE);
        totalCost.setText("$" +
                String.format("%.2f",
                        CostUtils.getTotalCost(itinerary.transportmodes, itinerary.itinerary)));
        totalCost.setVisibility(View.VISIBLE);
    }

    /**
     * onClickListner for the showItinerary button, which
     * shows the itinerary route on the map when pressed,
     * drawing markers, polylines and zooming out as necessary
     */
    public class showItineraryListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //Update map to show all markers in itinerary
            MainActivity.mapfragment.update(itinerary.itinerary, itinerary.transportmodes);
        }
    }

    /**
     * OnClickListener for planItinerary button, which
     * starts a dialog to prompt for the transport budget
     * The dialog persists until a valid input is given.
     * If all destination nodes are valid (ie. within out dataset)
     * the optimal itinerary will be generated along with the transport
     * modes, and the itinerary view and map will be updated to show the route
     * If not all of the destinations are valid, a toast will tell the
     * user that the nodes are not supported.
     */
    public class planItineraryListener implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //Dialog to input budget
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.dialog_transportbudget, null);
            final EditText edittextbudget = (EditText) dialogView.findViewById(R.id.edittext_transportbudget);
            final AlertDialog dialog = builder.setView(dialogView)
                    .setTitle("Enter your transport budget")
                    .setPositiveButton("Ok", null) //override functionality below
                    .setNegativeButton("Cancel", null)
                    .create();

            //Make sure user inputs valid amount before dismissing
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogint) {
                    Button b = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                    b.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                            //Get the budget input
                            double budget = edittextbudget.getText().toString().length() != 0 ?
                                    Double.parseDouble(edittextbudget.getText().toString()) : -1;

                            Toast toast = Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT);

                            // Center toast text
                            LinearLayout layout = (LinearLayout) toast.getView();
                            if (layout.getChildCount() > 0) {
                                TextView tv = (TextView) layout.getChildAt(0);
                                tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                            }

                            // Don't dismiss dialog if input is empty
                            if (budget == -1) {
                                toast.setText("Please enter your transport budget");
                                toast.show();

                            } else {
                                dialog.dismiss();
                                //Optional: allow toggle to use bruteforce planner
                                ArrayList<String> plannedit = ItineraryManager.planItinerary(0,
                                        itinerary.destinations);
                                if (plannedit != null) {
                                    itinerary.itinerary = plannedit;
                                    itinerary.transportmodes = CostUtils.getTransportMode(plannedit, budget)
                                            .get(0).get(0);
                                    Log.d(TAG, "saveditineraries" + ItineraryManager.saveditineraries);

                                    double totalcost = CostUtils
                                            .getTotalCost(itinerary.transportmodes, itinerary.itinerary);

                                    //Automatically save to budget
                                    BudgetManager.totalBudget += budget;
                                    BudgetManager.totalSpent += totalcost;
                                    BudgetManager.totalRemaining = BudgetManager.totalBudget - BudgetManager.totalSpent;
                                    BudgetManager.expItemsArr.add(new ExpItem("Transport",
                                            Double.toString(totalcost)));

                                    //Update list view
                                    mAdapter.notifyDataSetChanged();

                                    //Update map to show all markers in itinerary
                                    MainActivity.mapfragment.update(itinerary.itinerary, itinerary.transportmodes);

                                    //Hide plan itinerary button and show total time and cost
                                    setItineraryDescription();

                                    //Hide dividers
                                    mListView.setDividerHeight(0);

                                } else {
                                    toast.setText( "Insufficient cost data to plan itinerary. Not" +
                                            " all destintation nodes are supported.");
                                    toast.show();
                                }

                            }
                        }
                    });
                }
            });

            dialog.show();

        }
    }

    @Override
    public void onStop()
    {
        //Remove map fragment and set containing view to gone

        getActivity().findViewById(R.id.myMapFragment).setVisibility(View.GONE);
        Log.d(TAG, "Map fragment visible?" + getActivity().findViewById(R.id.myMapFragment).
                getVisibility());

        FileUtils.writeBudgetToFile(getActivity(), "BudgetFile", BudgetManager.totalBudget,
                BudgetManager.totalSpent, BudgetManager.totalRemaining, BudgetManager.expItemsArr);
        ItineraryManager.writeSavedItineraries(getActivity());
        Log.d(TAG, "writing saved itineraries");
        super.onStop();
    }
}
