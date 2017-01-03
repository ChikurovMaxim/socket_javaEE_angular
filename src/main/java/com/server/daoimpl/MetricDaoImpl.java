package com.server.daoimpl;

import com.server.dao.MetricsDao;
import com.server.entities.Metric;
import com.server.entities.PlainModel;

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

    private Metric findById(int id){
        Query q = entityManager.createQuery("SELECT m FROM Metric m WHERE m.id = :id" );
        q.setParameter("id", id);
        return (Metric) q.getSingleResult();
    }

    @Override
    public Metric saveMetric(Metric metric) {
        Metric newMetric = new Metric(metric.getName(),metric.getValue(),metric.getPlainModel());
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

    @Override
    public Metric findMetric(String name, PlainModel plainModel) {
        String plainName = plainModel.getName();
        Query q = entityManager.
                createQuery("SELECT m FROM Metric m WHERE m.name = :name AND m.plainModel = :plain_name");
        q.setParameter("name",name);
        q.setParameter("plain_name", plainName);
        return (Metric) q.getSingleResult();
    }

    @Override
    public Double getStandartValue(Metric metric) {
        return findById(metric.getId()).getValue();
    }

    @Override
    public Collection<Metric> getPlainMetric(int id) {
        Query q = entityManager.createQuery("SELECT m FROM Metric m WHERE m.plainModel = :plain");
        q.setParameter("plain",id);
        return q.getResultList();
    }

    @Override
    public Collection<Metric> getPlainMetric(PlainModel p) {
        Query q = entityManager.createQuery("SELECT m FROM Metric m WHERE m.plainModel = :plain");
        q.setParameter("plain",p);
        return q.getResultList();
    }
}
