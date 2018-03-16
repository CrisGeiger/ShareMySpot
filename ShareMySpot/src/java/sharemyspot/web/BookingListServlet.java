/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.web;

import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sharemyspot.ejb.UserBean;
import  sharemyspot.jpa.User;

/**
 *
 * @author Becker
 * Servlet dient dazu die BookingListe abzurufen, wenn der User diese anfragt
 */
@WebServlet(name = "BookingListServlet", urlPatterns = {"/BookingListServlet"})
public class BookingListServlet extends HttpServlet {

    @EJB UserBean userBean;

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
        List<User> bookingList=user.getBookingList();
        String messageNoSpots="Es sind keine Parkplätze gebucht.";
        
        if(bookingList!=null){
         request.setAttribute("bookingList",bookingList);   
        }
        else{
            request.setAttribute("messageNoSpots",messageNoSpots);
        }
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/app/SpotList.jsp");
        
    }
}
