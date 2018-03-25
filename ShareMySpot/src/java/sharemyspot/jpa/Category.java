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
public enum Category {
    CAR, MOTORCYCLE;
    
    public String getLabel() {
        switch(this) {
            case CAR:
                return "Auto";
            case MOTORCYCLE:
                return "Motorrad";
            default:
                return this.toString();
        }
    }
}
