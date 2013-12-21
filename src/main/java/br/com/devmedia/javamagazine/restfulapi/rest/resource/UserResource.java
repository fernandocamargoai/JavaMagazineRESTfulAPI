package br.com.devmedia.javamagazine.restfulapi.rest.resource;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import br.com.devmedia.javamagazine.restfulapi.model.bean.User;

import javax.ws.rs.core.UriInfo;
import java.util.*;

@SuppressWarnings("unchecked")
public class UserResource extends Link{

    public static final String NAME = "name";
    public static final String LOGIN = "login";
    public static final String EMAIL = "email";
    public static final String POSTS = "posts";
    public static final String COMMENTS = "comments";

    public UserResource(UriInfo info, User user, Collection<String> fields, Collection<String> expand) {
        super(info, user);

        if(fields == null || fields.isEmpty()){
            fields = getDefaultFields();
        }

        if(expand == null){
            expand = Collections.emptyList();
        }

        if(fields.contains(NAME)){
            put(NAME, user.getName());
        }

        if(fields.contains(LOGIN)){
            put(LOGIN, user.getName());
        }

        if(fields.contains(EMAIL)){
            put(EMAIL, user.getEmail());
        }

        if(fields.contains(POSTS)){
            List<Post> posts = Post.findPostsByAuthor(user, CollectionResource.DEFAULT_OFFSET, CollectionResource.DEFAULT_LIMIT);

            List<Link> commentResources = new ArrayList<Link>(posts.size());

            for(Post post : posts){
                commentResources.add(new Link(info, post));
            }

            put(POSTS, new CollectionResource(info, getPathForPosts(user.getId()), commentResources));
        }

        if(fields.contains(COMMENTS)){
            List<Comment> comments = Comment.findCommentsByAuthor(user, CollectionResource.DEFAULT_OFFSET, CollectionResource.DEFAULT_LIMIT);

            List<Link> commentResources = new ArrayList<Link>(comments.size());

            for(Comment comment : comments){
                commentResources.add(new Link(info, comment));
            }

            put(COMMENTS, new CollectionResource(info, getPathForComments(user.getId()), commentResources));
        }
    }

    public UserResource(UriInfo info, User user) {
        this(info, user, getDefaultFields(), null);
    }

    public static Collection<String> getDefaultFields(){
        return Arrays.asList(NAME, LOGIN, EMAIL);
    }

    private static String getPathForPosts(String id){
        StringBuilder path = new StringBuilder(Link.USERS);
        path.append(Link.PATH_SEPARATOR);
        path.append(id);
        path.append(Link.POSTS);
        return path.toString();
    }

    private static String getPathForComments(String id){
        StringBuilder path = new StringBuilder(Link.USERS);
        path.append(Link.PATH_SEPARATOR);
        path.append(id);
        path.append(Link.COMMENTS);
        return path.toString();
    }

}
