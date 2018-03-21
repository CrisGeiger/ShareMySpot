/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sharemyspot.ejb.BookingBean;
import sharemyspot.ejb.UserBean;
import sharemyspot.jpa.Booking;
import  sharemyspot.jpa.User;

/**
 *
 * @author Becker
 * Servlet dient dazu die BookingListe abzurufen, wenn der User diese anfragt
 */
@WebServlet(name = "BookingListServlet", urlPatterns = {"/Bookings"})
public class BookingListServlet extends HttpServlet {

    @EJB UserBean userBean;
    
    @EJB BookingBean bb;

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

        User user= this.userBean.getCurrentUser();
        List<Booking> bookingList= bb.findBookingsByUsername(user.getUsername());
        
        if(bookingList!=null){
         request.setAttribute("bookingList",bookingList);   
        }
        else{
            try {
                throw new Exception();
            } catch (Exception ex) {
      
            }
        }
        request.getRequestDispatcher("/WEB-INF/app/Bookings.jsp").forward(request, response);
        
    }
}
