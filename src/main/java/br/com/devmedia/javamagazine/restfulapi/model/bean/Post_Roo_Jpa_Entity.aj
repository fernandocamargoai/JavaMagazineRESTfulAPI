// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

privileged aspect Post_Roo_Jpa_Entity {
    
    declare @type: Post: @Entity;
    
    declare @type: Post: @Table(name = "posts");
    
    @Version
    @Column(name = "version")
    private Integer Post.version;
    
    public Integer Post.getVersion() {
        return this.version;
    }
    
    public void Post.setVersion(Integer version) {
        this.version = version;
    }
    
}
