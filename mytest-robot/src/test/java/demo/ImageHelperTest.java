package demo;

import org.junit.Test;
import static demo.utils.ImageHelper.*;


/**
 * @author top37 2018/6/10
 */
public class ImageHelperTest {

    /**
     * @author top37 2018/6/10
     */
    @Test
    public void testTo2Val0(){
        to2Val();
    }

    /**
     * @author top37 2018/6/10
     */
    @Test
    public void testTo2Val3(){
        to2Val("jpg","e:/temp/temp.jpg","e:/temp/temp1.jpg");
    }


}
