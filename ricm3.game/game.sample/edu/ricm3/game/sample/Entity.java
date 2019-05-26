package edu.ricm3.game.sample;

import java.awt.Graphics;
import java.util.LinkedList;

public interface Entity {
	
	public void location(int x, int y);
	
	//public void location(Location l);
	
	//Return the x coordinate of the top left hand corner
	public int getx();
	
	//Return the y coordinate of the top left hand corner
	public int gety();
	
	//Return the height of the rectangle
	public int getHeight();
	
	//Return the width of the rectangle
	public int getWidth();
	
	public void move(char d, LinkedList<Plateforme> plateformes_list);
	
	public boolean collision(int x, int y, int w, int h);
	
	void paint(Graphics g);

}
