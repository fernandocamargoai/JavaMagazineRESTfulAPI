package br.com.devmedia.javamagazine.restfulapi.model.bean;

import br.com.devmedia.javamagazine.restfulapi.model.interfaces.Entidade;
import java.util.Date;
import java.util.UUID;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(identifierType = String.class)
public class Comentario implements Entidade {

    @Id
    private String id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private Usuario autor;

    @NotNull
    @Size(max = 1000)
    private String texto;

    @NotNull
    @ManyToOne
    private Postagem postagem;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date dataCriacao;

    @PrePersist
    private void ensureId() {
        this.setId(UUID.randomUUID().toString().replace("-", ""));
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
