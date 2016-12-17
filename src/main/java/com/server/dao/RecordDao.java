package com.server.dao;

import com.server.entities.Record;

import javax.ejb.Local;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maksym on 14.12.2016.
 */
@Local
public interface RecordDao {

    Record saveRecord(Record record);

    void deleteRecord(Record record);

    Collection<Record> getAllRecords();

    Record findRecord(int rId);

}
