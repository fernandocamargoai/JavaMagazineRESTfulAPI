package br.com.devmedia.javamagazine.restfulapi.rest.resource;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;

import javax.ws.rs.core.UriInfo;
import java.util.*;

@SuppressWarnings("unchecked")
public class PostResource extends Link{

    public static final String AUTHOR = "author";
    public static final String TITLE = "title";
    public static final String TEXT = "text";
    public static final String DATE_CREATED = "dateCreated";
    public static final String COMMENTS = "comments";

    public PostResource(UriInfo info, Post post, Collection<String> fields, Collection<String> expand) {
        super(info, post);

        if(fields == null || fields.isEmpty()){
            fields = getDefaultFields();
        }

        if(expand == null){
            expand = Collections.emptyList();
        }

        if(fields.contains(AUTHOR)){
            if(expand.contains(AUTHOR)){
                put(AUTHOR, new UserResource(info, post.getAuthor()));
            }
            else {
                put(AUTHOR, new Link(info, post.getAuthor()));
            }
        }

        if(fields.contains(TITLE)){
            put(TITLE, post.getTitle());
        }

        if(fields.contains(TEXT)){
            put(TEXT, post.getText());
        }

        if(fields.contains(DATE_CREATED)){
            put(DATE_CREATED, post.getDateCreated());
        }

        if(fields.contains(COMMENTS)){
            List<Comment> comments = Comment.findCommentsByPost(post, CollectionResource.DEFAULT_OFFSET, CollectionResource.DEFAULT_LIMIT);

            List<Link> commentResources = new ArrayList<Link>(comments.size());

            for(Comment comment : comments){
                commentResources.add(new Link(info, comment));
            }

            put(COMMENTS, new CollectionResource(info, Link.COMMENTS, commentResources));
        }
    }

    public PostResource(UriInfo info, Post post) {
        this(info, post, getDefaultFields(), null);
    }

    public static Collection<String> getDefaultFields(){
        return Arrays.asList(AUTHOR, TITLE, TEXT, DATE_CREATED);
    }

}
