package demo.utils;

import demo.cfg.CommonStr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 自动截屏
 *
 *
 */
public class CaptureHelper implements CommonStr {

    private static Logger logger = LoggerFactory.getLogger(CaptureHelper.class);

    private static Robot robot;

    static {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param x x坐标
     * @param y y坐标
     * @param widthx 测宽时x坐标
     * @param heighty 测宽时y坐标
     * @return 截图格式
     */
    public static boolean createCapEasyFinish(int x, int y, int widthx, int heighty){
        return createCapFinish(x, y, widthx-x, heighty-y);
    }

    public static boolean createCapFinish(int x, int y, int width, int height){
        return createCapFinish(x, y, width, height,jpg,tempPath);
    }

    public static boolean createCapFinish(int x, int y, int width, int height,String capType,String targetPath){
        logger.info("截屏开始...");
        BufferedImage bImage = robot.createScreenCapture(new
                Rectangle(x, y, width, height));
        try {
            ImageIO.write(bImage, capType, new File(targetPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("截屏结束...");
        return true;
    }

}

