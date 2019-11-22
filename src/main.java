import java.util.ArrayList;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class main {

	public static void main(String[] args) {

		// Starting variables
		String eq = "x^3 - x - 1";
		int a = 0;
		int b = 2;
		double eps = .000001;
		double start = 2;
		int N = 10;
		
		bisection (eq , a, b, eps, N);
		System.out.println();
		falsePosition(eq, a, b, eps, N);
		System.out.println();
		newtonsMethod(eq, start, eps);
	}
	
	public static double[] bisection (String eq, double a, double b, double eps, int N)
	{
		int n = 1; // counter for the number of interval
		double[] returnIntervals = new double[2];
		
		System.out.printf("%-5s %-8s %-8s %-10s %-10s\n", "n", "a", "b", "x", "f_x");
		while (n <= N)
		{
			double x = (a + b)/2; // find the midpoint of the intervals
			
			if (x - a <= eps)
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
	
	public static double[] falsePosition (String eq, double a, double b, double eps, int N)
	{
		int n = 1; // counter for the number of interval
		double[] returnIntervals = new double[2];
		System.out.printf("%-5s %-8s %-8s %-10s %-10s\n", "n", "a", "b", "x", "f_x");

		while (n <= N)
		{
			// find the x-intercept of the straight line through the points at a and b
			double x = (a * solveFunction(eq, b) - b * solveFunction(eq, a))/(solveFunction(eq, b) - solveFunction(eq, a));
		
			
			if (x - a <= eps)
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
	
	public static double newtonsMethod(String eq, double start, double eps)
	{
		int n = 0;
		double x = start;
	    System.out.printf( "%-5s %-12s %-12s\n", "n", "x", "f_x");	

		while (solveFunction(eq, x) > eps)
		{
			x = x - (solveFunction(eq, x)/derivative(eq, x));
			double f_x = solveFunction(eq, x);
		    System.out.printf( "%-5s %-12.7f %-12.7f\n", n, x, f_x);	
		    n ++; 
		}
		
		return 0.0;
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
		double h = .000000001;
		double f_prime = (solveFunction(eq, var + h) - solveFunction(eq, var))/(h);
		return f_prime;
	}
	
	
}
