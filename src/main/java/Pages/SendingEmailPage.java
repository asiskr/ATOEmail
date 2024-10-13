package Pages;

import com.asis.util.MainClass;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class SendingEmailPage extends MainClass {

    public static void saveEmailAsDraft(String subject, String body, String attachmentPath) {

        Properties properties = new Properties();
        properties.put("mail.imap.ssl.enable", "true"); // SSL/TLS for IMAP
        properties.put("mail.imap.auth", "true");
        properties.put("mail.imap.starttls.enable", "true"); // Enable STARTTLS
        properties.put("mail.imap.host", "outlook.office365.com"); // IMAP server
        properties.put("mail.imap.port", "993"); // SSL port for IMAP

        String username = "toptechautomation@theoutsourcepro.com.au";
        String password = "Jenkinssssss";  // Use app password instead of regular password

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        try {
            // Create the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setSubject(subject);

            // Create message body part
            MimeBodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText(body);

            // Create attachment body part
            MimeBodyPart attachmentBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(attachmentPath);
            attachmentBodyPart.setDataHandler(new DataHandler(source));
            attachmentBodyPart.setFileName("AMAN");

            // Combine body and attachment
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            // Connect to IMAP server and save to Drafts
            Store store = session.getStore("imap");
            store.connect("outlook.office365.com", username, password);

            Folder draftsFolder = store.getFolder("Drafts");
            if (!draftsFolder.exists()) {
                System.out.println("Drafts folder not found");
                return;
            }
            draftsFolder.open(Folder.READ_WRITE);

            // Append message to drafts folder
            draftsFolder.appendMessages(new Message[] { message });

            System.out.println("Email saved as draft successfully!");

            draftsFolder.close(false);
            store.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to save email as draft.");
        }
    }

    public static void main(String[] args) {
        String subject = "Your PDF File";
        String body = "Please find the attached PDF file.";
        String attachmentPath = "C:\\Users\\test\\Downloads\\null - ABN - Registered, replaced or reinstated.pdf";

        saveEmailAsDraft(subject, body, attachmentPath);
    }
}
