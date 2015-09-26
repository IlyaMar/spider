package org.imartynov.spider.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class LoginInfo {
	@Id
	@GeneratedValue
	Long id;
		
	Boolean last_result;
	Date last_date;
	Date next_date;
	
	public LoginInfo() {
		last_result = false;
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

	
	
}
