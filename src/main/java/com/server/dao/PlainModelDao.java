package com.server.dao;

import com.server.entities.Metric;
import com.server.entities.PlainModel;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Maksym on 28.11.2016.
 */
@Local
public interface PlainModelDao {

    PlainModel savePlain(PlainModel plainModel);

    void deletePlain(int plainModelId);

    List<PlainModel> findAll();

    PlainModel findPlainModel(int plainModelId);

    PlainModel findPlainModel(String name);

}
