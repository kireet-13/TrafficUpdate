package com;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class BusMatrix {

    public static void main(String[] args) throws Exception {
        ArrayList<String> bus_stand = new ArrayList<String>();
        HashMap<String, ArrayList<String>> hm1 = new HashMap<String, ArrayList<String>>();
        FileReader fr = new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\stage.json");
        BufferedReader br = new BufferedReader(fr);
        String line = br.readLine();
        StringBuffer data = new StringBuffer();
        while (line != null) {
            data.append(line);
            line = br.readLine();
        }
        //System.out.println(data);
        Object obj = JSONValue.parse(data.toString());
        JSONArray jar = (JSONArray) obj;
        int size = jar.size();
        for (int i = 0; i < size; i++) {
            ArrayList<String> al = new ArrayList<String>();
            String par = jar.get(i).toString();
            Object obj1 = JSONValue.parse(par);
            JSONObject jso1 = (JSONObject) obj1;
            String pk = jso1.get("pk").toString();
            String par1 = jso1.get("fields").toString();
            Object obj2 = JSONValue.parse(par1);
            JSONObject jso2 = (JSONObject) obj2;
            al.add(jso2.get("name").toString());
            al.add(jso2.get("coordinates").toString());
            hm1.put(pk, al);

        }
        //System.out.println(hm1);

        HashMap<String, String> hm2 = new HashMap<String, String>();
        FileReader fr1 = new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\route.json");
        BufferedReader br1 = new BufferedReader(fr1);
        String line1 = br1.readLine();
        StringBuffer data1 = new StringBuffer();
        while (line1 != null) {
            data1.append(line1);
            line1 = br1.readLine();
        }
        Object obj_1 = JSONValue.parse(data1.toString());
        JSONArray jar_1 = (JSONArray) obj_1;
        int size_1 = jar_1.size();
        for (int j = 0; j < size_1; j++) {
            String par_1 = jar_1.get(j).toString();
            Object obj_2 = JSONValue.parse(par_1);
            JSONObject jso1 = (JSONObject) obj_2;
            String pk = jso1.get("pk").toString();
            String fields = jso1.get("fields").toString();
            Object obj_3 = JSONValue.parse(fields);
            JSONObject jso2 = (JSONObject) obj_3;
            String route_no = jso2.get("name").toString();
            hm2.put(pk, route_no);
        }
        //System.out.println(hm2);

        HashMap<String, ArrayList<String>> hm3 = new HashMap<String, ArrayList<String>>();
        for (int ii = 1; ii <= 570; ii++) {
            hm3.put(hm2.get(ii + ""), new ArrayList<String>());
        }

        FileReader fr2 = new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\stageseq.json");
        BufferedReader br2 = new BufferedReader(fr2);
        String line2 = br2.readLine();
        StringBuffer data2 = new StringBuffer();
        while (line2 != null) {
            data2.append(line2);
            line2 = br2.readLine();
        }
        Object obj_2 = JSONValue.parse(data2.toString());
        JSONArray jar_2 = (JSONArray) obj_2;
        int size_2 = jar_2.size();
        ArrayList<String> arr = new ArrayList<String>();
        for (int k = 0; k < size_2; k++) {
            String par_2 = jar_2.get(k).toString();
            Object obj_3 = JSONValue.parse(par_2);
            JSONObject jso2 = (JSONObject) obj_3;
            String fields_1 = jso2.get("fields").toString();
            Object obj_4 = JSONValue.parse(fields_1);
            JSONObject jso3 = (JSONObject) obj_4;
            String route_index = jso3.get("route").toString();

            Object obj_5 = JSONValue.parse(fields_1);
            JSONObject jso4 = (JSONObject) obj_5;
            String waypoint = jso4.get("stage").toString();

            hm3.get(hm2.get(route_index)).add(hm1.get(waypoint).get(0));
        }
        ArrayList<String> stops = new ArrayList<String>();
        Iterator it = hm1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            String str = hm1.get(pair.getKey()).get(0);
            if (!stops.contains(str)) {
                stops.add(str);
            }
        }
        //System.out.println(stops);

        ArrayList<String> buses = new ArrayList<String>();
        Iterator it1 = hm2.entrySet().iterator();
        while (it1.hasNext()) {
            Map.Entry pair = (Map.Entry) it1.next();
            String str = hm2.get(pair.getKey());
            if (!buses.contains(str)) {
                buses.add(str);
            }
        }
        //System.out.println(buses);

        int mat[][] = new int[buses.size() + 1][stops.size()];
        for (int a = 0; a < buses.size(); a++) {
            ArrayList<String> arr1 = new ArrayList<String>();
            arr1.addAll(hm3.get(buses.get(a) + ""));
            for (int b = 0; b < stops.size(); b++) {
                for (int y = 0; y < arr1.size(); y++) {
                    if (stops.get(b).equals(arr1.get(y))) {
                        mat[a][b] = 1;
                    }
                }
            }
        }
        for (int ii = 0; ii < stops.size(); ii++) {
            int count = 0;
            for (int jj = 0; jj < buses.size(); jj++) {
                if (mat[jj][ii] == 1) {
                    count = count + 1;
                }
            }
            mat[buses.size()][ii] = count;
        }

        for (int u = 0; u < buses.size() + 1; u++) {
            for (int v = 0; v < stops.size(); v++) {
                System.out.print(mat[u][v] + " ");
            }
            System.out.println("");
        }
    }

}