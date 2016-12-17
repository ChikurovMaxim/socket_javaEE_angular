package com.server.dao;

import com.server.entities.Users;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maksym on 14.12.2016.
 */
@Local
public interface UserDao {

    Users savePerson(Users newPerson);

    Users findPerson(int id);

    void deletePerson(Users person);

    Collection<Users> getAll();

    Users getUserIdByAuthData(String login, String password);

    Users findUserByName(String name);

    List<Users> findUsersByRole(String role);
}
