package com.server.socket;

import com.server.dao.MetricsDao;
import com.server.dao.SituationDao;
import com.server.dao.UserDao;
import com.server.entities.Metric;
import com.server.entities.Situation;

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
@Path("/situation-metric")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SituationMetricsRest extends Application {

    @EJB(name = "java:global/MetricDaoImpl")
    MetricsDao metricsDao;

    @EJB(name = "java:global/SituationDaoImpl")
    SituationDao situationDao;

    @EJB(name = "java:global/UserDAOImpl")
    UserDao userDao;

    @GET
    @Path("/get-situations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Situation> getAllSituations() {
        return situationDao.findAll();
    }

    @GET
    @Path("/get-situation-metrics/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<Metric> getSituationMetrics(@PathParam("id") int id) {
        return (List<Metric>) situationDao.findSituationMetrics(id);
    }

    @POST
    @Path("/save-metric/{situation}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveMetric(@PathParam("situation") int id, String name) {
        if(metricsDao.findById(id) != null){
            Metric metric = metricsDao.findById(id);
            metric.setName(name);
        }
        else {
            Metric metric = new Metric();
            metric.setName(name);
            metricsDao.saveMetric(metric);
        }
    }

    @POST
    @Path("/save-situation/{situationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public String saveSituation(@PathParam("situationId") int situationId, String sitJson) {
        JSONObject json = new JSONObject(sitJson);
        String name = json.getString("name");
        String desc = json.getString("desc");
        if(situationDao.findSituation(situationId) != null){
            Situation p = situationDao.findSituation(situationId);
            p.setName(name);
            p.setDesc(desc);
            situationDao.saveSituation(p);
        }
        else {
            Situation p = new Situation();
            p.setName(name);
            p.setDesc(desc);
            situationDao.saveSituation(p);
        }
        return "Success";
    }

    @DELETE
    @Path("/delete-situation/{delete}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteSituation(@PathParam("delete") boolean delete, int situationId) {
        if (delete) situationDao.deleteSituation(situationId);
    }

    @DELETE
    @Path("/delete-situation-metric/{delete}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteMetric(@PathParam("delete") boolean delete, int metricId) {
        if (delete) metricsDao.deleteMetric(metricId);
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
