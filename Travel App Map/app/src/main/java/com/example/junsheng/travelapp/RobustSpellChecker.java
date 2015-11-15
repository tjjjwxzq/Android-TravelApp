package com.example.junsheng.travelapp;

import net.java.frej.Regex;

import java.util.Scanner;

/**
 * Created by user on 11/11/2015.
 */
public class RobustSpellChecker {

    public String SpellChecker(String inputLocation){

        String output;
        String input = inputLocation;

        inputLocation=inputLocation.toLowerCase();
        inputLocation=inputLocation.replaceAll("\\s+", "");
        inputLocation=inputLocation.replaceAll("[^a-zA-Z ]", "");

        Regex test6a = new Regex("[buddhatoothrelictemple]");
        Regex test6b = new Regex("[toothrelictemple]");
        Regex test6c = new Regex("[buddhatoothtemple]");

        Regex test68a = new Regex("[SingaporeFlyer]");
        Regex test68b = new Regex("[Flyer]");
        Regex test68c = new Regex("[FlyerSingapore]");

        Regex test75a = new Regex("[SingaporeZoo]");
        Regex test75b = new Regex("[Zoo]");
        Regex test75c = new Regex("[ZooofSingapore]");

        Regex test98a = new Regex("[marinabaysands]");
        Regex test98b = new Regex("[mbs]");
        Regex test98c = new Regex("[marinasands]");

        Regex test99a = new Regex("[vivocity]");
        Regex test99b = new Regex("[vivo city]");
        Regex test99c = new Regex("[vivo's city]");

        Regex test100a = new Regex("[Resortsworldsentosa]");
        Regex test100b = new Regex("[sentosa]");
        Regex test100c = new Regex("[sentosabeach]");

        if (test6a.match(inputLocation)|test6b.match(inputLocation)|test6c.match(inputLocation)|inputLocation.equals("BTRT")) {
            output = "Buddha Tooth Relic Temple";
        } else if (test68a.match(inputLocation)|test68b.match(inputLocation)|test68c.match(inputLocation)|inputLocation.equals("SF")) {
            output = "Singapore Flyer";
        }else if (test75a.match(inputLocation)|test75b.match(inputLocation)|test75c.match(inputLocation)|inputLocation.equals("SZ")) {
            output = "Singapore Zoo";
        }else if (test98a.match(inputLocation)|test98b.match(inputLocation)|test98c.match(inputLocation)|inputLocation.equals("MBS")) {
            output = "Marina Bay Sands";
        }else if (test99a.match(inputLocation)|test99b.match(inputLocation)|test99c.match(inputLocation)|inputLocation.equals("VC")) {
            output = "VivoCity";
        }else if (test100a.match(inputLocation)|test100b.match(inputLocation)|test100c.match(inputLocation)|inputLocation.equals("RWS")) {
            output = "Resorts World Sentosa";
        }else {
            output = input;
        }
        return output;
    }
}
