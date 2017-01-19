package com.server.dao;

import com.server.entities.Metric;
import com.server.entities.Situation;

import javax.ejb.Local;
import java.util.Collection;
import java.util.List;

/**
 * Created by Maksym on 16.12.2016.
 */
@Local
public interface MetricsDao {

    Metric saveMetric(Metric metric);

    void deleteMetric(int metricid);

    List<Metric> getAll();

    Metric findById(int id);

}
