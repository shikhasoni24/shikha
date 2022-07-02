package com.shikha.core.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.mail.MessagingException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.osgi.framework.Constants;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.DownloadPDFService;
import com.shikha.core.service.WelcomeEmailService;

@Component(service = Servlet.class, property = { Constants.SERVICE_DESCRIPTION + "=Download PDF Servlet",
		"sling.servlet.methods=" + HttpConstants.METHOD_GET, "sling.servlet.paths=" + "/bin/hotel/DownloadPDF" })
public class DownloadPDFServlet extends SlingAllMethodsServlet {

	@Reference
	DownloadPDFService downloadPDFService;
	
	@Reference
	WelcomeEmailService welcomeEmailService;
	
	private static final long serialVersionUID = 1L;

	protected void doPost(final SlingHttpServletRequest req, final SlingHttpServletResponse res)
			throws ServletException, IOException {

		OutputStream os = res.getOutputStream();
		// JSONObject j=new JSONObject(req.getParameter("userdata").toString());
		// JSONObject j=new JSONObject(req.getParameter("userdata").toString());
		String userId = req.getParameter("firstName");
		String userName = req.getParameter("lastName");
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			downloadPDFService.createPdf(baos, Integer.parseInt(userId), userName);
			res.setContentType("application/pdf");
			res.setContentLength(baos.size());
			baos.writeTo(os);// writeTo(OutputStream out) method writes the
								// content of this byte array output stream to
								// the specified ouput stream argument.
			res.setStatus(SlingHttpServletResponse.SC_OK);
			os.flush();
			os.close();
			baos.flush();
			baos.close();

			welcomeEmailService.setEmailData(userName, baos, "pdf");
		} catch (ServiceException | MessagingException e) {
			e.printStackTrace();
		}

	}
	

}
