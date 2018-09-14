package demo;

import org.junit.Test;

import static demo.utils.MouseKvUtils.kvPressOne;
import static demo.utils.MouseKvUtils.mousePressTwo;
import static java.awt.event.KeyEvent.VK_Q;

public class MouseKvUtilsTest {

    @Test
    public void testKvPressOne(){
        kvPressOne(VK_Q);
    }

    @Test
    public void testMousePressTwo(){
        mousePressTwo(0,0);
    }

}
