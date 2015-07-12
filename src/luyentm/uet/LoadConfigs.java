package luyentm.uet;

import java.io.BufferedReader;
import java.io.FileReader;

public class LoadConfigs {
	private String name;
	// file nay se tra ve danh sach cac file du lieu
	public LoadConfigs(String xname) {
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
			// Read file line by line and print on the console
			int n = Integer.parseInt(bufferReader.readLine());
			int m = Integer.parseInt(bufferReader.readLine());
			int nn = Integer.parseInt(bufferReader.readLine());
			int ns = Integer.parseInt(bufferReader.readLine());
			int sk = Integer.parseInt(bufferReader.readLine());
			CF.setConfigs(n, m, nn, ns, sk);
			bufferReader.close();
		} catch (Exception e) {
			System.out.println("Error while reading file line by line:"
					+ e.getMessage());
		}
	}
}
