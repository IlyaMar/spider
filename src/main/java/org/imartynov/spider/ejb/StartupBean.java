package org.imartynov.spider.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.imartynov.spider.domain.Account;

@Startup
@Singleton
public class StartupBean {
	
    @Inject
    private EntityManager em;
    
    @EJB
    private AccountManager am;
	
	@PostConstruct
	void init() {
		System.out.println("StartupBean init");
		System.out.println("am is " + am);
		System.out.println("em is " + em);
		for (Account a : am.getAll()) {
			System.out.println("StartupBean process: " + a.getLogin());
			a.getLoginInfo().setIn_process(false);
			em.persist(a.getLoginInfo());
		}
		System.out.println("StartupBean finish");
		
		/*{
	        LoginInfo li = new LoginInfo();
	        li.setNext_date(new Date());
	        em.persist(li);
	        Account a = new Account();
	        a.setLogin("sergey");
	        a.setPassword("234jitewt$");        
	        a.setLoginInfo(li);
	        em.persist(a);
		}

		{
	        LoginInfo li = new LoginInfo();
	        li.setNext_date(new Date());
	        em.persist(li);
	        Account a = new Account();
	        a.setLogin("ivan");
	        a.setPassword("UNOwef235_");        
	        a.setLoginInfo(li);        
	        em.persist(a);
		}

		
        {
        	Calendar c = Calendar.getInstance(); 
        	c.setTime(new Date()); 
        	c.add(Calendar.DATE, -1);
        	Date yesterday = c.getTime();
        	
        	LoginInfo li = new LoginInfo();
        	li.setLast_result(true);
        	li.setLast_date(yesterday);
	        li.setNext_date(new Date());
	        em.persist(li);
	        Account a = new Account();
	        a.setLogin("maria");
	        a.setPassword("&dsf07IY");        
	        a.setLoginInfo(li);        
	        em.persist(a);
        }*/

	}
	
}
