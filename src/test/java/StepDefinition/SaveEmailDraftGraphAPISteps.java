package StepDefinition;


import Pages.XeroSaveEmailDraftAPI;
import io.cucumber.java.en.*;
import com.asis.util.MainClass;


public class SaveEmailDraftGraphAPISteps extends MainClass{

	XeroSaveEmailDraftAPI email = new XeroSaveEmailDraftAPI();

	@Given("I run the SaveEmailDraftGraphAPI program")
    public void iRunTheSaveEmailDraftGraphAPIProgram() throws Exception {
	  	email.renamePdfFilesInDownloads(downloadDir);
    	email.saveEmailsAsDraftsFromExcel(filePath, downloadDir);
//    	email.renamePdfFilesInDownloads(downloadDir);
    }
}