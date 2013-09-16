package br.com.devmedia.javamagazine.apacheshiro.model.bean;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findUsuariosByLoginEquals" })
public class Usuario {

    @NotNull
    private String nome;

    @NotNull
    @Column(unique = true)
    private String login;

    @NotNull
    private String senha;

    private String salt;

    @ManyToMany
    private Set<Papel> papeis = new HashSet<Papel>();

    @ManyToMany
    private Set<Permissao> permissoes = new HashSet<Permissao>();

    @Override
    public String toString() {
        return nome + " (" + login + ")";
    }
}
