package com.example.aqi.travelapp;

/**
 * Destinations class holds the cost-time data and a hashmap
 * of the valid destinations (mapping to their corresponding indices)
 */
import java.util.*;

public class Destinations {
    //Static final variables can be initialized in a static block
    public static final HashMap<String,Integer> DESTINATION_MAP = new HashMap<String,Integer>();
    static
    {
      DESTINATION_MAP.put("Marina Bay Sands",0);
      DESTINATION_MAP.put("Singapore Flyer",1);
      DESTINATION_MAP.put("VivoCity",2);
      DESTINATION_MAP.put("Resorts World Sentosa",3);
      DESTINATION_MAP.put("Buddha Tooth Relic Temple",4);
      DESTINATION_MAP.put("Singapore Zoo",5);
    }

    public static final int NUM_DESTINATIONS = DESTINATION_MAP.size();

    public static final double[][][] COST_ARR= new double[3][NUM_DESTINATIONS][NUM_DESTINATIONS];
    static
    {
      COST_ARR[1][0][1] = 0.83;
      COST_ARR[1][0][2] = 1.18;
      COST_ARR[1][0][3] = 4.03;
      COST_ARR[1][0][4] = 0.88;
      COST_ARR[1][0][5] = 1.96;

      COST_ARR[1][1][0] = 0.83;
      COST_ARR[1][1][2] = 1.26;
      COST_ARR[1][1][3] = 4.03;
      COST_ARR[1][1][4] = 0.98;
      COST_ARR[1][1][5] = 1.89;

      COST_ARR[1][2][0] = 1.18;
      COST_ARR[1][2][1] = 1.26;
      COST_ARR[1][2][3] = 2.00;
      COST_ARR[1][2][4] = 0.98;
      COST_ARR[1][2][5] = 1.99;

      COST_ARR[1][3][0] = 1.18;
      COST_ARR[1][3][1] = 1.26;
      COST_ARR[1][3][2] = 0.0;
      COST_ARR[1][3][4] = 0.98;
      COST_ARR[1][3][5] = 1.99;

      COST_ARR[1][4][1] = 0.98;
      COST_ARR[1][4][2] = 0.98;
      COST_ARR[1][4][3] = 0.98;
      COST_ARR[1][4][4] = 3.98;
      COST_ARR[1][4][5] = 1.91;

      COST_ARR[1][5][1] = 1.88;
      COST_ARR[1][5][2] = 1.96;
      COST_ARR[1][5][3] = 2.11;
      COST_ARR[1][5][4] = 4.99;
      COST_ARR[1][5][5] = 1.91;

      // Taxi cost
      COST_ARR[2][0][1] = 3.22;
      COST_ARR[2][0][2] = 6.96;
      COST_ARR[2][0][3] = 8.50;
      COST_ARR[2][0][4] = 4.98;
      COST_ARR[2][0][5] = 18.40;

      COST_ARR[2][1][0] = 4.32;
      COST_ARR[2][1][2] = 7.84;
      COST_ARR[2][1][3] = 9.38;
      COST_ARR[2][1][4] = 4.76;
      COST_ARR[2][1][5] = 18.18;

      COST_ARR[2][2][0] = 8.30;
      COST_ARR[2][2][1] = 7.96;
      COST_ARR[2][2][3] = 4.54;
      COST_ARR[2][2][4] = 6.42;
      COST_ARR[2][2][5] = 22.58;

      COST_ARR[2][3][0] = 8.74;
      COST_ARR[2][3][1] = 8.40;
      COST_ARR[2][3][2] = 3.22;
      COST_ARR[2][3][4] = 6.64;
      COST_ARR[2][3][5] = 22.80;

      COST_ARR[2][4][0] = 5.32;
      COST_ARR[2][4][1] = 4.76;
      COST_ARR[2][4][2] = 4.98;
      COST_ARR[2][4][3] = 6.52;
      COST_ARR[2][4][5] = 18.40;

      COST_ARR[2][5][0] = 22.48;
      COST_ARR[2][5][1] = 19.40;
      COST_ARR[2][5][2] = 21.48;
      COST_ARR[2][5][3] = 23.68;
      COST_ARR[2][5][4] = 21.60;

    }

