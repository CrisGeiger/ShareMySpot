/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import javax.ejb.Stateless;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author cgeiger1
 */
@Entity
@Stateless
public class Favorit implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id = 0L;
    
    @ManyToOne
    private Spot spot;
    
    @ManyToOne
    private User user;
    
//<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public Favorit() {
        
    }
    
    public Favorit(Spot spot, User user) {
        this.spot = spot;
        this.user = user;
    }
//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="Getter und Setter">
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Spot getSpot() {
        return spot;
    }
    
    public void setSpot(Spot spot) {
        this.spot = spot;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
//</editor-fold>
    
}
