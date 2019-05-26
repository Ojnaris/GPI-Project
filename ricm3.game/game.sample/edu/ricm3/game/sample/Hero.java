package edu.ricm3.game.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;

public class Hero implements Entity {
	
	Model m_model;
	private int HP;
	int m_x, m_y;
	int m_side;
	Color m_color;
	int x_prev, y_prev;
	char m_direction;
	//int right_border;
	//int bottom_border;
	
	Hero(Model m, int x, int y) {
		this.m_model = m;
		this.m_x = x;
		this.m_y = y;
		this.m_side = 40;
		this.m_color = Color.green;
		this.HP = 5;
		//this.right_border = m_model.getGameUI().m_frame.getWidth();
		//this.bottom_border = m_model.getGameUI().m_frame.getHeight();
	}

	@Override
	public void location(int x, int y) {
		this.m_x = x;
		this.m_y = y;
		// TODO Auto-generated method stub

	}

	@Override
	public void move(char dir, LinkedList<Plateforme> m_plateformes) {
		//Initialization of previsional coordinates
		x_prev = 25;
		y_prev = 600;
		//Boolean to test if there is a platform at the planned destination
		boolean isAvailable;
		isAvailable = true;
		m_direction = dir;
		//According to the selected direction, we test if Mario is getting out of the window borders
		if(dir == 'N') {
			y_prev = this.m_y - 10;
			x_prev = this.m_x; //here
		}
		else if(dir == 'E') {
			x_prev = this.m_x + 10;
			y_prev = this.m_y;  //here
		}
		else if(dir == 'S') {
			y_prev = this.m_y + 10;
			x_prev = this.m_x;  //here
		}
		else {
			x_prev = this.m_x - 10;
			y_prev = this.m_y;  //here
		}
		//If the coordinates are available, Mario moves
		//Check if Mario stay inside the window
		if(!(x_prev < 0 || x_prev + this.m_side > 1024)) {
			
			if(!(y_prev < 0 || y_prev + this.m_side > 690)) {
				
				
				//Check if Mario is stopped by a wall (platform)
				Iterator<Plateforme> iter = m_plateformes.iterator();  //here
				while(iter.hasNext()) {   //here
					Plateforme p = iter.next();  //here
					if(collision(p.getx(), p.gety(), p.get_top_right(), p.get_bottom_left())) {  //here
						isAvailable = false; //here
					} //here
				} //here
				if(isAvailable) { //here
					this.m_x = this.x_prev; //here
					this.m_y = this.y_prev; //here
				} //here
				
			}
		}
	}

	@Override
	//Return true if there is a collision
	public boolean collision(int x, int y, int w, int h) {
		
		//We check if there isn't any collision
		//if((x_prev + m_side < x) || (x_prev > x + h) || (y_prev + m_side < y) || (y_prev > y + h)) {
		if((x_prev + m_side < x) || (x_prev > w) || (y_prev + m_side < y) || (y_prev > h)) {
			return false;
		}
		return true;
	}

	@Override
	public void paint(Graphics g) {
		if(this.getHP() > 0) {
			if(m_color != null) {
				g.setColor(m_color);
			}
			g.fillRect(this.m_x, this.m_y, this.m_side, this.m_side);
		}

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
		return this.m_side;
	}

	@Override
	public int getWidth() {
		return this.m_side;
	}

	//Return the number of Marios Health Points
	public int getHP() {
		return this.HP;
	}
	
	//Set the number of Marios Health Point to hp
	public void setHP(int hp) {
		this.HP = hp;
	}

}
