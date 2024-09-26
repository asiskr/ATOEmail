

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

	public void createEmptyExcelSheet() {
		workbook = new HSSFWorkbook();
		sheet = workbook.createSheet("Client Data");

		String[] headers = { "Name", "Client ID", "Subject", "Channel", "Issue Date", "Client Code", "Client Email ID" };

		Row headerRow = sheet.createRow(0); 
		for (int i = 0; i < headers.length; i++) {
			Cell cell = headerRow.createCell(i);  
			cell.setCellValue(headers[i]); 
		}
	}
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

				// Format the name if it contains a comma
				clientName = formatCommaSeparatedName(clientName);
				clientName = capitalizeName(clientName);
				//	            System.out.println(clientName);
				clientNames.add(clientName); 
			}
		}
	}

	private static String formatCommaSeparatedName(String name) {
		if (name.contains(",")) {
			// Replace ",<no space>" with ", "
			name = name.replaceAll(",(\\S)", ", $1");
		}
		return name;
	}

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

	public static void addClientData(String clientCode, String clientEmail) {
		if (sheet != null) {
			Row row = sheet.getRow(currentRowNum); // Get existing row or create a new one
			if (row == null) {
				row = sheet.createRow(currentRowNum);
			}

			Cell codeCell = row.createCell(5); // Column index for "Client Code" is 5
			Cell emailCell = row.createCell(6); // Column index for "Client Email ID" is 6
			codeCell.setCellValue(clientCode); // Set the value in the "Client Code" column
			emailCell.setCellValue(clientEmail); // Set the value in the "Client Email ID" column

			currentRowNum++; // Increment row number for the next entry
		}
	}

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
}
/*
	private static int currentRowNum = 1; // Start from row 1 since row 0 is for headers

	public static void addClientCode(String clientCode) {
		if (sheet != null) {
			Row row1 = sheet.createRow(currentRowNum++); // Create a new row and increment row number
			Cell cell2 = row1.createCell(5); // Column index for "Client Code" is 5
			cell2.setCellValue(clientCode); // Set the value in the "Client Code" column
		}
	}
	private static int currentRowNum2 = 1; // Start from row 1 since row 0 is for headers

	public static void addClientEmail(String clientEmail) {
		if (sheet != null) {
			Row row1 = sheet.createRow(currentRowNum2++); // Create a new row and increment row number
			Cell cell2 = row1.createCell(6); // Column index for "Client Code" is 5
			cell2.setCellValue(clientEmail); // Set the value in the "Client Code" column
		}
	}
}/*
	public static void main(String[] args) {
		ClientExcel clientExcel = new ClientExcel();
		clientExcel.createEmptyExcelSheet();

		// Add client codes
		clientExcel.addClientCode("ClientCode1");
		clientExcel.addClientCode("ClientCode2");
		clientExcel.addClientCode("ClientCode3");

		// Save the Excel file
		clientExcel.saveExcelFile();
	}


}
/*
	public static void main(String args[]) {
		ClientExcel clientExcel = new ClientExcel();
		clientExcel.clientNamesRemoval();
	}
}

	public void clientNamesRemoval() {
		String filePath = "ClientData.xls"; 
		ArrayList<String> firstColumn = readFirstColumn(filePath);
		ArrayList<String> clientNames = new ArrayList<>();

		System.out.println("All Client Names:");

		for (String clientName : firstColumn) {
			if (clientName.matches(".* [A-Z]$")) {
				clientName = clientName.substring(0, clientName.length() - 2); 
			}
			clientNames.add(clientName.trim()); 
			System.out.println(clientName); 
		}
	}
 */
