package com.server.dao;

import com.server.entities.Metric;
import com.server.entities.PlainModel;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Maksym on 16.12.2016.
 */
@Local
public interface MetricsDao {

    Metric saveMetric(Metric metric);

    void deleteMetric(int metricid);

    List<Metric> getAll();

    Metric findMetric(String name, PlainModel plainModel);

    Double getStandartValue(Metric metric);

}
