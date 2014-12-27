package com;
import java.util.*;
import java.io.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
public class ForBangalore {
    public ArrayList<ArrayList<String>> bangaloreRouteData(String number)
    {
        HashMap<String,ArrayList<ArrayList<String>>> hm=new HashMap<String,ArrayList<ArrayList<String>>>();
        try
        {
            
            BufferedReader br=new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Bangalore\\bmtc-master\\data\\routes.geojson"));
            String line=br.readLine();
            StringBuffer text=new StringBuffer();
            while(line!=null)
            {
                text.append(line);
                line=br.readLine();
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
                String origin=(String)jso2.get("origin");
                String dest=(String)jso2.get("dest");
                String stopData=(String) jso2.get("routes_map_json_content");
                Object obj3 = JSONValue.parse(stopData);
                JSONArray jar1 = (JSONArray) obj3;
                for(int j=0;j<jar1.size();j++)
                {
                    Object obj4=jar1.get(j);
                    JSONObject jso4 = (JSONObject) obj4;
                    String stopName=(String) jso4.get("busstop");
                    stopNameList.add(stopName);
                    JSONArray jar2 = (JSONArray) jso4.get("latlons");
                    String lat=(String) jar2.get(0);
                    String lng=(String) jar2.get(1);
                    stopLatList.add(lat);
                    stopLngList.add(lng);
                   // System.out.println(stopName+"  "+lat+"  "+lng);                  
                }      
                fullList.add(stopNameList);
                fullList.add(stopLatList);
                fullList.add(stopLngList);
                hm.put(route_no, fullList);
            }
             //System.out.println(hm);            
        }
        catch(Exception ex)
        {
            System.out.println();
        }
        return hm.get(number);
    }

}
