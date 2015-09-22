package org.imartynov.spider.batch;

import org.imartynov.spider.domain.Account;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.util.Cookie;

public class LoginProcessor {
	String domain = "portal.fedsfm.ru";
	String loginUrl = "https://portal.fedsfm.ru/Account/login.aspx";
	String logoutUrl = "https://portal.fedsfm.ru/account/logoff.aspx";
	
	Account account;
	
	public LoginProcessor(Account a) {
		init();
		account = a;		
	}
	
	void init() {
		/*cookieStore = new BasicCookieStore();
		client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
		localContext = new BasicHttpContext();
		localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);*/
	}
	
	
	/*void loadForm() throws Exception {
		HttpGet request = new HttpGet(loginUrl);

		request.addHeader("User-Agent", USER_AGENT);
		System.out.println("GET on login form ...");
		HttpResponse response = client.execute(request, localContext);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	}
	
	
	void sendPost() throws Exception {
		HttpUriRequest post = RequestBuilder.post()
                .setUri(new URI(loginUrl))
                .addParameter("loginText", account.getLogin())
                .addParameter("passwordText", account.getPassword())
                .build();
		
		// add header
		post.setHeader("User-Agent", USER_AGENT);

		System.out.println("POST on login form ...");
		CloseableHttpResponse r = client.execute(post, localContext);
        try {
            HttpEntity entity = r.getEntity();

            System.out.println("Login form get: " + r.getStatusLine());
            EntityUtils.consume(entity);

            System.out.println("Post logon cookies:");
            List<Cookie> cookies = cookieStore.getCookies();
            if (cookies.isEmpty()) {
                System.out.println("None");
            } else {
                for (int i = 0; i < cookies.size(); i++) {
                    System.out.println("- " + cookies.get(i).toString());
                }
            }
        } finally {
            r.close();
        }		
	}

	void logout () throws Exception {
		HttpGet request = new HttpGet(loginUrl);

		request.addHeader("User-Agent", USER_AGENT);
		System.out.println("GET on login form ...");
		HttpResponse response = client.execute(request, localContext);
		System.out.println("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	}*/
	
	public boolean run() throws Exception {
	    try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {		    	
	    	webClient.getOptions().setRedirectEnabled(true);
	    	webClient.getCookieManager().setCookiesEnabled(true);
	    	webClient.getOptions().setJavaScriptEnabled(true);
	    	webClient.getOptions().setThrowExceptionOnScriptError(false);
	    	
	    	java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(java.util.logging.Level.OFF);
	        java.util.logging.Logger.getLogger("org.apache.http").setLevel(java.util.logging.Level.OFF);
	    	
	        System.out.println("loading login form for " + account.getLogin());

	        final HtmlPage page1 = webClient.getPage(loginUrl);
	
	        // Get the form that we are dealing with and within that form, 
	        // find the submit button and the field that we want to change.
	        final HtmlForm form = page1.getForms().get(0);
	
	        final HtmlSubmitInput button = form.getInputByName("logInButton");
	        final HtmlTextInput loginField = form.getInputByName("loginText");
	        final HtmlPasswordInput pwdField = form.getInputByName("passwordText");
	
	        // Change the value of the text field
	        loginField.setValueAttribute(account.getLogin());
	        pwdField.setValueAttribute(account.getPassword());
	
	        System.out.println("submit form ... ");
	        button.click();
	        Cookie authCookie = webClient.getCookieManager().getCookie("ASP.NET_SessionId");
	        System.out.println("got cookie: " + authCookie);
	        return authCookie != null;
	    }
	}
	
	
}
