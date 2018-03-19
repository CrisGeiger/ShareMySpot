/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.ejb;

import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import sharemyspot.jpa.Category;

/**
 *
 * @author JU_FI
 */
@Stateless
@RolesAllowed("ShareMySpot-user")
public class CategoryBean extends EntityBean<Category, Long> {

    public CategoryBean() {
        super(Category.class);
    }

    /**
     * Auslesen aller Kategorien, alphabetisch sortiert.
     *
     * @return Liste mit allen Kategorien
     */
    public List<Category> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Category c ORDER BY c.name").getResultList();
    }
}

