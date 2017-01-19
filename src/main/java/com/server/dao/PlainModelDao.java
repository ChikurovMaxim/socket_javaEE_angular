package com.server.dao;

import com.server.entities.Plains;
import com.server.entities.Situation;

import javax.ejb.Local;
import java.util.List;

/**
 * Created by Maksym on 28.11.2016.
 */
@Local
public interface PlainModelDao {

    Plains savePlain(Plains situation);

    void deletePlain(int plainModelId);

    List<Plains> findAll();

    Plains findPlainModel(int plainModelId);

    Plains findPlainModel(String name);

}
