package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "posts")
@NamedQueries(value = {
        @NamedQuery(name = "Post.findByAuthor", query = "SELECT o FROM Post AS o WHERE o.author = :author")
})
public class Post implements Entity {

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
