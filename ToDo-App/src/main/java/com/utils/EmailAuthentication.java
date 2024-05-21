package com.utils;

import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
/**
 * EmailAuthentication is a simple class that handles setting up properties,emial creation, generation of OTP
 * 
 */
public class EmailAuthentication {

	private static Session session;
	private final static String senderEmail = "pulusujithendra@gmail.com";
	private final static String password = "hfbdwlkrrhuxulab";

	static {
		Session session = null;
	}

	/**
	 * SMTP server configuration for Gmail
	 */
	public static void initiate() {

		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		// Creating a session with authentication
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(senderEmail, password);
			}
		});
	}
	
	/**
	 * Compose and sends Email to the valid User. 
	 *
	 * @param String the subject for the composed Email.
	 * @param String the textBody for the composed Email.
	 * @param String the Email of the user.
	 */
	public static void emailCreator(String subject, String textBody, String userEmail) {

		try {
			// Creating a MimeMessage object
			Message message = new MimeMessage(session);

			// Setting sender and recipient email addresses
			message.setFrom(new InternetAddress(senderEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(userEmail));

			// Setting email subject and content
			message.setSubject(subject);
			message.setText(textBody);

			// Sending the email
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Generating the OTP randomly of 6 characters length. 
	 */
	public static StringBuilder generateOtp() {

		// Define the length of the OTP
		int otpLength = 6;

		String numbers = "0123456789";

		// Using StringBuilder to efficiently construct the OTP
		StringBuilder otpvalue = new StringBuilder(otpLength);

		// Creating a Random object
		Random random = new Random();

		for (int i = 0; i < otpLength; i++) {
			// Append a random character from the character set
			otpvalue.append(numbers.charAt(random.nextInt(numbers.length())));
		}
		return otpvalue;
	}

}
