package demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/myjobstest.xml")
public class MainTest {
    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;
    private JobParametersBuilder jobParamBuilder = new JobParametersBuilder();

    @Autowired
    private Job ccsJob;
    @Autowired
    private JobLauncher jobLauncher;

    @Test
    public void doTest() throws Exception{
        JobExecution jobExecution = jobLauncherTestUtils.launchJob(jobParamBuilder.toJobParameters());
        assertThat(jobExecution.getExitStatus().toString(), containsString("COMPLETED") );
    }

    @Test
    public void doTest2() throws Exception{
        JobExecution jobExecution = jobLauncher.run(ccsJob, new JobParameters());
        assertThat(jobExecution.getExitStatus().toString(), containsString("COMPLETED") );
    }

}
