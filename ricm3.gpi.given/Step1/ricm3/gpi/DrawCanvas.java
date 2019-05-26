package ricm3.gpi;

import java.util.ArrayList;
import java.util.List;

import ricm3.gpi.gui.Color;
import ricm3.gpi.gui.Graphics;
import ricm3.gpi.gui.KeyListener;
import ricm3.gpi.gui.MouseListener;
import ricm3.gpi.gui.layout.Container;
import ricm3.gpi.gui.widgets.Canvas;
import warmup.layout.Location;

/**
 * A canvas to draw lines freely.
 * 
 * To draw a line, two possibilities.
 * 
 *  1) Press down the left button and move the mouse
 *     The drawing ends when the left button is released.
 *     
 *  2) Press the space-bar key on the keyboard toggles 
 *     the drawing mode. Moving the mouse draws if 
 *     drawing is on.
 *     
 * To clear the canvas, press the key 'c' on your keyboard.
 * 
 * @author Pr. Olivier Gruber  (olivier dot gruber at acm dot org)
 */
public class DrawCanvas extends Canvas implements MouseListener, KeyListener {

	protected boolean m_down, k_down, clear;
	protected Location Start, End;
	protected List<Location> ArrayOfLines; //Cette liste stocke les locations
	int cptLines;
	Graphics g_Drawcanvas;

	public DrawCanvas(Container parent) {
		super(parent);
		this.m_ml = this;
		this.m_kl = this;
		this.m_down = false;
		this.k_down = false;
		this.clear = false;
		this.cptLines = 0;
		this.ArrayOfLines = new ArrayList<Location>();

	}

	@Override
	public void keyPressed(char k, int code) {
		System.out.println("KeyPressed");
		//On vérifie que la touche 'c' est pressée
		if(k == 'c') {
			//System.out.println("C !");
			//Booléen pour choisir la couleur d'affichage
			//On affiche à nouveau les lignes de la couleur du fond pour les effacer
			this.clear = true;
			this.repaint();
			//On vide les variables contenant les coordonnées des lignes tracées
			this.Start = null;
			this.End = null;
			int size = this.ArrayOfLines.size();
			for(int i = 0; i < size; i++) {
				this.ArrayOfLines.remove(0);
			} 
		}
		/*
		 * Ce code fonctionne et inscrit une coordonnée dans la liste de Locations
		else if(code == 32) {
			this.k_down = true;
			this.Start = new Location(this.x(), this.y());
			this.ArrayOfLines.add(this.Start);
		}
		*/

	}

	@Override
	public void keyReleased(char k, int code) {
		//Test pour sélectionner la touche espace
		//Ne fonctionne pas, n'est jamais appelé
		if((code == 32) && (this.k_down)) {
			this.k_down = false;
			this.End = new Location(this.x(), this.y());
			this.ArrayOfLines.add(this.End);
			this.repaint();
		}
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(int x, int y) {
		System.out.println("DC mouseMoved");
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(int x, int y, int buttons) {
		//m_down is a boolean to know if the mouse is pressed or not
		this.m_down = true;
		//Initialization of the current Location of the mouse
		this.Start = new Location(x, y);
		//We keep this location in memory into a list
		this.ArrayOfLines.add(this.Start);
		System.out.println("DC mousePressed -> this.Start : ("+this.Start.x()+", "+this.Start.y()+")");

	}

	@Override
	public void mouseReleased(int x, int y, int buttons) {
		if(this.m_down) {
			//The mouse was pressed, we have to keep in memory the mouse's position
			System.out.println("DC mouseReleased");
			this.m_down = false;
			//We get the current location of the mouse
			this.End = new Location(x, y);
			//We keep this location into a list
			this.ArrayOfLines.add(this.End);
			System.out.println("DC mouseReleased -> this.End : ("+this.End.x()+", "+this.End.y()+")");
			this.repaint();
		}

	}

	@Override
	public void mouseEntered(int x, int y) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(this.Start != null && this.End != null) {
			System.out.println("("+Start.x()+" , "+Start.y()+")  ("+this.End.x()+", "+this.End.y()+")");
			System.out.println("Painting !");
			//Si on est en mode clear, on recouvre les lignes de la couleur du fond de l'interface graphique
			if(this.clear) {
				g.setColor(this.m_bgColor);
				this.clear = false;
			}
			//Sinon, on affiche les lignes en noir
			else {
				g.setColor(Color.black);
			}
			//g.drawLine(this.Start.x(), this.Start.y(), this.End.x(), this.End.y());
			int i, sizeList;
			sizeList = this.ArrayOfLines.size();
			for(i = 0; i < sizeList - 1; i = i + 2) {
				g.drawLine(ArrayOfLines.get(i).x(), ArrayOfLines.get(i).y(), ArrayOfLines.get(i+1).x(), ArrayOfLines.get(i+1).y());
			}
		}
	}

}
