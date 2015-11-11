package com.example.aqi.travelapp;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Arbiter on 10/11/2015.
 */
public class SavedItinerary {

    //Name of itinerary
    public String name;

    //List of destinations
    public ArrayList<String> destinations;

    //Itinerary (ordered destinations)
    public ArrayList<String> itinerary;

    //Transport modes
    public int[] transportmodes;

    //default constructor
    public SavedItinerary(){}

    //Constructor
    public SavedItinerary(String name, ArrayList<String> destinations,
                          ArrayList<String> itinerary, int[] transportmodes)
    {
        this.name = name;
        this.destinations = destinations;
        this.itinerary = itinerary;
        this.transportmodes = transportmodes;
    }

    /**
     * Sets attributes of SavedItinerary object when reading from
     * a text file, which is written to in a given format.
     * name
     * destinations as a string of comma-separated names
     * itinerary as a string of comma-separated names
     * transportmodes as a string of comma-separated numbers
     * @param i attribute number in file for that itinerary entry
     * @param attr attribute (line) for that itinerary entry
     */
    public void setAttributes(int i,String attr)
    {
      switch(i)
      {
        case 0:
          name = attr;
          break;
        case 1:
          ArrayList<String> result1 =
          new ArrayList<String>(Arrays.asList(attr.split(",")));
          destinations =result1;
          break;
        case 2:
          ArrayList<String> result2 =
          new ArrayList<String>(Arrays.asList(attr.split(",")));
          itinerary =result2;
          break;
        case 3:
          String[] strresult = attr.trim().split(",");
          int[] result3 = new int[strresult.length];
          for(int j=0; j< strresult.length; j++)
          {
            result3[j] = Integer.parseInt(strresult[j]);
          }
          transportmodes = result3;
          break;
      }
    }


    public ArrayList<String> convertToFileOutput()
    {
        return new ArrayList<String>(Arrays
                .asList(toString().split("\n")));
    }

    @Override
    public String toString(){
      // If any of the fields are null, the file is empty
      if(destinations == null | name == null
      | itinerary == null | transportmodes == null)
        return null;

      String formatteddest = "";
      for(String dest: destinations)
      {
          formatteddest+= dest+",";
      }
      String formattedit = "";
      for(String node: itinerary)
      {
        formattedit += node+",";
      }
      String result =name + "\n" + formatteddest+ "\n" + formatteddest+ "\n";
      for(int num: transportmodes)
      {
        result+=num + " ";
      }
      return result;
    }

}
