/*
 * Name: Steven Phung
 * Program: #2
 * Due: November 13th, 2018
 * Course: cs-3010-01-f18
 * 
 * Description:
 * 	A program that implements five methods: bisection, false position, Newton-Raphson, secant, and modified secant.
 * 	Each method is used to evaluate the roots of two given functions.
 * 		1. f(x) = 2x^3 - 11.7x^2 + 17.7x - 5
 * 		2. f(x) = x + 10 - xcosh(50/x)
 */
package Project2_LocatingRoots;

public class LocatingRoots {

	//Number of iterations allowed
	static final int MAX_NUMBER_OF_ITERATIONS = 100;
	//given delta value
	static double delta = 0.01;
	//given error value
	static double error = 0.01;
	//Counter to keep track of iterations
	static int counter = 0;
	//final value obtained is c, previousC keeps track of different iteration's values
	static double c = 0.0, previousC = 0.0;
	
	public static void main(String[] args) {
		//Function 1
		bisectionMethod(0.0, 1.0, 0.356316, 1);
		bisectionMethod(1.0, 2.0, 1.92174, 1);
		bisectionMethod(2.0, 3.0, 3.56316, 1);
		bisectionMethod(3.0, 4.0, 3.56316, 1);
		falsePositionMethod(0.0, 1.0, 0.356316, 1);
		falsePositionMethod(1.0, 2.0, 1.92174, 1);
		falsePositionMethod(2.0, 3.0, 3.56316, 1);
		falsePositionMethod(3.0, 4.0, 3.56316, 1);
		newtonRaphsonMethod(0.0, 0.365098, 1);
		newtonRaphsonMethod(2.0, 1.92174, 1);
		newtonRaphsonMethod(3.0, 0.356316, 1);
		secantMethod(0.0, 1.0, 0.356316, 1);
		secantMethod(2.0, 3.0, 1.92174, 1);
		secantMethod(3.0, 4.0, 3.56316, 1);
		modifiedSecantMethod(0.0, 0.356316, 1);
		modifiedSecantMethod(1.0, 0.356316, 1);
		modifiedSecantMethod(2.0, 1.92174, 1);
		modifiedSecantMethod(3.0, 3.56316, 1);
		//Function 2
		bisectionMethod(120.0, 130.0, 126.75, 2);
		falsePositionMethod(120.0, 130.0, 126.75, 2);
		newtonRaphsonMethod(120.5, 126.75, 2);
		secantMethod(120.0, 130.0, 126.75, 2);
		modifiedSecantMethod(120.0, 126.75, 2);
	}
	
	//Function 1
	public static double function1(double x) {
		return ((2.0 * (x*x*x)) - (11.7 * (x*x)) + (17.7 * (x)) - 5.0);
	}
	
	//Function 1's derivative
	public static double function1Derivative(double x) {
		return ((6.0 * (x*x)) - (23.4 * (x)) + 17.7);
	}
	
	//Function 2
	public static double function2(double x) {
		return x + 10.0 - (x * (Math.cosh(50.0 / x)));
	}
	
	//Function 2's derivative
	public static double function2Derivative(double x) {
		return ((50.0 * (Math.sinh(50.0 / x))) / x) - Math.cosh(50.0 / x) + 1;
	}
	
	//Function that takes in current value and previous value and calculates approximate error
	public static double getApproximateError(double currentValue, double previousValue) {
		double approximateError;
		approximateError = (Math.abs(currentValue - previousValue) / currentValue);
		return approximateError;
	}
	
	//Function that takes in current value and true root and calculates true error
	public static double getTrueError(double currentValue, double actualRoot) {
		double trueError;
		trueError = (Math.abs(actualRoot - currentValue) / actualRoot);
		return trueError;
	}
	
