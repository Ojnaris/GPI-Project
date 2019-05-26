package ricm3.gpi.gui.layout;

import ricm3.gpi.gui.KeyListener;
import ricm3.gpi.gui.MouseListener;
import ricm3.gpi.gui.WindowListener;
import ricm3.gpi.gui.Window;

/**
 * This is the root container, it is the pivot between the underlying window
 * and the tree of widgets (components and containers).
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Root extends Container implements MouseListener, KeyListener, WindowListener {

	Window m_window;
	Component c;

	public Root(Window w) {
		super();
		m_window = w;
		m_width = w.getWidth();
		m_height = w.getHeight();
		w.setKeyListener(this);
		w.setMouseListener(this);
		w.setWindowListener(this);
		w.repaint(0, 0, m_width, m_height);
		this.m_ml = this;
		this.m_kl = this;
	}

	/**
	 * @return the currently selected component,
	 *         that is, the component on top and
	 *         under the last know mouse position
	 */
	public Component selected() {
		if(c != null) {
			//return super.select(100, 100);
			//return super.select(25, 25);
			return super.select(c.x(), c.y());
		}
		return null;
	}

	@Override
	public void resized(int width, int height) {
		m_width = width;
		m_height = height;
	}

	/**
	 * A key is pressed down on the keyboard.
	 * That event must be propagated to the correct component,
	 * the one currently selected, that is, the one that was
	 * selected by the last mouse move.
	 */
	@Override
	public void keyPressed(char k, int code) {
		Component selected = this.selected();
		if(selected != null) {
			//if(! (this.selected() instanceof ricm3.gpi.DrawCanvas)) {
			if(! (selected instanceof Component)) {
				System.out.println("Root keyPressed: '"+k+"' ("+code+")");
			}
			System.out.println("this.selected = "+selected.getClass()+"");
			selected.m_kl.keyPressed(k, code);
		}
		System.out.println("kPressed this.selected = "+selected.getClass()+"");
	}

	/**
	 * The key that pressed down has been released.
	 * That event must be propagated to the correct component,
	 * the one currently selected, that is, the one that was
	 * selected by the last mouse move.
	 */
	@Override
	public void keyReleased(char k, int code) {
		Component selected = this.selected();
		if(selected != null) {
			//if(! (this.selected() instanceof ricm3.gpi.DrawCanvas)) {
			if(! (selected instanceof Component)) {
				System.out.println("Root keyRelease: '"+k+"' ("+code+")");
			}
			else {
				System.out.println("kReleased this.selected = "+selected.getClass()+"");
				selected.m_kl.keyReleased(k, code);
			}
		}
	}

	/**
	 * The mouse has moved at the given location
	 * with the given button status.
	 * The location is given in local coordinates.
	 * You must determine the component selected
	 * by the given mouse position and forward
	 * a mouseMoved event to that component,
	 * using local coordinates.
	 * Nota Bene: 
	 *    be sure to handle properly the
	 *    notification mouseEnter and mouseExit
	 */
	@Override
	public void mouseMoved(int x, int y) {
		//Keep coordinates in memory
		c = select(x, y);
		
		Component selected = this.selected();
		//On vérifie que l'élément renvoyé par la fonction selected n'est pas nul
		//Et qu'il possède un mouseListener
		if((selected != null) && (selected.m_ml != null)) {
			//if(! (this.selected() instanceof ricm3.gpi.DrawCanvas)) {
			if(! (selected instanceof Component )) {
				System.out.println("Root mouseMoved");
				System.out.println("Root this.selected = "+selected.getClass()+"");
			}
			else {
				System.out.println("mMoved this.selected = "+selected.getClass()+"");
				selected.m_ml.mouseMoved(x, y);
			}
		}
	}

	/**
	 * One or more mouse buttons have been pressed,
	 * at the given location.
	 * The location is given in local coordinates.
	 * You must determine the component selected
	 * by the given mouse position and forward
	 * a mousePressed event to that component,
	 * using local coordinates.
	 * Nota Bene: 
	 *   be sure to handle properly the
	 *   notification mouseEnter and mouseExit
	 */
	@Override
	public void mousePressed(int x, int y, int buttons) {
		//Keep coordinates in memory
		c = select(x, y);
		
		Component selected = this.selected();
		if(selected != null) {
			//if(!(this.selected() instanceof ricm3.gpi.DrawCanvas)) {
			if(!(selected instanceof Component)) {
				System.out.println("Root mousePressed");
			}
			else {
				System.out.println("mPressed this.selected = "+selected.getClass()+"");
				selected.m_ml.mousePressed(x, y, buttons);
			}
		}
	}

	/**
	 * One or more mouse buttons have been released,
	 * at the given location.
	 * The location is given in local coordinates.
	 * You must determine the component selected
	 * by the given mouse position and forward
	 * a mouseReleased event to that component,
	 * using local coordinates.
	 * Nota Bene: 
	 *    be sure to handle properly the
	 *    notification mouseEnter and mouseExit
	 */
	@Override
	public void mouseReleased(int x, int y, int buttons) {
		//Keep coordinates in memory
		c = select(x, y);
		
		Component selected = this.selected();
		if(selected != null) {
			//Check the class of selected
			//If the class isn't a Component, we don't call the function of the mouseListener
			if(!(selected instanceof Component)) {
				System.out.println("Root mouseReleased");
			}
			else {
				System.out.println("mReleased this.selected = "+selected.getClass()+"");
				selected.m_ml.mouseReleased(x, y, buttons);
			}
		}
	}

	/**
	 * The mouse has entered the window this root component
	 * is displayed on, at the given location.
	 * The location is given in local coordinates.
	 * You must determine the component selected
	 * by the given mouse position and forward
	 * this mouseEntered event to that component.
	 */
	@Override
	public void mouseEntered(int x, int y) {
		//Keep coordinates in memory
		c = select(x, y);
		
		Component selected = this.selected();
		if(selected != null) {
			//Check the class of selected
			//If the class isn't a Component, we don't call the function of the mouseListener
			if(!(selected instanceof Component)) {
				System.out.println("Root mouseEntered");
			}
			else {
				System.out.println("mEntered this.selected = "+selected.getClass()+"");
				selected.m_ml.mouseEntered(x, y);
			}
		}
	}

	/**
	 * The mouse has left the window this root component
	 * is displayed on. You must propagate this mouseExit
	 * event to the currently selected component and 
	 * move to a state where there is no selected component.
	 */
	@Override
	public void mouseExited() {
		
		Component selected = this.selected();
		if(selected != null) {
			//Check the class of selected
			//If the class isn't a Component, we don't call the function of the mouseListener
			if(!(selected instanceof Component)) {
				System.out.println("Root mouseExited");
			}
			else {
				System.out.println("mExited this.selected = "+selected.getClass()+"");
				selected.m_ml.mouseExited();
			}
		}
	}

}
