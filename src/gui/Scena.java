package gui;

import java.awt.Canvas;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class Scena extends Canvas implements Runnable {
	
	private Simulacija owner;
	private int p, k, m;
	private CheckboxGroup cbg;
	private int pomeraj;
	private boolean aktivna, pauza, kraj = false;
	private Thread t;
	private Label l;
	private String string;
	private ArrayList<Figura> figure;
	
	public Scena(Simulacija s, CheckboxGroup c, Label ll) { this(s, 3, c, ll); }
	
	public void setPomeraj(int p) { pomeraj = p; }
	
	public Scena(Simulacija s, int pom, CheckboxGroup c, Label ll)
	{
		setBackground(Color.GRAY);
		owner = s;
		aktivna = true;
		pauza = true;
		figure = new ArrayList<>();
		pomeraj = pom;
		t = new Thread(this);
		string = new String("");
		cbg = c;
		l = ll;
		repaint();
	}
	
	public void zapocniPosao() { t.start(); }
	
	public boolean getPauzirana() { return pauza; }
	
	public void ukloniFiguru(int x, int y)
	{
		for(Figura f : figure)
			if(f.vektorUnutar(new Vektor(x,y))) 
			{
				if(f.getVrsta() == "Kamen") k--;
				else if(f.getVrsta() == "Makaze") m--;
				else if(f.getVrsta() == "Papir") p--;
				figure.remove(f);  
				repaint(); 
				break;
			}
	}
	
	
	public void dodajFiguru(Figura f) 
	{
		double x = f.getCentar().getX(), y = f.getCentar().getY(), r = f.getR();
		if(x + r >= this.getWidth() || x - r < 0 || y + r >= this.getHeight() || y - r < 0) return;
		
		for(Figura f1 : figure)
			if(f1.kruznicePreklapaju(f)) return;
		
		if(f.getVrsta() == "Kamen") k++;
		else if(f.getVrsta() == "Makaze") m++;
		else if(f.getVrsta() == "Papir") p++;
		
		figure.add(f);
	}
	
	public void obrisi()
	{
		m = k = p = 0;
		figure = new ArrayList<>();
		repaint();
	}
	
	
	public void pomeriFiguru(Figura f)
	{
		double x = f.getCentar().getX(), y = f.getCentar().getY(), r = f.getR();
		if(x + r >= this.getWidth() || x - r <= 0) { f.getPomeraj().getOrt().set(0, -f.getPomeraj().getOrt().get(0)); }
		else if(y + r >= this.getHeight() || y - r <= 0) { f.getPomeraj().getOrt().set(1, -f.getPomeraj().getOrt().get(1)); }
		
		for(int i = 0; i < figure.size(); i++)
		{
			Figura f1 = figure.get(i);
			if(f.kruznicePreklapaju(f1)) 
			{
				Vektor pom = f1.getPomeraj();
				f1.setPomeraj(f.getPomeraj());
				f.setPomeraj(pom);
				if(f.jaca(f1))
				{ 
					if(f1.getVrsta() == "Kamen") k--;
					else if(f1.getVrsta() == "Makaze") m--;
					else if(f1.getVrsta() == "Papir") p--;
					int ind = figure.indexOf(f1); figure.set(ind, Figura.stvoriNovu(f, f1.polozaj, f1.pomeraj, f1.r));
					if(f.getVrsta() == "Kamen") k++;
					else if(f.getVrsta() == "Makaze") m++;
					else if(f.getVrsta() == "Papir") p++;
				}
			}
		}
		
		f.getPolozaj().setX(f.getPolozaj().getX() + pomeraj * (double)f.getPomeraj().getOrt().get(0));
		f.getPolozaj().setY(f.getPolozaj().getY() + pomeraj * (double)f.getPomeraj().getOrt().get(1));
	}
	
	
	@Override
	public void paint(Graphics g) {
		l.setText("Papir:" + p + " Kamen:" + k + " Makaze:" + m + " Ukupno:" + (p+k+m));
		g.setFont(new Font("SanSerif", Font.BOLD, 50));
		for(Figura f : figure)
			f.paint(g);
		if(getPauzirana())
		{
			g.setColor(Color.black);
			g.drawString("PAUZA", 250, 250);
		}
		if(kraj)
		{
			g.setColor(Color.RED);
			g.drawString("KRAJ", 250, 250);
		}
	}
	
	
	@Override
	public void run() {
		try {
			while(!Thread.interrupted()) 
			{
				if(!pauza) 
				{
					if((k == 0 && m == 0 && p != 0) || (k == 0 && m != 0 && p == 0) || (k != 0 && m == 0 && p == 0))
					{ 
						kraj = true; 
						owner.zavrsi();
					}
					for(int i = 0; i < figure.size(); i++)
					{
						Figura f = figure.get(i);
						pomeriFiguru(f);
					}
					repaint();
				}
				Thread.sleep(100);
			}
		}catch (InterruptedException e) {}
	}
	
	public void zaustavi() { t.interrupt(); }
	
	public void pauziraj() {pauza = true; repaint(); }
	
	public void nastavi() { pauza = false; repaint(); }
	
}
