package demo;

import org.apache.commons.cli.ParseException;
import org.junit.Test;
//import static junit.framework.Assert.*;

public class TestMain {

    @Test
    public void testAlive() throws ParseException {
        String args[]={"-alive"};
        Main.execCmd(args);
    }

    /**
     * 会以空格的方式split成String[]
     * @throws ParseException
     * @deprecated  抛出解析异常
     */
    @Test
    public void testNameAndSex() throws ParseException {
        String args[]={"--name=单强","-sex=男"};
        Main.execCmd(args);
    }
}
