// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.apacheshiro.model.bean;

import br.com.devmedia.javamagazine.apacheshiro.model.bean.Comentario;
import br.com.devmedia.javamagazine.apacheshiro.model.bean.Usuario;

privileged aspect Comentario_Roo_JavaBean {
    
    public Usuario Comentario.getAutor() {
        return this.autor;
    }
    
    public void Comentario.setAutor(Usuario autor) {
        this.autor = autor;
    }
    
    public String Comentario.getTexto() {
        return this.texto;
    }
    
    public void Comentario.setTexto(String texto) {
        this.texto = texto;
    }
    
}
