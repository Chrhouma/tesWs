package com.pictimegroupe.FrontVendeur.testWebservice;
import org.hibernate.annotations.Cascade;
import org.springframework.beans.factory.annotation.Autowired;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.persistence.*;

import java.util.List;

import static javax.persistence.CascadeType.REMOVE;

/**
 * la class utilisateur
 */
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String login;
    private String password;
    @OneToOne
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private Role role;

    public User() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    public JsonObjectBuilder getUserJson() {

        JsonObjectBuilder objetBuilder=Json.createObjectBuilder();
        JsonObjectBuilder objetBuilderRole=Json.createObjectBuilder();

        objetBuilder.add("id", this.getId());
        objetBuilder.add("login",this.getLogin());
        objetBuilder.add("password",this.getPassword());

        objetBuilderRole.add("roleID",this.getRole().getId());
        objetBuilderRole.add("roleName",this.getRole().getName());
        objetBuilderRole.add("roleRight",this.getRole().getRights());

        objetBuilder.add("roles",objetBuilderRole);

        return objetBuilder ;

    }
}


