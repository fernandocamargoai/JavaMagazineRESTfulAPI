package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity;
import java.util.Date;
import java.util.List;
import javax.*;
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
@RooJpaActiveRecord(identifierType = String.class)
@NamedQueries(value = {
        @NamedQuery(name = "Comment.findByPost", query = "SELECT o FROM Comment AS o WHERE o.post = :post"),
        @NamedQuery(name = "Comment.findByAuthor", query = "SELECT o FROM Comment AS o WHERE o.author = :author")
})
public class Comment implements Entity {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User author;

    @NotNull
    @Size(max = 1000)
    private String text;

    @NotNull
    @ManyToOne
    private Post post;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dateCreated;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static List<Comment> findCommentsByPost(Post post, int offset, int limit) {
        if (post == null) throw new IllegalArgumentException("The post argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Comment.findByPost", Comment.class).setParameter("post", post).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public static List<Comment> findCommentsByAuthor(User author, int offset, int limit) {
        if (author == null) throw new IllegalArgumentException("The post argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Comment.findByAuthor", Comment.class).setParameter("author", author).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
