package br.com.devmedia.javamagazine.restfulapi.model.bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
@NamedQueries(value = {
        @NamedQuery(name = "Post.findByAuthor", query = "SELECT o FROM Post AS o WHERE o.author = :author")
})
public class Post implements br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity {

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User author;

    @NotNull
    private String title;

    @NotNull
    @Size(max = 2000)
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateCreated;

    @PrePersist
    private void ensureId() {
        if(id == null){
            setId(UUID.randomUUID().toString().replace("-", ""));
        }
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<Post> findPostsByAuthor(User author, int offset, int limit) {
        if (author == null) throw new IllegalArgumentException("The post argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Post.findByAuthor", Post.class).setParameter("author", author).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
