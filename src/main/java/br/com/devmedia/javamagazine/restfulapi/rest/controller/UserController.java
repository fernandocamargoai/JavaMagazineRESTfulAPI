package br.com.devmedia.javamagazine.restfulapi.rest.controller;

import br.com.devmedia.javamagazine.restfulapi.model.bean.User;
import br.com.devmedia.javamagazine.restfulapi.rest.exception.UnknownResourceException;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.CollectionResource;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.Link;
import br.com.devmedia.javamagazine.restfulapi.rest.resource.UserResource;
import br.com.devmedia.javamagazine.restfulapi.service.UserService;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Path(Link.USERS)
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public CollectionResource list(@Context UriInfo info, @QueryParam("fields") List<String> fields,
                                   @DefaultValue("false") @QueryParam("expand") boolean expand,
                                   @DefaultValue(CollectionResource.DEFAULT_OFFSET+"") @QueryParam("offset") int offset,
                                   @DefaultValue(CollectionResource.DEFAULT_OFFSET+"") @QueryParam("limit") int limit){
        List<User> users = User.findUserEntries(offset, limit);

        List<Link> userResources = new ArrayList<Link>(users.size());

        if(expand){
            for(User user : users){
                userResources.add(new UserResource(info, user, fields, null));
            }
        }
        else {
            for(User user : users){
                userResources.add(new Link(info, user));
            }
        }

        return new CollectionResource(info, Link.USERS, userResources);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Context UriInfo info, User user){
        user.persist();
        UserResource userResource = new UserResource(info, user);
        return created(userResource);
    }

    @Path("/{id}/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserResource getUser(@Context UriInfo info, @PathParam("id") String id,
                                @QueryParam("fields") List<String> fields,
                                @QueryParam("expand") List<String> expand){
        User user = User.findUser(id);

        if(user == null){
            throw new UnknownResourceException();
        }

        return new UserResource(info, user, fields, expand);
    }

    @Path("/{id}/")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@Context UriInfo info, @PathParam("id") String id, Map properties){
        User user = User.findUser(id);

        if(user == null){
            throw new UnknownResourceException();
        }

        try {
            BeanUtils.populate(user, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        user.merge();

        return Response.ok(new UserResource(info, user), MediaType.APPLICATION_JSON).build();
    }

    @Path("/{id}/")
    @DELETE
    public void deleteUser(@PathParam("id") String id){
        User user = User.findUser(id);
        if(user == null){
            throw new UnknownResourceException();
        }
        user.remove();
    }

}
