package com.server.daoimpl;


import com.server.dao.RecordDao;
import com.server.entities.Record;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maksym on 14.12.2016.
 */
@Stateless
@EJB(name = "java:global/RecordDaoImpl",
        beanInterface = RecordDao.class, beanName = "RecordDaoImpl")
public class RecordDaoImpl implements RecordDao{

    @PersistenceContext(unitName = "UNIT")
    private EntityManager entityManager;

    @Override
    @Transactional
    public Record saveRecord(Record record) {
        Record newRec;
        if(findRecord(record.getId())==null){
            newRec = new Record(record.getDate(),
                    record.getSimData(),
                    record.getUser(),
                    record.getPlainModel());
            entityManager.persist(newRec);
        }
        else {
            newRec = record;
            entityManager.merge(newRec);
        }
        return newRec;
    }

    @Override
    @Transactional
    public void deleteRecord(Record record) {
        entityManager.remove(entityManager.contains(record) ? record
                : entityManager.merge(record));
    }

    @Override
    public Collection<Record> getAllRecords() {
        Query q = entityManager.createQuery("SELECT r FROM Record r");
        return q.getResultList();
    }

    @Override
    public Record findRecord(int rId) {
        try {
            Query q = entityManager.createQuery("SELECT r FROM Record r WHERE r.Id = :id");
            q.setParameter("id", rId);
            return (Record) q.getSingleResult();
        }catch (NoResultException e ){
            return null;
        }
    }
}

