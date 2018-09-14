package mytest;

class Interrupted_root_make extends Thread{

    @Override
    public void run() {
        while (true) {

            if ( Thread.currentThread().isInterrupted() ) {
                System.out.println("i has interputed");
                break;
            }
            System.out.println("-----");


            /*
             * 使用中断命令，外部只是其的一个通知命令，实际上的中断的操作还是需要内部自己判断操作，退出
             */
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Interrupted When Sleep ...");
                // Thread.sleep()方法由于中断抛出异常。
                // Java虚拟机会先将该线程的中断标识位清除，然后抛出InterruptedException，
                // 因为在发生InterruptedException异常的时候，会清除中断标记
                // 如果不加处理，那么下一次循环开始的时候，就无法捕获这个异常。
                // 故在异常处理中，再次设置中断标记位
                Thread.currentThread().interrupt();

            }

            Thread.yield();
        }
    }
}
public class Interruped_test {
    public static void main(String[] args) throws InterruptedException {
        Interrupted_root_make interrupted_root_make = new Interrupted_root_make();
        interrupted_root_make.start();
        Thread.sleep(1000);
        interrupted_root_make.interrupt();




    }
}
