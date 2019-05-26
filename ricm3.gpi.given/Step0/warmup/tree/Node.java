package warmup.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a node of a tree.
 * Each node has a name.
 * Therefore, each node is reachable through a path.
 * Each node may be attached an object.
 * 
 * @author Pr. Olivier Gruber  (olivier dot gruber at acm dot org)
 */

public class Node {
  Node m_parent;
  String m_name;
  List<Node> m_children;
  Object m_attachment;

  /**
   * @param name
   * @throws IllegalArgumentException if the name contains 
   *         the character Tree.pathSeparator
   */
  protected Node(String name) {
	  if (name.contains(Tree.pathSeparatorString)) {
		  throw new IllegalArgumentException();
	  }
	  this.m_name = name;
	  this.m_parent = null;
	  this.m_children = new ArrayList();
	  this.m_attachment = null;
  }

  /**
   * @param name
   * @throws IllegalArgumentException if the name contains 
   *         the character Tree.pathSeparator
   */
  public Node(Node parent, String name) {
	  if (name.contains(Tree.pathSeparatorString)) {
		  throw new IllegalArgumentException();
	  }
	  if(parent.child(name) != null) {
		  throw new IllegalStateException();
	  }
	  this.m_parent = parent;
	  this.m_parent.m_children.add(this);
	  this.m_name = name;
	  this.m_children = new ArrayList();
	  this.m_attachment = null;
  }

  public String toString() {
    if (m_name == null)
      return "";
    return m_name;
  }

  public Node parent() {
    return m_parent;
  }

  public void attach(Object e) {
    m_attachment = e;
  }

  public Object attachment() {
    return m_attachment;
  }

  public void name(String name) {
    m_name = name;
  }

  public String name() {
    return m_name;
  }

  public String path() {
	  /*
	  if(this.m_parent == null) {
		  return this.name();
	  }
	  return this.m_parent.path() + this.name();
	  */
	  Node n;
	  String p;
	  p = this.m_name;
	  n = this.m_parent;
	  while(n != null) {
		  p = n.name() + Tree.pathSeparator + p;
		  n = n.parent();
	  }
	  return p;
  }

  public void remove() {
	  this.m_parent = null;
  }

  public Iterator<Node> children() {
	  return this.m_children.iterator();
  }
  
  public Node child(String name) {
	  Iterator iter = this.children();
	  while(iter.hasNext()) {
		  Node n;
		  n = (Node) iter.next();
		  if(n.name().equals(name)) {
			  return n;
		  }
	  }
	  return null;
  }

}
