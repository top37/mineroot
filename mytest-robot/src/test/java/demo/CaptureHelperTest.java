package demo;

import demo.cfg.CommonStr;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static demo.utils.CaptureHelper.createCapEasyFinish;
import static demo.utils.CaptureHelper.createCapFinish;
import static demo.utils.ImageHelper.to2Val;
import static demo.utils.OCRHelper.resolvImg;

/**
 * 单强 2018年6月10日12:07:01
 * 单元测试
 */
public class CaptureHelperTest implements CommonStr {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 测试 easy方法
     */
    @Test
    public void testCreateCapEasyFinish() {
//        createCapEasyFinish(150,670,229,718); //返回
//        createCapEasyFinish(270,194,314,272); //比寿 - 啡吝
//        createCapEasyFinish(242,198,300,399); //羁绊对战 - 羁绊过战
//        createCapEasyFinish(593,197,646,407); //忍者大赛 - 忍老犬寐
//        createCapEasyFinish(489,113,815,334); //大蛇丸试炼 - 硼
//        createCapEasyFinish(578,567,738,607); //准备就绪 - 准备就绪
        createCapEasyFinish(1092,213,1171,243); //准备就绪 - 准备就绪
        to2Val();
        logger.info(resolvImg(tempPath));
        logger.info(resolvImg(tempPath1));
        logger.info(" -ok- ");
    }

    /**
     * 测试 4参方法
     */
    @Test
    public void testCreateCapFinish4() {
        if(createCapFinish(78,116,142-78,140-116))
            logger.info(" -ok- ");
    }

    /**
     * 测试 6参方法
     */
    @Test
    public void testCreateCapFinish6() {
        if(createCapFinish(78,116,142-78,140-116,"jpg","e:/temp/temp.jpg"))
            logger.info(" -ok- ");
    }

}
