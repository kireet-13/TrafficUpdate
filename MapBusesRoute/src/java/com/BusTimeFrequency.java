package com;

import java.io.*;
import java.util.*;

public class BusTimeFrequency {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\routes_time\\1column1.csv"));
        HashMap<String,ArrayList<ArrayList<String>>> hm=new HashMap<String,ArrayList<ArrayList<String>>>();
        String line = br.readLine();
        while (line != null) {
            if (line.startsWith("Route No.")) {
                StringTokenizer st = new StringTokenizer(line, " ");
                st.nextToken();
                st.nextToken();
                String routeNo = st.nextToken();
                br.readLine();
                line = br.readLine();
                st = new StringTokenizer(line, "##");
                String startPoint = st.nextToken();
                String endPoint = st.nextToken();
                ArrayList<String> stend=new ArrayList<String>();
                stend.add(startPoint);
                stend.add(endPoint);
                System.out.println(routeNo + "  " + startPoint + "  " + endPoint);               
                ArrayList<String> firstWay=new ArrayList<String>();
                ArrayList<String> secondWay=new ArrayList<String>();
                line=br.readLine().trim();
                while (!(line.startsWith("Route No."))) {
                    st = new StringTokenizer(line);
                    String fir=st.nextToken().trim();
                    String sec=st.nextToken().trim();
                    //System.out.println(fir+"   "+sec);
                    if(!(fir.equals("-")))
                    {
                        firstWay.add(fir);
                    }
                    if(!(sec.equals("-")))
                    {
                        secondWay.add(sec);
                    }
                    
                    line = br.readLine().trim();
                }
                ArrayList<ArrayList<String>> finalList=new ArrayList<ArrayList<String>>();
                finalList.add(stend);
                finalList.add(firstWay);
                finalList.add(secondWay);
                hm.put(routeNo,finalList);
            }
            line=br.readLine();
        }
        System.out.println(hm);
    }

}
