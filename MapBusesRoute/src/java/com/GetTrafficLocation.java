
package com;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
public class GetTrafficLocation {
    public ArrayList<TrafficInfo> getLoc()
    {
        ArrayList<TrafficInfo> atl=new ArrayList<TrafficInfo>();
        try
        {
            FileReader fr=new FileReader("D:\\traffic_data_delhi_map.txt");
            Date d=new Date();
            Long dL=d.getTime();
            //System.out.println(dL);
            BufferedReader br=new BufferedReader(fr);
            String line=br.readLine();
            StringTokenizer st=new StringTokenizer(line,"###");
            while(line!=null)
            {
                st=new StringTokenizer(line,"###");
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(st.nextToken());
                Long dateL=date.getTime();
                
                String name=st.nextToken();
                if(!(name.equals("New Delhi, Delhi, India")))
                {
                    System.out.println(date);
                    double lat=Double.parseDouble(st.nextToken());
                    double lng=Double.parseDouble(st.nextToken());
                    TrafficInfo t=new TrafficInfo(name, lat, lng);
                    
                    atl.add(t);
                }
                line=br.readLine();
            }
                    
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        return atl;
    }
    
}
