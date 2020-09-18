/*
 * Name: Steven Phung
 * Program: #3
 * Due: December 4th, 2018
 * Course: cs-3010-01-f18
 * 
 * Description:
 * 	A program that takes input and prints out a divided difference table, a polynomial in Newton's form, and Lagrange's form.
 */
package Project3_DividedDifference;

import java.util.ArrayList;
import java.io.FileInputStream;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class DividedDifference {

	private double[][] table;
	private String[][] values;
	private ArrayList<Double> coefficients;
	private String fileName;

	public DividedDifference(String file) throws IOException {
		fileName = file;
		coefficients = new ArrayList<Double>();
		this.loadInputValues();
	}
	
	//Gets values from file and inputs into double matrix
	@SuppressWarnings("resource")
	public void loadInputValues() throws IOException {
		FileInputStream inputFile = new FileInputStream(fileName);
		DataInputStream dataInput = new DataInputStream(inputFile);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(dataInput));
		
		String[] values1 = buffer.readLine().split("\\s+");
		String[] values2 = buffer.readLine().split("\\s+");
		
		if(values1.length != values2.length) {
			System.out.println("Unable to read data correctly");
			System.exit(0);
		}
		
		table = new double[values1.length][values1.length + 1];
		
		for(int i = 0; i < values1.length; i++) {
			table[i][0] = Double.parseDouble(values1[i]);
		}
		for(int i = 0; i < values2.length; i++) {
			table[i][1] = Double.parseDouble(values2[i]);
		}
	}
	
	//Prints out all necessary information
	public void solve() {
		//Calculates values
		compute();
		//Prints out values
		print();
		//Prints out unsimplified form
		unsimplified();
		//Prints out simplified form
		simplified();
	}
	
	//Calculates values using formula
	public void compute() {
		for(int i = 2; i < table[0].length; i++) {
			for(int j = 0; j < table[0].length - i; j++) {
				table[j][i] = (table[j + 1][i - 1] - table[j][i - 1])/ (table[j + (i - 1)][0] - table[j][0]);
			}
		}
		for(int i = 1; i < table[0].length; i++) {
			coefficients.add(table[0][i]);
		}
	}
	
	//Prints out table
	public void print() {
		System.out.println("Divided Differences Table: ");
		format();
		for(String[] s : values) {
			for(String ss : s) {
				System.out.print(ss + " ");
			}
			System.out.println();
		}
	}
	
	//Values are formatted neatly
	public void format() {
		int num1 = 2 * table.length;
		int num2 = table[0].length;
		values = new String[num1][num2];
		for(int i = 0; i < values.length; i++) {
			for(int j = 0; j < values[i].length; j++) {
				values[i][j] = String.format("%6s", " ");
			}
		}
		int num = 0;
		for(int i = 0; i < table.length; i++) {
			values[num][0] = String.format("%6.2f", table[i][0]);
			num += 2;
		}
		num = 0;
		for(int i = 0; i < table.length; i++) {
			values[num][1] = String.format("%6.2f", table[i][1]);
			num += 2;
		}
		for(int i = 2; i < num2; i++) {
			num = (i - 1);
			for(int j = 0; j < num2 - i; j++) {
			values[num][i] = String.format("%6.2f", table[j][i]);
			num += 2;
			}
		}
	}
	
	//Prints out polynomial in unsimplified form
	public void unsimplified() {
		System.out.println("Interpolating Polynomial: ");
		ArrayList<String> list = new ArrayList<String>();
		String polynomial = "";
		for(int i = 0; i < table.length - 1; i++) {
			double currentValue = table[i][0];
			if(currentValue < 0) {
				polynomial = "+";
			} else if(currentValue > 0) {
				polynomial = "-";
			}
			if(round(currentValue) == 0) {
				list.add("(x)");
			} else {
				list.add(String.format("(x%s%.2f)", polynomial, currentValue));
			}
		}
		String fullPoly = String.format("%.2f", coefficients.get(0));
		for(int i = 1; i < list.size() + 1; i++) {
			double currentValue = coefficients.get(i);
			if(currentValue != 0) {
				if(currentValue > 0) {
					polynomial = "+";
				} else {
					polynomial = "-";
				}
				String s = "";
				for(int j = 0; j < i; j++) {
					s += list.get(j);
				}
				fullPoly += String.format(" %s %.2f%s", polynomial, Math.abs(currentValue), s);
			}
		}
		System.out.println(fullPoly);
	}
	
	public double round(double num) {
		return (double) Math.round(num * 1000) / 1000;
	}
	
	//Prints out polynomial in simplified form
	public void simplified() {
		System.out.println("\nSimplified Polynomial: ");
		Polynomial poly = new Polynomial();
		ArrayList<Double> list = new ArrayList<Double>();
		ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
		for(int i = 0; i < table[0].length - 1; i++) {
			list.add(0.0);
		}
		list.add(0, coefficients.get(0));
		matrix.add(list);
		for(int i = 1; i < coefficients.size(); i++) {
			list = new ArrayList<Double>();
			double num = coefficients.get(i);
			for(int j = 0; j < i; j++) {
				list.add(table[j][0]);
			}
			matrix.add(poly.expand(num, list, table[0].length));
		}
		list = poly.simplify(matrix);
		System.out.println(toString(list));
	}
	
	//Converts list to print-able string
	private String toString(ArrayList<Double> list) {
		String polynomial = "";
		String power = "";
		for(int i = 0; i < list.size(); i++) {
			Double num = list.get(i);
			power = String.format("x^%d", i);
			if(num != 0) {
				if(num == 0) {
					polynomial += String.format(" %+.2f", num);
				} else {
					polynomial += String.format(" %+.2f%s", num, power);
				}
			}
		}
		return polynomial;
	}
}

//Class with functions specifically for polynomials
class Polynomial {
	public ArrayList<Double> expand(double num, ArrayList<Double> list, int size) {
		ArrayList<ArrayList<Double>> matrix = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> returnList = new ArrayList<Double>();
		for(int i = 0; i < list.size() + 1; i++) {
			returnList.add(0.0);
		}
		returnList.add(0, num);
		for(int i = 0; i < list.size(); i++) {
			matrix.add(push(returnList));
			matrix.add(multiply(returnList, -list.get(i)));
			returnList = simplify(matrix);
			matrix.clear();
		}
		int num1 = returnList.size();
		for(int i = 0; i < size - num1; i++) {
			returnList.add(0.0);
		}
		return returnList;
	}
	
	private ArrayList<Double> push(ArrayList<Double> list) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		returnList.add(0.0);
		for(int i = 0; i < list.size() - 1; i++) {
			returnList.add(list.get(i));
		}
		return returnList;
	}
	
	private ArrayList<Double> multiply(ArrayList<Double> list, double num) {
		ArrayList<Double> returnList = new ArrayList<Double>();
		for(double d : list) {
			returnList.add(d * num);
		}
		return returnList;
	}
	
	public ArrayList<Double> simplify(ArrayList<ArrayList<Double>> list) {
		ArrayList<Double> simplifiedList = new ArrayList<Double>();
		for(int i = 0; i < list.get(0).size(); i++) {
			double sum = 0.0;
			for(int j = 0; j < list.size(); j++) {
				sum += list.get(j).get(i);
			}
			simplifiedList.add(sum);
		}
		return simplifiedList;
	}
}
