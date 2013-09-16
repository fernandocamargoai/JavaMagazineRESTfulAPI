package br.com.devmedia.javamagazine.rest.model.bean;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Comentario {

    @ManyToOne
    private Usuario autor;

    @Size(min = 15, max = 255)
    private String texto;
}
