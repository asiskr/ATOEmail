package Pages;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.asis.util.ClientExcel;
import com.asis.util.MainClass;
import Driver_manager.DriverManager;

public class XeroSearchClientPage extends MainClass {
	public static String client ;
	public static String subject ;
	public String emailText = null;
	public String clientCodeText = null;

	@FindBy(xpath = "//button[@title='GlobalSearch']//div[@role='presentation']//*[name()='svg']")
	WebElement searchButton;

	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement inputBox;

	@FindBy(xpath = "//div[@class='form-item']//div[contains(text(), 'Client Code')]/following-sibling::div")
	WebElement clientCode;

	@FindBy(xpath = "//div[@class='panel-item']//span[contains(text(), 'Email')]/following-sibling::span/a")
	WebElement clientEmail;

	@FindBy(xpath = "//span[@class='value u-email']")
	WebElement clientEmail2;

	public XeroSearchClientPage() {
		PageFactory.initElements(DriverManager.getDriver(), this);
	}

	public void clickOnSearchButton() {
		searchButton.click();
	}

	public void inputTheClientName() throws InterruptedException {
		ClientExcel.clientNamesRemoval();
		ClientExcel.readSubjectColumn(filePath);
		//		System.out.println(clientNames.size());

		for (int i = 0; i < Math.min(clientNames.size(), subjectColumnData.size()); i++) {
			client = clientNames.get(i);
			subject = subjectColumnData.get(i);
			Thread.sleep(3000);
			wait.until(ExpectedConditions.elementToBeClickable(searchButton));
			searchButton.click();
			Thread.sleep(2000);
			wait.until(ExpectedConditions.elementToBeClickable(inputBox));
			inputBox.clear();
			inputBox.sendKeys(client);
			Thread.sleep(3000);

			try {
				List<WebElement> elements = DriverManager.getDriver().findElements(By.xpath("//a"));
				boolean clientFound = false;
				for (WebElement ele : elements) {
					if (ele.getText().trim().equalsIgnoreCase(client.trim())) {
						ele.click();
						clientFound = true;
						break; 
					}
				}
				if (clientFound) {
					try {
						wait.until(ExpectedConditions.visibilityOf(clientEmail));
					} catch (Exception e1) {
						try {
							wait.until(ExpectedConditions.visibilityOf(clientEmail2));
						} catch (Exception e2) {
							//							System.out.println("Both clientEmail and clientEmail2 are not visible. " + client);
						}
					}
					wait.until(ExpectedConditions.visibilityOf(clientCode));

					String emailText = null;
					String clientCodeText = null;

					try {
						emailText = clientEmail.getText().trim();
					} catch (Exception e1) {
						try {
							emailText = clientEmail2.getText().trim();
						} catch (Exception e2) {
							//							System.out.println("Failed to retrieve email text from both clientEmail and clientEmail2.");
						}
					}

					if (clientCode.isDisplayed()) {
						clientCodeText = clientCode.getText().trim();
					}
					if (emailText != null && clientCodeText != "-") {
						ClientExcel.addClientData(clientCodeText, emailText);
						ClientExcel.writeCombinedDataToExcel(clientCodeText, subject );
					} 
					else {
						ClientExcel.addClientData("client code not found", "client email not found");
						ClientExcel.writeCombinedDataToExcel(clientCodeText, subject );
						ClientExcel.saveExcelFile();
					}
				} else {

					Thread.sleep(3000);
					ClientExcel.addClientData("client name not found", "client name not found");
					ClientExcel.writeCombinedDataToExcel(clientCodeText, subject );
					ClientExcel.saveExcelFile();
					wait.until(ExpectedConditions.elementToBeClickable(searchButton));
					searchButton.click();
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void renamePdfFilesInDownloads(String downloadDir) {
	    ArrayList<String> pdfFileNames = ClientExcel.readPdfFileNamesFromColumn8(filePath);
	    ArrayList<String> fileNamesColumn7 = ClientExcel.readFileNamesFromColumn7(filePath); // Assuming column 7 is used for renaming

	    // Check if fileNamesColumn7 has enough entries
	    if (pdfFileNames.size() != fileNamesColumn7.size()) {
	        System.out.println("Mismatch between column 8 and column 7 sizes.");
	        return;
	    }

	    int cnt = 0;
	    for (String pdfFileName : pdfFileNames) {
	        String fullPath = downloadDir + File.separator + pdfFileName.trim();
	        File pdfFile = new File(fullPath);

	        if (pdfFile.exists()) {
	            System.out.println("Found: " + pdfFileName);
	            
	            // Get the current file extension
	            String currentExtension = getFileExtension(pdfFile);

	            // Ensure we are not going out of bounds
	            if (cnt < fileNamesColumn7.size()) {
	                // Create a new file name with the correct extension
	                String newFilePath = downloadDir + File.separator + fileNamesColumn7.get(cnt) + "." + currentExtension;
	                File renamedFile = new File(newFilePath);
	                
	                if (pdfFile.renameTo(renamedFile)) {
	                    System.out.println("Renamed " + pdfFileName + " to " + fileNamesColumn7.get(cnt) + "." + currentExtension);
	                } else {
	                    System.out.println("Failed to rename " + pdfFileName);
	                }
	                cnt++;
	            } else {
	                System.out.println("Index out of bounds for fileNamesColumn7.");
	                break;
	            }
	        } else {
	            System.out.println("File not found: " + pdfFileName);
	        }
	    }
	}

	// Helper method to get the file extension
	private String getFileExtension(File file) {
	    String fileName = file.getName();
	    int dotIndex = fileName.lastIndexOf('.');
	    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
	        return fileName.substring(dotIndex + 1);  // Return the file extension without the dot
	    } else {
	        return "";  // No extension found
	    }
	}

	

}