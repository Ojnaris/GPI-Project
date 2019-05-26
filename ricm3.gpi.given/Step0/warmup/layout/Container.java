package warmup.layout;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a container within a tree of containers and components.
 * A container is a component that has children components.
 * The children are managed as an ordered set.
 * Children components are painted on top of their parent container.
 * 
 * @author Pr. Olivier Gruber (olivier dot gruber at acm dot org)
 */

public class Container extends Component {
	
	//Creation of a child list so as to check if the given coordinates are within this
	protected List<Component> m_children;

  Container() {
    super();
    this.m_children = new ArrayList();
  }

  public Container(Container parent) {
	super();
    this.m_parent = parent;
    this.m_parent.m_children.add(this);
    this.m_children = new ArrayList();
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
	  //Check if it contains the coordinates
	  if(! this.inside(x,y)) {
		  return null;
	  }
	  //Initialization of a variable containing the size of the child list
	  int size;
	  size = this.m_children.size();
	  
	  //Travel of the child list
	  while(size != 0) {
		  //Check if the child contains the coordinates
		  if(this.m_children.get(size - 1).inside(x, y)) {
			  return this.childrenAt(size - 1).select(x, y);
		  }
		  size--;
	  }
	  return this;
  }

}
