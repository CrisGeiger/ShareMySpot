/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import java.util.Date;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author cgeiger1
 */
@Entity
@Stateless
public class Booking implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id = 0L;
    
    @ManyToOne
    private User user;
    
    @ManyToOne
    private Spot spot;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date endDate = new Date();
    
    
//<editor-fold defaultstate="collapsed" desc="Konsruktoren">
    public Booking() {
        
    }
    
    public Booking(Date startDate, Date endDate, User user, Spot spot) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.spot = spot;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public Spot getSpot() {
        return spot;
    }
    
    public void setSpot(Spot spot) {
        this.spot = spot;
    }
    
    public Date getStartDate() {
        return startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    
//</editor-fold>
  
    
}
