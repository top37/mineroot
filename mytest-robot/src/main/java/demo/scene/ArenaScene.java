package demo.scene;

import demo.thread.DoThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static demo.utils.BooleanExp.*;
import static demo.utils.MouseKvUtils.kvPressOne;
import static demo.utils.MouseKvUtils.mousePressOne;
import static java.awt.event.KeyEvent.*;

public class ArenaScene {

    public static final Logger logger = LoggerFactory.getLogger(ArenaScene.class);

    public static void battle(){
        /*等待角斗场加载10s*/
        stop(10000);

        //创建线程池
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        //奥义 - I//密卷 - N//通灵 - M//2s
        Thread thread1 = new DoThread(2000,() -> {  logger.info("释放奥义/密卷/替身");kvPressOne(VK_I);kvPressOne(VK_N);kvPressOne(VK_M); });

        //一技能 - J//二技能 - U//替身 - H//4s
        Thread thread2 = new DoThread(4000,() -> { logger.info("释放技能");kvPressOne(VK_J);kvPressOne(VK_U);kvPressOne(VK_H); });

        //普攻//0.2s
        Thread hit = new DoThread(200,() -> { logger.info("攻击...");kvPressOne(VK_K); });

        executor.execute(thread1);
        executor.execute(thread2);
        executor.execute(hit);

        //由进程负责监听 20s截屏校验确定一次状态
        while (true){
            stop(20000);
            if(isJBPage()) break;
            if(isRZDSPage()) break;
            //进入大蛇丸试炼 - 单独处理
            if(isDSWGZPage() || isDSWZBJXPage()){
                //结束各线程动作
                executorIsOver(executor);
                //等待30s
                stop(30000);
                //点击返回
                mousePressOne(134,689);
                break;
            }
        }
        executorIsOver(executor);
    }
}
