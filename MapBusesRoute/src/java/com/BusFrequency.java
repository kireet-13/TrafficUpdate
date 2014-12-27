package com;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
public class BusFrequency {
    public static void main(String[] args) throws Exception
    {
        ArrayList<String> bus_stand=new ArrayList<String>();
        HashMap<String,ArrayList<String>> hm1=new HashMap<String,ArrayList<String>>();
        FileReader fr=new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\stage.json");
        BufferedReader br=new BufferedReader(fr);
        String line=br.readLine();
        StringBuffer data=new StringBuffer();
        while(line!=null)
        {
            data.append(line);
            line=br.readLine();
        }
        //System.out.println(data);
        Object obj=JSONValue.parse(data.toString());
        JSONArray jar= (JSONArray) obj;
        int size=jar.size();
        for(int i=0;i<size;i++)
        {
            ArrayList<String> al=new ArrayList<String>();
            String par=jar.get(i).toString();
            Object obj1=JSONValue.parse(par);
            JSONObject jso1=(JSONObject) obj1;
            String pk=jso1.get("pk").toString();
            String par1=jso1.get("fields").toString();
            Object obj2=JSONValue.parse(par1);
            JSONObject jso2=(JSONObject) obj2;
            al.add(jso2.get("name").toString());
            al.add(jso2.get("coordinates").toString());
            hm1.put(pk,al);
                      
        }        
        //System.out.println(hm1);
        
        
        
        HashMap<String, String> hm2 = new HashMap<String, String>();
        FileReader fr1=new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\route.json");
        BufferedReader br1=new BufferedReader(fr1);
        String line1=br1.readLine();
        StringBuffer data1=new StringBuffer();
        while(line1!=null)
        {
            data1.append(line1);
            line1=br1.readLine();
        }
        Object obj_1=JSONValue.parse(data1.toString());
        JSONArray jar_1= (JSONArray) obj_1;
        int size_1=jar_1.size();
        for(int j=0;j<size_1;j++){
            String par_1=jar_1.get(j).toString();
            Object obj_2=JSONValue.parse(par_1);
            JSONObject jso1=(JSONObject) obj_2;
            String pk=jso1.get("pk").toString();
            String fields=jso1.get("fields").toString();
            Object obj_3=JSONValue.parse(fields);
            JSONObject jso2=(JSONObject) obj_3;
            String route_no=jso2.get("name").toString();
            hm2.put(pk, route_no);
        }
        //System.out.println(hm2);
        
        
        
        HashMap<String, ArrayList<String>> hm3 = new HashMap<String, ArrayList<String>>();
        for(int ii=1; ii<=570; ii++){
            hm3.put(hm2.get(ii+""),new ArrayList<String>());
        }
        
        FileReader fr2=new FileReader("F:\\3rdSEM\\capstone\\DTC-Bus-Routes-master\\rost\\fixtures\\stageseq.json");
        BufferedReader br2=new BufferedReader(fr2);
        String line2=br2.readLine();
        StringBuffer data2=new StringBuffer();
        while(line2!=null)
        {
            data2.append(line2);
            line2=br2.readLine();
        }
        Object obj_2=JSONValue.parse(data2.toString());
        JSONArray jar_2= (JSONArray) obj_2;
        int size_2=jar_2.size();
        ArrayList<String> arr = new ArrayList<String>();
        for(int k=0;k<size_2;k++){
            String par_2=jar_2.get(k).toString();
            Object obj_3=JSONValue.parse(par_2);
            JSONObject jso2=(JSONObject) obj_3;
            String fields_1=jso2.get("fields").toString(); 
            Object obj_4=JSONValue.parse(fields_1);
            JSONObject jso3=(JSONObject) obj_4;
            String route_index=jso3.get("route").toString();
            
            Object obj_5=JSONValue.parse(fields_1);
            JSONObject jso4=(JSONObject) obj_5;
            String waypoint=jso4.get("stage").toString();
              
            hm3.get(hm2.get(route_index)).add(hm1.get(waypoint).get(0));
        }
        Iterator it=hm1.entrySet().iterator();
        while(it.hasNext())
        {
            Entry pair=(Entry) it.next();
            ArrayList<String> temp=(ArrayList<String>) pair.getValue();
            bus_stand.add(temp.get(0).trim());
            
        }
        HashMap<String,Integer> stand_occupancy=new HashMap<String,Integer>();
        for(String stand:bus_stand)
        {
            stand_occupancy.put(stand,0);
        }
        Iterator it1=hm3.entrySet().iterator();
        while(it1.hasNext())
        {
            Entry pair=(Entry) it1.next();
            ArrayList<String> temp=(ArrayList<String>) pair.getValue();
            for(String name:temp)
            {
                if(stand_occupancy.containsKey(name.trim()))
                {
                    stand_occupancy.put(name,stand_occupancy.get(name)+1);
                }
            }
        }
        Set<Entry<String, Integer>> set = stand_occupancy.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(set);
        Collections.sort( list, new Comparator<Map.Entry<String, Integer>>()
        {
            public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 )
            {
                return (o2.getValue()).compareTo( o1.getValue() );
            }
        } );
        for(Map.Entry<String, Integer> entry:list){
            //System.out.println(entry.getKey()+" ==== "+entry.getValue());
        }
        System.out.println(hm3);
        
        
    }
    
}



        //System.out.println(hm3);
  