package org.imartynov.spider.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.imartynov.spider.domain.Account;

public class AccountDTO {
	private static SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
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

	public String getLast_date() {
		if (last_date == null)
			return null;
		return dt.format(last_date);
	}

	public void setLast_date(Date last_date) {
		this.last_date = last_date;
	}

	public String getNext_date() {
		if (next_date == null)
			return null;
		return dt.format(next_date);
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
		this.next_date = a.getLoginInfo().getNext_date();
	}

}
