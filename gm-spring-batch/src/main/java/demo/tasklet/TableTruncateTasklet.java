package demo.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Required;

public class TableTruncateTasklet  implements Tasklet {


    private String mypp;

    public String getMypp() {
        return mypp;
    }

    @Required
    public void setMypp(String mypp) {
        this.mypp = mypp;
    }

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
        System.out.println("TableTruncateTasklet-->"+mypp);
        return null;
    }
}
