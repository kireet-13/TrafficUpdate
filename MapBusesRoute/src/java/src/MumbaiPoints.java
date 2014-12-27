/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package src;

import com.MumbaiPointsInfo;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 *
 * @author kanu
 */
@WebServlet(name = "MumbaiPoints", urlPatterns = {"/MumbaiPoints"})
public class MumbaiPoints extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        ArrayList<MumbaiPointsInfo> send=new ArrayList<MumbaiPointsInfo>();
        BufferedReader br=new BufferedReader(new FileReader("F:\\3rdSEM\\capstone\\Mumbai\\points.txt"));
        String line=br.readLine();
        while(line!=null)
        {
            
            StringTokenizer st=new StringTokenizer(line,"-,,");
            st.nextToken();
            double lat=Double.parseDouble(st.nextToken());
            double lng=Double.parseDouble(st.nextToken());
            MumbaiPointsInfo m=new MumbaiPointsInfo(lat, lng);
            send.add(m);    
            line=br.readLine();
        }
        request.setAttribute("send", send);
        request.getRequestDispatcher("MumbaiPoints.jsp").forward(request, response);
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
