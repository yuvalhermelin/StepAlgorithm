package com.yuvalhermelin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/***
 * A class to read/write numerical CSV files and allow easy access
 * 
 * @author yuvalhermelin
 *
 */
public class CSVData {
	private double[][] data;
	private String[] columnNames;

	public static void main(String[] args) {
		CSVData data = CSVData.readCSVFile("/Users/yuvalhermelin/Downloads/GyroTest2out.csv", 0);
		for (int i = 0; i < data.data.length; i++) {
			System.out.println(Arrays.toString(data.getRow(i)));
		}
	}
	
	public double[][] getAllData()
	{
		return data;
	}
	public int numberOfRows()
	{
		return data.length;
	}
	
	public int numberOfColumns()
	{
		return data[0].length;
	}

	public CSVData(int x, int y) {
		data = new double[x][y];
	}

	/***
	 * Read a file given the fileName. Ignore the first "numlinesToIgnore" lines
	 * The first line should be made columnNames, because we are assuming the
	 * actual file did Not provide the column names.
	 * 
	 * @param fileName
	 *            The name of the file to read
	 * @param numLinesToIgnore
	 *            The number of lines in the beginning to ignore
	 * @param columnNames
	 *            The names of all the columns
	 * @return A new CSVData object with all the data read from the file
	 */
	public static CSVData readCSVFile(String fileName, int numLinesToIgnore, String[] columnNames) {
		ArrayList<String> file = readFileAsString(fileName);
		CSVData toReturn = new CSVData(file.size(), file.get(0).length());
		toReturn.columnNames = columnNames;

		for (int i = 0; i < file.size(); i++) {
			String[] row = file.get(i).split(",");
			double[] rowConverted = new double[row.length];
			for (int x = 0; x < row.length; x++)
				rowConverted[x] = Double.valueOf(row[x]);
			toReturn.setRow(i, rowConverted);
		}
		return toReturn;
	}

	/***
	 * Read a file given the fileName. Ignore the first "numLinesToIgnore"
	 * lines.
	 * 
	 * @param fileName
	 *            The name of the file to read
	 * @param numLinesToIgnore
	 *            The number of lines in the beginning to ignore
	 * @return A new CSVData object with all the data read from the file
	 */
	public static CSVData readCSVFile(String fileName, int numLinesToIgnore) {
		ArrayList<String> file = readFileAsString(fileName);
		CSVData toReturn = new CSVData(file.size(), file.get(0).length());
		toReturn.columnNames = file.get(0).split(",");

		file.remove(0);
		for (int i = 0; i < file.size(); i++) {
			String[] row = file.get(i).split(",");
			
			double[] rowConverted = new double[row.length];
			for (int x = 0; x < row.length; x++)
				rowConverted[x] = Double.valueOf(row[x]);
			toReturn.setRow(i, rowConverted);
		}
		return toReturn;
	}

	public static ArrayList<String> readFileAsString(String filepath) {
		ArrayList<String> output = new ArrayList<>();
		try (Scanner scanner = new Scanner(new File(filepath))) {
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				output.add(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return output;
	}

	/***
	 * Get the full row at the given index
	 * 
	 * @param rowIndex
	 *            The index of the row to get
	 * @return The row at the index given.
	 */
	public double[] getRow(int rowIndex) {
		return data[rowIndex];
	}

	/***
	 * Get the column at the given index
	 * 
	 * @param columIndex
	 *            The index of the column to get
	 * @return The colum at columnIndex
	 */
	public double[] getColumn(int columnIndex) {
		double[] column = new double[data.length];
		for (int i = 0; i < data.length; i++)
			column[i] = data[i][columnIndex];
		return column;
	}

	/***
	 * Get all the rows at the given indexes.
	 * 
	 * @param rowIndexes
	 *            All the indexes of the rows to get
	 * @return The rows at all the given indexes.
	 */
	public double[][] getRows(int[] rowIndexes) {
		double[][] rowsToReturn = new double[rowIndexes.length][data[0].length];
		for (int i = 0; i < rowIndexes.length; i++)
			rowsToReturn[i] = getRow(rowIndexes[i]);

		return rowsToReturn;
	}

	/***
	 * Get all the columns at the given indexes.
	 * 
	 * @param ColumnIndexes
	 *            All of the indexes of the columns to get.
	 * @return All of the columns at the given indexes.
	 */
	public double[][] getColumns(int[] columnIndexes) {
		double[][] columnsToReturn = new double[columnIndexes.length][data.length];
		for (int i = 0; i < columnIndexes.length; i++)
			columnsToReturn[i] = getColumn(columnIndexes[i]);
		return columnsToReturn;
	}

	/***
	 * Sets the value given at the location given.
	 * 
	 * @param row
	 *            X coordinate
	 * @param column
	 *            Y coordinate
	 * @param value
	 *            The value to set at (x,y)
	 */
	public void setIndividualValue(int row, int column, double value) {
		data[row][column] = value;

	}

	/***
	 * Sets the row at the given index (rowIndex) with the new row (rowValues)
	 * 
	 * @param rowIndex
	 *            The index of the row to override
	 * @param rowValues
	 *            The new row value to set the row at "rowIndex" to.
	 */
	public void setRow(int rowIndex, double[] rowValues) {
		data[rowIndex] = rowValues;
	}

	/***
	 * Sets the column at the given index with the new column values.
	 * 
	 * @param columnIndex
	 *            The index of the column to set.
	 * @param columnValues
	 *            The values to override the current column with.
	 */
	public void setColumn(int columnIndex, double[] columnValues) {
		for (int i = 0; i < data.length; i++)
			data[i][columnIndex] = columnValues[i];
	}

	/***
	 * Gets all of the titles of all of the columns.
	 * 
	 * @return A string array containing the titles of all the columns.
	 */
	public String[] getTitles() {
		return columnNames;
	}

	/***
	 * Saves the current state of the CSVData object.
	 */
	public void saveCurrentState() {

	}

}

