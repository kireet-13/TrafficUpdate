package com;

import java.io.*;
import java.util.*;
import java.util.Map.Entry;

public class ForMumbai {

    public ArrayList<MumbaiBusInfo>  main(String getCode) {
        ArrayList<MumbaiBusInfo> last=new ArrayList<MumbaiBusInfo>();
        HashMap<String, ArrayList<String>> routeInfo = new HashMap<String, ArrayList<String>>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\routes.txt"));
            String line = br.readLine();
            StringTokenizer st;
            while (line != null) {
                ArrayList<String> temp = new ArrayList<String>();
                st = new StringTokenizer(line, ",");
                String key = st.nextToken();
                temp.add(st.nextToken());
                temp.add(st.nextToken());
                temp.add(st.nextToken());
                temp.add(st.nextToken());
                routeInfo.put(key, temp);
                line = br.readLine();
            }
            //System.out.println(routeInfo);
            BufferedReader br1 = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\trips.txt"));
            HashMap<String, ArrayList<String>> tripInfo = new HashMap<String, ArrayList<String>>();
            Iterator itr = routeInfo.entrySet().iterator();
            while (itr.hasNext()) {
                Entry pair = (Entry) itr.next();
                String key = (String) pair.getKey();
                tripInfo.put(key, new ArrayList<String>());
            }
            String trip = br1.readLine();
            while (trip != null) {
                st = new StringTokenizer(trip, ",");
                ArrayList<String> temp;
                String key = st.nextToken();
                st.nextToken();
                temp = tripInfo.get(key);
                temp.add(st.nextToken());
                tripInfo.put(key, temp);

                trip = br1.readLine();
            }
            //System.out.println(tripInfo);
            String routeId = getCode;
            ArrayList<String> stopsId = new ArrayList<>();
            if (tripInfo.containsKey(routeId)) {
                stopsId.add(tripInfo.get(routeId).get(0));
            }
            //System.out.println(stopsId);
            BufferedReader br2 = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\stop_times.txt"));
            HashMap<String, ArrayList<String>> standInfo = new HashMap<String, ArrayList<String>>();
            ArrayList<String> standCodes = new ArrayList<String>();
            String stand = br2.readLine();
            stand = br2.readLine();
            while (stand != null) {
                st = new StringTokenizer(stand, ",");
                //System.out.println(st.countTokens());
                int count = st.countTokens();
                for (int kk = 1; kk <= count - 2; kk++) {
                    st.nextToken();
                }
                String number = st.nextToken();
                st = new StringTokenizer(stand, ",");
                if (count == 4 || count == 6) {
                    String key = st.nextToken() + "," + st.nextToken();
                    if (stopsId.contains(key)) {
                        standCodes.add(number);
                    }
                    //System.out.println(key+"  "+number);
                } else {
                    String key = st.nextToken();
                    if (stopsId.contains(key)) {
                        standCodes.add(number);
                    }
                    // System.out.println(st.nextToken()+"  "+number);
                }

                stand = br2.readLine();
            }

            //System.out.println(standCodes);
            HashMap<String,ArrayList<String>> atl=new HashMap<String,ArrayList<String>>();
            BufferedReader br3 = new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\ChaloBEST-master\\chaloBEST\\gtfs\\gtfs_mumbai_bus\\stops.txt"));
            
            br.readLine();
            String standData=br3.readLine();
            while(standData!=null)
            {
                st=new StringTokenizer(standData,",");
                ArrayList<String> temp=new ArrayList<String>();
                String code=st.nextToken();
                temp.add(st.nextToken());
                temp.add(st.nextToken());
                temp.add(st.nextToken());
                atl.put(code, temp);
                standData=br3.readLine();
            }
            for(String la:standCodes)
            {
                if(atl.containsKey(la))
                {
                    ArrayList<String> temp=atl.get(la);
                    String name=temp.get(0);
                    double lat=Double.parseDouble(temp.get(1));
                    double lng=Double.parseDouble(temp.get(2));
                    //System.out.println(name+"  "+lat+"  "+lng);
                    MumbaiBusInfo m=new MumbaiBusInfo(name, lat, lng);
                    last.add(m);
                }
            }
            
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return last;
    }

}
