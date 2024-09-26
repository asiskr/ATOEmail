package Pages;

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
		System.out.println(clientNames.size());

		for (int i = 0; i < clientNames.size(); i++) {
			String client = clientNames.get(i);
			Thread.sleep(3000);
			wait.until(ExpectedConditions.elementToBeClickable(searchButton));
			searchButton.click();
			Thread.sleep(1000);
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
							System.out.println("Both clientEmail and clientEmail2 are not visible. " + client);
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
							System.out.println("Failed to retrieve email text from both clientEmail and clientEmail2.");
						}
					}

					if (clientCode.isDisplayed()) {
						clientCodeText = clientCode.getText().trim();
					}
					if (emailText != null && clientCodeText != "-") {
						System.out.println("Extracted Email: " + emailText);
						System.out.println("Extracted Client Code: " + clientCodeText);


						ClientExcel.addClientData(clientCodeText, emailText);
						ClientExcel.saveExcelFile();
					} else {

						System.out.println("Client Code or Email not found for client: " + client);
						ClientExcel.addClientData("client code not found", "client email not found");
						ClientExcel.saveExcelFile();
					}
				} else {
					System.out.println("Client name not found: " + client);
					Thread.sleep(3000);
					ClientExcel.addClientData("client name not found", "client name not found");
					ClientExcel.saveExcelFile();
					wait.until(ExpectedConditions.elementToBeClickable(searchButton));
					searchButton.click();
				}

			} catch (Exception e) {
				System.out.println("Error occurred while searching for client: " + client);
				e.printStackTrace();
			}
		}
		System.out.println(clientEmails);
		System.out.println(clientCodes);
	}
}
