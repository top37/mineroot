package demo.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.listener.StepExecutionListenerSupport;

public class DebugSupportListener extends StepExecutionListenerSupport {

    public DebugSupportListener() {
    }
    public void beforeStep(StepExecution stepExecution) {
        System.out.println("DebugSupportListener -- bfStep");
        if (stepExecution.getStepName().equals(System.getProperty("debug.break.before"))) {
            throw new RuntimeException("调试断点，停在[" + stepExecution.getStepName() + "]之前");
        }
    }
}
