package org.imartynov.spider.batch;

import org.imartynov.spider.domain.Account;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class LoginProcessor {
	String domain = "portal.fedsfm.ru";
	String loginUrl = "https://portal.fedsfm.ru/Account/login";
	String logoutUrl = "https://portal.fedsfm.ru/account/logoff";
	String loginButtonName = "loginButton";
	String loginInputName = "loginEditor";
	String passwordInputName = "passwordEditor";
	
	Account account;
	
	public LoginProcessor(Account a) {
		init();
		account = a;		
	}
	
	void init() {
	}
	
	public boolean run() throws Exception {
	    boolean result = false;
		try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {		    	
	    	webClient.getOptions().setRedirectEnabled(true);
	    	webClient.getCookieManager().setCookiesEnabled(true);
	    	webClient.getOptions().setJavaScriptEnabled(true);
	    	webClient.getOptions().setThrowExceptionOnScriptError(false);
	    	
	    	//java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	        //java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	    	
	        System.out.println("loading login form for " + account.getLogin());

	        final HtmlPage page1 = webClient.getPage(loginUrl);
	
	        // Get the form that we are dealing with and within that form, 
	        // find the submit button and the field that we want to change.
	        final HtmlForm form = page1.getForms().get(0);
	
	        final HtmlButton button = form.getButtonByName(loginButtonName);
	        final HtmlTextInput loginField = form.getInputByName(loginInputName);
	        final HtmlPasswordInput pwdField = form.getInputByName(passwordInputName);
	
	        // Change the value of the text field
	        loginField.setValueAttribute(account.getLogin());
	        pwdField.setValueAttribute(account.getPassword());
	
	        System.out.println("submit form ... ");
	        button.click();
	        Cookie authCookie = webClient.getCookieManager().getCookie("ASP.NET_SessionId");
	        System.out.println("got auth cookie: " + authCookie);
	        System.out.println("dump all cookies:");
	        for (Cookie c : webClient.getCookieManager().getCookies()) {
		        System.out.println(c);
	        }
	        result = authCookie != null;
	    }
		catch (Exception e) {
			System.out.println("Exception during form login:");
			e.printStackTrace(System.out);
		}
		return result;
	}
	
	
}
