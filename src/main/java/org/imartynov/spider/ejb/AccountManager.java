package org.imartynov.spider.ejb;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

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
//@DependsOn("StartupBean")
public class AccountManager {
	private final int LOGIN_HOUR_MIN = 9;
	private final int LOGIN_HOUR_MAX = 11;
	private final int LOGIN_DURATION_LIMIT = 3600; // seconds
		
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

	public void delete(final Long id) {
		Account a = get(id);
		em.remove(a);
	}

	@SuppressWarnings("unchecked")
	public List<Account> getAll() {
		//System.out.println("getAll");
        Query query = em.createQuery("select x from " + Account.class.getSimpleName() + " x");
        return (List<Account>) query.getResultList();
	}

	public Account get(final Long id) {
        return em.find(Account.class, id);
	}
	
	public void runCallback(final Long id, final boolean result) {
		Account a = get(id);		
		LoginInfo li = a.getLoginInfo();
		li.setIn_process(false);
		li.setLast_result(result);
		li.setLast_date(new Date());
		Date nextTime = getNextDate(a);
		li.setNext_date(nextTime);		
		em.persist(li);
		System.out.println("result: " + result + ", next run set to " + nextTime + " for login " + a.getLogin());
	}
	
	private int getLoginHour() {
		Random rand = new Random();
	    int randomNum = rand.nextInt((LOGIN_HOUR_MAX - LOGIN_HOUR_MIN) + 1) + LOGIN_HOUR_MIN;
	    return randomNum;		
	}
	
	private int getLoginMinSec() {
		Random rand = new Random();
	    int randomNum = rand.nextInt((59 - 0) + 1) + 0;
	    return randomNum;		
	}
	
	
	private Date getNextDate(Account a) {
		LoginInfo li = a.getLoginInfo();
		Date last = li.getLast_date();		
		Calendar c = new Calendar.Builder().setInstant(last).build();
	    c.add(Calendar.DATE, 1);
	    c.set(Calendar.HOUR_OF_DAY, getLoginHour());
	    c.set(Calendar.MINUTE, getLoginMinSec());
	    c.set(Calendar.SECOND, getLoginMinSec());
		return c.getTime();
	}
	
	
	public void schedule(final Long id) {
		System.out.println("schedule task for account " + id);
		Account a = get(id);
		a.getLoginInfo().setIn_process(true);
		em.persist(a.getLoginInfo());
		JobOperator jobOperator = BatchRuntime.getJobOperator();
		Properties props = new Properties();
		props.setProperty("id", id.toString());
		props.setProperty("login", a.getLogin());
		props.setProperty("password", a.getPassword());
		
		long execID = jobOperator.start("doLogin", props);
		System.out.println("started job " + execID + " for login " + a);
	}
	
	@Schedule(second="*/7", minute="*", hour="*")
	public void doScheduled() {
		System.out.println("doScheduled");
		Date now = new Date();		
		for (Account a : getAll()) {
			Date next = a.getLoginInfo().getNext_date();
			//System.out.println("account " + a.getLogin() + ", next date " + next + ", now " + now);
			//System.out.println("cond1 " + now.after(next));
			//System.out.println("cond2 " + !a.getLoginInfo().getIn_process());
			if ( (now.after(next) && !a.getLoginInfo().getIn_process()))			
				schedule(a.getId());
		}		
		//System.out.println("doSchedulde finish");
	}
}
