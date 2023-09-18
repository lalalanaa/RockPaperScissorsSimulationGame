package gui;

import java.awt.Color;
import java.awt.Graphics;

public class Papir extends Figura {

	public Papir(Vektor pol, Vektor pom) {
		super(pol, pom);
	}
	
	public Papir(Vektor pol, Vektor pom, double r) {
		super(pol, pom, r);
	}

	@Override
	public Color getBoja() {
		return Color.RED;
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		
		int x[] = new int[4], y[] = new int[4];	
		
		for(int i = 0; i < 4; i++)
		{
			x[i] = (int)(getCentar().getX() + r * Math.cos(i * Math.PI / 2));
			y[i] = (int)(getCentar().getY() + r * Math.sin(i * Math.PI / 2));
		}
		
		g.fillPolygon(x, y, 4);

	}

	@Override
	public Vektor getCentar() {
		return polozaj;
	}

	@Override
	public String getVrsta() {
		return "Papir";
	}

	@Override
	public boolean jaca(Figura f) {
		return f.getVrsta() == "Kamen";
	}

}
