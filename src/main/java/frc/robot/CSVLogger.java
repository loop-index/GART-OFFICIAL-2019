package frc.robot;

import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class CSVLogger {

// 	public static void main(String[] args) {
// 		// TODO Auto-generated method stub
// //		save("test.csv", new double[] {35.4, 65.2, 54});
// //		save("test.csv", new double[] {354.4, 652, 554});
// //		save("test.csv", new double[] {35.43, 65.32, 5.4});
// //		save("test.csv", new double[] {35.4, 65.2, 54});
// //		save("test.csv", new double[] {354.4, 652, 554});
// //		save("test.csv", new double[] {35.43, 65.32, 5.4});
// //		System.out.println(load("test.csv"));
// 		double[][] test = load("E:\\workspace\\csv data\\trapTestPID12.csv");
// 		for (int j = 0; j < test.length; j++) {
// 			for (int i = 0; i < test[0].length; i++) {
// 				System.out.print(test[j][i]);
// 				System.out.print("\t");
// 			}
// 			System.out.println();
// 		}
// 	}

	public static void delete(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			if(file.delete()) {
				System.out.println("File has been deleted successful!");
			} else {
				System.out.println("Can't delete file!");
			}
		} else {
			System.out.println("File doesn't exist, please check your path again!");
		}
	}
	
	public static void create(String fileName) {
		File file = new File(fileName);
		if (file.exists()) {
			System.out.println("File's already existed! Please check your path again!");
		} else {
			try {
				if (file.createNewFile()) {
					System.out.println("File has been created successful!");
				} else {
					System.out.println("Can't create file!");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("****WARNING: Error!");
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 
	 * @param fileName file name
	 * @param data[] (double array)
	 */
	public static void save(String fileName, double[] data) {
		File file;
		FileWriter fr = null;
		BufferedWriter br = null;
		
		try {
			file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			fr = new FileWriter(file, true);
			br = new BufferedWriter(fr);
			for (double temp : data) {
				br.append(Double.toString(temp));
				br.append(';');
			}
			br.newLine();
		} catch (IOException e) {
			System.out.println("Can't write data to file csv!");
			e.printStackTrace();
		} finally {
			try {
				br.flush();
				br.close();
				fr.close();
				System.out.println("Done!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static double[][] load(String fileName) {
    	// Initialize
    	BufferedReader br = null;
    	String line;
    	ArrayList<String> rows = new ArrayList<String>();
    	
    	try {
    		br = new BufferedReader(new FileReader(fileName));
    		while ((line = br.readLine()) != null) {
    			// read file
    			if (!line.equals("")) {
					rows.add(line);	// get data
    			}
    		}
    		if (rows.size() > 0) {
    			String[] temp = rows.get(0).split(";");
    			double[][] results = new double[rows.size()][temp.length];
    			for (int i = 0; i < rows.size(); i++) {
    				temp = rows.get(i).split(";");
    				for (int j = 0; j < temp.length; j ++) {
    					results[i][j] = Double.parseDouble(temp[j]);
    				}
    			}
    			return results;
    		} else {
    			System.out.println("There is no data in this file, please check again!");
    			return null;
    		}
    	} catch (FileNotFoundException ef) {
    		System.out.println("File doesn't exist, please check your path again!");
    		return null;
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
			System.out.println("****WARNING: Error!");
			e.printStackTrace();
			return null;
    	} finally {
    		if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}
    	}
    }
}
