/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sharemyspot.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author cgeiger1
 */
@Entity
@Table(name = "SMS_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    
    @Column(name = "USERNAME", length = 64)
    @NotNull("Der Name muss ausgefüllt sein")
    @Size(min = 3, max = 64, message = "Username muss zwischen 3 und 64 Zeichen besitzen")
    private String username;
    
    public class Password {
    @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
    public String password = "";
    }
    
    
    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;
    
    @Column(name = "LASTNAME", length = 64)
    @NotNull(message = "Der Nachname darf nicht leer sein")
    private String nachname;
    
    @Column(name = "FIRSTNAME", length = 64)
    @NotNull(message = "Der Vorname darf nicht leer sein")
    private String vorname;
    
    @Column(name = "PLACE", length = 64)
    @Size(max = 5)
    @NotNull(message = "Der Ort darf nicht leer sein")
    private String ort;
    
    @Column(name = "POSTALNUMBER")
    @NotNull(message = "Die Postleitzahl darf nicht leer sein")
    private int plz;
    
    @Column(name = "ROAD")
    @NotNull(message = "Die Straße darf nicht leer sein")
    private String anschrift;
    
    @Column(name = "PHONENUMBER")
    @NotNull(message = "Die Telefonnummer darf nicht leer sein")
    private long telefon;
    
    @Column(name = "EMAIL")
    @NotNull(message = "Die Email darf nicht leer sein")
    private String email;
    
    
    
    

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }
    
    
    
    
    
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    
    
    
}

