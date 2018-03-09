/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import sharemyspot.jpa.Category;
import sharemyspot.jpa.Spot;
import sharemyspot.jpa.SpotStatus;
import sharemyspot.jpa.User;

/**
 *
 * @author JU_FI
 *
 * Einfache EJB mit den üblichen CRUD-Methoden für Parkplätze.
 */

@Stateless
@RolesAllowed("ShareMySpot-user")
public class SpotBean extends EntityBean<Spot, Long> {
    
    public SpotBean(){
        super(Spot.class);
    }
/**
     * Suche nach Parkplätzen anhand ihrer Kategorie, Status, PLZ, Ort und Eigentümer
     * @param owner
     * @param ort 
     * @param plz 
     * @param status
     * @param category
     * @param search
     * @return Liste mit den gefundenen Parkplätzen
     */
    public List<Spot> search(String search, User owner, Spot ort, Spot plz, SpotStatus status, Category category){
        
        //Hilfsojekt zum Bauen der Query
            CriteriaBuilder cd = this.em.getCriteriaBuilder();
    
        //Select t FROM Spot t
            CriteriaQuery<Spot> query = cd.createQuery(Spot.class);
            Root<Spot> from = query.from(Spot.class);
            query.select(from);
    
        //Suche nach Text
        if (search != null && !search.trim().isEmpty()){
            query.where(cd.like(from.get("shortText"), "%" + search + "%"));
        }
        
        //Suche nach Kategorie
        if (category != null){
            query.where(cd.equal(from.get("category"), category));
        }
        
        //Suche nach Status
        if (status != null){
            query.where(cd.equal(from.get("status"), status));
        }
        
        //Suche nach Eigentümer
        if (owner != null){
            query.where(cd.equal(from.get("owner"), owner));
        }
        
        //Suche nach Ort
        if (ort != null){
            query.where(cd.equal(from.get("ort"), ort));
        }
        //Suche nach PLZ
        if (plz != null){
            query.where(cd.equal(from.get("plz"), plz));
        }
        
        return em.createQuery(query).getResultList();
    }
}
    
    