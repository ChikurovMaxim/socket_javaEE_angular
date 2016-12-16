package com.server.socket;

import com.server.dao.RecordDao;
import com.server.dao.UserDao;
import com.server.entities.Record;
import com.server.entities.Role;
import com.server.entities.Users;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.*;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;
import java.util.HashMap;

import com.server.socket.proc.SocketCustom;
import org.json.*;

@Stateless
@ApplicationPath("/resources")
@Path("/server")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ServerRest extends Application {

    private final static String PILOT_NAME = "name";
    private final static String PORT = "port";
    private final static String PLAIN_MODEL = "plain";

    private Record record = new Record();
    private SocketCustom socketConnection;
    private HashMap<String, String> json = null;


    @EJB(name = "java:global/RecordDaoImpl")
    RecordDao recordDao;

    @EJB(name = "java:global/UserDAOImpl")
    UserDao userDao;

    private boolean dataFromJson(String data) {
        try {
            json = new HashMap<>();
            JSONObject jsonObject = new JSONObject(data);
            json.put(PILOT_NAME, jsonObject.getString(PILOT_NAME));
            json.put(PORT, jsonObject.getString(PORT));
            json.put(PLAIN_MODEL, jsonObject.getString(PLAIN_MODEL));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void enrichRecord(String name, String plain) {
        record.setUser(name);
        record.setDate(LocalDateTime.now());
        record.setPlainModel(plain);
        recordDao.saveRecord(record);
    }

    private String checkRole(String role){
        for (Role r : Role.values()){
            if(r.getRole().equals(role))
                return role;
        }
        return null;
    }

    @POST
    @Path("/startData/{start}")
    @Consumes(MediaType.APPLICATION_JSON)
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
    public String stop(@PathParam("stop") boolean stop) {
        if (stop) {
            socketConnection.stop();
            enrichRecord(json.get(PILOT_NAME), json.get(PLAIN_MODEL));
        }
        return "Stop!";
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
                json.getString("login"),json.getString("pass"));
        userDao.savePerson(user);
    }


}