// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Post_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Post.entityManager;
    
    public static final EntityManager Post.entityManager() {
        EntityManager em = new Post().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Post.countPosts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Post o", Long.class).getSingleResult();
    }
    
    public static List<Post> Post.findAllPosts() {
        return entityManager().createQuery("SELECT o FROM Post o", Post.class).getResultList();
    }
    
    public static Post Post.findPost(String id) {
        if (id == null || id.length() == 0) return null;
        return entityManager().find(Post.class, id);
    }
    
    public static List<Post> Post.findPostEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Post o", Post.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Post.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Post.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Post attached = Post.findPost(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Post.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Post.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Post Post.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Post merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
