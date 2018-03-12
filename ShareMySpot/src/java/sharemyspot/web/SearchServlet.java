/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
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
import javax.servlet.http.HttpSession;
import sharemyspot.ejb.SpotBean;
import sharemyspot.ejb.ValidationBean;
import sharemyspot.jpa.Spot;

/**
 *
 * @author Studium
 */
@WebServlet(name = "SearchServlet", urlPatterns = {"/SearchServlet"})
public class SearchServlet extends HttpServlet {

    @EJB
    SpotBean spotBean;
    
    @EJB
    ValidationBean validationBean;
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
        
        List<String> error= new ArrayList<>();
        
        /**boolean correctStartDate=false;// alle anderen Werte sind keine Pflicht einzugeben oder haben eine Message
        boolean correctStartTime=false;
        boolean correctEndDate=false;
        boolean correctEndTime=false;
 
       
        */boolean correctKategory=false;
        
        
        String startDate=request.getParameter("search_startDate");
        String startTime=request.getParameter("search_startTime");
        String endDate=request.getParameter("search_endDate");
        String endTime=request.getParameter("search_endTime");
        String place=request.getParameter("search_place");
        String plz=request.getParameter("search_plz");
        String adresse=request.getParameter("search_adresse"); // Abändern, wenn Attribute street vorhanden
        String kategory=request.getParameter("search_kategory");
        
        
        Date dueStartDate = WebUtils.parseDate(startDate);
        Time dueStartTime = WebUtils.parseTime(startTime);
        
        Date dueEndDate = WebUtils.parseDate(endDate);
        Time dueEndTime = WebUtils.parseTime(endTime);
        
        /**Spot spot = new Spot(dueStartDate, dueStartTime,dueEndDate,dueEndTime,place,plz,adresse,kategory);
        List<String> errors = this.validationBean.validate(spot);
        this.validationBean.validate(spot, errors);

         if (errors.isEmpty()) {
            this.spotBean.search(spot);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: neue Seite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/spotlist/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("spotList_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
       /**if(!startTime.trim().isEmpty()) {
            if(duestartTime!=null){
               correctStartTime=true;
           }
            else{
              error.add("Bitte geben Sie das StartZeit im Format HH:mm:ss ein.");
            }
        }
        */
       
        
        
        
    }
}
