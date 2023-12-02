package il.ac.tau.cs.sw1.hw6;

import java.util.Arrays;

public class Polynomial {

	int deg;
	double[] polynom;

	/*
	 * Creates the zero-polynomial with p(x) = 0 for all x.
	 */

	public Polynomial()
	{
		this.deg = 0;
		this.polynom = new double [deg +1];
		this.polynom[0] = 0;
	} 
	/*
	 * Creates a new polynomial with the given coefficients.
	 */
	public Polynomial(double[] coefficients) 
	{
		this.deg = coefficients.length -1;
		this.polynom = Arrays.copyOf(coefficients, coefficients.length);
	}
	public void fixpoly(){
		int newsize = polynom.length;
		for(int i = this.deg; i >= 0; i--){
			if(this.polynom[i] == 0){
				newsize--;
			}
			else {
				break;
			}
			if (newsize != this.polynom.length){
				if(newsize == 0){
					this.polynom = new double []{0.0};
					this.deg = 0;
				}
				else{
					this.polynom = Arrays.copyOf(this.polynom, newsize);
					this.deg = newsize -1;
				}

			}
		}
	}
	/*
	 * Addes this polynomial to the given one
	 *  and retruns the sum as a new polynomial.
	 */
	public Polynomial adds(Polynomial polynomial)
	{
		int maxlen = Integer.max(this.deg, polynomial.deg) +1;
		double[] newcoefficients = new double[maxlen];

		for (int i = 0 ; i < maxlen; i++ ){
			if (i <= this.deg){
				newcoefficients[i] += this.polynom[i];
			}
			if (i <= polynomial.deg){
				newcoefficients[i] += polynomial.polynom[i];
			}
		}
		Polynomial sumpoly = new Polynomial(newcoefficients);
		sumpoly.fixpoly();
		return sumpoly;
		
	}
	/*
	 * Multiplies a to this polynomial and returns 
	 * the result as a new polynomial.
	 */
	public Polynomial multiply(double a)
	{
		double[] newcoefficients = new double[this.polynom.length];
		for (int i = 0 ; i < this.polynom.length; i++){
			newcoefficients[i] = a * this.polynom[i ];
		}
		Polynomial multpoly = new Polynomial(newcoefficients);
		multpoly.fixpoly();
		return multpoly;
		
	}
	/*
	 * Returns the degree (the largest exponent) of this polynomial.
	 */
	public int getDegree()
	{
		return this.deg;
	}
	/*
	 * Returns the coefficient of the variable x 
	 * with degree n in this polynomial.
	 */
	public double getCoefficient(int n)
	{
		if (n > this.deg || n < 0 ){
			return 0.0;
		}
		return this.polynom[n];
	}
	
	/*
	 * set the coefficient of the variable x 
	 * with degree n to c in this polynomial.
	 * If the degree of this polynomial < n, it means that that the coefficient of the variable x 
	 * with degree n was 0, and now it will change to c. 
	 */
	public void setCoefficient(int n, double c)
	{
		if (c==0){
			if (n == this.deg){
				if(n == 0){
					double [] newpo = new double[1];
					newpo[0] = 0.0;
					this.polynom = Arrays.copyOf(newpo,1);
					this.deg = 0;
				}
				else {
					int newco = this.deg -1;
					while (this.polynom[newco] == 0){
						if(newco == 0){
							break;
						}
						newco--;
					}
					this.polynom = Arrays.copyOf(this.polynom, newco + 1);
					this.deg = newco;
				}

			}
		}

		else{
			if (n <= this.deg){
				this.polynom[n] = c;
			}
			double[] newcoefficients = new double[n+1];
			for (int i = 0 ; i < n + 1; i++){
				if (i <= this.deg){
					newcoefficients[i] = this.polynom[i];
				}
				else if (i ==n){
					newcoefficients[i] = c;
				}
				else {
					newcoefficients[i] = 0;
				}
			}
			this.polynom = Arrays.copyOf(newcoefficients,newcoefficients.length);
			this.deg = n;
		}
	}
	
	/*
	 * Returns the first derivation of this polynomial.
	 *  The first derivation of a polynomal a0x0 + ...  + anxn is defined as 1 * a1x0 + ... + n anxn-1.
	
	 */
	public Polynomial getFirstDerivation()
	{
		if (this.deg == 0){
			double[] newcoefficients = new double[1];
			newcoefficients[0] = 0.0;
			Polynomial devpoly = new Polynomial(newcoefficients);
			return devpoly;
		}
		double[] newcoefficients = new double[this.deg];
		for (int i = 0 ; i < this.deg ; i++){
			newcoefficients[i] = this.polynom[i+1]*(i+1);
		}
		Polynomial devpoly = new Polynomial(newcoefficients);
		return devpoly;
	}
	
	/*
	 * given an assignment for the variable x,
	 * compute the polynomial value
	 */
	public double computePolynomial(double x)
	{
		double res = 0.0;
		for (int i = 0 ; i < this.deg + 1 ; i++){
			res += this.polynom[i]*Math.pow(x,i);
		}
		return res;
	}
	
	/*
	 * given an assignment for the variable x,
	 * return true iff x is an extrema point (local minimum or local maximum of this polynomial)
	 * x is an extrema point if and only if The value of first derivation of a polynomal at x is 0
	 * and the second derivation of a polynomal value at x is not 0.
	 */
	public boolean isExtrema(double x)
	{
		Polynomial firstdev = this.getFirstDerivation() ;
		Polynomial secdev = firstdev.getFirstDerivation();
		if (firstdev.computePolynomial(x) == 0 ){
			if(secdev.computePolynomial(x) != 0){
				return true;
			}
		}
		return false;
	}
	
	
	
	

    
    

}
