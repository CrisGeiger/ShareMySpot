/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sharemyspot.ejb.SpotBean;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;

/**
 *
 * @author Studium
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    @EJB
    SpotBean SpotBean;
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
   

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
        
        request.setCharacterEncoding("utf-8");
        
        List<String> fehler= new ArrayList<>();
        
        
        String startDate=request.getParameter("search_startDate");
        String startTime=request.getParameter("search_startTime");
        String endDate=request.getParameter("search_endDate");
        String endTime=request.getParameter("search_endTime");
        String ort=request.getParameter("search_ort");
        String plz=request.getParameter("search_plz");
        String anschrift=request.getParameter("search_anschrift");
        String kategorie=request.getParameter("search_kategorie");
        
        
        Date duestartDate = WebUtils.parseDate(startDate);
        Time duestartTime = WebUtils.parseTime(startTime);
        
        Date dueendDate = WebUtils.parseDate(endDate);
        Time dueendTime = WebUtils.parseTime(endTime);
        if (kategorie != null && !kategorie.trim().isEmpty()) {
            try {
                
            } 
            catch (IllegalArgumentException ex) {
                fehler.add("Die ausgewählte Kategorie ist nicht vorhanden.");
            }
         
        }
    }

   

}
