/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.List;
import javax.ejb.Stateless;
import sharemyspot.jpa.Favorit;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.User;

/**
 *
 * @author cgeiger1
 */
@Stateless
public class FavoritBean extends EntityBean<Favorit, Long> {
    public FavoritBean() {
        super(Favorit.class);
    }
    
    public List<Favorit> getFavorits(String username) {
        return em.createQuery("SELECT f FROM Favorit f WHERE f.user.username = :username")
                .setParameter("user", username)
                .getResultList();
    }
    
//hier fehlt noch eine Methode, in der ein Favoriten-Objekt vom Benutzer angelegt wird
    public Favorit addToFavorit(User user, Spot spot) {
        Favorit fav = new Favorit(spot, user);
        return fav;
    }
    
}