	//Bisection method, prints iteration number and errors for every iteration
	public static void bisectionMethod(double a, double b, double root, int functionNumber) {
		//Calculates bisection method for function 1
		if (functionNumber == 1) {
			System.out.print("Finding root for function 1 using BISECTION method at [" + a + ", " + b + "]");
			//Quits method if unable to find root between points a and b
			if (function1(a) * function1(b) >= 0) {
				System.out.println("\nUnable to find roots with the current a and b input.\n");
				return;
			}
			//Gives a value to c
			c = a;
			do {
				//Sets previous iteration of C before calculating for new value of C
				previousC = c;
				c = (a + b) / 2;
				//If f(c) is zero than root is found
				if (function1(c) == 0) {
					break;
				//If f(c) has the same sign as f(b), replace b with c
				} else if (function1(c) * function1(a) < 0) {
					b = c;
				//If f(c) has the same sign as f(a), replace a with c
				} else if (function1(c) * function1(b) < 0) {
					a = c;
				}
				//Increment counter
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, (getApproximateError(c, previousC) * 100), (getTrueError(c, root) * 100));
			//Ends iterations once approximate error is less than given error or iterations has reached 100
			} while ((getApproximateError(c, previousC) >= error && counter < MAX_NUMBER_OF_ITERATIONS)); 
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
			//Calculates bisection method for function 2
		} else if (functionNumber == 2) {
			System.out.print("Finding root for function 2 using BISECTION method at [" + a + ", " + b + "]");
			//Quits method if unable to find root between points a and b
			if (function2(a) * function2(b) >= 0) {
				System.out.println("\nUnable to find roots with the current a and b input.\n");
				return;
			}
			//Gives a value to c
			c = a;
			do {
				//Sets previous iteration of C before calculating for new value of C
				previousC = c;
				c = (a + b) / 2;
				//If f(c) is zero than root is found
				if (function2(c) == 0) {
					break;
				//If f(c) has the same sign as f(b), replace b with c
				} else if (function2(c) * function2(a) < 0) {
					b = c;
				//If f(c) has the same sign as f(a), replace a with c
				} else if (function2(c) * function2(b) < 0) {
					a = c;
				}
				//Increment counter
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, (getApproximateError(c, previousC) * 100), (getTrueError(c, root) * 100));
			//Ends iterations once approximate error is less than given error or iterations has reached 100
			} while ((getApproximateError(c, previousC) >= error && counter < MAX_NUMBER_OF_ITERATIONS)); 
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		}
	}
	
	//False position method
	public static void falsePositionMethod(double a, double b, double root, int functionNumber) {
		//Calculates bisection method for function 1
		if (functionNumber == 1) {
			System.out.print("Finding root for function 1 using FALSE POSITION method at [" + a + ", " + b + "]");
			//Quits method if unable to find root between points a and b
			if (function1(a) * function1(b) >= 0) {
				System.out.println("\nUnable to find roots with the current a and b input.\n");
				return;
			}
			//Gives a value to c
			c = a;
			do {
				//Sets previous iteration of C before calculating for new value of C
				previousC = c;
				c = a - ((function1(a) * (b - a)) / (function1(b) - function1(a)));
				//If f(c) is zero than root is found
				if (function1(c) == 0) {
					break;
				//If f(c) has the same sign as f(b), replace b with c
				} else if (function1(c) * function1(a) < 0) {
					b = c;
				//If f(c) has the same sign as f(a), replace a with c
				} else if (function1(c) * function1(b) < 0) {
					a = c;
				}
				//Increment counter
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, (getApproximateError(c, previousC) * 100), (getTrueError(c, root) * 100));
			//Ends iterations once approximate error is less than given error or iterations has reached 100
			} while((getApproximateError(c, previousC) >= error && counter < MAX_NUMBER_OF_ITERATIONS));
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		//Calculates bisection method for function 2
		} else if (functionNumber == 2) {
			System.out.print("Finding root for function 2 using FALSE POSITION method at [" + a + ", " + b + "]");
			//Quits method if unable to find root between points a and b
			if (function2(a) * function2(b) >= 0) {
				System.out.println("\nUnable to find roots with the current a and b input.\n");
				return;
			}
			//Gives a value to c
			c = a;
			do {
				//Sets previous iteration of C before calculating for new value of C
				previousC = c;
				c = a - ((function2(a) * (b - a)) / (function2(b) - function2(a)));
				//If f(c) is zero than root is found
				if (function2(c) == 0) {
					break;
				//If f(c) has the same sign as f(b), replace b with c
				} else if (function2(c) * function2(a) < 0) {
					b = c;
				//If f(c) has the same sign as f(a), replace a with c
				} else if (function2(c) * function2(b) < 0) {
					a = c;
				}
				//Increment counter
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, (getApproximateError(c, previousC) * 100), (getTrueError(c, root) * 100));
			//Ends iterations once approximate error is less than given error or iterations has reached 100
			} while((getApproximateError(c, previousC) >= error && counter < MAX_NUMBER_OF_ITERATIONS));
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		}
	}
	
	//Newton-Raphson method
	public static void newtonRaphsonMethod(double a, double root, int functionNumber) {
		//Calculates bisection method for function 1
		if (functionNumber == 1) {
			System.out.print("Finding root for function 1 using NEWTON-RAPHSON method at [" + a + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while (loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c = a - (function1(a) / function1Derivative(a));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c, a) * 100);
				//Increment error
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//Give c's value to a, a will be next c value
				a = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		//Calculates bisection method for function 2
		} else if (functionNumber == 2) {
			System.out.print("Finding root for function 2 using NEWTON-RAPHSON method at [" + a + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while (loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c = a - (function2(a) / function2Derivative(a));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c, a) * 100);
				//Increment error
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//Give c's value to a, a will be next c value
				a = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		}
	}
	
	//Secant method
	public static void secantMethod(double a, double b, double root, int functionNumber) {
		//Calculates bisection method for function 1
		if (functionNumber == 1) {
			System.out.print("Finding root for function 1 using SECANT method at [" + a + ", " + b + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while(loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c = b - ((function1(b) * (b - a)) / (function1(b) - function1(a)));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c, a) * 100);
				//Increment error
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//if x(n) becomes x(n-1) then x(n) = b becomes x(n-1) = a
				a = b;
				//if x(n+1) becomes x(n) then x(n+1) = c becomes x(n) = b
				b = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		//Calculates bisection method for function 2
		} else if (functionNumber == 2) {
			System.out.print("Finding root for function 2 using SECANT method at [" + a + ", " + b + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while(loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c = b - ((function2(b) * (b - a)) / (function2(b) - function2(a)));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c, a) * 100);
				//Increment error
				counter++;
				//Prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//if x(n) becomes x(n-1) then x(n) = b becomes x(n-1) = a
				a = b;
				//if x(n+1) becomes x(n) then x(n+1) = c becomes x(n) = b
				b = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//Prints out final value
			System.out.printf("\nThe final value of x is : %.4f", c);
			System.out.println("\n");
			//Resets counter and c values
			resetNumbers();
		}
	}
	
	public static void modifiedSecantMethod(double a, double root, int functionNumber) {
		//Calculates bisection method for function 1
		if (functionNumber == 1) {
			System.out.print("Finding root for function 1 using MODIFIED SECANT method at [" + a + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while(loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c =  a - function1(a) * ((delta * a) / (function1(a + (delta * a)) - function1(a)));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c,  a) * 100);
				//Increment error
				counter++;
				//If c is not a number, quit method
				if(Double.isNaN(c)) {
					System.out.println("\nUnable to find roots with current input\n");
					break;
				}
				//Otherwise, prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//Give c's value to a, a will be next c value
				a = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//If c is a number, print out final value
			if(!Double.isNaN(c)) {
				System.out.printf("\nThe final value of x is : %.4f", c);
				System.out.println("\n");
			}
			//Resets counter and c values
			resetNumbers();
		//Calculates bisection method for function 2
		} else if (functionNumber == 2) {
			System.out.print("Finding root for function 2 using MODIFIED SECANT method at [" + a + "]");
			boolean loop = true;
			//Ends loop once approximate error is less than given error and iterations has reached 100
			while(loop && counter < MAX_NUMBER_OF_ITERATIONS) {
				c =  a - function2(a) * ((delta * a) / (function2(a + (delta * a)) - function2(a)));
				//Gets approximate error
				double approximation = Math.abs(getApproximateError(c,  a) * 100);
				//Increment error
				counter++;
				//If c is not a number, quit method
				if(Double.isNaN(c)) {
					System.out.println("\nUnable to find roots with current input\n");
					break;
				}
				//Otherwise, prints out iteration number, approximate error, and true error
				if (counter < 10) {
					System.out.print("\nIteration: 0" + counter + ",");
				} else {
					System.out.print("\nIteration: " + counter + ",");
				}
				System.out.printf(" x = %8.4f  with relative error : %-6.2f  with true error : %-6.2f",
						c, Math.abs(getApproximateError(c, a) * 100), Math.abs(getTrueError(c, root) * 100));
				//Give c's value to a, a will be next c value
				a = c;
				//End loop when approximate error is less than given error
				if (error > approximation) {
					loop = false;
				}
			}
			//If c is a number, print out final value
			if(!Double.isNaN(c)) {
				System.out.printf("\nThe final value of x is : %.4f", c);
				System.out.println("\n");
			}
			//Resets counter and c values
			resetNumbers();
		}
	}
	
	//Sets c values and counter to zero
	public static void resetNumbers() {
		previousC = c = counter = 0;
	}
}