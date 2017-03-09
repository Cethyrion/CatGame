package edu.neumont.csc150.finalProject;

import java.awt.AWTEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Stage extends JPanel implements ActionListener{
	
	public static final double GRAV = -1;
	
	private static ArrayList<Collidable> surfaces;
	private static ArrayList<Moveable> moveables;
	private static ArrayList<TickListener> tickListeners;
	private static ArrayList<Attack> attacks;
	
	public static final int tickLength = 10;
	private Timer t;
	
	public Stage() {
		initVars();
		initUI();
	}

	private void initUI() {
		
		setLayout(null);
       	setFocusable(true);
        setDoubleBuffered(true);
        setBounds(0, 0, MainFrame.CONTENT_WIDTH, MainFrame.CONTENT_HEIGHT);
        
        setBackground(Color.cyan);
	}
	
	private void initVars() {
		
		surfaces = new ArrayList<>();
		moveables = new ArrayList<>();
		tickListeners = new ArrayList<>();
		attacks = new ArrayList<>();
		
		t = new Timer(tickLength, this);
		t.start();
	}
	
	public static ArrayList<Collidable> getSurfaces() {
		return surfaces;
	}
	
	public static ArrayList<Attack> getAttacks() {
		return attacks;
	}
	
	@Override
	public Component add(Component c) {
		if (c instanceof Collidable) {
			surfaces.add((Collidable) c);
		}
		if (c instanceof Moveable) {
			moveables.add((Moveable) c);
		}
		if (c instanceof TickListener) {
			tickListeners.add((TickListener) c);
		}
		if (c instanceof KeyListener) {
			addKeyListener((KeyListener) c);
		}
		if (c instanceof Attack) {
			attacks.add((Attack) c);
		}
		return super.add(c);
	}
	
	@Override
	public void remove(Component c) {
		if (c instanceof Collidable) {
			surfaces.remove((Collidable) c);
		}
		if (c instanceof Moveable) {
			moveables.remove((Moveable) c);
		}
		if (c instanceof TickListener) {
			tickListeners.remove((TickListener) c);
		}
		if (c instanceof KeyListener) {
			removeKeyListener((KeyListener) c);
		}
		if (c instanceof Attack) {
			attacks.remove((Attack) c);
		}
		super.remove(c);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		for (int i = 0; i < tickListeners.size(); i++) {
			tickListeners.get(i).doTick();
		}
		repaint();
	}

	public void createKittens(int playerCount) {
		playerCount = playerCount <= PlayerID.values().length ? playerCount: PlayerID.values().length;
		for (int i = 0; i < playerCount; i++) {
			add(new Cat(PlayerID.values()[i]));
		}
		
	}

	public void createTestStage() {
		add(new Surface(0, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 10), MainFrame.CONTENT_WIDTH, MainFrame.CONTENT_HEIGHT / 10));
		
		add(new Surface(MainFrame.CONTENT_WIDTH / 5, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 5), MainFrame.CONTENT_WIDTH / 20, MainFrame.CONTENT_HEIGHT / 10));
		add(new Surface(MainFrame.CONTENT_WIDTH / 5 * 2, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 10) * 4, MainFrame.CONTENT_WIDTH / 20, MainFrame.CONTENT_HEIGHT / 5));
		add(new Surface(MainFrame.CONTENT_WIDTH / 10 * 3, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 10) * 3, MainFrame.CONTENT_WIDTH / 20, MainFrame.CONTENT_HEIGHT / 10));
		add(new Surface(MainFrame.CONTENT_WIDTH / 10 * 6, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 5), MainFrame.CONTENT_WIDTH / 20, MainFrame.CONTENT_HEIGHT / 10));
		add(new Surface(MainFrame.CONTENT_WIDTH / 10 * 5, MainFrame.CONTENT_HEIGHT - (MainFrame.CONTENT_HEIGHT / 10) * 3, MainFrame.CONTENT_WIDTH / 20, MainFrame.CONTENT_HEIGHT / 10));
		add(new Attack(10, 1, PlayerID.FOURTH, "left", true, -1, 100, 100));
	}

}
