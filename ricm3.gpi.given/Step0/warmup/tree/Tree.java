package warmup.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * This is a tree of named node.
 * 
 * @author Pr. Olivier Gruber  (olivier dot gruber at acm dot org)
 */
public class Tree extends Node {

  public static final char pathSeparator = '/';
  public static final String pathSeparatorString = "/";

  public Tree() {
    super("");
  }

  /**
   * Finds a node corresponding to a path in the tree.
   * If the path does not exists, throws NotFoundException
   *  
   * @param path
   *          of the searched node
   * @return the node named by the given path
   * @throws NotFoundException
   *           if the path does not exist
   */
  public Node find(String path) throws NotFoundException {
	  //On vérifie que la chaîne de caractères entrée en paramètres est bien un path
	  //C'est-à-dire qu'elle contient au moins un séparateur
	  if(! path.contains(pathSeparatorString)) {
		  throw new IllegalArgumentException();
	  }
	  //On sépare le path en tableau de String séparée par des '/'
	  String s[] = path.split(pathSeparatorString);
	  //On change ce tableau en ArrayList afin de retirer les espaces inutiles avant des séparateurs
	  ArrayList <String> path_list = new ArrayList<String>(Arrays.asList(s));
	  path_list.removeIf(str -> str.isEmpty());
	  
	  //Initialisations des variables courantes et indices afin de parcourir l'arraylist
	  Node noeud;
	  int i;
	  i = 0;
	  noeud = this;
	  
	  //On parcourt le path tant que le noeud d'arrivée est non nul
	  while(i < path_list.size() && noeud != null) {
		  noeud = noeud.child(path_list.get(i));
		  i++;
	  }
	  //Si le noeud d'arrivée est null, le path entré en paramètre n'est pas valide
	  if(noeud ==  null) {
		  throw new NotFoundException(path);
	  }
	  
	  //On retourne le noeud trouvé en suivant le path
	  return noeud;
  }

  /**
   * Make a path in the tree, leading either to a leaf or to a node.
   * @throws IllegalStateException
   *         if the path should be to a leaf but it already exists
   *         to a node, of if the path should be to a node but it already
   *         exists to a leaf. 
   */
  public Node makePath(String path, boolean isLeaf) {
	  /*
	  if(path.charAt(0) == pathSeparator) {
		  throw new IllegalStateException();
	  }
	  */
	  String s[] = path.split(pathSeparatorString);
	  //On change ce tableau en ArrayList afin de retirer les espaces inutiles avant des séparateurs
	  ArrayList <String> path_list = new ArrayList<String>(Arrays.asList(s));
	  path_list.removeIf(str -> str.isEmpty());
	  
	  //node_tmp et noeud2 sont des variables tampons, de passage
	  Node noeud, node_tmp, noeud2;
	  noeud = this;
	  node_tmp = this;
	  int i = 0;
	  //Deux cas :
	  //1 : Soit le path correspond à des noeuds déjà existants
	  //2 : Soit le path n'existe pas -> il faut donc créer les noeuds correspondants
	 
	  //Cas 2 :
	  while(i < path_list.size() && (noeud.child(path_list.get(i)) == null)) {
		  //On vérifie si le dernier noeud est une feuille
		  if(i == path_list.size() - 1 && isLeaf) {
			  Leaf newLeaf = new Leaf(node_tmp, path_list.get(i));
			  return newLeaf;
		  }
		  else if(i == path_list.size()) {
			  node_tmp = new Node(node_tmp, path_list.get(i));
			  return node_tmp;
		  }
		  //On créé les nouveaux noeuds
		  node_tmp = new Node(noeud, path_list.get(i));
		  noeud = node_tmp;
		  i++;
	  }
	  
	  //Cas 1:
	  //On parcourt le path
	  while(i < path_list.size() && noeud != null) {
		  //Variable pour garder en mémoire le dernier noeud non null
		  node_tmp = noeud;
		  noeud = noeud.child(path_list.get(i));
		  i++;
	  }
	  //On a parcouru tout le path : est-ce que le dernier noeud est une feuille ou un noeud ?
	  if(noeud == null && i < path_list.size()) {
		  if((isLeaf && node_tmp instanceof Node) || ((! isLeaf) && node_tmp instanceof Leaf)) {
			  throw new IllegalStateException();
		  }
		  return node_tmp;
	  }
	  
	  if(noeud != null && i <= path_list.size()) {
		  if((isLeaf && noeud instanceof Node) || ((! isLeaf) && noeud instanceof Leaf)) {
			  throw new IllegalStateException();
		  }
	  }
	  
	  noeud2 = node_tmp;
	  //Le noeud suivant n'est pas trouvé -> on le créé
	  while(noeud == null && i <= path_list.size()) {
		  if(i == path_list.size() && isLeaf) {
			  Leaf newLeaf = new Leaf(node_tmp, path_list.get(i - 1));
			  return newLeaf;
		  }
		  else if(i == path_list.size()) {
			  node_tmp = new Node(noeud2, path_list.get(i));
			  return node_tmp;
		  }
		  noeud2 = new Node(noeud2, path_list.get(i));
		  noeud = noeud2;
		  i++;
	  }
	  return noeud;
  }


  public String toString() {
    TreePrinter p = new TreePrinter(this);
    return p.toString();
  }

}
