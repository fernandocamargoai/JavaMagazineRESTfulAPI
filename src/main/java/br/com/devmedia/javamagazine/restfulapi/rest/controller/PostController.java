package br.com.devmedia.javamagazine.restfulapi.rest.controller;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import br.com.devmedia.javamagazine.restfulapi.rest.exception.OnlyPostsCreatorCanDeleteException;
import br.com.devmedia.javamagazine.restfulapi.rest.exception.OnlyPostsCreatorCanModifyException;
import br.com.devmedia.javamagazine.restfulapi.rest.exception.UnknownResourceException;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.CollectionResource;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.Link;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.PostResource;
import br.com.devmedia.javamagazine.restfulapi.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Path(Link.POSTS)
@Component
public class PostController extends BaseController {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CollectionResource list(@Context UriInfo info, @QueryParam("fields") List<String> fields,
                                   @DefaultValue("false") @QueryParam("expand") boolean expand,
                                   @DefaultValue(CollectionResource.DEFAULT_OFFSET + "") @QueryParam("offset") int offset,
                                   @DefaultValue(CollectionResource.DEFAULT_LIMIT + "") @QueryParam("limit") int limit) {
        List<Post> posts = Post.findPostEntries(offset, limit);

        List<Link> postResources = new ArrayList<Link>(posts.size());

        if (expand) {
            for (Post post : posts) {
                postResources.add(new PostResource(info, post, fields, null));
            }
        } else {
            for (Post post : posts) {
                postResources.add(new Link(info, post));
            }
        }

        return new CollectionResource(info, Link.POSTS, postResources);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo info, Post post) {
        post.setAuthor(userService.getCurrentUser());
        post.persist();
        PostResource postResource = new PostResource(info, post);
        return created(postResource);
    }

    @Path("/{id}/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public PostResource getPost(@Context UriInfo info, @PathParam("id") String id,
                                @QueryParam("fields") List<String> fields,
                                @QueryParam("expand") List<String> expand) {
        Post post = Post.findPost(id);

        if (post == null) {
            throw new UnknownResourceException();
        }

        return new PostResource(info, post);
    }

    @Path("/{id}/")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updatePost(@Context UriInfo info, @PathParam("id") String id, Map properties) {
        Post post = Post.findPost(id);

        if (post == null) {
            throw new UnknownResourceException();
        }

        if(!post.getAuthor().equals(userService.getCurrentUser())){
            throw new OnlyPostsCreatorCanModifyException();
        }

        try {
            BeanUtils.populate(post, properties);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        post.merge();

        return Response.ok(new PostResource(info, post), MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}/")
    @DELETE
    public void deleteUser(@PathParam("id") String id){
        Post post = Post.findPost(id);

        if (post == null) {
            throw new UnknownResourceException();
        }

        if(!post.getAuthor().equals(userService.getCurrentUser())){
            throw new OnlyPostsCreatorCanDeleteException();
        }

        post.remove();
    }

}
