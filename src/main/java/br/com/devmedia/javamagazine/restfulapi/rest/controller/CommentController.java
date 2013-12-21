package br.com.devmedia.javamagazine.restfulapi.rest.controller;

import br.com.devmedia.javamagazine.restfulapi.model.bean.Comment;
import br.com.devmedia.javamagazine.restfulapi.model.bean.Post;
import br.com.devmedia.javamagazine.restfulapi.rest.exception.*;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.CollectionResource;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.CommentResource;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

@Path(Link.COMMENTS)
@Component
public class CommentController extends BaseController {

    @Autowired
    private UserService userService;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CommentResource getComment(@Context UriInfo info, @PathParam("id") String id,
                                   @QueryParam("fields") List<String> fields,
                                   @QueryParam("expand") List<String> expand) {
        Comment comment = Comment.findComment(id);

        if (comment == null) {
            throw new UnknownResourceException();
        }

        return new CommentResource(info, comment, fields, expand);
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateComment(@Context UriInfo info, @PathParam("id") String id, Map properties) {
        Comment comment = Comment.findComment(id);

        if (comment == null) {
            throw new UnknownResourceException();
        }

        if(!comment.getAuthor().equals(userService.getCurrentUser())){
            throw new OnlyCommentsCreatorCanModifyException();
        }

        try {
            BeanUtils.populate(comment, properties);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        comment.merge();

        return Response.ok(new CommentResource(info, comment), MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}")
    @DELETE
    public void deleteComment(@PathParam("id") String id){
        Comment comment = Comment.findComment(id);

        if (comment == null) {
            throw new UnknownResourceException();
        }

        if(!comment.getAuthor().equals(userService.getCurrentUser())){
            throw new OnlyCommentsCreatorCanDeleteException();
        }

        comment.remove();
    }

}
