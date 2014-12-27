/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import com.BangaloreBusInfo;
import com.BusData;
import com.ForBangalore;
import com.ForMumbai;
import com.LatLong;
import com.MumbaiBusInfo;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author kanu
 */
@WebServlet(name = "GetRoute", urlPatterns = {"/GetRoute"})
public class GetRoute extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /*String routenumber = request.getParameter("text");
        ArrayList<BangaloreBusInfo> send=new ArrayList<BangaloreBusInfo>();
        ForBangalore fbr=new ForBangalore();
        ArrayList<ArrayList<String>> temp=fbr.bangaloreRouteData(routenumber);
        ArrayList<String> one=temp.get(0);
        ArrayList<String> two=temp.get(1);
        ArrayList<String> three=temp.get(2);
        for(int i=0;i<one.size();i++)
        {
            BangaloreBusInfo b=new BangaloreBusInfo(one.get(i), Double.parseDouble(two.get(i)), Double.parseDouble(three.get(i)));
            send.add(b);
            System.out.println(one.get(i));
        }
        request.setAttribute("send", send);
        request.getRequestDispatcher("ShowLineRoute.jsp").forward(request, response);*/
        ForMumbai fr=new ForMumbai();
        String routenumber = request.getParameter("text");
        ArrayList<MumbaiBusInfo> send=fr.main(routenumber);
        request.setAttribute("send", send);
        request.getRequestDispatcher("ShowLineRoute.jsp").forward(request, response);
        
        
        
       /* try {
            ArrayList<LatLong> send=new ArrayList<LatLong>();
            String routenumber = request.getParameter("text");
            BusData b = new BusData();
            ArrayList<String> al = b.getPoints(routenumber);
            for (String str : al) {
                str = str + " bus stop,delhi";
                String url = "https://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(str) + "&sensor=false&key=AIzaSyC-SQ14QC6ZcilMloviU-KPU-EfmA8qnlY";
                URL url1=new URL(url);
                System.out.println(url);
                StringBuffer text = new StringBuffer();
                HttpURLConnection conn = (HttpURLConnection) url1.openConnection();
                conn.connect();
                InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
                BufferedReader buff = new BufferedReader(in);
                String st1 = buff.readLine();
                while (st1 != null) {
                    text.append(st1);
                    st1 = buff.readLine();
                }
                String data = text.toString();
                Object obj = JSONValue.parse(data);
                JSONObject jso = (JSONObject) obj;
                JSONArray arr1 = (JSONArray) jso.get("results");
                if(arr1.size()<=2)
                {
                String data1 = arr1.get(0).toString();
                Object obj1 = JSONValue.parse(data1);
                JSONObject jso1 = (JSONObject) obj1;
                String address_updated = jso1.get("formatted_address").toString();
                //System.out.println(address_updated);
                String data2 = jso1.get("geometry").toString();
                Object obj2 = JSONValue.parse(data2);
                JSONObject jso2 = (JSONObject) obj2;
                String data3 = jso2.get("location").toString();
                Object obj3 = JSONValue.parse(data3);
                JSONObject jso3 = (JSONObject) obj3;
                double lat = Double.parseDouble(jso3.get("lat").toString());
                double lng = Double.parseDouble(jso3.get("lng").toString());
                System.out.println(lat+"   "+lng);
                send.add(new LatLong(lat, lng));
                }
                
            }
            System.out.println(al);
            request.setAttribute("al", al);
            request.setAttribute("send", send);
            request.getRequestDispatcher("ShowLineRoute.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(GetRoute.class.getName()).log(Level.SEVERE, null, ex);
        }*/

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
