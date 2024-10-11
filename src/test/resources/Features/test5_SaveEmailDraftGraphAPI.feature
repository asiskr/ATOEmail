Feature: Save emails as drafts based on Excel data
  As a user
  I want to save emails as drafts from data in an Excel sheet
  So that I can attach files and save them in the draft folder

  Scenario: Process Excel file and save emails as drafts
    #Given an Excel file ClientData.xls exists
    #And the file contains valid email addresses and file paths
    When I run the SaveEmailDraftGraphAPI program
    #Then it should create email drafts with attachments
    #And the drafts should be saved in the "drafts" folder
