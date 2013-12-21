package br.com.devmedia.javamagazine.restfulapi.rest.resource;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import br.com.devmedia.javamagazine.restfulapi.rest.controller.BaseController;
import br.com.devmedia.javamagazine.restfulapi.service.UserService;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.*;

@SuppressWarnings("unchecked")
public class PostResource extends Link{

    private UserService userService;

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

            put(COMMENTS, new CollectionResource(info, getPathForComments(post.getId()), commentResources));
        }
    }

    public PostResource(UriInfo info, Post post) {
        this(info, post, getDefaultFields(), null);
    }

    public static Collection<String> getDefaultFields(){
        return Arrays.asList(AUTHOR, TITLE, TEXT, DATE_CREATED);
    }

    private static String getPathForComments(String id) {
        StringBuilder path = new StringBuilder(Link.POSTS);
        path.append(Link.PATH_SEPARATOR);
        path.append(id);
        path.append(Link.COMMENTS);
        return path.toString();
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @POST
    @Path(Link.COMMENTS)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createComment(@Context UriInfo info, Comment comment) {
        comment.setAuthor(userService.getCurrentUser());
        comment.setDateCreated(new Date());
        comment.setPost(Post.findPost(getHrefLastPathSegment()));
        comment.persist();
        CommentResource commentResource = new CommentResource(info, comment);
        return BaseController.created(commentResource);
    }

    @GET
    @Path(Link.COMMENTS)
    @Produces(MediaType.APPLICATION_JSON)
    public CollectionResource listComments(@Context UriInfo info, @QueryParam("fields") List<String> fields,
                                           @DefaultValue("false") @QueryParam("expand") boolean expand,
                                           @DefaultValue(CollectionResource.DEFAULT_OFFSET+"") @QueryParam("offset") int offset,
                                           @DefaultValue(CollectionResource.DEFAULT_LIMIT+"") @QueryParam("limit") int limit){
        String id = getHrefLastPathSegment();

        List<Comment> comments = Comment.findCommentsByPostId(id, offset, limit);

        List<Link> commentResources = new ArrayList<Link>(comments.size());

        if(expand){
            for(Comment comment : comments){
                commentResources.add(new CommentResource(info, comment, fields, null));
            }
        }
        else {
            for(Comment comment : comments){
                commentResources.add(new Link(info, comment));
            }
        }

        return new CollectionResource(info, getPathForComments(id), commentResources);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PostResource getPostResource(){
        return this;
    }

}
