package demo.thread;

import demo.cfg.Act;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DoThread extends Thread{

    private static Logger logger = LoggerFactory.getLogger(DoThread.class);

    private Act act;
    private int sleepTime;

    public DoThread(int sleepTime,Act act ){
        this.sleepTime = sleepTime;
        this.act = act;
    }

    @Override
    public void run(){
        Thread thisThread = Thread.currentThread();
        while (!thisThread.isInterrupted()) {
            act.doAct();
            try {
                Thread.sleep(sleepTime);
            } catch (InterruptedException e) {
                logger.info("重置标志位,下次循环终止"+thisThread.getName());
                thisThread.interrupt();
            }
        }
    }

}
