package Pages;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExcelExtraction {
	private static final String FILE_PATH = "ClientData.xls"; 
	private static int currentRow = 1; 

	public static void appendValueToRow(String value) {
		try {
			// Check if the file exists, if not, create it
			File csvFile = new File(FILE_PATH);
			if (!csvFile.exists()) {
				csvFile.createNewFile(); 
			}
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
				bw.write(value); // Write the value
				bw.newLine(); // Move to the next line
			}

			System.out.println("Value '" + value + "' added to row " + currentRow + ", column 1");
			currentRow++; // Increment the row for the next call

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
