package luyentm.uet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/*
 * Load data thi file len, la 1 mang cac gia tri double
 * 
 */
public class LoadData1D {

	private String name;
	private ArrayList<Double> mList;

	public LoadData1D(String xname) {

		mList = new ArrayList<Double>();
		name = xname;
		genData();
	}

	public void genData() {
		// doc file va in ra ket qua cho
		try {

			// Create object of FileReader
			FileReader inputFile = new FileReader(name);
			// Instantiate the BufferedReader Class
			BufferedReader bufferReader = new BufferedReader(inputFile);
			// Variable to hold the one line data
			String line;
			// Read file line by line and print on the console
			while ((line = bufferReader.readLine()) != null) {
				// System.out.println(line);
				mList.add(Double.parseDouble(line));
			}
			System.out.println(mList.size());
			// Close the buffer reader
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
	}

	public ArrayList<Double> getData() {
		return mList;
	}
}
