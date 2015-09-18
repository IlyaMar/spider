package org.imartynov.spider.batch;

import java.util.Properties;

import javax.batch.api.Batchlet;
import javax.batch.runtime.context.JobContext;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

@Dependent
@Named("LoginBatchlet")
public class LoginBatchlet implements Batchlet {
	@Inject
    private JobContext jobCtx;

    public LoginBatchlet() {
	}

    @Override
    public String process() throws Exception {
        Properties p = jobCtx.getProperties();
    	String login = p.getProperty("login");
        System.out.println("LoginBatchlet process, login " + login);
        return "COMPLETED";
    }

	@Override
	public void stop() throws Exception {
        System.out.println("LoginBatchlet stop");
		
	}
}