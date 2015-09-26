package org.imartynov.spider.ejb;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.DependsOn;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.imartynov.spider.domain.Account;
import org.imartynov.spider.domain.LoginInfo;

@Stateless
@DependsOn("StartupBean")
public class AccountManager {

    @Inject
    private EntityManager em;

	
	public AccountManager() {
	}
	
	public void add(final Account a) {
        System.out.println("Registering " + a);
        LoginInfo li = new LoginInfo();
        li.setNext_date(new Date());
        em.persist(li);		
        a.setLoginInfo(li);        
        em.persist(a);		
	}

	public void delete(final String login) {
		Account a = get(login);
		em.remove(a);
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAll() {
        Query query = em.createQuery("select x from " + Account.class.getSimpleName() + " x");
        return (List<Account>) query.getResultList();
	}

	public Account get(final String login) {
        return em.find(Account.class, login);
	}
	
	public void schedule(final String login) {
		Account a = get(login);
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();
		props.setProperty("login", a.getLogin());
		props.setProperty("password", a.getPassword());
		
		long execID = jobOperator.start("doLogin", props);
		System.out.println("started job " + execID + " for login " + a);
	}
	
	
	
	@Schedule(second="*/10", minute="*", hour="2")
	public void doLogin() {
		System.out.println("do scheduled login");
		for (Account a : getAll()) {
			schedule(a.getLogin());
		}		
	}
}
