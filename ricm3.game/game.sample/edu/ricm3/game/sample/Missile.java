package edu.ricm3.game.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

public class Missile {

	Model m_model;
	Color m_color;
	int m_x, m_y, m_side;
	long m_lifetime, m_lastMove;
	//long m_death;
	char m_direction; //N || E || S || W
	boolean m_touch, m_display;
	
	//Square with a side of length "side"
	//x, y coordinates of the top left corner
	Missile(Model m, Hero hero) {
		m_model = m;
		m_direction = hero.m_direction;
		m_side = 10;
		m_color = Color.orange;
		m_lifetime = 0;
		m_touch = false;
		m_display = true;
		//m_death = 1000L;
		if(m_direction == 'N') {
			this.m_x = hero.getx();
			this.m_y = hero.m_y - m_side;
		}
		else if(m_direction == 'W') {
			this.m_x = hero.getx() - m_side;
			this.m_y = hero.gety();
		}
		else if(m_direction == 'E') {
			this.m_x = hero.getx() + hero.getWidth();
			this.m_y = hero.gety();
		}
		else if(m_direction == 'S') {
			this.m_x = hero.getx() + hero.getWidth() - m_side;
			this.m_y = hero.gety() + hero.getHeight();
		}
	}
	
	public void setColor(Color c) {
		this.m_color = c;
	}
	
	public void location(int x, int y) {
		this.m_x = x; 
		this.m_y = y;
	}
	
	public int getX() {
		return this.m_x;
	}
	
	public int getY() {
		return this.m_y;
	}
	
	public int getSide() {
		return this.m_side;
	}
	
	public void paint(Graphics g) {
		if(m_display) {
			if(this.m_color != null) {
				g.setColor(m_color);
			}
			g.fillRect(m_x, m_y, m_side, m_side);
		}
	}
	
	 boolean collision(int x, int y, int w, int h) {
		
		//We check if there isn't any collision
		if((this.m_x + m_side < x) || (this.m_x > w) || (this.m_y + m_side < y) || (this.m_y > h)) {
			return false;
		}
		return true;
	}
	
	void step(long now) {
		long elapsed = now - m_lastMove;
		//The missile has a time to live
		//When this time is over, it should be destroyed
		/*
		if(elapsed > m_death) {
			this.m_display = false;
			return;
		}
		*/
		if(elapsed > 50L) {
			m_lastMove = now;
			//The missile goes on the same direction as the hero is moving
			if(m_direction == 'N') {
				m_y--;
			}
			else if(m_direction == 'E') {
				m_x++;
			}
			else if(m_direction == 'S') {
				m_y++;
			}
			else { //m_direciton == 'W'
				m_x--;
			}
		}
		
		//Check if the missile has touched something
		//If the missile encounters a wall, it shouldn't be displayed and will be destroyed
		//If the missile encounters a ghost, the ghost loses health points
		//And the missile is destroyed too
		Iterator<Plateforme> iter_plat = m_model.plateformes();
		while(iter_plat.hasNext()) {
			Plateforme p = iter_plat.next();
			if(collision(p.getx(), p.gety(), p.get_top_right(), p.get_bottom_left())) {
				this.m_display = false;
			}
		}
		
		Iterator<Ghost> iter_ghosts = m_model.ghosts();
		while(iter_ghosts.hasNext() && !m_touch) {
			Ghost g = iter_ghosts.next();
			if(collision(g.getx(), g.gety(), g.getx() + g.getWidth(), g.gety() + g.getHeight())) {
				this.m_touch = true;
				this.m_display = false;
				g.setHP(g.getHP() - 1);
				System.out.println("Ghost's HP : "+g.getHP()+"");
			}
		}
		
		
	}
}
