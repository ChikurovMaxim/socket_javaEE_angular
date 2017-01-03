package com.server.socket;

import com.server.dao.MetricsDao;
import com.server.dao.PlainModelDao;
import com.server.dao.UserDao;
import com.server.entities.Metric;
import com.server.entities.PlainModel;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

import com.server.entities.Users;
import org.json.*;

/**
 * Created by Maksym on 16.12.2016.
 */
@Stateless
@ApplicationPath("/resources")
@Path("/plainmetric")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PlainMetricsRest extends Application {

    @EJB(name = "java:global/MetricDaoImpl")
    MetricsDao metricsDao;

    @EJB(name = "java:global/PlainModelDaoImpl")
    PlainModelDao plainModelDao;

    @EJB(name = "java:global/UserDAOImpl")
    UserDao userDao;

    @GET
    @Path("/get-plains")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<PlainModel> getAllPlains() {
        return plainModelDao.findAll();
    }

    @GET
    @Path("/get-plain-metrics/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Metric> getPlainMetrics(@PathParam("id") int id) {
        return (List<Metric>) metricsDao.getPlainMetric(plainModelDao.findPlainModel(id));
    }

    @POST
    @Path("/save-metric/{plain}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveMetric(@PathParam("plain") int id, String data) {
        JSONObject json = new JSONObject(data);
        Metric metric = new Metric(json.getString("name"), json.getDouble("value"), plainModelDao.findPlainModel(id));
        metricsDao.saveMetric(metric);
    }

    @GET
    @Path("/save-plain/{plainName}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String savePlain(@PathParam("plainName") String plainName) {
        PlainModel p = new PlainModel(plainName);
        plainModelDao.savePlain(p);
        return plainName;
    }

    @DELETE
    @Path("/delete-plain/{delete}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deletePlain(@PathParam("delete") boolean delete, int plainId) {
        if (delete) plainModelDao.deletePlain(plainId);
    }

    @DELETE
    @Path("/delete-plain-metric/{delete}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteMetric(@PathParam("delete") boolean delete, int meticId) {
        if (delete) metricsDao.deleteMetric(meticId);
    }

    @GET
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public String logOut(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("personId", null);
        session.invalidate();
        return "Bye =3";
    }

    @GET
    @Path("/logedIn")
    @Consumes(MediaType.APPLICATION_JSON)
    public Users getLogedP(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        return userDao.findPerson((int) session.getAttribute("personId"));
    }
}
