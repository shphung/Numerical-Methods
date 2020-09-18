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

import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws IOException {
		//Grab file name
		String fileName = "";
		//File used: test.txt
		System.out.println("Please enter the name of the file (without '.txt')");
		@SuppressWarnings("resource")
		Scanner key = new Scanner(System.in);
		fileName = key.nextLine();
		DividedDifference dd1 = new DividedDifference(fileName + ".txt");
		dd1.solve();
	}
}
