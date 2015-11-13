package com.example.aqi.travelapp;

/**
 * Created by Arbiter on 12/11/2015.
 * Contains methods to do fuzzy matching of
 * two strings
 */
public class FuzzyStringMatcher {

    /**
     * Returns the minimum of three numbers
     * @param a
     * @param b
     * @param c
     * @return
     */
    private static int minimum(int a, int b, int c)
    {
        return Math.min(Math.min(a,b),c);
    }

    /**
     * Calculates the Levenshtein distance between two strings
     * using an iterative method.
     * The distance array element distance[i][j] stores the
     * distance between the substring containing i characters
     * of the first string and the substring containing the first
     * j characters of the second string
     * @param lhs
     * @param rhs
     * @return
     */

    public static double computeLevenshteinDistance(String lhs, String rhs)
    {
        //distance[i][j] holds the levenshtein distance between
        //the first i chars of lhs and j chars of rhs
        int[][] distance = new int[lhs.length()+1][rhs.length()+1];

        //To match a string of length i to an empty string,
        //we have to do i deletions (edits)
        for(int i =0; i <= lhs.length(); i++)
            distance[i][0] = i; //
        //To match a string of length j to an empty string,
        //we have to do j deletions (edits)
        for(int j =1; j<= rhs.length(); j++)
            distance[0][j] = j;

        for(int i =1; i <= lhs.length(); i++)
            for(int j =1; j <= rhs.length(); j++)
                distance[i][j] = minimum(
                        distance[i-1][j] + 1, //insertion
                        distance[i][j-1] + 1,// deletion
                        distance[i-1][j-1] + (lhs.charAt(i-1) == rhs.charAt(j-1)?0:1));//substitution

        return distance[lhs.length()][rhs.length()];
    }

    public static double computeFuzzyScore(String lhs, String rhs)
    {
        //Split the strings into words (space as word boundary)
        String[] lhswords = lhs.split(" ");
        String[] rhswords = rhs.split(" ");

        //Since the words may be swapped around in the autocomplete
        //suggestion as opposed to the stored value, check each
        //string against every other in the array and take the minimum
        //distance value
        int outerloop;
        int innerloop;
        String[] outerarr;
        String[] innerarr;
        if(lhswords.length<=rhswords.length)
        {
            outerloop = lhswords.length;
            innerloop = rhswords.length;
            outerarr = lhswords;
            innerarr = rhswords;
        }
        else
        {
            outerloop = rhswords.length;
            innerloop = lhswords.length;
            outerarr = rhswords;
            innerarr = lhswords;
        }


        double cumulativedist = 0;
        double normdist = 0;
        for(int i =0; i<outerloop; i++)
        {
            for(int j =0; j < innerloop; j++)
            {
                double newnormdist = 1 - computeLevenshteinDistance(outerarr[i],innerarr[j])/
                        Math.max(outerarr[i].length(), innerarr[j].length());
                if(newnormdist>normdist)
                    normdist =  newnormdist;
            }

            cumulativedist += normdist/((double)outerloop); //divide by outerloop length to normalize
        }

        return cumulativedist;

    }

}

