package mytest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * isShutDown当调用shutdown()或shutdownNow()方法后返回为true。
 * isTerminated当调用shutdown()方法后，并且所有提交的任务完成后返回为true;
 * isTerminated当调用shutdownNow()方法后，成功停止后返回为true;
 * 如果线程池任务正常完成，都为false
 *
 * Terminated - 结束，终
 */
public class ThreadPoolTest {

    private static Logger logger = LoggerFactory.getLogger(ThreadPoolTest.class);

    public static void main(String[] args) throws InterruptedException {
//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10, 200, TimeUnit.MILLISECONDS,
//                new ArrayBlockingQueue<Runnable>(5));
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        Thread.sleep(1000);

        //每隔4秒按技能/替身键
        Thread thread1 = new Thread(){
            @Override
            public void run() {
                while (true) {
                    if ( Thread.currentThread().isInterrupted() ) {
                        logger.info("i has interputed");
                        break;
                    }
                    logger.info("释放奥义/密卷/替身");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        logger.info("重置标志位");
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        //攻击
        Thread hit = new Thread(){
            @Override
            public void run() {
                while (!Thread.currentThread().isInterrupted()) {
                    logger.info("攻击...");
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        logger.info("重置标志位,下次循环终止"+Thread.currentThread().getName());
                        Thread.currentThread().interrupt();
                    }
                }
            }
        };

        executor.execute(thread1);
        executor.execute(hit);
        //由进程负责监听 20s截屏校验确定一次状态
        Thread.sleep(3000);


        executor.shutdownNow();
        logger.info(executor.isShutdown()+"1");
        logger.info(executor.isTerminated()+"2");
        logger.info("--ok--");

    }
}


