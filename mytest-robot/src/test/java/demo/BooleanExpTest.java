package demo;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static demo.utils.BooleanExp.*;

public class BooleanExpTest {

    private static Boolean testTrue(String str){
        return str.contains("s");
    }

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 经测通过
     */
    @Deprecated
    @Test
    public void testIsReject(){
        logger.info(String.valueOf(isReject()));
    }

    @Deprecated
    @Test
    public void testIsEntered(){
        logger.info(String.valueOf(isEntered()));
    }
    
    @Test
    public void testIsRZDSPage(){
        logger.info(String.valueOf(isRZDSPage()));
    }

    @Test
    public void testIsJBPage(){
        logger.info(String.valueOf(isJBPage()));
    }

    @Test
    public void testIsDSWGZPage(){
        logger.info(String.valueOf(isDSWGZPage()));
    }

    @Test
    public void testIsDSWZBJXPage(){
        logger.info(String.valueOf(isDSWZBJXPage()));
    }

    @Test
    public void testinvokeByMethodName(){
        /*私有方法也可以通过反射调用*/
        Boolean b = invokeByMethodName("testTrue","yyyys", BooleanExpTest.class);
        logger.info(b.toString());

        logger.info(testTrue("jjj").toString());
        logger.info(testTrue("sss").toString());
    }
}
