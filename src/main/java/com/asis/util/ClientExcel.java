
package com.asis.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

public class ClientExcel extends MainClass{

	private static Workbook workbook;
	private static Sheet sheet;
	private static int currentRowNum = 1;
	private static int currentRowNum2 = 1;
	private static int currentRowNum3 = 1;

	/*====================Creation of Empty Excel Sheet===================================*/

	public void createEmptyExcelSheet() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Client Data");

		String[] headers = { "Name", "Client ID", "Subject", "Channel", "Issue Date", "Client Code", "Client Email ID", "File Name", "PDF File" };

		Row headerRow = sheet.createRow(0); 
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);  
			cell.setCellValue(headers[i]); 
		}
	}

	/*====================Table Extraction and Putting data into Excel===================================*/

	public static ArrayList<ArrayList<String>> writeDataToExcel(ArrayList<ArrayList<String>> data) {
		if (sheet == null) {
			return data;
		}
		int rowNum = 1;
		for (ArrayList<String> rowData : data) {
			Row row = sheet.createRow(rowNum++);
			int colNum = 0;
			for (String cellData : rowData) {
				Cell cell = row.createCell(colNum++);
				cell.setCellValue(cellData);
			}
		}
		return data;
	}
	/*====================Read Of First Column===================================*/

	public static ArrayList<String> readFirstColumn(String filePath) {
		ArrayList<String> firstColumnData = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				Cell cell = row.getCell(0); 
				if (cell != null && cell.getCellType() == CellType.STRING) {
					firstColumnData.add(cell.getStringCellValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return firstColumnData;
	} 

	/*====================Removal of Last character of client name ===================================*/

	public static void clientNamesRemoval() {
		String filePath = "ClientData.xls"; 
		ArrayList<String> firstColumn = readFirstColumn(filePath); 

		for (int cnt = 0; cnt < firstColumn.size(); cnt++) {
			String clientName = firstColumn.get(cnt).trim(); 

			if (cnt > 0) {
				int length = clientName.length();
				if (length > 2 && clientName.charAt(length - 2) == ' ' 
						&& Character.isLetter(clientName.charAt(length - 1))) {
					clientName = clientName.substring(0, length - 2);
				}
				clientName = formatCommaSeparatedName(clientName);
				clientName = capitalizeName(clientName);
				//	            System.out.println(clientName);
				clientNames.add(clientName); 
			}
		}
	}

	/*====================Formating of the client name data===================================*/

	private static String formatCommaSeparatedName(String name) {
		if (name.contains(",")) {
			name = name.replaceAll(",(\\S)", ", $1");
		}
		return name;
	}

	/*====================Changing the client name into capital form===================================*/

	private static String capitalizeName(String name) {
		if (name == null || name.isEmpty()) {
			return name; 
		}
		String[] words = name.split(" ");
		StringBuilder capitalized = new StringBuilder();
		for (String word : words) {
			if (!word.isEmpty()) {
				String capitalizedWord = word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
				capitalized.append(capitalizedWord).append(" ");
			}
		}
		return capitalized.toString().trim();
	}

	/*====================Adding the client code and email into excel sheet===================================*/

	public static void addClientData(String clientCode, String clientEmail) {
		if (sheet != null) {
			Row row = sheet.getRow(currentRowNum);
			if (row == null) {
				row = sheet.createRow(currentRowNum);
			}

			Cell codeCell = row.createCell(5); 
			Cell emailCell = row.createCell(6); 
			codeCell.setCellValue(clientCode); 
			emailCell.setCellValue(clientEmail); 

			currentRowNum++;
		}
	}
	/*====================Saving the Excel===================================*/

	public static void saveExcelFile() {
		String fileName = "ClientData.xls";
		File file = new File(fileName);

		try (FileOutputStream fileOut = new FileOutputStream(file)) {
			workbook.write(fileOut);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/*====================Writing the Combined data of client name and client code into column of excel===================================*/

	public static void writeCombinedDataToExcel(String clientName, String clientCode) {
		if (sheet != null) {
			Row row = sheet.getRow(currentRowNum2); 
			if (row == null) {
				row = sheet.createRow(currentRowNum2);
			}

			String combinedData = clientName + "_" + clientCode;

			Cell combinedCell = row.createCell(7);
			combinedCell.setCellValue(combinedData);
			saveExcelFile();
			currentRowNum2++;
		}
	}

	/*====================Adding the PDF name into excel sheet===================================*/

	public static void addPdfName(String name) {
		if (sheet != null) {
			Row row = sheet.getRow(currentRowNum3);
			if (row == null) {
				row = sheet.createRow(currentRowNum3);
			}


			Cell codeCell = row.createCell(8); 
			codeCell.setCellValue(name); 
			saveExcelFile();
			currentRowNum3++;
		}
	}

	/*====================Read Of Subject Column===================================*/

	public static ArrayList<String> readSubjectColumn(String filePath) {
		ArrayList<String> subjectColumnData = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				Cell cell = row.getCell(2); 
				if (cell != null && cell.getCellType() == CellType.STRING) {
					subjectColumnData.add(cell.getStringCellValue());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		subjectColumnData.remove(0);
				System.out.println(subjectColumnData);
		return subjectColumnData;
	} 
	
	/*====================Read PDF File Names from Column 7===================================*/
	
	public static ArrayList<String> readFileNamesFromColumn7(String filePath) {
	    ArrayList<String> fileNamesColumn7 = new ArrayList<>();

	    try (FileInputStream fis = new FileInputStream(new File(filePath));
	         Workbook workbook = WorkbookFactory.create(fis)) {

	        Sheet sheet = workbook.getSheetAt(0);
	        for (Row row : sheet) {
	            Cell cell = row.getCell(7);
	            if (cell != null && cell.getCellType() == CellType.STRING) {
	                fileNamesColumn7.add(cell.getStringCellValue());
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    fileNamesColumn7.remove(0);
//	    System.out.println(fileNamesColumn7);
	    return fileNamesColumn7;
	}

	/*====================Read PDF File Names from Column 8===================================*/

	public static ArrayList<String> readPdfFileNamesFromColumn8(String filePath) {
		ArrayList<String> pdfFileNames = new ArrayList<>();

		try (FileInputStream fis = new FileInputStream(new File(filePath));
				Workbook workbook = WorkbookFactory.create(fis)) {

			Sheet sheet = workbook.getSheetAt(0);
			for (Row row : sheet) {
				Cell cell = row.getCell(8); 
				if (cell != null && cell.getCellType() == CellType.STRING) {
					pdfFileNames.add(cell.getStringCellValue());
				}
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		pdfFileNames.remove(0);
//		System.out.println(pdfFileNames);
		return pdfFileNames;
	}

	/*====================Main Method===================================*/

	public static void main(String[] args) {
//		readSubjectColumn(filePath);
		readFileNamesFromColumn7(filePath);
	}
}
