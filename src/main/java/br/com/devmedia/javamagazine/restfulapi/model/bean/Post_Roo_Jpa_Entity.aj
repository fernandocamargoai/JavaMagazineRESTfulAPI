// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Post_Roo_Jpa_Entity {
    
    declare @type: Post: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_")
    private Long Post.id_;
    
    @Version
    @Column(name = "version")
    private Integer Post.version;
    
    public Long Post.getId_() {
        return this.id_;
    }
    
    public void Post.setId_(Long id) {
        this.id_ = id;
    }
    
    public Integer Post.getVersion() {
        return this.version;
    }
    
    public void Post.setVersion(Integer version) {
        this.version = version;
    }
    
}