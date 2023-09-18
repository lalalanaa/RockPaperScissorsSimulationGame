package gui;

import java.util.ArrayList;

public class Generator 
{
	private int n, m, count, br;
	private ArrayList<Figura> tip = new ArrayList<>();
	
	public Generator(int nn, int mm, int c)
	{
		n = nn; m = mm;
		count = c;
		tip.add(new Disk(Vektor.pseudoslucajan(), Vektor.pseudoslucajan()));
		tip.add(new Papir(Vektor.pseudoslucajan(), Vektor.pseudoslucajan()));
		tip.add(new Makaze(Vektor.pseudoslucajan(), Vektor.pseudoslucajan()));
		br = 0;
	}
	
	public ArrayList<Figura> generisi()
	{
		ArrayList<Figura> figure = new ArrayList<>();
		
		for(int i = 0; i < count; i++)
		{
			for(int j = 0; j < 100; j++)
			{
				Vektor pol = Vektor.pseudoslucajan();
				pol.setX(n * (pol.getX() + 1) / 2);
				pol.setY(n * (pol.getY() + 1) / 2);
				
				Vektor pom = Vektor.pseudoslucajan();
				
				Figura f = Figura.stvoriNovu(tip.get(br), pol, pom, 20);
				
				boolean prek = false;
				for(Figura f1 : figure)
					if(f1.kruznicePreklapaju(f)) { prek = true; break; }
				
				if(!prek)
					{ br = (br + 1) % 3; figure.add(f); break; }
			}
		}
		
		
		return figure;
	}
	
	

}
