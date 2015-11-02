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
    	Long id = Long.parseLong(jobParams.getProperty("id"));
        Account a = accountManager.get(id);
        System.out.println("LoginBatchlet process, login " + a.getLogin());
        LoginProcessor lp = new LoginProcessor(a);
        boolean result = true;
        String jobResult = "COMPLETED";
        if (lp.run()) {
        	result = true;
        	jobResult = "COMPLETED";
        }
        else {
        	result = false;
        	jobResult = "FAILED";
        }

    	accountManager.runCallback(id, result);
    	return jobResult;
    }

	@Override
	public void stop() throws Exception {
        System.out.println("LoginBatchlet stop");
		
	}
}