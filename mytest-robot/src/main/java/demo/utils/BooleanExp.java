package demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.lang.reflect.Method;
import java.util.concurrent.ScheduledExecutorService;

import static demo.utils.CaptureHelper.createCapEasyFinish;
import static demo.utils.ImageHelper.to2Val;
import static demo.utils.OCRHelper.resolvImg;

public class BooleanExp {

    private static Logger logger = LoggerFactory.getLogger(BooleanExp.class);

    private static Robot myRobot;

    static{
        try {
            myRobot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    /**
     * 单强 2018年6月12日14:10:06
     * 关闭线程池中所有线程
     * @param executor 线程池
     */
    public static void executorIsOver(ScheduledExecutorService executor){
        if(!executor.isTerminated()){
            executor.shutdownNow();
            while(!executor.isTerminated()){ logger.info("线程未结束..."); }
            logger.info("所有线程已关闭");
        }else {
            logger.info("已执行过该方法，线程已关闭");
        }
    }

    private static void process(String finishLoad,int x, int y, int widthx, int heighty){
        do {
            logger.info("等待画面跳转...1.5s");
            stop(1500);
            createCapEasyFinish(x,y,widthx,heighty);
            to2Val();
        }while (!resolvImg().contains(finishLoad));
    }

    private static boolean boolProcess(String finishLoad,int x, int y, int widthx, int heighty){
            createCapEasyFinish(x,y,widthx,heighty);
            to2Val();
            return resolvImg().contains(finishLoad);
    }

    /**
     * 忍者大赛页面
     * @return 状态
     */
    public static boolean isRZDSPage(){
        return boolProcess("忍",593,197,646,407);
    }
    /**
     * 羁绊对战页面
     * @return 真
     */
    public static boolean isJBPage(){
        return boolProcess("羁",242,198,300,399);
    }

    /**
     * 大蛇丸试炼规则
     * @return 返回
     */
    public static boolean isDSWGZPage(){
        return boolProcess("硼",489,113,815,334);
    }

    /**
     * 大蛇丸试炼准备就绪
     * @return 返回true/false
     */
    public static boolean isDSWZBJXPage(){
        return boolProcess("准备",578,567,738,607);
    }

    public static void isSelectHeroPage(){
        process("啡吝",270,194,314,272);
    }

    /**
     * 判断角斗场Arena页面是否推送完成
     * 判断点：
     * 1.返回标志加载完成
     * 判断方法：图片识别
     */
    public static void is2ArenaFinished(){
        process("返回",150,670,229,718);
    }

    /**
     * 判断角斗场Arena页面是否推送完成
     * 判断点：
     * 1.返回标志加载完成 73,676  -  249,217,62  -403138
     * 2.忍术对战栏加载完成 711,224  -  203,102,74  -3447222
     * 判断方法：Color
     * 因判断方法过于薄弱，舍弃。
     * 替代方案：is2ArenaFinished()
     */
    @Deprecated
    public static boolean isEntered(){
        return isMatch(73,676,"-403138") &&
                isMatch(711,224,"-3447222");
    }

    /**
     * 判断 是否有人干扰 : 弹出协助请求
     * 判断点：
     * 1.产生拒绝位 531,414  -  7,121,153 | -16287335
     * 2.产生接受位 729,414  -  245,180,2 | -674814
     * 判断方法：Color
     */
    @Deprecated
    public static boolean isReject(){
        boolean reject = isMatch(534,414,"-16287335");
        boolean accept = isMatch(534,414,"-674814");
        return reject && accept;
    }

    @Deprecated
    private static boolean isMatch(int x,int y,String rgb){
        Color color = myRobot.getPixelColor(x, y);
        return rgb.equals(color.getRGB()+"");
    }

    /**
     * @author top37 2018/6/10
     * 封装sleep,停顿进程
     * */
    public static void stop(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            logger.info("调用sleep异常");
        }
    }

    /**
     * 通过String类型的MethodName来调用相应的方法
     * @param clz 静态方法所在的类
     *
     */
    @SuppressWarnings("unchecked")
    public static Boolean invokeByMethodName(String MethodName,String str,Class clz){
        boolean b = false;
        try
        {
            Method method = clz.getDeclaredMethod(MethodName, String.class);
            method.setAccessible(true);
            b = (boolean) method.invoke(null, str);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return b;
    }
}
