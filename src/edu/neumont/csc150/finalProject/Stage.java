package edu.neumont.csc150.finalProject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Stage implements KeyListener {

	private int floorHeight;

	ArrayList<Rectangle> surfaces;

	private Kitten[] kittens;

	public Stage(int floorHeight, int floorWidth) {
		surfaces = new ArrayList<Rectangle>();

		this.floorHeight = floorHeight;

		surfaces.add(new Rectangle(0, Finals.FRAME_HEIGHT - floorHeight, floorWidth, floorHeight));
		surfaces.add(new Rectangle(Finals.FRAME_WIDTH / 2 - 50, Finals.FRAME_HEIGHT - (100 + floorHeight), 100, 100));
		surfaces.add(new Rectangle(Finals.FRAME_WIDTH / 2 - 150, Finals.FRAME_HEIGHT - (330 + floorHeight), 100, 100));
		surfaces.add(new Rectangle(Finals.FRAME_WIDTH / 2 - 50, Finals.FRAME_HEIGHT - (560 + floorHeight), 100, 100));
		surfaces.add(new Rectangle(Finals.FRAME_WIDTH / 2 + 50, Finals.FRAME_HEIGHT - (790 + floorHeight), 100, 100));

		kittens = new Kitten[2];
		
		for (int i = 0; i < kittens.length; i++) {
			kittens[i] = new Kitten(300, floorHeight, playerID.values()[i]);
		}
	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.DARK_GRAY);

		for (Rectangle r : surfaces) {
			g2d.fill(r);
		}

		for (Kitten k : kittens) {
			k.draw(g);
		}

		test(g);
	}

	public void test(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.RED);

		for (Rectangle r : surfaces) {
			for (Rectangle r2 : surfaces) {
				if (r2.intersects(r) && r2 != r) {
					g2d.fill(r.intersection(r2));
				}
			}
		}
	}

	public void actionPerformed() {
		for (Kitten k : kittens) {
			k.move(surfaces);
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {
		for (Kitten k : kittens) {
			k.keyPressed(e);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		for (Kitten k : kittens) {
			k.keyReleased(e);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		for (Kitten k : kittens) {
			k.keyTyped(e);
		}
	}

}