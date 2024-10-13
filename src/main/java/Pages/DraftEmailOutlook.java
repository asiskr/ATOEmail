package Pages;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class DraftEmailOutlook {

    public static void main(String[] args) {
        // Email account credentials
        String host = "outlook.office365.com";  // IMAP server for Outlook.com
        String username = "toptechautomation@theoutsourcepro.com.au";  // Your Outlook email
        String password = "Jenkinssssss";  // App password (if 2FA enabled)

        // Draft email details
        String toEmail = "asis.kaur@theoutsourcepro.com.au";  // Replace with recipient's email
        String subject = "Farewell Message";  // Replace with your subject
        String body = "Dear [Recipient],\n\nThis is a farewell message. Best of luck!\n\nBest regards,\n[Your Name]";  // Email body

        // Call the function to draft the email
        try {
            draftEmail(host, username, password, toEmail, subject, body);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void draftEmail(String host, String username, String password, 
                                  String toEmail, String subject, String body) throws Exception {
        // IMAP and SMTP server properties
        Properties properties = new Properties();
        
        // SMTP server settings for sending email
        properties.put("mail.smtp.host", "smtp-mail.outlook.com");  // SMTP server for sending emails
        properties.put("mail.smtp.port", "587");  // SMTP port
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");  // Enable STARTTLS encryption

        // IMAP server settings for accessing the Draft folder
        properties.put("mail.imap.ssl.enable", "true");  // Enable SSL
        properties.put("mail.imap.port", "993");  // IMAP port for SSL connection
        
        // Authenticate the session with username and password
        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);  // Use App Password if 2FA enabled
            }
        });

        // Create the email message
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);
        message.setText(body);

        // Connect to the IMAP server to access Drafts folder
        Store store = session.getStore("imap");  // Specify IMAP protocol
        store.connect(host, username, password);  // Connect to Outlook's IMAP server

        // Access the Drafts folder
        Folder draftsFolder = store.getFolder("Drafts");  // Ensure the folder name is correct
        if (!draftsFolder.exists()) {
            System.out.println("Drafts folder does not exist");
            return;
        }
        draftsFolder.open(Folder.READ_WRITE);

        // Append the message to the Drafts folder
        draftsFolder.appendMessages(new Message[]{message});
        System.out.println("Email saved to Drafts.");

        // Close connections
        draftsFolder.close(false);
        store.close();
    }
}
