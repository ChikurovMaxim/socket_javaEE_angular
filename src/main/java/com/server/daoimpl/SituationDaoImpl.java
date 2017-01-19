package com.server.daoimpl;

import com.server.dao.PlainModelDao;
import com.server.dao.SituationDao;
import com.server.entities.Metric;
import com.server.entities.Situation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by Maksym on 19.01.2017.
 */
@Stateless
@EJB(name = "java:global/SituationDaoImpl",
        beanInterface = SituationDao.class, beanName = "SituationDaoImpl")
public class SituationDaoImpl implements SituationDao{

    @PersistenceContext(unitName = "UNIT")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Situation saveSituation(Situation s) {
        Situation pm;
        if(s.getId()==0){
            pm = new Situation(s.getName());
            entityManager.persist(pm);
        }
        else {
            pm = s;
            entityManager.merge(pm);
        }
        return pm;
    }

    @Override
    @Transactional
    public void deleteSituation(int plainModelId) {
        Situation p = findSituation(plainModelId);
        entityManager.remove(entityManager.contains(p) ? p : entityManager.merge(p));
    }


    @Override
    @SuppressWarnings("unchecked")
    @Transactional
    public List<Situation> findAll() {
        Query q = entityManager.createQuery("SELECT p FROM Situation p");
        return q.getResultList();
    }

    @Override
    public Situation findSituation(int plainModelId) {
        try {
            Query q = entityManager.createQuery("SELECT p FROM Situation p WHERE p.id = :id");
            q.setParameter("id", plainModelId);
            return (Situation) q.getSingleResult();
        }
        catch (NullPointerException e){
            return null;
        }
    }

    @Override
    public Situation findSituation(String name) {
        Query q = entityManager.createQuery("SELECT p FROM Situation p WHERE p.name = :name");
        q.setParameter("name", name);
        return (Situation) q.getSingleResult();
    }

    @Override
    public List<Metric> findSituationMetrics(int id) {
        Query q = entityManager.createQuery("SELECT s FROM Metric s where s.situation.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }
}
