package com.example.aqi.travelapp;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/**
 * Class containing utility methods to read and write
 * SavedItinerary fields and BudgetManager fields from
 * and to files
 */
public class FileUtils {

    private static final String TAG = "FileUtils";

    /**
     * Writes SavedItineraries fields to file;
     * Each field in the SavedItineary is written as a line
     * Each SavedItinerary is passed as an arraylist of its field lines
     * The function takes an arraylist of these SavedItineraries in
     * the above form
     * @param context
     * @param filename
     * @param itineraries SavedItineraries in form for writing to file
     */
    public static void writeItineraryToFile(Context context, String filename,
                                            ArrayList<ArrayList<String>> itineraries)
    {
        try{
            FileOutputStream fileout = context.
                    openFileOutput(filename, Context.MODE_PRIVATE); //Overwrite the existing file
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

    /**
     * Reads and retunrs an ArrayList of SavedItineraries from a file
     * @param context
     * @param filename
     * @return
     */
    public static ArrayList<SavedItinerary> readItinerariesFromFile(Context context, String filename)
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
                i++;
                if(line.isEmpty())
                {
                    //if this happens to be the last line of the file
                    //we will remove the new SavedItinerary with null
                    //attributes later
                    i =0;
                    saveditineraries.add(new SavedItinerary());
                }
            }

            saveditineraries.remove(saveditineraries.size()-1);
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

    /**
     * Writes BudgetManager info to file
     * The totalBudget, totalSpend, and totalRemaining take up the first 3 lines
     * Expenditure items each take one line, with the title
     * demarcated from the amount by a comma
     * @param context
     * @param filename
     * @param totalBudget
     * @param totalSpent
     * @param totalRemaining
     * @param expItems
     */
    public static void writeBudgetToFile(Context context, String filename,
                                         double totalBudget, double totalSpent, double totalRemaining,
                                         ArrayList<ExpItem> expItems)
    {
        Log.d(TAG, "writing file");
        try{

            FileOutputStream fileout = context.openFileOutput(filename,Context.MODE_PRIVATE);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileout);
            BufferedWriter buffWriter = new BufferedWriter(outputStreamWriter);

            buffWriter.write(Double.toString(totalBudget));
            buffWriter.newLine();
            buffWriter.write(Double.toString(totalSpent));
            buffWriter.newLine();
            buffWriter.write(Double.toString(totalRemaining));
            buffWriter.newLine();

            for(int i =0; i< expItems.size();i++)
            {
                buffWriter.write(expItems.get(i).toString());
                buffWriter.newLine();
            }

            buffWriter.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Reads BudgetManager info from a file
     * returning them as an ArrayList of Objects,
     * the first being a double[3] containing the
     * totalBudget, totalSpend, and totalRemaining
     * the second beind an ArrayList of ExpItems
     * @param context
     * @param filename
     * @return
     */
    public static ArrayList<Object> readBudgetFromFile(Context context, String filename)
    {
        Log.d(TAG, "reading file");
        ArrayList<Object> returnarr = new ArrayList<>();
        String line;
        double [] totals = new double[3];
        ArrayList<ExpItem> expItemsArr = new ArrayList<>();
        returnarr.add(totals);
        returnarr.add(expItemsArr);
        try{
            FileInputStream filein = context.openFileInput(filename);
            InputStreamReader inputStreamReader = new InputStreamReader(filein);
            BufferedReader buffReader = new BufferedReader(inputStreamReader);


            if((line = buffReader.readLine())==null)
                return returnarr;

            Log.d(TAG, "reading" + line);

            totals[0] = Double.parseDouble(line);
            for(int i =1; i <3; i++)
            {
                totals[i] = Double.parseDouble(buffReader.readLine());
                Log.d(TAG, "reading" + totals[i]);
            }

            while((line = buffReader.readLine())!=null || !line.isEmpty())
            {
                Log.d(TAG, "reading" + line);
                String[] fields = line.split(",");
                expItemsArr.add(new ExpItem(fields[0],fields[1]));
            }

            buffReader.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return returnarr;
    }
}
