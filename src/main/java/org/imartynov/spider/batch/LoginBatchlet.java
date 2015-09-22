package org.imartynov.spider.batch;

import java.util.Properties;

import javax.batch.api.Batchlet;
import javax.batch.runtime.BatchRuntime;
import javax.batch.runtime.context.JobContext;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.inject.Named;

import org.imartynov.spider.domain.Account;
import org.imartynov.spider.ejb.AccountManager;

@Dependent
@Named("LoginBatchlet")
public class LoginBatchlet implements Batchlet {
	@Inject
    private JobContext jobCtx;

    @EJB
    AccountManager accountManager;
	
    public LoginBatchlet() {
	}

    @Override
    public String process() throws Exception {
    	Properties jobParams = BatchRuntime.getJobOperator().getParameters(jobCtx.getExecutionId());    	
    	String login = jobParams.getProperty("login");
        System.out.println("LoginBatchlet process, login " + login);
        
        Account a = accountManager.get(login);
        LoginProcessor lp = new LoginProcessor(a);
        if (lp.run())
        	return "COMPLETED";
        else
        	return "FAILED";
    }

	@Override
	public void stop() throws Exception {
        System.out.println("LoginBatchlet stop");
		
	}
}