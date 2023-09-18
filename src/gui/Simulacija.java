package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;


public class Simulacija extends Frame {
	
	private Scena scena;
	private Button b1, b2, b3;
	private Checkbox cb1, cb2, cb3;
	private CheckboxGroup cbg;
	private TextField tf;
	private Generator gen;
	private int br = 3;
	private Label l, l2;
	private Stoperica stoperica = null;
	
	public void populateWindow()
	{		
		Panel cbp = new Panel(new GridLayout(3, 1));
		cbg = new CheckboxGroup();
		cb1 = new Checkbox("Papir", false, cbg);
		cb1.setBackground(Color.red);
		cb1.setFocusable(false);
		cb2 = new Checkbox("Kamen", false, cbg);
		cb2.setBackground(Color.BLUE);
		cb2.setFocusable(false);
		cb3 = new Checkbox("Makaze", false, cbg);
		cb3.setBackground(Color.GREEN);
		cb3.setFocusable(false);
		
		cb1.addItemListener(ae -> {
			cb1.setForeground(Color.WHITE);
			cb2.setForeground(Color.BLACK);
			cb3.setForeground(Color.BLACK);
			cb1.setFocusable(false);
			requestFocus();
		});
		
		cb2.addItemListener(ae -> {
			cb1.setForeground(Color.BLACK);
			cb2.setForeground(Color.WHITE);
			cb3.setForeground(Color.BLACK);
			cb2.setFocusable(false);
			requestFocus();
		});
		
		cb3.addItemListener(ae -> {
			cb1.setForeground(Color.BLACK);
			cb2.setForeground(Color.BLACK);
			cb3.setForeground(Color.WHITE);
			cb3.setFocusable(false);
			requestFocus();
		});
		
		cbp.add(cb1);
		cbp.add(cb2);
		cbp.add(cb3);
		add(cbp, BorderLayout.EAST);
		
		tf = new TextField();
		
		tf.addActionListener(ae -> {
			requestFocus();
		});
		
		Label poeni = new Label();		
		Panel south = new Panel(new GridLayout(1, 3));
		l = new Label("Stoperica");
		south.add(poeni);
		south.add(tf);
		south.add(l);
		add(south, BorderLayout.SOUTH);
		
		stoperica = new Stoperica(l);
		
		MenuItem m1 = new MenuItem("generisanje", new MenuShortcut('G'));
		MenuItem m2 = new MenuItem("brisanje", new MenuShortcut('B'));
		MenuItem m3 = new MenuItem("resetovanje", new MenuShortcut('R'));
		
		m1.addActionListener(ae -> {
			if(tf.getText() == "") return;
			int br =  Integer.valueOf(tf.getText());
			gen = new Generator(scena.getWidth(), scena.getHeight(), br);
			ArrayList<Figura> figure = gen.generisi();
			for(Figura f : figure)
				scena.dodajFiguru(f);
			scena.repaint();
			requestFocus();
		});
		m2.addActionListener(ae -> {
			scena.obrisi();
			requestFocus();
		});
		m3.addActionListener(ae -> {
			if(stoperica != null) stoperica.reset();
			requestFocus();
		});
		
		Panel north = new Panel();
		b1 = new Button("<<");
		b2 = new Button(">>");
		b3 = new Button("X");
		b1.setFocusable(false);
		b2.setFocusable(false);
		b3.setFocusable(false);
		
		l2 = new Label(br + "");
		north.add(b1);
		l2.setAlignment(Label.CENTER);
		l2.setSize(new Dimension(b1.getWidth(), b1.getHeight()));
		north.add(l2);
		north.add(b2);
		north.add(b3);
		add(north, BorderLayout.NORTH);
		
		b1.addActionListener(ae -> {
			br = (br == 1) ? 1 : br - 1;
			scena.setPomeraj(br);
			l2.setText(br + "");
			l2.revalidate();
			requestFocus();
		});
		
		b2.addActionListener(ae -> {
			br = (br == 5) ? 5 : br + 1;
			scena.setPomeraj(br);
			l2.setText(br + "");
			l2.revalidate();
			requestFocus();
		});
		
		b3.addActionListener(ae -> {
			br = 3;
			scena.setPomeraj(br);
			l2.setText(br + "");
			l2.revalidate();
			requestFocus();
		});
		
		
		Menu m = new Menu("Meni");
		m.add(m1);
		m.add(m2);
		m.add(m3);
		MenuBar mb = new MenuBar();
		mb.add(m);
		setMenuBar(mb);
		
		scena = new Scena(this, cbg, poeni);
		scena.zapocniPosao();
		scena.setPreferredSize(new Dimension(550, 500));
		add(scena, BorderLayout.CENTER);
	}
		
