package br.com.devmedia.javamagazine.rest.model.bean;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.ManyToMany;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Papel {

    private String nome;

    @ManyToMany
    private Set<Permissao> permissoes = new HashSet<Permissao>();

	@Override
	public String toString() {
		return nome;
	}
    
}
