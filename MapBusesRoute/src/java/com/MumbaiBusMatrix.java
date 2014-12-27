/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.StringTokenizer;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kireet1337
 */
public class MumbaiBusMatrix {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, IOException, ParseException, java.text.ParseException {
        // TODO code application logic here

        String key = "AmqWvgp-Cua-H_9k97YXPrAsTGguupHyZMwo0XVklVuMO2le8MUVYxRsrooBzm8O";
        HashSet<String> hs = new HashSet<String>();
        HashMap<String, ArrayList<String>> hm = new HashMap<String, ArrayList<String>>();

        FileReader fr = new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\frequencies.txt");

        BufferedReader br = new BufferedReader(fr);

        br.readLine();
        String line;
        while ((line = br.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line, ",");
            if (st.countTokens() == 4) {
                hs.add(st.nextToken());
            } else {
                hs.add(st.nextToken() + "," + st.nextToken());
            }
        }
        for (String base : hs) {
            hm.put(base, new ArrayList<String>());
        }

        FileReader fr1 = new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\frequencies.txt");

        BufferedReader br1 = new BufferedReader(fr1);

        br1.readLine();
        String line1;
        while ((line1 = br1.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(line1, ",");
            String key1;
            if (st.countTokens() == 4) {
                key1 = st.nextToken();
            } else {
                key1 = st.nextToken() + "," + st.nextToken();
            }
            ArrayList<String> temp = hm.get(key1);

            String start = st.nextToken();
            StringTokenizer st1 = new StringTokenizer(start, ":");
            int x = Integer.parseInt(st1.nextToken()) * 60 * 60 + Integer.parseInt(st1.nextToken()) * 60 + Integer.parseInt(st1.nextToken());

            String end = st.nextToken();
            StringTokenizer st2 = new StringTokenizer(end, ":");
            int y = Integer.parseInt(st2.nextToken()) * 60 * 60 + Integer.parseInt(st2.nextToken()) * 60 + Integer.parseInt(st2.nextToken());

            String gap = st.nextToken();
            int z = Integer.parseInt(gap);

            while (x <= y) {
                int p;
                if (x >= 24 * 3600) {
                    p = x - 24 * 3600;
                } else {
                    p = x;
                }

                String str1 = (p / 3600) + "";
                String str2 = ((p % 3600) / 60) + "";
                String str3 = ((p % 3600) % 60) + "";
                if (str1.length() == 1) {
                    str1 = "0" + str1;
                }
                if (str2.length() == 1) {
                    str2 = "0" + str2;
                }
                if (str3.length() == 1) {
                    str3 = "0" + str3;
                }
                temp.add(str1 + ":" + str2 + ":" + str3);
                x = x + z;
            }
        }
        //System.out.println(hm);



        ArrayList<String> time_slots = new ArrayList<String>();

        String myTime = "00:00:00";
        for (int t = 0; t < 24; t++) {
            SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
            Date d = df.parse(myTime);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.SECOND, 3599);
            String newTime = df.format(cal.getTime());
            time_slots.add(myTime + "-" + newTime);

            d = df.parse(newTime);
            cal.setTime(d);
            cal.add(Calendar.SECOND, 1);
            myTime = df.format(cal.getTime());
        }
        //System.out.println(time_slots);


        HashMap<String, ArrayList<Integer>> hm_freq = new HashMap<String, ArrayList<Integer>>();

        for (String base1 : hs) {
            hm_freq.put(base1, new ArrayList<Integer>());
        }

        Iterator it = hm.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();

            ArrayList temp1 = hm_freq.get(pair.getKey());
            for (int u = 0; u < 24; u++) {
                temp1.add(0);
            }

            ArrayList arr = hm.get(pair.getKey());
            for (int p = 0; p < time_slots.size(); p++) {

                int e = 0;

                String parts[] = time_slots.get(p).split("-");
                String slot_start = parts[0];
                SimpleDateFormat df1 = new SimpleDateFormat("HH:mm:ss");
                Date d1 = df1.parse(slot_start);
                String slot_end = parts[1];
                SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
                Date d2 = df2.parse(slot_end);

                for (int q = 0; q < arr.size(); q++) {
                    String str = arr.get(q).toString();
                    SimpleDateFormat df3 = new SimpleDateFormat("HH:mm:ss");
                    Date d3 = df3.parse(str);

                    if (d3.after(d1) && d3.before(d2)) {
                        e++;
                    }
                }
                temp1.set(p, e);
            }
        }
        //System.out.println(hm_freq);



        FileReader fr2 = new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\stops.txt");

        BufferedReader br2 = new BufferedReader(fr2);

        HashMap<Integer, String> hm_stop_id_name = new HashMap<Integer, String>();
        ArrayList<String> stop_names = new ArrayList<String>();

        br2.readLine();
        String line2;

        while ((line2 = br2.readLine()) != null) {
            String parts[] = line2.split(",");
            if (!stop_names.contains(parts[1])) {
                stop_names.add(parts[1]);
            }
            hm_stop_id_name.put(Integer.parseInt(parts[0]), parts[1]);
        }
        //System.out.println(stop_names.size());
        //System.out.println(hm_stop_id_name);


        FileReader fr3 = new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\stop_times.txt");

        BufferedReader br3 = new BufferedReader(fr3);

        HashMap<String, ArrayList<String>> hm_route_stops = new HashMap<String, ArrayList<String>>();
        for (String base2 : hs) {
            hm_route_stops.put(base2, new ArrayList<String>());
        }

        br3.readLine();
        String line3;

        while ((line3 = br3.readLine()) != null) {
            String parts[] = line3.split(",");
            String str = "";
            if (parts.length == 6) {
                str = parts[0] + "," + parts[1];
            } else {
                str = parts[0];
            }

            ArrayList<String> temp2 = hm_route_stops.get(str);
            temp2.add(hm_stop_id_name.get(Integer.parseInt(parts[parts.length - 2])));
        }
        System.out.println(hm_route_stops);


        int mat[][] = new int[stop_names.size()][24];
        for (int i = 0; i < stop_names.size(); i++) {
            for (int j = 0; j < 24; j++) {
                mat[i][j] = 0;
            }
        }


        Iterator ii = hs.iterator();
        while (ii.hasNext()) {
            String str = (String) ii.next();
            ArrayList<String> rr = new ArrayList<String>();
            rr.addAll(hm_route_stops.get(str));
            for (int h = 0; h < rr.size(); h++) {
                for (int i = 0; i < stop_names.size(); i++) {
                    if (rr.get(h).equals(stop_names.get(i))) {
                        ArrayList<Integer> ww = hm_freq.get(str);
                        for (int j = 0; j < ww.size(); j++) {
                            mat[i][j] = mat[i][j] + ww.get(j);
                        }
                    }
                }
            }
        }


        System.out.println(stop_names);
        for (int i = 0; i < stop_names.size(); i++) {
            for (int j = 0; j < 24; j++) {
                System.out.print(mat[i][j] + "  ");
            }
            System.out.println("");
        }
    }
}