	public Simulacija()
	{
		setBounds(500, 150, 650, 500);
		setResizable(false);
		
		populateWindow();
		
		setTitle("Simulacija");
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_SPACE) 
				{ 
					if(scena.getPauzirana())
					{
						if(!stoperica.pokrenuta())
							stoperica.pokreni();
						else
							stoperica.odpauziraj();
						scena.nastavi(); 
						cb1.setEnabled(false);
						cb2.setEnabled(false);
						cb3.setEnabled(false);
						b1.setEnabled(false);
						b2.setEnabled(false);
						b3.setEnabled(false);
						tf.setEditable(false);
					}
					else 
					{
						if(stoperica.pokrenuta())
							stoperica.pauziraj();
						scena.pauziraj();
						cb1.setEnabled(true);
						cb2.setEnabled(true);
						cb3.setEnabled(true);
						b1.setEnabled(true);
						b2.setEnabled(true);
						b3.setEnabled(true);
						tf.setEditable(true);
					}
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
				{
					scena.zaustavi();
					stoperica.zaustavi();
					dispose();
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_DOWN)
				{
					if(cbg.getSelectedCheckbox() == cb1)
					{
						cbg.setSelectedCheckbox(cb2);
						cb1.setForeground(Color.BLACK);
						cb2.setForeground(Color.WHITE);
						cb3.setForeground(Color.BLACK);
						cb2.setFocusable(false);
						requestFocus();
					}
					else if(cbg.getSelectedCheckbox() == cb2)
					{
						cbg.setSelectedCheckbox(cb3);
						cb1.setForeground(Color.BLACK);
						cb2.setForeground(Color.BLACK);
						cb3.setForeground(Color.WHITE);
						cb3.setFocusable(false);
						requestFocus();
					}
				}
				
				else if(e.getKeyCode() == KeyEvent.VK_UP)
				{
					if(cbg.getSelectedCheckbox() == cb2)
					{
						cbg.setSelectedCheckbox(cb1);
						cb1.setForeground(Color.WHITE);
						cb2.setForeground(Color.BLACK);
						cb3.setForeground(Color.BLACK);
						cb1.setFocusable(false);
						requestFocus();
					}
					else if(cbg.getSelectedCheckbox() == cb3)
					{
						cbg.setSelectedCheckbox(cb2);
						{
							cbg.setSelectedCheckbox(cb2);
							cb1.setForeground(Color.BLACK);
							cb2.setForeground(Color.WHITE);
							cb3.setForeground(Color.BLACK);
							cb2.setFocusable(false);
							requestFocus();
						}
					}
				}
			}
		});
		
		scena.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getButton() == MouseEvent.BUTTON1)
				{
					if(scena.getPauzirana())
					{
						int x = e.getX();
						int y = e.getY();
						if(cbg.getSelectedCheckbox() == null) return;
						if(cbg.getSelectedCheckbox().getLabel() == "Kamen")
							scena.dodajFiguru(new Disk(new Vektor(x,y), Vektor.pseudoslucajan()));	
						else if(cbg.getSelectedCheckbox().getLabel() == "Papir")
							scena.dodajFiguru(new Papir(new Vektor(x,y), Vektor.pseudoslucajan()));
						else if(cbg.getSelectedCheckbox().getLabel() == "Makaze")
							scena.dodajFiguru(new Makaze(new Vektor(x,y), Vektor.pseudoslucajan()));
						scena.repaint();
					}
				}
				else if(e.getButton() == MouseEvent.BUTTON3)
				{
					int x = e.getX();
					int y = e.getY();
					scena.ukloniFiguru(x, y);
				}
				requestFocus();
			}
		});
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				zavrsi();
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	public void zavrsi()
	{
		scena.zaustavi();
		stoperica.zaustavi();
	}
	
	
	
	public static void main(String[] args) {
		new Simulacija();
		
	}

}
