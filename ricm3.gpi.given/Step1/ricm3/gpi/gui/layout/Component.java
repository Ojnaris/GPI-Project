package ricm3.gpi.gui.layout;

import ricm3.gpi.gui.Color;
import ricm3.gpi.gui.Graphics;
import ricm3.gpi.gui.Window;

/**
 * This is a component within a tree of containers and components.
 * Each component has a surface, defined by its bounds, within
 * the surface of its parent. 
 * 
 * A component may be ask to paint itself, at any moment, 
 * by invoking the method: paint(Graphics g) 
 * 
 * MouseListener:
 *   When the mouse enters, leaves, is moved, or clicked within 
 *   the surface of a component, the mouse listener of this 
 *   component is told about these events, if there is a mouse
 *   listener associated with this component at the time of the event.
 * 
 * KeyListener:
 *   If the mouse is currently within this component and a key
 *   is pressed or released on the keyboard, the key listener of this 
 *   component is told about these events, if there is a key
 *   listener associated with this component at the time of the event.
 *
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Component {

  protected Container m_parent;
  protected int m_x, m_y, m_width, m_height; // bounds for this component.
  protected ricm3.gpi.gui.KeyListener m_kl; // key listener for this component
  protected ricm3.gpi.gui.MouseListener m_ml; // mouse listener for this component
  protected Color m_bgColor; // background color for this component
  protected Color m_fgColor; // foreground color for this component
  
  protected Component() {
	  this.m_x = 0;
	  this.m_y = 0;
	  this.m_width = 0;
	  this.m_height = 0;
	  this.m_kl = null;
	  this.m_ml = null;
	  this.m_bgColor = Color.white;
	  this.m_fgColor = Color.black;
	  this.m_parent = null;
  }

  public Component(Container parent) {
	  this.m_x = 0;
	  this.m_y = 0;
	  this.m_width = 0;
	  this.m_height = 0;
	  this.m_kl = null;
	  this.m_ml = null;
	  this.m_bgColor = Color.white;
	  this.m_fgColor = Color.cyan;
	  this.m_parent = parent;
	  this.m_parent.m_children.add(this);
  }

  public String toString() {
    return getClass().getSimpleName() + "(" + m_x + "," + m_y + "," + m_width + "," + m_height + "," + ")";
  }

  public void setForeground(Color c) {
    m_fgColor = c;
  }

  public void setBackground(Color c) {
    m_bgColor = c;
  }

  /**
   * Set the key listener for this component.
   * @param kl is the key listener
   */
  public void setKeyListener(ricm3.gpi.gui.KeyListener kl) {
    m_kl = kl;
  }

  /**
   * Set the mouse listener for this component.
   * All callbacks with coordinates will be in local coordinates.
   * @param kl is the key listener
   */
  public void setMouseListener(ricm3.gpi.gui.MouseListener ml) {
    m_ml = ml;
  }

  /**
   * Convert a global location to a location that is local to this component
   * @param l
   */
  public void toLocal(Location l) {
	  //Initialization of the current Container
	  Container cont;
	  cont = this.m_parent;
	  //Initialization of the sum of the parent distance to their heir
	  int x, y;
	  x = 0;
	  y = 0;
	  while(cont != null) {
		  x = x + cont.x();
		  y = y + cont.y();
		  cont = cont.parent();
	  }
	  //Difference between global coordinates and the local coordinates
	  l.set(l.x() - x, l.y() -y);
  }

  /**
   * Convert a local location to a location that is global, that is, in the
   * coordinates of the root of the layout tree.
   * @param l
   */
  public void toGlobal(Location l) {
	  /*
	  //Initialization of the current Container
	  Container cont;
	  cont = this.m_parent;
	  //Initialization of the sum of the parent distance to their heir
	  int x = 0, y = 0;
	  while(cont != null) {
		  x = x + cont.x();
		  y = y + cont.y();
		  cont = cont.parent();
	  }
	  //Translation from the local location to the global location
	  l.translate(x, y);
	  */
	  l.translate(this.x(), this.y());
	  if(this.m_parent != null) {
		  this.m_parent.toGlobal(l);
	  }
  }

  public Container parent() {
    return m_parent;
  }

  public void remove() {
	  if(this.m_parent == null) {
		  throw new IllegalStateException();
	  }
	  this.m_parent = null;
  }

  public Location location() {
    return new Location(m_x, m_y);
  }

  public void location(Location loc) {
    m_x = loc.x();
    m_y = loc.y();
  }

  public int x() {
    return m_x;
  }

  public int y() {
    return m_y;
  }

  public int width() {
    return m_width;
  }

  public int height() {
    return m_height;
  }

  public void setLocation(int x, int y) {
    m_x = x;
    m_y = y;
  }

  public Dimension dimension() {
    return new Dimension(m_width, m_height);
  }

  public void setDimension(Dimension dim) {
    m_width = dim.width();
    m_height = dim.height();
  }

  public void setDimension(int w, int h) {
    m_width = w;
    m_height = h;
  }

  public void setBounds(Location loc, Dimension dim) {
    m_x = loc.x();
    m_y = loc.y();
    m_width = dim.width();
    m_height = dim.height();
  }

  public void setBounds(int x, int y, int w, int h) {
    m_x = x;
    m_y = y;
    m_width = w;
    m_height = h;
  }

  /**
   * Tells if the given location is within this component.
   * The location is given in the parent's coordinates.
   * @param x
   * @param y
   * @return
   */
  public boolean inside(int x, int y) {
	  if(x < 0 || y < 0) {
		  return false;
	  }
	  if(this.x() <= x && x < this.x() + this.width()) {
		  if(this.y() <= y && y < this.y() + this.height()) {
			  return true;
		  }
	  }
	  return false;
  }

  /**
   * Tells if the given location is within this component.
   * The location is given in local coordinates.
   * @param x
   * @param y
   * @return
   */
  public Component select(int x, int y) {
	  if(x < 0 || y < 0) {
		  return null;
	  }
	  if(inside(x, y)) {
		  return this;
	  }
	  return null;
  }

  /**
   * Paint this component using the given graphics.
   * The graphics work in local coordinates and
   * all drawing/fill operations are clipped to 
   * this component.
   * @param g
   */
  public void paint(Graphics g) {
	  Location loc = new Location(0,0);
	  toGlobal(loc);
	  g.setColor(m_bgColor);
	  g.fillRect(loc.x(), loc.y(), m_width, m_height);
  }

  /**
   * Ask a repaint of this component.
   * The result will be that at some later time, 
   * the paint method of this component will be called.
   */
  public void repaint() {
    Window win = Window.getWindow();
    Location l = new Location(0, 0);
    toGlobal(l);
    win.repaint(l.x(), l.y(), m_width, m_height);
  }

}
