// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.apacheshiro.model.bean;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Papel;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Permissao;
import java.util.Set;

privileged aspect Papel_Roo_JavaBean {
    
    public String Papel.getNome() {
        return this.nome;
    }
    
    public void Papel.setNome(String nome) {
        this.nome = nome;
    }
    
    public Set<Permissao> Papel.getPermissoes() {
        return this.permissoes;
    }
    
    public void Papel.setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
    
}
