package com.example.aqi.travelapp;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Arbiter on 10/11/2015.
 */
public class FileUtils {

    public static void writeToFile(Context context, String filename,
                                   ArrayList<ArrayList<String>> itineraries )
    {
        try{
            FileOutputStream fileout = context.
                    openFileOutput(filename, Context.MODE_APPEND); //append to existing file
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout,"UTF-8");
            BufferedWriter buffWriter = new BufferedWriter(outputWriter);
            for(ArrayList<String> Lines: itineraries)
            {
                for(String line: Lines)
                {
                    buffWriter.write(line);
                    buffWriter.newLine();
                }
                //Write a newline to demarcate itinerary entries
                buffWriter.newLine();
            }
           buffWriter.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static ArrayList<SavedItinerary> readFile(Context context, String filename)
    {

        ArrayList<SavedItinerary> saveditineraries = new ArrayList<SavedItinerary>();
        try{
            FileInputStream filein = context.openFileInput(filename);
            InputStreamReader inputReader = new InputStreamReader(filein, "utf-8");
            BufferedReader buffReader = new BufferedReader(inputReader);
            String line = null;
            int i = 0;
            saveditineraries.add(new SavedItinerary());
            while((line =buffReader.readLine()) != null)
            {
                saveditineraries.get(saveditineraries.size()-1)
                        .setAttributes(i,line);
                System.out.println(line);
                i++;
                if(line.isEmpty())
                {
                    i =0;
                    saveditineraries.add(new SavedItinerary());
                }
            }

            buffReader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //If the file is empty; no itineraries saved
        if(saveditineraries.toString().equals("[null]") | saveditineraries.size()==0)
            return null;

        return saveditineraries;


    }
}
