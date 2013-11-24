package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class User implements Entity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String password;

    private String salt;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name + " (" + login + ")";
    }

}
