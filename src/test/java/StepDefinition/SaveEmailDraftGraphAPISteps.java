package StepDefinition;


import Pages.SaveEmailDraftGraphAPI;
import io.cucumber.java.en.*;
import java.io.File;

import com.asis.util.MainClass;

import static org.junit.Assert.assertTrue;

public class SaveEmailDraftGraphAPISteps extends MainClass{

    SaveEmailDraftGraphAPI email = new SaveEmailDraftGraphAPI();

    @When("I run the SaveEmailDraftGraphAPI program")
    public void iRunTheSaveEmailDraftGraphAPIProgram() throws Exception {
    	email.saveEmailsAsDraftsFromExcel(filePath, downloadDir);
    }

}
