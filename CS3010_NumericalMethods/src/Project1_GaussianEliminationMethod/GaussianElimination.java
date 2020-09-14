/*
 * Name: Steven Phung
 * Program: #1
 * Due: October 16th, 2018
 * Course: cs-3010-01-f18
 * 
 * Description:
 * 	A program that takes in values to form an augmented matrix in order to solve linear systems.
 * 	Solved using Gaussian elimination with scaled partial pivoting, Gauss-Jacobi, and Gauss-Seidel.
 */
package Project1_GaussianEliminationMethod;

import java.util.Arrays;
import java.util.Scanner;
import java.io.*;

public class GaussianElimination {

	private static Scanner keyboard = new Scanner(System.in);
	private static int size;
	private static double desiredError;
	private static double[][] augMatrix;

	//Main function that grabs values, sets up matrix, and allows user to interact with the data
	public static void main(String[] args) throws FileNotFoundException {
		size = getAmountOfEquations();
		augMatrix = new double[size][size+1];
		inputValuesIntoArray();
		
		//Method 1: Gaussian Elimination with Scaled Partial Pivoting
		//Method 2: Gauss-Jacobi
		//Method 3: Gauss-Seidel
		System.out.println("Choose a method: \n1. Gaussian Elimination with Scaled Partial Pivoting"
				+ "\n2. Gauss-Jacobi \n3. Gauss-Seidel");
		int choice = keyboard.nextInt();
		switch(choice) {
			case 1:
				gaussElimWithScaledPartialPivoting();
				break;
			case 2:
				gaussJacobi();
				break;
			case 3:
				gaussSeidel();
				break;
			default:
				System.out.println("Invalid input");
				break;
		}
		
	}
	
	//Function to solve matrix using pivoting, prints array after each iteration as well as B-Vector after final iteration.
	private static void gaussElimWithScaledPartialPivoting() {
		for(int p = 0; p < (Math.min(augMatrix.length, augMatrix[0].length)); p++) {
			int max = p;
			for(int i = (p+1); i < augMatrix.length; i++) {
				if(Math.abs(augMatrix[i][p]) > Math.abs(augMatrix[max][p])) {
					max = i;
				}
			}
			
			double[] temp = augMatrix[p];
			augMatrix[p] = augMatrix[max];
			augMatrix[max] = temp;
			
			for(int i = (p+1); i < augMatrix.length; i++) {
				double alpha = augMatrix[i][p] / augMatrix[p][p];
				for(int j = p; j < augMatrix[0].length; j++) {
					augMatrix[i][j] -= alpha * augMatrix[p][j];
				}
				printArray();
			}
		}
		
		double[] solutionVector = new double[augMatrix[0].length];
		
		for(int i = (Math.min(augMatrix[0].length-1, augMatrix.length-1)); i >= 0; i--) {
			double sum = 0.0;
			for(int j = (i+1); j < augMatrix[0].length; j++) {
				sum += augMatrix[i][j] * solutionVector[j];
			}
			solutionVector[i] = (augMatrix[i][augMatrix.length] - sum) / augMatrix[i][i]; 
			if(Double.isNaN(solutionVector[i])) {
				System.out.println("Unable to solve matrix");
				return;
			}
		}
		printSolution(solutionVector);
	}
	
	//Function to solve matrix using Jacobi, prints out every iteration and ends at a max of 50 iterations
	private static void gaussJacobi() {
	    int iterations = 0;
	    
	    System.out.println("Enter the desired error: ");
	    desiredError = keyboard.nextDouble();
	    
	    double[] current = new double[augMatrix.length];
	    double[] previous = new double[augMatrix.length];
	    Arrays.fill(current, 0);
	    Arrays.fill(previous, 0);

	    while (true) {
	    	for (int i = 0; i < augMatrix.length; i++) {
	    		double sum = 0;
	    		for (int j = 0; j < augMatrix.length; j++) {
	    			if (j != i) {
	    				sum += augMatrix[i][j] * previous[j];
	    			}
	    		}
	    		current[i] = 1/augMatrix[i][i] * (augMatrix[i][augMatrix.length] - sum);   
	    	}
	    	
	    	System.out.print("\nX" + (iterations+1) + " = ");
	    	for (int i = 0; i < augMatrix.length; i++) {
	    		System.out.print("\n[");
	    		System.out.print(current[i]);
	    		System.out.print("]");
	    	}

	    	iterations++;
	    	
	    	boolean stopLoop = true;
	    	for (int i = 0; i < augMatrix.length && stopLoop; i++) {
	    		if ((Math.abs(current[i] - previous[i])/current[i]) > desiredError) {
	    			stopLoop = false;
	    		}
	    	}
	    	
	    	if (stopLoop || iterations == 50) {
	    		break;
	    	}
	    	previous = current.clone();
	    }
	}
	