    public static final int[][][] TIME_ARR = new int[3][NUM_DESTINATIONS][NUM_DESTINATIONS];
    static{
      //Walking time
      TIME_ARR[0][0][1] = 14;
      TIME_ARR[0][0][2] = 69;
      TIME_ARR[0][0][3] = 76;
      TIME_ARR[0][0][4] = 28;
      TIME_ARR[0][0][5] = 269;

      TIME_ARR[0][1][0] = 14;
      TIME_ARR[0][1][2] = 81;
      TIME_ARR[0][1][3] = 88;
      TIME_ARR[0][1][4] = 39;
      TIME_ARR[0][1][5] = 264;

      TIME_ARR[0][2][0] = 69;
      TIME_ARR[0][2][1] = 81;
      TIME_ARR[0][2][3] = 12;
      TIME_ARR[0][2][4] = 47;
      TIME_ARR[0][2][5] = 270;

      TIME_ARR[0][3][0] = 76;
      TIME_ARR[0][3][1] = 88;
      TIME_ARR[0][3][2] = 12;
      TIME_ARR[0][3][4] = 55;
      TIME_ARR[0][3][5] = 285;

      TIME_ARR[0][4][0] = 28;
      TIME_ARR[0][4][1] = 39;
      TIME_ARR[0][4][2] = 47;
      TIME_ARR[0][4][3] = 55;
      TIME_ARR[0][4][5] = 264;

      TIME_ARR[0][5][0] = 269;
      TIME_ARR[0][5][1] = 264;
      TIME_ARR[0][5][2] = 270;
      TIME_ARR[0][5][3] = 285;
      TIME_ARR[0][5][4] = 264;

      //Bus time
      TIME_ARR[1][0][1] = 17;
      TIME_ARR[1][0][2] = 26;
      TIME_ARR[1][0][3] = 35;
      TIME_ARR[1][0][4] = 19;
      TIME_ARR[1][0][5] = 84;

      TIME_ARR[1][1][0] = 17;
      TIME_ARR[1][1][2] = 31;
      TIME_ARR[1][1][3] = 38;
      TIME_ARR[1][1][4] = 24;
      TIME_ARR[1][1][5] = 85;

      TIME_ARR[1][2][0] = 24;
      TIME_ARR[1][2][1] = 29;
      TIME_ARR[1][2][3] = 10;
      TIME_ARR[1][2][4] = 18;
      TIME_ARR[1][2][5] = 85;

      TIME_ARR[1][3][0] = 33;
      TIME_ARR[1][3][1] = 38;
      TIME_ARR[1][3][2] = 10;
      TIME_ARR[1][3][4] = 27;
      TIME_ARR[1][3][5] = 92;

      TIME_ARR[1][4][0] = 18;
      TIME_ARR[1][4][1] = 23;
      TIME_ARR[1][4][2] = 19;
      TIME_ARR[1][4][3] = 28;
      TIME_ARR[1][4][5] = 83;

      TIME_ARR[1][5][0] = 86;
      TIME_ARR[1][5][1] = 87;
      TIME_ARR[1][5][2] = 86;
      TIME_ARR[1][5][3] = 96;
      TIME_ARR[1][5][4] = 84;

      // Taxi cost
      TIME_ARR[2][0][1] = 3;
      TIME_ARR[2][0][2] = 14;
      TIME_ARR[2][0][3] = 19;
      TIME_ARR[2][0][4] = 8;
      TIME_ARR[2][0][5] = 30;

      TIME_ARR[2][1][0] = 6;
      TIME_ARR[2][1][2] = 13;
      TIME_ARR[2][1][3] = 18;
      TIME_ARR[2][1][4] = 8;
      TIME_ARR[2][1][5] = 29;

      TIME_ARR[2][2][0] = 12;
      TIME_ARR[2][2][1] = 14;
      TIME_ARR[2][2][3] = 9;
      TIME_ARR[2][2][4] = 11;
      TIME_ARR[2][2][5] = 31;

      TIME_ARR[2][3][0] = 13;
      TIME_ARR[2][3][1] = 14;
      TIME_ARR[2][3][2] = 4;
      TIME_ARR[2][3][4] = 12;
      TIME_ARR[2][3][5] = 32;

      TIME_ARR[2][4][0] = 7;
      TIME_ARR[2][4][1] = 8;
      TIME_ARR[2][4][2] = 9;
      TIME_ARR[2][4][3] = 14;
      TIME_ARR[2][4][5] = 30;

      TIME_ARR[2][5][0] = 32;
      TIME_ARR[2][5][1] = 29;
      TIME_ARR[2][5][2] = 32;
      TIME_ARR[2][5][3] = 36;
      TIME_ARR[2][5][4] = 30;

    }

}
