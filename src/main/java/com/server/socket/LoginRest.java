package com.server.socket;

import com.server.dao.UserDao;
import com.server.entities.Users;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

/**
 * Created by Maksym on 14.12.2016.
 */
@Stateless
@ApplicationPath("/resources")
@Path("/login")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class LoginRest extends Application{

    @EJB(name = "java:global/UserDAOImpl")
    UserDao personService;

    @POST
    @Path("{login}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Users login(@PathParam("login") String login, String password, @Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        Users logedP = personService.getUserIdByAuthData(login,password);
        session.setAttribute("personId", logedP.getId());
        return logedP;
    }
}
