package org.imartynov.spider.service;

import java.util.Date;

import org.imartynov.spider.domain.Account;

public class AccountDTO {
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getLast_result() {
		return last_result;
	}

	public void setLast_result(Boolean last_result) {
		this.last_result = last_result;
	}

	public Date getLast_date() {
		return last_date;
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public Date getNext_date() {
		return next_date;
	}

	public void setNext_date(Date next_date) {
		this.next_date = next_date;
	}
	
	Long id;
	String login;
	String password;
	Boolean last_result;
	Date last_date;
	Date next_date;
	
	public AccountDTO(String login, String password, Boolean last_result, Date last_date, Date next_date) {
		this.login = login;
		this.password = password;
		this.last_result = last_result;
		this.last_date = last_date;
		this.next_date = next_date;
	}
	
	public AccountDTO(Account a) {
		this.id = a.getId();
		this.login = a.getLogin();
		this.password = a.getPassword();
		this.last_result = a.getLoginInfo().getLast_result();
		this.last_date = a.getLoginInfo().getLast_date();
		this.next_date = a.getLoginInfo().getLast_date();
	}

}
