package com;
import java.util.*;
import java.io.*;
import java.util.Map.Entry;
public class BusStandTimeFrequency {
    public static void main(String[] args)
    {
        ArrayList<String> al=new ArrayList<String>();
        HashMap<String,ArrayList<String>> hm1=new HashMap<String,ArrayList<String>>();
        HashMap<String,ArrayList<ArrayList<String>>> hm2=new HashMap<String,ArrayList<ArrayList<String>>>();
        HashMap<String,ArrayList<String>> hm3=new HashMap<String,ArrayList<String>>();
        for(String stand:al)
        {
            hm3.put(stand, new ArrayList<String>());
        }
        Iterator itr=hm3.entrySet().iterator();
        while(itr.hasNext())
        {
            Entry pair=(Entry) itr.next();
            ArrayList<ArrayList<String>> temp=(ArrayList<ArrayList<String>>) pair.getValue();
            ArrayList<String> temp1=temp.get(2);
            
        }        
    }    
}
