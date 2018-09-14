package demo.utils;

import java.awt.*;
import java.awt.event.KeyEvent;

public class MouseKvUtils {

    private static Robot myRobot;

    static{
        try {
            myRobot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 模拟键盘操作操作：
     * 按钮及自动释放
     */
    public static void kvPressOne(int key){
        myRobot.keyPress(key);
        myRobot.keyRelease(key);
    }

    /**
     * 模拟鼠标操作：
     * 单机鼠标左键
     */
    public static void mousePressOne(int x,int y){
        myRobot.mouseMove(x, y);
        myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

    /**
     * 模拟鼠标操作：
     * 双机鼠标左键
     */
    public static void mousePressTwo(int x,int y){
        myRobot.mouseMove(x, y);
        myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
        myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
        myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
    }

}
