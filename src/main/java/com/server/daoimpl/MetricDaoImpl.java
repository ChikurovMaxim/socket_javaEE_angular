package com.server.daoimpl;

import com.server.dao.MetricsDao;
import com.server.entities.Metric;
import com.server.entities.Situation;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maksym on 16.12.2016.
 */
@Stateless
@EJB(name = "java:global/MetricDaoImpl",
        beanInterface = MetricsDao.class, beanName = "MetricDaoImpl")
public class MetricDaoImpl implements MetricsDao {

    @PersistenceContext(unitName = "UNIT")
    private EntityManager entityManager;

    @Override
    public Metric findById(int id){
        Query q = entityManager.createQuery("SELECT m FROM Metric m WHERE m.id = :id" );
        q.setParameter("id", id);
        return (Metric) q.getSingleResult();
    }

    @Override
    public List<Metric> getAllBySituation(int id) {
        Query q = entityManager.createQuery("SELECT m FROM Metric m WHERE m.situation.id = :id");
        q.setParameter("id", id);
        return q.getResultList();
    }

    @Override
    public Metric saveMetric(Metric metric) {
        Metric newMetric = new Metric(metric.getName(),metric.getValue(),metric.getSituation());
        entityManager.persist(newMetric);
        return newMetric;
    }

    @Override
    public void deleteMetric(int metricid) {
        Metric newM = entityManager.find(Metric.class, findById(metricid));
        if (newM != null) entityManager.remove(newM);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Metric> getAll() {
        Query q = entityManager.createQuery("SELECT m FROM Metric m");
        return q.getResultList();
    }

}
