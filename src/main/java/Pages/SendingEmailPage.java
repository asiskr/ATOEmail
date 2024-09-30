package Pages;

import com.asis.util.MainClass;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendingEmailPage extends MainClass{

	public static void sendEmailWithAttachment(String recipient, String subject, String body, String attachmentPath) {
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", "smtp.office365.com"); 
		properties.put("mail.smtp.port", "587"); 
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true"); 

		String username = "toptechautomation@theoutsourcepro.com.au"; 
		String password = "J7OJb*ZwQD25HpC2KO8*n"; 

		Session session = Session.getInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
			message.setSubject(subject);

			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setText(body);

			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			DataSource source = new FileDataSource(attachmentPath);
			attachmentBodyPart.setDataHandler(new DataHandler(source));
			attachmentBodyPart.setFileName("AMAN"); 

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart); 
			multipart.addBodyPart(attachmentBodyPart); 

			message.setContent(multipart);

			Transport.send(message);

			System.out.println("Email sent successfully with attachment!");

		} catch (MessagingException e) {
			e.printStackTrace();
			System.out.println("Failed to send email.");
		}
	}

	public static void main(String[] args) {
		String recipient = "asis.kaur@theoutsourcepro.com.au";
		String subject = "Your PDF File";
		String body = "Please find the attached PDF file.";
		String attachmentPath = "C:\\Users\\test\\Downloads\\null - ABN - Registered, replaced or reinstated.pdf";

		sendEmailWithAttachment(recipient, subject, body, attachmentPath);
	}
}
