package trafficdata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class TrainData {

    public static void main(String[] args) {
        //String key="dd944ba6-693b-4e73-b365-3d3ea1d3208f";
        int total=0;
        HashMap<String, String> hm = new HashMap<String, String>();
        HashMap<String, ArrayList<String>> hm1 = new HashMap<String, ArrayList<String>>();
        String url = "http://api.erail.in/stations/?key=dd944ba6-693b-4e73-b365-3d3ea1d3208f";
        try {
            URL page = new URL(url);
            StringBuffer text = new StringBuffer();
            HttpURLConnection conn = (HttpURLConnection) page.openConnection();
            conn.connect();
            InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
            BufferedReader buff = new BufferedReader(in);
            String st = buff.readLine();
            while (st != null) {
                text.append(st);
                st = buff.readLine();
            }
            String data = text.toString();
            Object obj = JSONValue.parse(data);
            JSONArray array = (JSONArray) obj;
            int size = array.size();
            for (int i = 1; i < size; i++) {
                JSONObject obj2 = (JSONObject) array.get(i);
                String code = obj2.get("code").toString();
                String name = obj2.get("name").toString();
                hm.put(code, name);
            }
            Iterator itr = hm.entrySet().iterator();
            while (itr.hasNext()) {
                Map.Entry pairs = (Map.Entry) itr.next();
                //System.out.println(pairs.getKey());
                String dest = (String) pairs.getKey();
                String source="NdLS";
                URL url1 = new URL("http://api.erail.in/trains/?key=dd944ba6-693b-4e73-b365-3d3ea1d3208f&stnfrom=" + source + "&stnto="+dest+"&date=05-Dec-2014");
                conn = (HttpURLConnection) url1.openConnection();
                conn.connect();
                StringBuffer text1 = new StringBuffer();
                in = new InputStreamReader((InputStream) conn.getContent());
                buff = new BufferedReader(in);
                st = buff.readLine();
                while (st != null) {
                    text1.append(st);
                    st = buff.readLine();
                }
                Object js=JSONValue.parse(text1.toString());
                JSONObject obj2=(JSONObject)js;
                JSONArray arr1=(JSONArray) obj2.get("result");
                if(arr1.size()>0)
                {
                    for(int j=0;j<arr1.size();j++)
                    {
                        JSONObject obj3=(JSONObject) arr1.get(j);
                        String trainNo=(String) obj3.get("trainno");
                        ArrayList<String> al=new ArrayList<String>();
                        if(!hm1.containsKey(trainNo))
                        {
                            
//                             String from=(String) obj3.get("fromname");
//                            //String to=(String) obj3.get("toname");
//                            //String arrTime=(String) obj3.get("arr");
//                            String depTime=(String) obj3.get("dep");
//                            al.add(from);
//                            al.add(depTime);
                            hm1.put(trainNo,al);
                            total=total+1;
                           // System.out.println(trainNo+"  "+from+"   "+depTime);
                        }
                        
                    }
                }
                
                // avoids a ConcurrentModificationException
            }
           System.out.println(total);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
