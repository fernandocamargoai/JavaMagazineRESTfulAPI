// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.apacheshiro.model.bean;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Papel;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Permissao;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;
import java.util.Set;

privileged aspect Usuario_Roo_JavaBean {
    
    public String Usuario.getNome() {
        return this.nome;
    }
    
    public void Usuario.setNome(String nome) {
        this.nome = nome;
    }
    
    public String Usuario.getLogin() {
        return this.login;
    }
    
    public void Usuario.setLogin(String login) {
        this.login = login;
    }
    
    public String Usuario.getSenha() {
        return this.senha;
    }
    
    public void Usuario.setSenha(String senha) {
        this.senha = senha;
    }
    
    public String Usuario.getSalt() {
        return this.salt;
    }
    
    public void Usuario.setSalt(String salt) {
        this.salt = salt;
    }
    
    public Set<Papel> Usuario.getPapeis() {
        return this.papeis;
    }
    
    public void Usuario.setPapeis(Set<Papel> papeis) {
        this.papeis = papeis;
    }
    
    public Set<Permissao> Usuario.getPermissoes() {
        return this.permissoes;
    }
    
    public void Usuario.setPermissoes(Set<Permissao> permissoes) {
        this.permissoes = permissoes;
    }
    
}
