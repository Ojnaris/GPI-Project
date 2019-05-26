package edu.ricm3.game.sample;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Random;

public class Ghost implements Entity {
	
	Model m_model;
	private int HP;
	int m_x, m_y;
	int m_side;
	Color m_color;
	long m_lastMove;
	boolean m_move;
	boolean isdead;
	boolean m_isInside;
	
	
	Ghost(Model m, int x, int y) {
		this.m_model = m;
		this.m_x = x;
		this.m_y = y;
		this.m_side = 40;
		this.m_color = Color.blue;
		this.HP = 5;
		this.m_move = true;
		this.isdead = false;
		this.m_isInside = false;
	}

	@Override
	public void location(int x, int y) {
		this.m_x = x;
		this.m_y = y;

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
		return m_side;
	}

	@Override
	public int getWidth() {
		return m_side;
	}

	@Override
	public void move(char d, LinkedList<Plateforme> plateformes_list) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean collision(int x, int y, int w, int h) {
		if((this.m_x + m_side < x) || (this.m_x > w) || (this.m_y + m_side < y) || (this.m_y > h)) {
			return false;
		}
		return true;
	}

	@Override
	public void paint(Graphics g) {
		if(! isdead) {
			if(m_color != null) {
				g.setColor(m_color);
			}
			g.fillRect(m_x, m_y, m_side, m_side);
		}
	}
	
	void step(long now) {
	    long elapsed = now - m_lastMove;
	    int random = new Random().nextInt(10); //c
	    if (elapsed > 50L) { //c
	    	if((elapsed %2 == 0) && (random > 8)) {
	    		if(m_move) {
	    			m_move = false;
	    		}
	    		else {
	    			m_move = true;
	    		}
	    	}
	      m_lastMove = now;
	      if (m_move) {
	        m_x += 2;
	      } else {
	        m_x -= 2;
	      }
	    }
	    if(m_x < 5) {
	    	m_move = true;
	    }
	    else if(m_x + this.m_side > 1020) {
	    	m_move = false;
	    }
	    if(this.HP == 0) {
	    	this.isdead = true;
	    }
	    
	    //Check if the Ghost has hurt Mario
	    Hero hero = m_model.m_hero;
	    if(collision(hero.getx(), hero.gety(), hero.getx() + hero.getWidth(), hero.gety() + hero.getHeight())) {
	    	//The boolean m_isInside indicates if the Ghost is crossing/moving through Mario
	    	//And if Mario has already lost a lifepoint
	    	//Mario is loosing a lifepoint if the ghost is crossing him :
	    	//When the ghost get out of Mario's shape, Mario has lost only one HP
	    	if(!m_isInside) {
		    	hero.setHP(hero.getHP() - 1);
		    	System.out.println("Marios HP : "+hero.getHP()+"");
		    	m_isInside = true;
	    	}
	    }
	    else {
	    	//Here, it's when the ghost doesn't move through Mario anymore
	    	m_isInside = false;
	    }
	}

	//Return the number of the Ghosts Health Points
	public int getHP() {
		return HP;
	}
	
	//Set the number of the Ghosts Health Point to hp
	public void setHP(int hp) {
		this.HP = hp;
	}
	
	//return if the Ghost is still alive
	public boolean isdead() {
		return this.isdead;
	}
}
