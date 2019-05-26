package warmup.layout;

import java.util.ArrayList;
import java.util.List;

import warmup.tree.Node;

/**
 * This is a component within a tree of containers and components.
 * Each component has a surface, defined by its bounds, within
 * the surface of its parent. 
 *
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Component {

  protected Container m_parent;
  protected int m_x, m_y, m_width, m_height;
  protected List<Component> m_children;

  public Component() {
	  this.m_x = 0;
	  this.m_y = 0;
	  this.m_width = 0;
	  this.m_height = 0;
	  this.m_parent = null;
	  this.m_children = new ArrayList();
  }

  public Component(Container parent) {
	  this.m_x = 0;
	  this.m_y = 0;
	  this.m_width = 0;
	  this.m_height = 0;
	  this.m_parent = parent;
	  this.m_parent.m_children.add(this);
	  this.m_children = new ArrayList();
  }

  public String toString() {
    return getClass().getSimpleName() + "(" + m_x + "," + m_y + "," + m_width + "," + m_height + ")";
  }

  /**
   * Convert a global location to a location that is local to this component
   * 
   * @param l
   */
  public void toLocal(Location l) {
	  //Initialization of the Current component
	  Component c;
	  c = this;
	  //Initialization of the sum of the parent distance to their heir
	  int x, y;
	  x = 0;
	  y = 0;
	  while(c.parent() != null) {
		  x = x + c.x();
		  y = y + c.y();
		  c = c.parent();
	  }
	  //Difference between the global coordinates and the local coordinates
	 l.set(l.x() - x, l.y() - y);
  }

  /**
   * Convert a local location to a location that is global, that is, in the
   * coordinates of the root of the layout tree.
   * 
   * @param l
   */
  public void toGlobal(Location l) {
	  //Initialization of a current component
	  Component c;
	  c = this;
	  //Initialisation of the coordinates/distance from the root to the local location
	  int x, y;
	  x = 0;
	  y = 0;
	  while(c.parent() != null) {
		  x = x + c.x();
		  y = y + c.y();
		  c = c.parent();
	  }
	  //Translation from the local location to the global location
	  l.translate(x, y);
  }

  /**
   * @return the parent container of this component
   */
  public Component parent() {
    return m_parent;
  }

  /**
   * removes this component from its parent container.
   * @throws IllegalStateException if this remove is not allowed.
   */
  public void remove() {
	  if(this.m_parent == null) {
		  throw new IllegalStateException();
	  }
	  this.m_parent = null;
  }

  /**
   * @return the location of this component in the coordinate system of its
   *         parent.
   */
  public Location location() {
    return new Location(m_x, m_y);
  }

  /**
   * Set the location of this component to the given location, location in the
   * coordinate system of its parent.
   * 
   * @param loc
   */
  public void location(Location loc) {
    m_x = loc.x();
    m_y = loc.y();
  }

  /**
   * Set the location of this component to the given location, location in the
   * coordinate system of its parent.
   * 
   * @param loc
   */
  public void setLocation(int x, int y) {
    m_x = x;
    m_y = y;
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

  /**
   * Sets the dimension of this component
   */
  public void setDimension(int w, int h) {
    m_width = w;
    m_height = h;
  }

  /**
   * Sets the bounds of this component.
   * The given location is expressed in the
   * coordinate system of its parent.
   */
  public void setBounds(int x, int y, int w, int h) {
    m_x = x;
    m_y = y;
    m_width = w;
    m_height = h;
  }

  /**
   * Tells if the given location is within this component. 
   * Nota bene: 
   *    the location is given in the parent's coordinates.
   * @param x
   * @param y
   * @return
   */
  public boolean inside(int x, int y) {
	  if(this.x() <= x && x < this.x() + this.m_width) {
		  if(this.y() <= y && y < this.y() + this.m_height) {
			  return true;
		  }
	  }
	  return false;
  }

  /**
   * Select the component, on top, at the given location.
   * Nota Bene: the location is given in local coordinates.
   * @param x
   * @param y
   * @return this component only if it contains the given location, 
   *         given in local coordinates
   */
  public Component select(int x, int y) {
	  if(inside(x,y)) {
		  return this;
	  }
	  return null;
  }

}
