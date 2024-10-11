package StepDefinition;


import Pages.SaveEmailDraftGraphAPI;
import io.cucumber.java.en.*;
import java.io.File;

import com.asis.util.MainClass;

import static org.junit.Assert.assertTrue;

public class SaveEmailDraftGraphAPISteps extends MainClass{

    SaveEmailDraftGraphAPI email = new SaveEmailDraftGraphAPI();

//    @And("the file contains valid email addresses and file paths")
//    public void theFileContainsValidEmailsAndPaths() {
//        // Assuming the Excel file is valid for simplicity
//        // Further validation would happen in the actual program logic
//        assertTrue("Excel file should contain valid data", new File(filePath).exists());
//    }

    @When("I run the SaveEmailDraftGraphAPI program")
    public void iRunTheSaveEmailDraftGraphAPIProgram() throws Exception {
    	email.saveEmailsAsDraftsFromExcel(filePath, downloadDir);
    }

//    @Then("it should create email drafts with attachments")
//    public void itShouldCreateEmailDraftsWithAttachments() {
//        // Check if the drafts folder is created
//        File draftsDir = new File("drafts");
//        assertTrue("Drafts folder not created", draftsDir.exists() && draftsDir.isDirectory());
//
//        // Ensure there are some draft files created
//        File[] draftFiles = draftsDir.listFiles();
//        assertTrue("No draft files created", draftFiles != null && draftFiles.length > 0);
//    }

//    @And("the drafts should be saved in the {string} folder")
//    public void theDraftsShouldBeSavedInTheFolder(String folderName) {
//        // Verify the drafts folder exists
//        File draftsDir = new File(folderName);
//        assertTrue("Drafts folder not found: " + folderName, draftsDir.exists());
//
//        // Ensure that the folder contains the drafts
//        File[] files = draftsDir.listFiles();
//        assertTrue("No drafts found in the folder", files != null && files.length > 0);
//    }
}
