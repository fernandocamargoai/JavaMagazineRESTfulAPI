// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Postagem;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Usuario;
import java.util.Date;

privileged aspect Postagem_Roo_JavaBean {
    
    public Usuario Postagem.getAutor() {
        return this.autor;
    }
    
    public void Postagem.setAutor(Usuario autor) {
        this.autor = autor;
    }
    
    public String Postagem.getTitulo() {
        return this.titulo;
    }
    
    public void Postagem.setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String Postagem.getTexto() {
        return this.texto;
    }
    
    public void Postagem.setTexto(String texto) {
        this.texto = texto;
    }
    
    public Date Postagem.getDataCriacao() {
        return this.dataCriacao;
    }
    
    public void Postagem.setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
}