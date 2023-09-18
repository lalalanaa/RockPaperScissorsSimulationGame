package gui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Vektor {
	
	private double x, y;
	private ArrayList<Double> ort;
	
	public Vektor(double xx, double yy) 
	{ 
		x = xx; 
		y = yy;
		ort = new ArrayList<Double>();
		ort.add(x/Math.sqrt(x*x + y*y));
		ort.add(y/Math.sqrt(x*x + y*y));
	}
	
	public double getX() { return x; }

	public double getY() { return y; }
	
	public void setX(double xx) { x = xx; setOrt(); }
	
	public void setY(double yy) { y = yy; setOrt(); }
	
	public void setOrt() 
	{
		ort.add(0, x/Math.sqrt(x*x + y*y));
		ort.add(1, y/Math.sqrt(x*x + y*y));
	}
	
	public ArrayList<Double> getOrt() 
	{	
		return ort;
	}
	
	public static Vektor pseudoslucajan()
	{
		double xx = 0, yy = 0;
		while(xx == -1 || yy == -1 || (xx == 0 && yy == 0))
		{
			xx = (new Random().nextInt(2) == 0 ? -1 : 1) * new Random().nextDouble();
			yy = (new Random().nextInt(2) == 0 ? -1 : 1) * new Random().nextDouble();			
		}
		
		return new Vektor(xx, yy);
	}
}
