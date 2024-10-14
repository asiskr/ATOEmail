package Pages;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.asis.util.MainClass;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SaveEmailDraftGraphAPI extends MainClass{

    private static final String SMTP_HOST = "smtp.office365.com"; // e.g., smtp.gmail.com
    private static final String SMTP_PORT = "587"; // Common port for TLS
    private static final String USERNAME = "toptechautomation@theoutsourcepro.com.au"; // Your email address
    private static final String PASSWORD = "J7OJb*ZwQD25HpC2KO8*n"; // Your email password

    public void saveEmailsAsDraftsFromExcel(String filePath, String downloadsDir) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File not found: " + filePath);
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = filePath.endsWith(".xls") ? new HSSFWorkbook(fis) : new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0);

            for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                Row row = sheet.getRow(rowIndex);
                if (row != null) {
                    Cell emailCell = row.getCell(6);  // Email column (column 6)
                    Cell fileNameCell = row.getCell(7);  // File name/Subject column (column 7)

                    if (emailCell != null && fileNameCell != null) {
                        String email = emailCell.getStringCellValue().trim();
                        String fileName = fileNameCell.getStringCellValue().trim();

                        String subject = fileName;  
                        if (isValidEmail(email)) {
                            String filePathToSearch = searchFileWithCorrectExtension(downloadsDir, fileName);
                            
                            File fileToAttach = new File(filePathToSearch);
                            if (fileToAttach.exists()) {
                                saveEmailAsDraft(email, subject, filePathToSearch, downloadsDir); // Pass the subject here
                            } else {
                                System.err.println("File not found: " + fileToAttach.getAbsolutePath());
                            }
                        } else {
                            System.err.println("Invalid or missing email address for row " + rowIndex);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException("Error processing the Excel file: " + e.getMessage());
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);  // Return the file extension without the dot
        } else {
            return "";  // No extension found
        }
    }

    private String searchFileWithCorrectExtension(String downloadsDir, String fileName) {
        File folder = new File(downloadsDir);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.getName().startsWith(fileName)) {
                    String fileExtension = getFileExtension(file);
                    return downloadsDir + File.separator + fileName + "." + fileExtension;
                }
            }
        }

        return downloadsDir + File.separator + fileName + ".pdf";
    } 
    
    private static boolean isValidEmail(String email) {
        return email != null && email.contains("@") && !email.contains(" ") && !email.isEmpty();
    }

    private static void saveEmailAsDraft(String email, String subject, String attachmentPath, String downloadsDir) {
        // Set up properties for the mail session
        Properties properties = new Properties();
        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(USERNAME, PASSWORD);
            }
        });

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(USERNAME));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
            message.setSubject(subject);

            Multipart multipart = new MimeMultipart();

            BodyPart bodyPart = new MimeBodyPart();
            bodyPart.setText("Please find the attached document.");
            multipart.addBodyPart(bodyPart);

            if (attachmentPath != null && !attachmentPath.isEmpty()) {
                File attachmentFile = new File(attachmentPath);
                if (attachmentFile.exists()) {
                    MimeBodyPart attachmentPart = new MimeBodyPart();
                    attachmentPart.attachFile(attachmentFile);
                    multipart.addBodyPart(attachmentPart);
                } else {
                }
            }

            message.setContent(multipart);

            File draftsDir = new File(downloadsDir + File.separator + "drafts");
            if (!draftsDir.exists()) {
                draftsDir.mkdirs(); // Create the drafts directory inside the downloads folder
            }

            String draftPath = draftsDir.getAbsolutePath() + File.separator + subject + ".eml"; // Draft file path

            try (FileOutputStream fos = new FileOutputStream(draftPath)) {
                message.writeTo(fos);
            }

            //System.out.println("Draft saved as: " + draftPath);
        } catch (Exception e) {
            //System.err.println("Failed to save email draft for subject '" + subject + "': " + e.getMessage());
            e.printStackTrace(); // Log exception details
        }
    }
}