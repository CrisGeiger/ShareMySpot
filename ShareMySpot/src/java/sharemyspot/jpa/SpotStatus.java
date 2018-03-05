/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

/**
 *
 * @author cgeiger1
 */
public enum SpotStatus {
    FREE, RESERVED;
    
    public String getLabel() {
        switch (this) {
            case FREE:
                return "frei";
            case RESERVED:
                return "reserviert";
            default:
                return this.toString();
        }
    }
}
