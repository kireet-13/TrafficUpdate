package com;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.MumbaiBusInfo;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

public class TestBangaloreRoute {

    public static void main(String[] args) {
        String key = "AgcshwzkV7EoCAHx1RDRcnE2KzU_VS6TvkxzhuqpDuzF44B4h-XF9Hr40MRaKD5p";
        ArrayList<String> bus_stops = new ArrayList<String>();
        ArrayList<String> stops_taken = new ArrayList<String>();
        ArrayList<String> stops_rejected = new ArrayList<String>();
        ArrayList<String> coordinates = new ArrayList<String>();
        ArrayList<String> routes = new ArrayList<String>();
        ArrayList<String> route_id = new ArrayList<String>();

        try {
            
            BufferedReader br1 = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Bangalore\\bmtc-master\\data\\routes.geojson"));
            String line=br1.readLine();
            StringBuffer text=new StringBuffer();
            while(line!=null)
            {
                text.append(line);
                line=br1.readLine();
            }
            Object obj = JSONValue.parse(text.toString());
            JSONObject jso = (JSONObject) obj;
            JSONArray jar = (JSONArray) jso.get("features");
            for(int i=0;i<jar.size();i++)
            {
                ArrayList<ArrayList<String>> fullList=new ArrayList<ArrayList<String>>();
                ArrayList<String> stopNameList=new ArrayList<String>();
                ArrayList<String> stopLatList=new ArrayList<String>();
                ArrayList<String> stopLngList=new ArrayList<String>();
                Object obj1=jar.get(i);
                JSONObject jso1 = (JSONObject) obj1;
                Object obj2=jso1.get("properties");
                JSONObject jso2 = (JSONObject) obj2;
                String route_no=(String)jso2.get("route_no");
                route_id.add(route_no);
            }
            System.out.println(route_id.size());
            ForBangalore fr1 = new ForBangalore();
            int flag = 1;
            for (int kk = 0; kk < route_id.size(); kk++) {
                ArrayList<ArrayList<String>> send = fr1.bangaloreRouteData(route_id.get(kk));
                if (send.get(0).size() != 0) {
                    ArrayList<String> latList=send.get(1);
                    ArrayList<String> lngList=send.get(2);
                    for (int ii = 0; ii < latList.size(); ii++) {
                        
                        if (flag == 1) {
                            stops_taken.add(latList.get(ii) + "," + lngList.get(ii));
                            //System.out.println(iim.lat+","+iim.lng);
                            flag = 0;

                        }
                        for (int jj = ii; jj < ii + 4 && jj < latList.size(); jj++) {
                            
                            
                            String url_d = "http://dev.virtualearth.net/REST/V1/Routes?wp.0=" + latList.get(ii) + "," + lngList.get(ii) + "&wp.1=" + latList.get(jj) + "," + lngList.get(jj) + "&output=json&key=" + key;
                            //System.out.println(url_d);
                            URL url = new URL(url_d);
                            HttpURLConnection request = (HttpURLConnection) url.openConnection();
                            request.connect();

                            // Convert to a JSON object and then whole string using GSON API
                            JsonParser jp = new JsonParser(); //from gson
                            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
                            JsonObject rootobj = root.getAsJsonObject(); //may be an array, may be an object. 
                            String str = rootobj.toString();

                            JSONObject json = (JSONObject) new JSONParser().parse(str);
                            JSONArray get = (JSONArray) json.get("resourceSets");
                            String str1 = get.get(0).toString();

                            JSONObject json1 = (JSONObject) new JSONParser().parse(str1);
                            JSONArray get1 = (JSONArray) json1.get("resources");
                            String str2 = get1.get(0).toString();

                            JSONObject json2 = (JSONObject) new JSONParser().parse(str2);
                            String dist = json2.get("travelDistance").toString();

                            Double d = Double.parseDouble(dist);

                            if (d < 0.75 && ii!=jj) {
                                //System.out.println(d+" "+jjm.lat + "," + jjm.lng);
                                stops_rejected.add(latList.get(jj) + "," + lngList.get(jj));
                            }
                            if (d >= 0.75 && d <= 1.25 && ii != jj && !stops_taken.contains(latList.get(jj) + "," + lngList.get(jj)) && !stops_rejected.contains(latList.get(ii) + "," + lngList.get(ii))) {
                                if(!stops_taken.contains(latList.get(ii) + "," + lngList.get(ii)))
                                {
                                    stops_taken.add(latList.get(ii) + "," + lngList.get(ii));
                                }
                                stops_taken.add(latList.get(jj) + "," + lngList.get(jj));
                                routes.add(latList.get(ii) + "," + lngList.get(ii)+"-"+latList.get(jj) + "," + lngList.get(jj));
                                
                                break;
                            }
                            System.out.println(ii+" " + stops_taken);
                            //System.out.println(d);
                        }

                    }
                }


            }
            for (int ii = 0; ii < stops_taken.size(); ii++) {
                System.out.println(ii + "-" + stops_taken.get(ii));
            }
            ArrayList<String> routes_final = new ArrayList<String>();
            
            for(int p=0; p<routes.size(); p++){
                String lol = routes.get(p);
                String parts[] = lol.split("-");
                String ppp = stops_taken.indexOf(parts[0])+"-"+stops_taken.indexOf(parts[1]);
                routes_final.add(ppp);
            }
            for(int pp=0; pp<routes_final.size(); pp++){
                System.out.println(routes_final.get(pp));
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}