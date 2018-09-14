package demo.main;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainStart {
    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = new ClassPathXmlApplicationContext("myjobs.xml");
        JobLauncher launcher = (JobLauncher) ctx.getBean("jobLauncher");
        Job job = (Job) ctx.getBean("ccsJob");
        try {
            /* 运行Job */
            JobExecution result = launcher.run(job,new JobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        ctx.registerShutdownHook();
    }
}
