/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import static javax.swing.text.StyleConstants.Size;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Budda
 */
@Entity
@Table(name = "SMS_SPOT")
public class Spot implements Serializable{
    
    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "PLACE", length = 64)
    @NotNull(message = "Ort darf nicht leer sein")
    @Size(min = 3, max = 64, message = "Der Ort muss zw. 3 und 64 Zeichen lang sein")
    private String ort;
    
    @Column(name = "POSTALNUMBER", length = 5)
    @NotNull
    @Size(min = 5, max = 5, message = "Die PLZ muss 5 Zeichen lang sein")
    private String plz;
    
    @Column(name = "ROAD", length = 64)
    @NotNull
    @Size(min = 5, max = 64, message = "Straße musss zw. 5 und 64 Zeichen lang sein")
    private String anschrift;
    
    @Column(name = "DESCRIPTION", length = 128)
    @NotNull
    @Size(min = 3, max = 128, message = "Beschreibung muss zw. 3 und 128 Zeichen lang sein")
    private String beschreibung;
    
    @Enumerated(EnumType.STRING)
    @NotNull
    private SpotStatus status = SpotStatus.FREE;
}
