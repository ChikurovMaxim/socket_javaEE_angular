package dbtests;


import com.server.dao.MetricsDao;
import com.server.dao.PlainModelDao;
import com.server.entities.Metric;
import org.junit.After;
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
public class ArticlesTopicsTest {

    @Autowired
    private PlainModelDao plainModelDao;

    @Autowired
    private MetricsDao metricsDao;

    private Metric m;
    @Before
    public void createEnt(){
        m = new Metric("some",123.4,plainModelDao.findPlainModel(1));
    }

    @Test
    public void test(){
        assertNotNull(m);
    }
}
