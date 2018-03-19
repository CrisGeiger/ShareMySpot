/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import sharemyspot.jpa.Booking;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.User;

/**
 *
 * @author cgeiger1
 */
@Stateless
@RolesAllowed("ShareMySpot-user")
public class BookingBean extends EntityBean <Booking, Long> {
    public BookingBean() {
        super(Booking.class);
    }
    
    
    public List<Booking> findBookingsByUsername(String username) {
        return em.createQuery("SELECT b FROM Booking b WHERE b.user.username = :username")
                .setParameter("username", username)
                .getResultList();
    }
    
    public Booking book(Date startDate, Date endDate, User user, Spot spot) {
        List<Booking>  bookings = em.createQuery("SELECT b FROM Booking b WHERE b.spot = :spot AND b.startDate >= :startDate OR b.endDate <= :endDate")
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .setParameter("SpotId", spot)
                .getResultList();
        
        Boolean bool1 = bookings.isEmpty();
        List<Booking> bookings2 = em.createQuery("SELECT b FROM Booking b where b.spot = :spot AND b.startDate <= :startDate and b.endDate >= :endDate")
                .setParameter("SpotId", spot)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();
        Boolean bool2 = bookings2.isEmpty();
        
        if (bool1 && bool2) {
            Booking newBook = new Booking(startDate, endDate, user, spot);
            saveNew(newBook);
            return newBook;
        }
        
        return null;
    }
    
}
