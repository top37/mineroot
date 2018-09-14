package mytest.packageThread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static demo.utils.BooleanExp.executorIsOver;

public class MainTest {

    private static Logger logger = LoggerFactory.getLogger(MainTest.class);

    public static void main(String[] args) throws InterruptedException {

//        Act act;
//        act = new Act() {
//            @Override
//            public void doAct() {
//                logger.info("攻击--");
//            }
//        };
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);

        Thread t1 = new DoThread(() -> logger.info("攻击--"),200);
        Thread t2 = new DoThread(() -> logger.info("防守--"),300);

        executor.execute(t1);
        executor.execute(t2);

        Thread.sleep(2000);
        executorIsOver(executor);

        executorIsOver(executor);
    }
}
