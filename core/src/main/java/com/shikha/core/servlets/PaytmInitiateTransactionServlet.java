package com.shikha.core.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;

import com.shikha.core.service.PaytmInitiateTransactionService;

@Component(service=Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=paytm Initiate Transaction",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/initiateTransaction" })
public class PaytmInitiateTransactionServlet extends SlingAllMethodsServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private transient PaytmInitiateTransactionService paytmInitiateTransactionService;
	
	@Override
	protected void doGet(final SlingHttpServletRequest req, final SlingHttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.getWriter().write(paytmInitiateTransactionService.getInitiateTransactionResponsse());
		
	}

}
