package gui;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Figura {
	
	protected Vektor polozaj, pomeraj;
	protected double r;
	
	public Figura(Vektor pol, Vektor pom) { this(pol, pom, 20); }
	
	public Figura(Vektor pol, Vektor pom, double rr) 
	{ 
		polozaj =  pol;
		pomeraj = pom;
		r = rr;
	}
	
	public Vektor getPolozaj() { return polozaj; }
	
	public Vektor getPomeraj() { return pomeraj; }
	
	public void setPomeraj(Vektor p) { pomeraj = p; }
	
	
	
	public double getR() { return r; }
	
	public abstract Color getBoja();
	
	public abstract void paint(Graphics g);
	
	public abstract Vektor getCentar();
	
	public boolean vektorUnutar(Vektor v)
	{
		double rast = Math.sqrt(Math.pow(getCentar().getX() -  v.getX(), 2) + Math.pow(getCentar().getY() -  v.getY(), 2));
		return rast <= r;
	}
	
	public boolean kruznicePreklapaju(Figura f)
	{
		double rast = Math.sqrt(Math.pow(getCentar().getX() -  f.getCentar().getX(), 2) + Math.pow(getCentar().getY() -  f.getCentar().getY(), 2));
		return rast <= f.r + r ;
	}
	
	public abstract String getVrsta();
	
	public abstract boolean jaca(Figura f);
	
	public static Figura stvoriNovu(Figura f, Vektor pol, Vektor pom, double r)
	{
		if(f.getVrsta() == "Kamen") return new Disk(pol, pom, r);
		if(f.getVrsta() == "Papir") return new Papir(pol, pom, r);
		if(f.getVrsta() == "Makaze") return new Makaze(pol, pom, r);
		return  null;
	}
}
