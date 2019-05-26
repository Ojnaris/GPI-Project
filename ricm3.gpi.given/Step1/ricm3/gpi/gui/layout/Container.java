package ricm3.gpi.gui.layout;

import java.util.ArrayList;
import java.util.List;

import ricm3.gpi.gui.Graphics;

/**
 * This is a container within a tree of containers and components.
 * A container is a component that has children components.
 * Children components are painted on top of their parent container.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */
public class Container extends Component {
	
	//Creation of a child list so as to check if the given coordinates are within it
	protected List<Component> m_children;
	
  Container() {
	  super();
	  this.m_children = new ArrayList<Component>();
  }

  public Container(Container parent) {
    super(parent);
    //this.m_parent.m_children.add(this); 
    this.m_children = new ArrayList<Component>();
  }

  /**
   * @return the number of components that are 
   *         children to this container
   */
  public int childrenCount() {
	  return this.m_children.size();
  }

  /**
   * @return the component indexed by the given
   *         index.
   */
  public Component childrenAt(int i) {
    return this.m_children.get(i);
  }


  /**
   * Select the component, on top, at the given location.
   * The location is given in local coordinates.
   * Reminder: children are above their parent.
   * @param x
   * @param y
   * @return this selected component 
   */
  public Component select(int x, int y) {
	  
	  if(x < 0 || y < 0) {
		  return null;
	  }
	  if(! inside(x, y)) {
		  return null;
	  }
	  
      //Initialization of a variable to count the number of children
	  int nbChild = this.childrenCount();
	  int cptChild = 0;
	  
	  //Course of the children list
	  //Check if the coordinates are inside each child
	  while(cptChild < nbChild && (! this.childrenAt(cptChild).inside(x, y))) {
		  cptChild++;
	  }
	  //Case of the children within the coordinates inside it
	  if((cptChild < nbChild) && (this.childrenAt(cptChild) instanceof Container)) {
		  //We call again the method select so as to search the coordinates into this child
		  return this.childrenAt(cptChild).select(x, y);
	  }
	  else if((cptChild < nbChild) && (this.childrenAt(cptChild) instanceof Component)) {
		  return this.childrenAt(cptChild);
	  }
	  //The coordinates are inside the Component so we could return it
	  return this;
  }

  /**
   * Painting a container is a two-step process
   * in order to paint children components above.
   *    - First, the container paints itself.
   *    - Second, the container paints its children
   */
  public void paint(Graphics g) {
	  //The container paints itself
	  super.paint(g);
	  
	  //The container paints its children
	  //Initialization of the temporary graphic
	  Graphics tmp_graph = g;
	  Component child;
	  
	  //Travel of the child list
	  int nbChild = 0;
	  while(nbChild != this.childrenCount()) {
		  child = this.childrenAt(nbChild);
		  /*
		  tmp_graph.create(child.x(), child.y(), child.width(), child.height());
		  child.paint(tmp_graph);
		  tmp_graph.dispose();
		  */
		  child.paint(g);
		  nbChild++;
	  }
  }
  
}
