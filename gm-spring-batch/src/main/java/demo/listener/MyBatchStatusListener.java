package demo.listener;

import org.springframework.batch.core.*;
import org.springframework.stereotype.Component;

@Component("myBatchStatusListener")
public class MyBatchStatusListener implements StepExecutionListener, JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("myBatchStatusListener -- bfjob");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("myBatchStatusListener -- afjob");
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("myBatchStatusListener -- bfstep");
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        System.out.println("myBatchStatusListener -- afstep");
        return stepExecution.getExitStatus();
    }
}