	//Function to solve matrix using Seidel, prints out every iteration and ends at a max of 50 iterations
	private static void gaussSeidel() {
	    int iterations = 0;
	    
	    System.out.println("Enter the desired error: ");
	    desiredError = keyboard.nextDouble();

	    double[] current = new double[augMatrix.length];
	    double[] previous = new double[augMatrix.length];
	    Arrays.fill(current, 0);

	    while (true) {
	    	for (int i = 0; i < augMatrix.length; i++) {
	    		double sum = 0;
	    		for (int j = 0; j < augMatrix.length; j++) {
	    			if (j != i) {
	    				sum += augMatrix[i][j] * current[j];
	    			}
	    		}
	    		current[i] = 1/augMatrix[i][i] * (augMatrix[i][augMatrix.length] - sum);
	    	}
	    	
	    	System.out.print("\nX" + (iterations+1) + " = ");
	    	for (int i = 0; i < augMatrix.length; i++) {
	    		System.out.print("\n[");
	    		System.out.print(current[i]);
	    		System.out.print("]");
	    	}

	    	iterations++;

	    	boolean stopLoop = true;
	    	for (int i = 0; i < augMatrix.length && stopLoop; i++) {
	    		if ((Math.abs(current[i] - previous[i])/current[i]) > desiredError) {
	    			stopLoop = false;
	    		}
	    	}

	    	if (stopLoop || iterations == 50) {
	    		break;
	    	}
	    	previous = current.clone();
	    }
	}

	//Function that gets values from user either through command line or text file
	@SuppressWarnings("resource")
	private static void inputValuesIntoArray() throws FileNotFoundException {
		int option = 0, counter = 0;
		System.out.println("Choose an option: \n1. Enter values using command line\n2. Enter values using file input");
		
		do {
			option = keyboard.nextInt();
			
			if(option == 1) {
				while(augMatrix.length > counter) {
					System.out.println("Enter " + (augMatrix[0].length) + " values for row " + (counter+1) + ":");
					
					Scanner key = new Scanner(System.in);
					String input = key.nextLine();
					String[] stringArray = input.split(" ");
					
					for(int i = 0; i < (augMatrix[0].length); i++) {
						augMatrix[counter][i] = Integer.parseInt(stringArray[i]);
					}
					counter++;
				}
				printArray();
				break;
			} else if (option == 2) {
				String fileName = "";
				System.out.println("Enter name of file (without '.txt')");
				Scanner key = new Scanner(System.in);
				fileName = key.nextLine();
				File inputFile = new File(fileName + ".txt");
				Scanner sc = new Scanner(inputFile);
				
				while(augMatrix.length > counter && sc.hasNextLine()) {
					String input = sc.nextLine();
					String[] stringArray = input.split(" ");
					
					for(int i = 0; i < (augMatrix[0].length); i++) {
						augMatrix[counter][i] = Integer.parseInt(stringArray[i]);
					}
					counter++;
				}
				printArray();
				break;
			} else {
				System.out.println("Invalid option, please try again");
			}
		} while(true);
	}

	//Function that sets up the size of the matrix
	private static int getAmountOfEquations() {
		int number = 0;
		System.out.println("Enter the number of linear equations to solve (n <= 10): ");
		
		while(true) {
			number = keyboard.nextInt();

			if(number > 0 && number <= 10) {
				break;
			} else {
				System.out.println("Invalid number, please try again: ");
			}
		}
		return number;
	}

	//Function that prints out the matrix
	private static void printArray() {
		for(double[] x : augMatrix) {
			for(double xx : x) {
				System.out.print("[");
				System.out.printf("%.3f", xx);
				System.out.print("]" + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	
	//Function that prints out the B-Vector
	private static void printSolution(double[] x) {
		char[] variables = {'x', 'y', 'z', 'w', 'a', 'b', 'c', 'd', 'e', 'f', 'g'};
		System.out.println("Solution:");
		for(int i = 0; i < augMatrix.length; i++) {
			System.out.print(variables[i] + " = ");
			System.out.printf("%.3f", x[i]);
			System.out.println();
		}
		System.out.println();
	}
}
