package dbtests;


import com.server.dao.MetricsDao;
import com.server.dao.PlainModelDao;
import com.server.dao.RecordDao;
import com.server.dao.UserDao;
import com.server.entities.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.*;

/**
 * Created by Maksym on 1/21/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:spring-context.xml")
public class TestClass {

    @Autowired
    private PlainModelDao plainModelDao;
    @Autowired
    private MetricsDao metricsDao;
    @Autowired
    private RecordDao recordsDAO;
    @Autowired
    private UserDao userDAO;


    private Users u;
    private Situation p;
    @Before
    public void createEnt(){
        p = new Situation("Ty-135");
        u = new Users("Maksym", Role.ADMIN.getRole(),"asd","123123");
    }

    @Test
    public void checkUsers(){
        int firstLook = userDAO.getAll().size();
        userDAO.savePerson(u);
        assertEquals(userDAO.getAll().size(),firstLook+1);
    }

    @Test
    public void checkPlains(){
        int firstLook = plainModelDao.findAll().size();
        plainModelDao.savePlain(p);
        assertEquals(plainModelDao.findAll().size(),firstLook+1);
    }
}
