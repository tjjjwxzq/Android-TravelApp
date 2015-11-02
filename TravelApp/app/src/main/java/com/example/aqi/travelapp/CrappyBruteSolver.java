package com.example.aqi.travelapp;

/**
 * Created by aqi on 2/11/15.
 */

import java.util.*;
import static com.example.aqi.travelapp.CostUtils.*;

public class CrappyBruteSolver {

    //Generates an arraylist of all possible permutations of an arraylist of strings
    public static ArrayList<ArrayList<String>> generatePermutations(ArrayList<String> destinations)
    {
        if(destinations.size() == 1)
        {
            ArrayList<ArrayList<String>> permutelists = new ArrayList<ArrayList<String>>();
            permutelists.add(destinations);
            return permutelists;
        }
        //Note that arrlist.remove(int index) returns the element removed!
        //It mutates the arrlist
        String lastele = destinations.remove(0); //remove the first element from destinations
        ArrayList<ArrayList<String>> oldpermutelists = generatePermutations(destinations);
        ArrayList<ArrayList<String>> newpermutelists = new ArrayList<ArrayList<String>>();
        for(ArrayList<String> list : oldpermutelists)
        {
            //Not modifying the array list of arraylists itself, but the ararylist elements
            for(int i=0; i<=list.size(); i++)
            {
                ArrayList<String> newlist = new ArrayList<String>(list);
                newlist.add(i,lastele);
                newpermutelists.add(newlist);
            }
        }

        return newpermutelists;
    }

    //Returns the optimal itinerary based on the total time-cost average
    public static ArrayList<String> getOptimalItinerary(ArrayList<ArrayList<String>> itineraries)
    {
    double optimaltimecost = -1.0;
    ArrayList<String> optimalit = new ArrayList<String>();
    for(int i =0; i<itineraries.size();i++)
    {
      double newtimecost = TotalTimeCostAve(itineraries.get(i));
      if(optimaltimecost == -1.0 || newtimecost < optimaltimecost)
      {
        optimaltimecost = newtimecost;
        optimalit = itineraries.get(i);
      }
    }

    return optimalit;
    }





}
