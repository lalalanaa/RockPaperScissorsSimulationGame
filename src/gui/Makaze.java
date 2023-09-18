package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Makaze extends Figura {

	public Makaze(Vektor pol, Vektor pom) {
		super(pol, pom);
	}
	
	public Makaze(Vektor pol, Vektor pom, double r) {
		super(pol, pom, r);
	}

	@Override
	public Color getBoja() {
		return Color.GREEN;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.GREEN);
		
		int x[] = new int[3], y[] = new int[3];	
		
		for(int i = 0; i < 3; i++)
		{
			x[i] = (int)(getCentar().getX() + r * Math.cos(i * 2*Math.PI / 3));
			y[i] = (int)(getCentar().getY() + r * Math.sin(i * 2*Math.PI / 3));
		}
		
		g.fillPolygon(x, y, 3);	
	}

	@Override
	public Vektor getCentar() {
		return polozaj;
	}

	@Override
	public String getVrsta() {
		return "Makaze";
	}

	@Override
	public boolean jaca(Figura f) {
		return f.getVrsta() == "Papir";
	}

}
