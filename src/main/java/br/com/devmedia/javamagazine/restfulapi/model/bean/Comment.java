package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entity;
import java.util.Date;
import java.util.List;
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
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(table = "comments")
@NamedQueries(value = {
        @NamedQuery(name = "Comment.findByPost", query = "SELECT c FROM Comment AS c WHERE c.post = :post"),
        @NamedQuery(name = "Comment.findByPostId", query = "SELECT c FROM Comment AS c WHERE c.post.id = :postId"),
        @NamedQuery(name = "Comment.findByAuthor", query = "SELECT c FROM Comment AS c WHERE c.author = :author"),
        @NamedQuery(name = "Comment.findByAuthorId", query = "SELECT c FROM Comment AS c WHERE c.author.id = :authorId")
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

    public static List<Comment> findCommentsByPostId(String postId, int offset, int limit) {
        if (postId == null) throw new IllegalArgumentException("The postId argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Comment.findByPostId", Comment.class).setParameter("post", postId).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public static List<Comment> findCommentsByAuthor(User author, int offset, int limit) {
        if (author == null) throw new IllegalArgumentException("The author argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Comment.findByAuthor", Comment.class).setParameter("author", author).setFirstResult(offset).setMaxResults(limit).getResultList();
    }

    public static List<Comment> findCommentsByAuthor(String authorId, int offset, int limit) {
        if (authorId == null) throw new IllegalArgumentException("The authorId argument is required");
        EntityManager em = Comment.entityManager();
        return em.createNamedQuery("Comment.findByAuthorId", Comment.class).setParameter("author", authorId).setFirstResult(offset).setMaxResults(limit).getResultList();
    }
}
