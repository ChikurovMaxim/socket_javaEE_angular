package com.server.socket;

import com.server.dao.PlainModelDao;
import com.server.entities.Plains;
import com.server.entities.Situation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by Maksym on 19.01.2017.
 */
@Stateless
@ApplicationPath("/resources")
@Path("/plains")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlainRest extends Application {

    @EJB(name = "java:global/PlainModelDaoImpl")
    PlainModelDao plainModelDao;

    @GET
    @Path("/save-plain/{plainName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String savePlain(@PathParam("plainName") String plainName) {
        Plains p = new Plains(plainName);
        return p.getName();
    }

    @DELETE
    @Path("/delete-plain/{delete}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteSituation(@PathParam("delete") boolean delete, int plainId) {
        if (delete) plainModelDao.deletePlain(plainId);
    }

    @GET
    @Path("/get-plains")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Plains> findAll() {
        return plainModelDao.findAll();
    }

    @GET
    @Path("/get-plain-id/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Plains findPlainById(@PathParam("id") int id) {
        return plainModelDao.findPlainModel(id);
    }

    @GET
    @Path("/get-plain-name/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Plains findPlainById(@PathParam("name") String name) {
        return plainModelDao.findPlainModel(name);
    }
}
