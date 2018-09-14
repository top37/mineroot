package demo;

import demo.utils.OCRHelper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.matchers.StringContains;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import static demo.utils.OCRHelper.resolvImg;

public class OCRHelperTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testResolv2Val0(){
        logger.info( resolvImg() );
    }

    @Test
    public void testResolv2Val1(){
        logger.info( resolvImg("e:/temp/temp1.jpg") );
    }

    @Test
    public void testOcr() throws Exception {
            //图片文件：此图片是需要被识别的图片
//            File file = new File("D:\\04.jpg");
            File file = new File("D:\\test2.png");
            String recognizeText = new OCRHelper().recognizeText(file);
            System.out.print(recognizeText + "\t");
            Assert.assertThat(recognizeText,StringContains.containsString("绝"));
    }

    @Test
    public void testOcr1() throws Exception {
        //图片文件：此图片是需要被识别的图片
//            File file = new File("D:\\04.jpg");
        File file = new File("E:\\temp\\temp1.jpg");
        String recognizeText = new OCRHelper().recognizeText(file);
        System.out.println(recognizeText);

        System.out.println(recognizeText.contains("返回"));
//        Assert.assertThat(recognizeText,StringContains.containsString("绝"));
    }
}
