package com.shikha.core.service.serviceimpl;

import java.io.ByteArrayOutputStream;


import java.io.IOException;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;
import com.shikha.core.exception.exceptionimpl.ServiceException;
import com.shikha.core.service.WelcomeEmailService;

@Component(service = WelcomeEmailService.class, immediate = true)
public class WelcomeEmailServiceImpl implements WelcomeEmailService {

	@Reference
	private ConfigurationAdmin configAdmin;

	@Reference
	MessageGatewayService messageGatewayService;

	protected final String pid = "com.day.cq.mailer.DefaultMailService";
	private int propssSize;

	@Activate
	protected void activate() throws Exception {

	}

	public Map<String, String> getOsgiData() throws ServiceException {
		Configuration conf = null;

		try {
			Map<String, String> map = new HashMap<>();
			conf = configAdmin.getConfiguration(pid);
			propssSize = 0;
			Dictionary<String, Object> propss = conf.getProperties();
			propssSize = propss.size();
			if (propssSize > 0) {
				map.put("ssl", propss.get("smtp.ssl").toString());
				map.put("host", propss.get("smtp.host").toString());
				map.put("port", propss.get("smtp.port").toString());
				map.put("user", propss.get("smtp.user").toString());
				map.put("password", propss.get("smtp.password").toString());
			}
			return map;
		} catch (IOException e) {
			throw new ServiceException();
		}
	}

	public void setEmailData(String loginUsername, ByteArrayOutputStream baos, String fileFormat) throws ServiceException {
		try {
			Map<String, String> map1 = getOsgiData();
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.ssl.enable", map1.get("ssl"));
			props.put("mail.smtp.host", map1.get("host"));
			props.put("mail.smtp.port", map1.get("port"));

			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(map1.get("user"), map1.get("password"));
				}
			});

			String toEmail = "shikhasoni1225@gmail.com";
			String ccEmail = "shikhasoni1224@gmail.com";
			String fromEmail = "shikhasoni1224@gmail.com";
			String subject = "Welcome Email";
			Multipart m = new MimeMultipart();
			DataSource source = null;
			
			if(fileFormat.equalsIgnoreCase("pdf"))
				source = new ByteArrayDataSource(baos.toByteArray(), "application/pdf");//for pdf
			else if(fileFormat.equalsIgnoreCase("excel"))
			 source = new ByteArrayDataSource(baos.toByteArray(), "application/vnd.ms-sheet");// we
																								// are
																								// recieving
																								// the
																								// document
																								// (baos)
																								// and
																								// changing
																								// it
																								// to
																								// byte
																								// array.
			MimeBodyPart textBodypart = new MimeBodyPart();
			textBodypart.setText("Welcome " + loginUsername
					+ " You are successfully logged in...Please start booking Rooms\nPlease find the attachment to view your already booked rooms\n ");
			MimeBodyPart pdfAttachment = new MimeBodyPart();

			pdfAttachment.setDataHandler(new DataHandler(source));// same as
			if(fileFormat.equalsIgnoreCase("pdf"))			
			 pdfAttachment.setFileName("BookedRooms.pdf"); 
			else if(fileFormat.equalsIgnoreCase("excel"))
			pdfAttachment.setFileName("BookedRooms.xlsx");
			
			m.addBodyPart(textBodypart);
			m.addBodyPart(pdfAttachment);
			sendEmail(toEmail, ccEmail, fromEmail, subject, m, session);

		} catch (MessagingException e) {

			e.printStackTrace();
		}

	}

	public void sendEmail(String toEmail, String ccEmail, String fromEmail, String subject, Multipart m,
			Session session) throws ServiceException {
		try {
			// Setting up the email message
			/*
			 * Email email = new SimpleEmail(); first we were using Email email
			 * = new SimpleEmail(); when we wanted to just send the mail , but
			 * for attaching pdf in mail, we did not find any method in email so
			 * we used Mimemessage msg= new
			 * MimeMessage(session);(msg.setContent(m);) but it needed session
			 * so we took session and read the OOB configs
			 */
			MimeMessage msg = new MimeMessage(session);

		//	msg.setFrom(fromEmail);

			msg.setSubject(subject);
			msg.setContent(msg, "text/plain");

			msg.setText("Email Body Text");
			msg.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

			msg.setContent(m);

			// Message msg=new WelcomeEmailServiceImpl()
			// Get the details to send email
			// email.setSubject(subject);
			// email.setMsg(content);
			// email.addTo(toEmail);
			// email.addCc(ccEmail);
			// email.setFrom(fromEmail);
			// Inject the message gateway service and send email
			// MessageGateway<Email> messageGateway
			// =messageGatewayService.getGateway(Email.class);
			// Send the email
			Transport.send(msg);
		} catch (MessagingException e) {
			throw new ServiceException(e);
		}

	}

	public void sendOTP(String emailid,String otp) throws ServiceException {

		try {
			String toEmail = emailid;
			String ccEmail = "shikhasoni1224@gmail.com";
			String fromEmail = "shikhasoni1225@gmail.com";
			/* fromEmail cannot be blank here its value should be declared mandatorily and it should be in format "anything@gmail.com"
			 but it will always pick the value from day cq mail service OOTB config and this day cq config mailid should have less 
			 secure app permission always enabled */
			
			String subject = "OTP";
			String content = otp;
			Email email = new SimpleEmail();
			email.setSubject(subject);
			email.setMsg(content);
			email.addTo(toEmail);
			email.addCc(ccEmail);
			email.setFrom(fromEmail);
			MessageGateway<Email> messageGateway = messageGatewayService.getGateway(Email.class);
			messageGateway.send(email);
		} catch (EmailException e) {

			throw new ServiceException(e);
		}

	}
}
