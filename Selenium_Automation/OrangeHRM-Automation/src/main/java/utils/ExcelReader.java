package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	private XSSFSheet sheet;
	private XSSFWorkbook workbook;

	public ExcelReader(String filePath, String sheetName) throws Exception {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				throw new Exception("Excel file not found at: " + filePath);
			}
			FileInputStream inputStream = new FileInputStream(file);
			workbook = new XSSFWorkbook(inputStream);
			sheet = workbook.getSheet(sheetName);
			if (sheet == null) {
				throw new Exception("Sheet '" + sheetName + "' not found in Excel file");
			}
		} catch (Exception e) {
			System.err.println("Error initializing ExcelReader: " + e.getMessage());
			e.printStackTrace();
			throw e;
		}
	}

	public String getStringCellValue(String cellContent) {
		String cellValue = "";
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellType() == CellType.STRING) {
					if (cell.getRichStringCellValue().getString().trim().equals(cellContent)) {
						cellValue = sheet.getRow(row.getRowNum() + 1).getCell(cell.getColumnIndex())
								.getStringCellValue().toString().trim();
					}
				}
			}
		}
		return cellValue;
	}
	
//	public List<String[]> getAllRows() {
//	    List<String[]> rows = new ArrayList<>();
//
//	    for (Row row : sheet) {
//	        if (row.getRowNum() == 0) continue; // skip header row
//
//	        int lastCellNum = row.getLastCellNum();
//	        String[] data = new String[lastCellNum];
//
//	        for (int i = 0; i < lastCellNum; i++) {
//	            Cell cell = row.getCell(i);
//	            data[i] = (cell == null) ? "" : cell.toString().trim();
//	        }
//
//	        rows.add(data);
//	    }
//
//	    return rows;
//	}
	
	public List<String[]> logingetAllRows() {
	    List<String[]> rows = new ArrayList<>();
	    
	    if (sheet == null) {
	    	throw new RuntimeException("Sheet is not initialized. Check your Excel file and sheet name.");
	    }

		String sheetName = sheet.getSheetName();
		System.out.println("Reading data from sheet: " + sheetName);

	    for (Row row : sheet) {
	        if (row.getRowNum() == 0) continue; // skip header row

	        String username = "";
	        String password = "";
	        String status = "";

	        Cell userCell = row.getCell(1); // column index 1 = Username
	        Cell passCell = row.getCell(2); // column index 2 = Password
	        Cell statusCell = row.getCell(5);
	        
	        if (userCell != null) username = userCell.toString().trim();
	        if (passCell != null) password = passCell.toString().trim();
			if (statusCell !=null) status = statusCell.toString().trim();

	        rows.add(new String[]{username, password, status});
	    }

	    return rows;
	}



}
