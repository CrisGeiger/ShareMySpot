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
    @Size(min = 3, max = 64, message = "Name muss zwischen 3 und 64 Zeichen besitzen")
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
    
    @Column(name = "LASTNAME")
    
    

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

