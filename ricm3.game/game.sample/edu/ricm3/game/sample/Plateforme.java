package edu.ricm3.game.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public class Plateforme implements Entity {

	
	Model m_model;
	Color m_color;
	int m_x, m_y, m_width, m_height;
	
	//Rectangle de longueur height et de largeur width
	//x, y coordonnées de l'angle en haut à gauche
	//width largeur du rectangle (axe des x)
	//height longueur du rectangle (axe des y)
	Plateforme(Model m, int x, int y, int width, int height) {
		m_model = m;
		m_x = x; 
		m_y = y;
		m_width = width;
		m_height = height;
		m_color = Color.red;
	}
	
	
	public void setColor(Color c) {
		this.m_color = c;
	}
	
	@Override
	public void location(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void move(char dir, LinkedList<Plateforme> p_list) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collision(int x, int y, int w, int h) {
		if(this.m_x <= x + w) {
			return false;
		}
		if(this.m_x + this.m_width >= x) {
			return false;
		}
		if(this.m_y <= y + h) {
			return false;
		}
		if(this.m_y + this.m_height >= y) {
			return false;
		}
		return true;
		
	}


	@Override
	public void paint(Graphics g) {
		if(this.m_color != null) {
			g.setColor(m_color);
		}
		g.fillRect(m_x, m_y, m_width, m_height);

	}


	@Override
	public int getx() {
		return this.m_x;
	}


	@Override
	public int gety() {
		return this.m_y;
	}


	@Override
	public int getHeight() {
		return this.m_height;
	}


	@Override
	public int getWidth() {
		return this.m_width;
	}
	
	public int get_top_right() {
		return this.m_x + this.m_width;
	}
	
	public int get_bottom_left() {
		return this.m_y + this.m_height;
	}

}
