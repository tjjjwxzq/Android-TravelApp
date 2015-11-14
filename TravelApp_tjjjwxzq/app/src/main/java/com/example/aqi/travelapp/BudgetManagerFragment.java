package com.example.aqi.travelapp;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class BudgetManagerFragment extends Fragment {
    View root;

    public static Fragment newInstance(Context context) {
        BudgetManagerFragment frag = new BudgetManagerFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_budget_manager, container, false);

        return root;
    }

    @Override
    public void onStop()
    {
        super.onStop();
    }

}
