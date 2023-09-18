package gui;

import java.awt.Label;

public class Stoperica extends Thread {
	
	private int m, s, ss;
	private Label l;
	private boolean pok = false;
	private boolean pauzirana = false;
	
	public Stoperica(Label ll) {
		l = ll;
		m = s = ss = 0;
		l.setText("Stoperica: " + m + ":" + s + ":" + ss);
	}
	
	@Override
	public void run() {
		try {
			while(true)
			{
				synchronized (this) {
					while(pauzirana) wait();
				}
				ss++;
				if(ss == 60) 
				{ 
					ss = 0;
					s++;
					if(s == 60) { s = 0; m++; }
				}
				l.setText("Stoperica: " + m + ":" + s + ":" + ss);
				l.revalidate();
				sleep(100);
			}
		}catch(InterruptedException e) {}
	}
	
	public boolean pokrenuta() { return pok; }
	
	public void odpauziraj() { pauzirana = false; synchronized (this) {
		notify();
	} }
	
	public void pauziraj() { pauzirana = true; }
	
	public void reset() { pauziraj(); s = ss = m = 0; l.setText("Stoperica: " + m + ":" + s + ":" + ss); l.revalidate(); }
	
	public void pokreni() { pok = true; this.start(); }
	
	public void zaustavi() { this.interrupt(); }
}
