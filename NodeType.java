/** 
 * File: NodeType.java
 * Package: project3
 * Name: Jake Caruana
 * Email: jtc94964@uga.edu
 * Class: CSCI2720
 * Date Created: 10/22/2024
 * Description: Class to create and assign pointers to nodes in the BST
 */

package project3;

public class NodeType<T extends Comparable<T>> {
	// data members for pointer
	public T info;
	public NodeType <T> left;
	public NodeType <T> right;

	// constructor for a node that assigns both pointers to null
	public NodeType (T item) {
		this.info = item;
		this.left = null;
		this.right = null;
	}
}
