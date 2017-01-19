package com.server.daoimpl;

import com.server.dao.PlainModelDao;
import com.server.entities.Plains;
import com.server.entities.Situation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Maksym on 29.11.2016.
 */
@Stateless
@EJB(name = "java:global/PlainModelDaoImpl",
        beanInterface = PlainModelDao.class, beanName = "PlainModelDaoImpl")
public class PlainModelDaoImpl implements PlainModelDao {

    @PersistenceContext(unitName = "UNIT")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Plains savePlain(Plains plains) {
        Plains pm;
        if(plains.getId()==0){
            pm = new Plains(plains.getName());
            entityManager.persist(pm);
        }
        else {
            pm = plains;
            entityManager.merge(pm);
        }
        return pm;
    }

    @Override
    @Transactional
    public void deletePlain(int plainModelId) {
        Plains p = findPlainModel(plainModelId);
        entityManager.remove(entityManager.contains(p) ? p : entityManager.merge(p));
    }


    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Plains> findAll() {
        Query q = entityManager.createQuery("SELECT p FROM Plains p");
        return q.getResultList();
    }

    @Override
    public Plains findPlainModel(int plainModelId) {
        Query q = entityManager.createQuery("SELECT p FROM Plains p WHERE p.id = :id");
        q.setParameter("id", plainModelId);
        return (Plains) q.getSingleResult();
    }

    @Override
    public Plains findPlainModel(String name) {
        Query q = entityManager.createQuery("SELECT p FROM Plains p WHERE p.name = :name");
        q.setParameter("name", name);
        return (Plains) q.getSingleResult();
    }

}
