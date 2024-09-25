package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.asis.util.MainClass;

import Driver_manager.DriverManager;

public class ATOSelectingDatePage extends MainClass{

	@FindBy(xpath="//select[@id='dd-atoo-cch-time-period-001']")
	private WebElement dateSelection;
	@FindBy(xpath="//option[contains(text(),'Last 24 hours')]")
	private WebElement last24Hours;
	@FindBy(xpath="//label[contains(text(),'SMS')]")
	private WebElement sms;
	@FindBy(xpath="//button[contains(text(),'Search')]")
	private WebElement search;
	@FindBy(xpath="//select[@id='dd-atoo-cch-results-per-page-001']")
	private WebElement resultPerPage;
	@FindBy(xpath="//option[contains(text(),'100')]")
	private WebElement pages100;
	//option[contains(text(),'100')]
	
	public ATOSelectingDatePage(){
		PageFactory.initElements(DriverManager.getDriver(), this); 
	}
	public void clickDateOption() {
		wait.until(ExpectedConditions.elementToBeClickable(dateSelection));
		dateSelection.click();
	}
	public void selectDate() {
		wait.until(ExpectedConditions.elementToBeClickable(last24Hours));
		last24Hours.click();
	}
	public void clickSMS() {
		wait.until(ExpectedConditions.elementToBeClickable(sms));
		sms.click();
	}
	public void clickSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(search));
		search.click();
		wait.until(ExpectedConditions.elementToBeClickable(resultPerPage));
		resultPerPage.click();
		wait.until(ExpectedConditions.elementToBeClickable(pages100));
		pages100.click();
	}
	public void clickPageSearchButton() {
		wait.until(ExpectedConditions.elementToBeClickable(resultPerPage));
		resultPerPage.click();
	}
}
