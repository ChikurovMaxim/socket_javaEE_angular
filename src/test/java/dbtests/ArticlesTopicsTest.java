package dbtests;


import com.server.dao.MetricsDao;
import com.server.dao.PlainModelDao;
import com.server.dao.RecordDao;
import com.server.dao.UserDao;
import com.server.entities.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by Maksym on 1/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-context.xml")
public class ArticlesTopicsTest {

    @Autowired
    private PlainModelDao plainModelDao;

    @Autowired
    private MetricsDao metricsDao;
    @Autowired
    private RecordDao recordsDAO;
    @Autowired
    private UserDao userDAO;


    private Metric m;
    private Record r;
    private Users u;
    private PlainModel p;
    @Before
    public void createEnt(){
        p = new PlainModel("Ty-135");
        u = new Users("Maksym", Role.ADMIN.getRole(),"asd","123123");
    }

    @Test
    public void test(){
        userDAO.savePerson(u);
        plainModelDao.savePlain(p);
        java.util.Date datetime = Timestamp.valueOf(LocalDateTime.now());
        r = new Record(datetime,"String",userDAO.findPerson(1),plainModelDao.findPlainModel(1));
        recordsDAO.saveRecord(r);
        assertEquals(recordsDAO.getAllRecords().size(),1);
    }
}
