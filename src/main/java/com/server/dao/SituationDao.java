package com.server.dao;

import com.server.entities.Metric;
import com.server.entities.Situation;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Maksym on 19.01.2017.
 */
@Local
public interface SituationDao {

    Situation saveSituation(Situation situation);

    void deleteSituation(int plainModelId);

    List<Situation> findAll();

    Situation findSituation(int plainModelId);

    Situation findSituation(String name);

    List<Metric> findSituationMetrics(int id);
}
