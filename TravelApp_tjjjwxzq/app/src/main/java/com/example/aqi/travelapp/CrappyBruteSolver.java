package com.example.aqi.travelapp;

/**
 * Brute force solver that generates all possible permutations of a given
 * list of destinations and retursn the most optimal itinerary from it
 * Optimality is given in terms of the time-cost average as defined
 * in the CostUtils class
 */

import java.util.*;
import static com.example.aqi.travelapp.CostUtils.*;

public class CrappyBruteSolver {

    /**
     * Generates an arraylist of all the permutations of a given list of desinations
     * @param destinations the list of destinations to permuatate
     * @return arraylist of different permutations
     */
    public static ArrayList<ArrayList<String>> generatePermutations(ArrayList<String> destinations)
    {
        if(destinations.size() <= 1)
        {
            ArrayList<ArrayList<String>> permutelists = new ArrayList<ArrayList<String>>();
            permutelists.add(destinations);
            return permutelists;
        }
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

    /**
     * Returns the optimal itinerary from an arraylist of different permutations
     * based on the total time-cost average
     * @param itineraries list of permutations
     * @return optimal itinerary
     */
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
