package com.server.daoimpl;

import com.server.dao.UserDao;
import com.server.entities.Role;
import com.server.entities.Users;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Maksym on 1/12/2016.
 */
@Stateless
@EJB(name = "java:global/UserDAOImpl",
        beanInterface = UserDao.class, beanName = "UserDAOImpl")
public class UserDAOImpl implements UserDao {


    @PersistenceContext(unitName = "UNIT")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Users savePerson(Users newPerson) {
        Users u = new Users(newPerson.getName(), newPerson.getRole(),
                newPerson.getLogin(), newPerson.getPassword());
        if (newPerson.getId() == 0) {
            entityManager.persist(u);
        } else {
            u.setId(newPerson.getId());
            entityManager.merge(u);
        }
        return u;
    }


    @Override
    public Users findPerson(int id){
        if(id != 0)return entityManager.find(Users.class,id);
        else return null;
    }

    @Override
    public Users findUserByName(String name) {
        Query q = entityManager.createQuery("SELECT u FROM Users u WHERE u.name = :uname");
        q.setParameter("uname", name);
        return (Users)q.getSingleResult();
    }

    @Override
    public List<Users> findUsersByRole(String role){
        return getAll().stream()
                .filter(u -> u.getRole().equals(role))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deletePerson(Users person) {
        Users newP = entityManager.find(Users.class, findPerson(person.getId()));
        if (newP != null) entityManager.remove(newP);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Collection<Users> getAll() {
        Query q = entityManager.createQuery("select p from Users p");
        return q.getResultList();
    }

    @Override
    public Users getUserIdByAuthData(String login, String password) {
        try {
            Query q = entityManager.
                    createQuery("SELECT u FROM Users u WHERE u.login = :login AND u.password = :password");
            q.setParameter("login", login);
            q.setParameter("password", password);
            return (Users) q.getSingleResult();
        }
        catch (Exception e){
            return null;
        }
    }
}
