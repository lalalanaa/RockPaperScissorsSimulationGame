package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Disk extends Figura {

	public Disk(Vektor pol, Vektor pom) {
		this(pol, pom, 20);
	}
	
	public Disk(Vektor pol, Vektor pom, double r) {
		super(pol, pom, r);
	}

	@Override
	public Color getBoja() {
		return Color.BLUE;
	}

	@Override
	public Vektor getCentar() {
		return polozaj;
	}
	
	@Override
	public void paint(Graphics g) 
	{
		g.setColor(Color.BLUE);
		
		int x[] = new int[8], y[] = new int[8];	
		
		for(int i = 0; i < 8; i++)
		{
			x[i] = (int)(getCentar().getX() + r * Math.cos(i * Math.PI / 4));
			y[i] = (int)(getCentar().getY() + r * Math.sin(i * Math.PI / 4));
		}
		
		g.fillPolygon(x, y, 8);
	}
	
	@Override
	public String getVrsta() {
		return "Kamen";
	}
	
	@Override
	public boolean jaca(Figura f) {
		return (f.getVrsta() == "Makaze");
	}
	

}
