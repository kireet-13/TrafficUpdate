/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package trafficdata;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class GetGeoInfo {
    public static void main(String[] args) throws URISyntaxException
    {
        String address="Sadashiva,bangalore";
        String url = "https://maps.googleapis.com/maps/api/geocode/json?address="+address+"&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
        URI uri =new URI(url);
        try
        {
            URL page = new URL(uri.toString());
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
            JSONObject jso=(JSONObject) obj;
            JSONArray arr1=(JSONArray) jso.get("results");
            
            String data1=arr1.get(0).toString();
             Object obj1 = JSONValue.parse(data1);
             JSONObject jso1=(JSONObject) obj1;
             String address_updated=jso1.get("formatted_address").toString();
             System.out.println(address_updated);
             String data2=jso1.get("geometry").toString();
             Object obj2 = JSONValue.parse(data2);
             JSONObject jso2=(JSONObject) obj2;
             String data3=jso2.get("location").toString();
             Object obj3 = JSONValue.parse(data3);
             JSONObject jso3=(JSONObject) obj3;
             String lat=jso3.get("lat").toString();
             String lng=jso3.get("lng").toString();
           // int size = array.size();
            //System.out.println(data);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    
}
