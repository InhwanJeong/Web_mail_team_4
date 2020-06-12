package cse.maven_webmail.model;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

public class BookedMailExecutor implements Job {

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        StringBuilder builder = new StringBuilder();
        builder.append(new Date());
        //System.out.println(builder.toString());
    }
}
