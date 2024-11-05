package StepDefinition;

import com.asis.util.MainClass;

import Pages.ATOLoginPage;
import io.cucumber.java.en.*;

public class ATOLoginStep extends MainClass {

	private ATOLoginPage loginPage;

	@Given("User launch website")
	public void user_launch_website() throws InterruptedException {
		setupDriver("Chrome");
		loginPage = new ATOLoginPage();

		launchSite("https://onlineservices.ato.gov.au/onlineservices/");
		loginPage.clickOnMyGOVButton();
		loginPage.sendingEmailAddress();
		loginPage.clickOnLoginButton();        
	}

	@Then("send sc")
	public void send_sc() {
	}
}