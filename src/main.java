import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class main {

	public static void main(String[] args) {

		// Starting variables
		String eq = "e^x + 1";
		int a = 0;
		int b = 1;
		
		// constant variables
		double eps = .00001;
		double start = 0;
		int N = (int)(Math.log((b-a)/eps)/Math.log(2)); // limits the number of trails run in bisection and false position
		
		printResult(bisection (eq , a, b, eps, N), "bisection");
		System.out.println();
		printResult(falsePosition(eq, a, b, eps, N), "false position");
		System.out.println();
		printResult(newtonsMethod(eq, start, eps), "newton's method");
	}
	
	// print statement
	// will be inputting in an array of length two and which algorithm was used
	// this represents the range of the answer given by the algorithm
	// if they are the same, print the exact result, else print the range given by the algorithm
	public static void printResult(double[] result, String algo)
	{
		System.out.println();
		if (result[0] == result[1])
		{
			if (result[0] == Double.NEGATIVE_INFINITY || result[0] == Double.POSITIVE_INFINITY) System.out.println("No answer found");
			else {
			System.out.printf("%s algorithm finds answer of: %.8f", algo, result[0]);
			}
		}
		else
		{
			System.out.printf("%s algorithm finds approximate answer between [%.8f, %.8f]", algo, result[0], result[1]);
		}
		System.out.println("\n============================================================================");
	}
	
	public static double[] bisection (String eq, double a, double b, double eps, int N)
	{
		int n = 1; // counter for the number of interval
		double[] returnIntervals = new double[2];
		
		System.out.printf("%-5s %-8s %-8s %-10s %-10s\n", "n", "a", "b", "x", "f_x");
		while (n <= N)
		{
			double x = (a + b)/2; // find the midpoint of the intervals
			
			if (Math.abs(x - a) <= eps)
			{
				returnIntervals[0] = x;
				returnIntervals[1] = x;
				return returnIntervals; // if the x is close enough to the edge of the interval, return it
			}
			
			
			double f_x = solveFunction(eq,x); // plug into the equation to find the y
			if (f_x == 0)  // if we find the zero, great! Return this value
			{
				returnIntervals[0] = x;
				returnIntervals[1] = x;
				return returnIntervals; 
			}
			
			if (f_x * solveFunction(eq,a) < 0)
			{
				b = x;
			}
			else
			{
				a = x;
			}
			
		    System.out.printf( "%-5s %-8.3f %-8.3f %-10.5f %-5.5f\n", n, a, b, x, f_x);		    
			n++;
		}
		
		returnIntervals[0] = a;
		returnIntervals[1] = b;
		return returnIntervals; // if the iteration limit has been reached, return the intervals around the x
	}
	
	public static double[] falsePosition (String eq, double a, double b, double eps, int N)
	{
		int n = 1; // counter for the number of interval
		double[] returnIntervals = new double[2];
		System.out.printf("%-5s %-8s %-8s %-10s %-10s\n", "n", "a", "b", "x", "f_x");

		while (n <= N)
		{
			// find the x-intercept of the straight line through the points at a and b
			double x = (a * solveFunction(eq, b) - b * solveFunction(eq, a))/(solveFunction(eq, b) - solveFunction(eq, a));
		
			
			if (Math.abs(x - a) <= eps)
			{
				returnIntervals[0] = x;
				returnIntervals[1] = x;
				return returnIntervals; // if the x is close enough to the edge of the interval, return it
			}
			
			
			double f_x = solveFunction(eq,x); // plug into the equation to find the y
			if (f_x == 0)  // if we find the zero, great! Return this value
			{
				returnIntervals[0] = x;
				returnIntervals[1] = x;
				return returnIntervals; 
			}
			
			if (f_x * solveFunction(eq,a) < 0)
			{
				b = x;
			}
			else
			{
				a = x;
			}
			
		    System.out.printf( "%-5s %-8.3f %-8.3f %-10.5f %-10.5f\n", n, a, b, x, f_x);		    
			n++;
		}
		
		returnIntervals[0] = a;
		returnIntervals[1] = b;
		return returnIntervals; // if the iteration limit has been reached, return the intervals around the x
	}
	
	public static double[] newtonsMethod(String eq, double start, double eps)
	{
		int n = 0;
		double x = start;
	    System.out.printf( "%-5s %-12s %-12s\n", "n", "x", "f_x");	

		while (Math.abs(solveFunction(eq, x)) > eps && n < 1000) 
		{
			x = x - (solveFunction(eq, x)/derivative(eq, x));
			double f_x = solveFunction(eq, x);
		    System.out.printf( "%-5s %-12.7f %-12.7f\n", n, x, f_x);	
		    n ++; 
		}
		double[] out = new double[2];
		out[0] = x;
		out[1] = x;
		return out;
	}
	
	
	public static double solveFunction(String eq, double var) {
		Expression e = new ExpressionBuilder(eq)
		        .variables("x")
		        .build()
		        .setVariable("x", var);
		double result = e.evaluate();
		
		return result;
	}
	
	// Using the definition of a derivative and using a small value for h
	public static double derivative(String eq, double var)
	{
		double h = .0000000000001;
		double f_prime = (solveFunction(eq, var + h) - solveFunction(eq, var))/(h);
		return f_prime;
	}
	
	
}
