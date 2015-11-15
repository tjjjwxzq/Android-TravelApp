package com.example.aqi.travelapp;

/**
 * Class which contains the the fast approximate algorithms
 * to generate an optimal itinerary given a set of destinations
 * The algorithms used are Two-Opt on a starting itinerary generated
 * by Nearest Neighbour.
 * THe nearest neighbout is based on the time-cost average,
 * as defined in the CostUtils class
 */
import java.util.*;
import static com.example.aqi.travelapp.CostUtils.*;
//Static import to get all the static members of CostUtils to reduce verbosity

public class FastApproxSolver {


    //Generates an itinerary (ArrayList<String>) based on nearest neighbour,
    //given an ArrayList<String> of destinations
    //The starting point will be the first element of the destinations ArrayList,
    //while the ending point will be the last

    /**
     * Generates an itienrary based on the Nearest Neigbour given an arraylist
     * of destinations
     * The starting point will be taken as the first element of the destionation
     * ArrayList, and the ending point will be the last
     * @param destinations ArrayList<String> of destination nodes
     * @return planned itinerary
     */
    public static ArrayList<String> planItineraryNN(ArrayList<String> destinations)
    {
        //Clone so method doesn't mutate ArrayList passed as paramter
        ArrayList<String> destinationsclone = (ArrayList<String>) destinations.clone();

        double timecost = 0;
        String nextdestination ="";
        //If the first and last nodes are the same, set the itinerary size accordingly
        int itinerarysize = destinationsclone.get(0).equals(
                destinationsclone.get(destinationsclone.size()-1))?
                destinationsclone.size()-1:destinationsclone.size();

        ArrayList<String> itinerary = new ArrayList<String>();
        itinerary.add(destinationsclone.remove(0));//add starting point

        while(itinerary.size()<itinerarysize)
        {
            timecost = TimeCostAve(itinerary.get(itinerary.size()-1), destinationsclone.get(0));
            nextdestination = destinationsclone.get(0);

            //Get nearest neighbour
            for(int i =0; i<destinationsclone.size()-1;i++) //exclude the ending point
            {
                double newtimecost = TimeCostAve(itinerary.get(itinerary.size()-1), destinationsclone.get(i));
                if(newtimecost < timecost)
                {
                    timecost = newtimecost;
                    nextdestination = destinationsclone.get(i);

                }
            }

            itinerary.add(nextdestination);
            destinationsclone.remove(nextdestination);
        }

        return itinerary;
    }

    /**
     * Swaps two nodes in an itinerary. To be used in Two-Opt
     * @param itinerary starting itinerary
     * @param node1 first node
     * @param node2 second node
     * @return itinerary with node1 and node2 swapped
     */
    public static ArrayList<String> swapNodes(ArrayList<String> itinerary, int node1, int node2)
    {
        if(node1==node2)
            return itinerary;

        //Since the swap assumes node1<node2
        if(node1>node2)
        {
            int temp = node1;
            node1 = node2;
            node2 = temp;
        }

        ArrayList<String> newitinerary = new ArrayList<String>();
        for(int i = 0; i<node1;i++)
            newitinerary.add(itinerary.get(i));
        for(int i = node2; i>=node1; i--)
            newitinerary.add(itinerary.get(i));
        for(int i = node2+1; i<itinerary.size();i++)
            newitinerary.add(itinerary.get(i));

        return newitinerary;

    }

    //Greedy 2-opt: given an itinerary, swaps nodes and checks for improvement,
    //If there is an improvement, swap the nodes immediately.
    //If not continue until no further improvement is possible

    /**
     * Greedy 2-opt algorithm: given an itinerary, run through
     * every pair of nodes and swaps nodes
     * and checks for improvement
     * If there is an improvement, swap the nodes immediately
     * If not continue from the beginning until no further improvement
     * is possible
     * @param itinerary starting itinerary
     * @return improved itinerary
     */
    public static ArrayList<String> getTwoOpt(ArrayList<String> itinerary)
    {
        ArrayList<String> newitinerary;
        double timecost = TotalTimeCostAve(itinerary);
        int i = 1;
        int j = 2;

        while(i<itinerary.size())
        {
            while(j<itinerary.size())
            {
                newitinerary = swapNodes(itinerary,i,j);
                double newtimecost = TotalTimeCostAve(newitinerary);

                if(newtimecost < timecost)
                {
                    timecost = newtimecost;
                    itinerary = newitinerary;


                    i = 1;
                    j = 2;
                }
                else
                {
                    j++;
                }

            }
            i++;
            j = i+1;
        }

        return itinerary;
    }
}

