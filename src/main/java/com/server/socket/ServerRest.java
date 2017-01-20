package com.server.socket;

import com.server.dao.*;
import com.server.entities.*;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.server.socket.proc.SocketCustom;
import org.json.*;

@Stateless
@ApplicationPath("/resources")
@Path("/server")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerRest extends Application {

    @EJB(name = "java:global/RecordDaoImpl")
    RecordDao recordDao;

    private final static String PILOT = "user";
    private final static String PORT = "port";
    private final static String PLAIN_MODEL = "situation";
    private final static String EMERGENCY = "emergency";

    private Record record = new Record();
    private SocketCustom socketConnection;
    private HashMap<String, String> json = null;


    @EJB(name = "java:global/MetricDaoImpl")
    MetricsDao metricsDao;

    @EJB(name = "java:global/UserDAOImpl")
    UserDao userDao;

    @EJB(name = "java:global/SituationDaoImpl")
    SituationDao situationDao;


    private boolean dataFromJson(String data) {
        try {
            json = new HashMap<>();
            JSONObject jsonObject = new JSONObject(data);
            json.put(PILOT, jsonObject.getString(PILOT));
            json.put(PORT, jsonObject.getString(PORT));
            json.put(PLAIN_MODEL, jsonObject.getString(PLAIN_MODEL));
            json.put(EMERGENCY,jsonObject.getString(EMERGENCY));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void enrichRecord(int uid,int sid, int pid) {
        record.setUser(userDao.findPerson(uid));
        java.util.Date datetime = Timestamp.valueOf(LocalDateTime.now());
        record.setDate(datetime);
        record.setSituation(situationDao.findSituation(sid));
        record.setSituation(situationDao.findSituation(pid));
        processSimData(record);
        recordDao.saveRecord(record);
    }

    private String checkRole(String role){
        for (Role r : Role.values()){
            if(r.getRole().equals(role))
                return role;
        }
        return null;
    }

    private void processSimData(Record record){
        HashMap <String,String> procSimData = new HashMap<>();
        JSONObject object = new JSONObject(record.getSimData());
        List<Metric> metricList = metricsDao.getAllBySituation(record.getSituation().getId());
        for(Metric metric : metricList){
            procSimData.put(metric.getName(),object.getString(metric.getName()));
        }
        JSONObject dinalObject = new JSONObject(procSimData);
        String simdata = dinalObject.toString();
        record.setSimData(simdata);
    }

    @POST
    @Path("/startData/{start}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void start(@PathParam("start") boolean start, String data) {
        if (start) {
            if (dataFromJson(data)) {
                socketConnection = new SocketCustom(Integer.parseInt(json.get(PORT)));
                record.setSimData(socketConnection.start());
            }
        }
    }

    @GET
    @Path("/stopData/{stop}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String stop(@PathParam("stop") boolean stop) {
        if (stop) {
            socketConnection.stop();
            enrichRecord(Integer.parseInt(json.get(PILOT)),
                    Integer.parseInt(json.get(EMERGENCY)),
                    Integer.parseInt(json.get(PLAIN_MODEL)));
        }
        return "Stop!";
    }

    @GET
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String logOut(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("personId", null);
        session.invalidate();
        return "Bye =3";
    }

    @GET
    @Path("/logedIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Users getLogedP(@Context HttpServletRequest request) {
        HttpSession session = request.getSession();
        return userDao.findPerson((int) session.getAttribute("personId"));
    }

    @DELETE
    @Path("/delete-record/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteRecord(@PathParam("id") int id) {
        recordDao.deleteRecord(recordDao.findRecord(id));
    }

    @DELETE
    @Path("/delete-user/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void deleteUser(@PathParam("id") int id) {
        userDao.deletePerson(userDao.findPerson(id));
    }

    @POST
    @Path("/save-user/{name}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void saveUser(@PathParam("name") String userName, String data) {
        JSONObject json = new JSONObject(data);
        Users user = new Users(userName,checkRole(json.getString("role")),
                json.getString("login"),json.getString("password"));
        userDao.savePerson(user);
    }

    @GET
    @Path("/get-all-user-records/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Record> getRecordsForUser(@PathParam("id") int id){
        return recordDao.getRecordsForUser(userDao.findPerson(id));
    }

    @GET
    @Path("/get-all-users/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Users> getAllUsers(){
        return userDao.getAll();
    }

    @GET
    @Path("/get-all-records/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Record> getAllRecords(){
        return recordDao.getAllRecords();
    }

}