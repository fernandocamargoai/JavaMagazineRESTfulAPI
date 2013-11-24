package br.com.devmedia.javamagazine.restfulapi.rest.resource;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;

import javax.ws.rs.core.UriInfo;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@SuppressWarnings("unchecked")
public class CommentResource extends Link{

    public static final String AUTHOR = "author";
    public static final String TEXT = "text";
    public static final String DATE_CREATED = "dateCreated";
    public static final String POST = "post";

    public CommentResource(UriInfo info, Comment comment, Collection<String> fields, Collection<String> expand) {
        super(info, comment);

        if(fields == null){
            fields = getDefaultFields();
        }

        if(expand == null){
            expand = Collections.emptyList();
        }

        if(fields.contains(AUTHOR)){
            if(expand.contains(AUTHOR)){
                put(AUTHOR, new UserResource(info, comment.getAuthor()));
            }
            else {
                put(AUTHOR, new Link(info, comment.getAuthor()));
            }
        }

        if(fields.contains(TEXT)){
            put(TEXT, comment.getText());
        }

        if(fields.contains(DATE_CREATED)){
            put(DATE_CREATED, comment.getDateCreated());
        }

        if(fields.contains(POST)){
            if(expand.contains(POST)){
                put(POST, new PostResource(info, comment.getPost()));
            }
            else {
                put(POST, new Link(info, comment.getPost()));
            }
        }
    }

    public CommentResource(UriInfo info, Comment comment) {
        this(info, comment, getDefaultFields(), null);
    }

    public static Collection<String> getDefaultFields(){
        return Arrays.asList(AUTHOR, TEXT, DATE_CREATED, POST);
    }

}
