package demo;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.event.KeyEvent;

import static demo.scene.ArenaScene.battle;
import static demo.utils.BooleanExp.*;
import static demo.utils.MouseKvUtils.mousePressOne;

public class RobotTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private Robot myRobot;

    @Before
    public void setUp() throws AWTException {
        myRobot = new Robot();
        myRobot.setAutoDelay(200);
    }

    /**
     * 模拟键盘操作
     * 这里要注意的是，所有的键按下以后都要再释放（不然它就一直按着了= =）。
     * @throws Exception 抛出异常：Thread
     */
    @Test
    public void testRobotKey() throws Exception {
        myRobot.keyPress(KeyEvent.VK_Q);        // 模拟键盘按下Q键（小写）
        myRobot.keyRelease(KeyEvent.VK_Q);      //键盘释放Q键
        myRobot.keyPress(KeyEvent.VK_Q);
        myRobot.keyRelease(KeyEvent.VK_Q);
        Thread.sleep(2000);
        myRobot.keyPress(KeyEvent.VK_BACK_SPACE); //
    }

    /**
     * 鼠标移动的代码会直接将鼠标从当前的位置跳到要到的位置
     * 模拟双击
     */
    @Test
    public void testRobotMouse() {
        myRobot.mouseMove(684, 184);
        myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);     // 模拟按下鼠标左键
        myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);   // 模拟释放鼠标左键
        myRobot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);     // 模拟按下鼠标左键
        myRobot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);   // 模拟释放鼠标左键
    }

    @Test
    public void testRobotColor(){
        Color color = myRobot.getPixelColor(73,676);
        System.out.println(color.getRed());
        System.out.println(color.getGreen());
        System.out.println(color.getBlue());
        System.out.println(color.getRGB());
        color = myRobot.getPixelColor(711,224);
        System.out.println(color.getRed());
        System.out.println(color.getGreen());
        System.out.println(color.getBlue());
        System.out.println(color.getRGB());
    }

    /**
     * 角斗场自动化运行
     */
    @Test
    public void testGameArean() {
        //点击角斗场
        mousePressOne(467, 384);
        //等待至页面跳转完成
        is2ArenaFinished();
        //点击忍术对战选人页面
        mousePressOne(762, 418);
        //忍术对战选人页面加载完成
        isSelectHeroPage();
        //点击开战
//        mousePressOne(1198,636);
        for(int i = 0;i < 3;i++)
        {
            logger.info("--------start 一局----------");
            //点击开战
            mousePressOne(1198,636);
            battle();
            //点击返回
            mousePressOne(134,689);
            //等待至页面跳转完成
            is2ArenaFinished();
            //点击忍术对战选人页面
            mousePressOne(762, 418);
            //忍术对战选人页面加载完成
            isSelectHeroPage();
            logger.info("--------end 一局----------");
        }
    }


}
