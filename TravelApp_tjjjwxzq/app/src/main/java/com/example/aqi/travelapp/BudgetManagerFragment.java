package com.example.aqi.travelapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BudgetManagerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BudgetManagerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BudgetManagerFragment extends Fragment {
    View root;

    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(View view);
    }

    public static Fragment newInstance(Context context) {
        BudgetManagerFragment frag = new BudgetManagerFragment();
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        root = (ViewGroup) inflater.inflate(R.layout.fragment_budget_manager, container, false);

        return root;
    }
}
