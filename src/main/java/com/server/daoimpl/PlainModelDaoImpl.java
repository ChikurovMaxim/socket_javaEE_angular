package com.server.daoimpl;

import com.server.dao.PlainModelDao;
import com.server.entities.Metric;
import com.server.entities.PlainModel;

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
    public PlainModel savePlain(PlainModel plainModel) {
        PlainModel pm;
        if(plainModel.getId()==0){
            pm = new PlainModel(plainModel.getName());
            entityManager.persist(pm);
        }
        else {
            pm = plainModel;
            entityManager.merge(pm);
        }
        return pm;
    }

    @Override
    @Transactional
    public void deletePlain(int plainModelId) {
        PlainModel p = findPlainModel(plainModelId);
        entityManager.remove(entityManager.contains(p) ? p : entityManager.merge(p));
    }


    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<PlainModel> findAll() {
        Query q = entityManager.createQuery("SELECT p FROM PlainModel p");
        return q.getResultList();
    }

    @Override
    public PlainModel findPlainModel(int plainModelId) {
        Query q = entityManager.createQuery("SELECT p FROM PlainModel p WHERE p.id = :id");
        q.setParameter("id", plainModelId);
        return (PlainModel) q.getSingleResult();
    }

    @Override
    public PlainModel findPlainModel(String name) {
        Query q = entityManager.createQuery("SELECT p FROM PlainModel p WHERE p.name = :name");
        q.setParameter("name", name);
        return (PlainModel) q.getSingleResult();
    }

}
