import java.util.function.Function;

public class Romberg {
    /**
     * calculates R(n,m) where n = m = degree
     **/
    public static double integrate(Function<Double,Double> f, double lower, double upper, int degree) {
        // trapezoidal rule implemented below
		return integrator(f, lower, upper, degree, degree);
        //return (f.apply(lower) + f.apply(upper))*(upper - lower)*0.5;
    }
	
	public static int power(int x, int p){
		int r = 1;
		int c = 0;
		while (c<p){
			r *= x;
			c++;
		}
		return r;
	}
	
	public static double integrator(Function<Double,Double> f, double a, double b, int n, int m){
		if (n==0 && m==0){
			double result = ((b-a)/2.0) * (f.apply(a)+f.apply(b));
			return result;
		} else if (m==0){
			double result = 0.5 * integrator(f,a,b,n-1,0);
			double addition = 0.0;
			int k = 1;
			while (k<=power(2,n-1)){
				addition += f.apply(a + ((b-a)/power(2,n)) * (2*k-1));
				k++;
			}
			result += ((b-a)/power(2,n))*addition;
			return result;
		} else {
			double result = (1.0/(power(4,m)-1))*((power(4,m)*integrator(f,a,b,n,m-1))-integrator(f,a,b,n-1,m-1));
			return result;
		}
	}
    
    public static void main(String[] args) {
        // tests
        double area, expected;
        area = integrate(x -> x*x, 0, 1, 5);
        expected = 0.3333333;
        // 99% accuracy
        assert Math.abs(area - expected) < Math.abs(0.01 * expected);
        
        area = integrate(x -> Math.sin(x), 0, Math.PI, 5);
        expected = 2;
		System.out.println(area);
		System.out.println(expected);
        assert Math.abs(area - expected) < Math.abs(0.01 * expected);
    }
}
