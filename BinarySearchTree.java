/** 
 * File: BinarySearchTree.java
 * Package: project3
 * Name: Jake Caruana
 * Email: jtc94964@uga.edu
 * Class: CSCI2720
 * Date Created: 10/22/2024
 * Description: Class to create BST and methods to interact with it
 */
package project3;

public class BinarySearchTree<T extends Comparable<T>> {
	// Instantiate a new root node
	private NodeType<T> root;

	// Constructor to create a new tree
	public BinarySearchTree() {
		root = null;
	}

	// Insert a new node
	public void insert(T key) {
		if (search(key)) {
			System.out.println("Item already exists in the tree.");
		} else {
			root = insertRec(root, key);
		}
	}

	// Recursive method to insert the new node in the correct spot
	private NodeType<T> insertRec(NodeType<T> node, T key) {
		if (node == null) { // If the tree is empty
			node = new NodeType<>(key);
			return node;
		}
		if (key.compareTo(node.info) < 0) { // If value is less, go to left subtree, else, go to right subtree
			node.left = insertRec(node.left, key);
		} else if (key.compareTo(node.info) > 0) {
			node.right = insertRec(node.right, key);
		}
		return node;  // Duplicate keys are not allowed
	}

	// Delete a node
	public void delete(T key) {
		if (!search(key)) { // if item is not present in the tree
			System.out.println("Item is not present in the tree.");
		} else {
			root = deleteRec(root, key);
		}
	}

	// Recursive method to delete nodes
	private NodeType<T> deleteRec(NodeType<T> node, T key) {
		if (node == null) return node;

		if (key.compareTo(node.info) < 0) { // If value is less, go to left subtree, else, go to right subtree
			node.left = deleteRec(node.left, key);
		} else if (key.compareTo(node.info) > 0) {
			node.right = deleteRec(node.right, key);
		} else {
			// Node with only one child or no child
			if (node.left == null) return node.right;
			if (node.right == null) return node.left;

			// Node with two children: get the inorder successor (smallest in the right subtree)
			node.info = minValue(node.right);
			node.right = deleteRec(node.right, node.info);
		}

		return node;
	}

	// method to find the minimum value of the node
	private T minValue(NodeType<T> node) {
		T minValue = node.info;
		while (node.left != null) {
			minValue = node.left.info;
			node = node.left;
		}
		return minValue;
	}

	// Search for a key in the tree
	public boolean search(T key) {
		return searchRec(root, key);
	}

	// Recursive method to search through the list recursively
	private boolean searchRec(NodeType<T> node, T key) {
		if (node == null) return false;
		if (key.compareTo(node.info) == 0) return true;
		return key.compareTo(node.info) < 0 ? searchRec(node.left, key): searchRec(node.right, key);
	}

	// In-order traversal
	public void inOrder() {
		inOrderRec(root);
	}

	// Recursive method to traverse the tree inorder
	private void inOrderRec(NodeType<T> node) {
		if (node != null) {
			inOrderRec(node.left);
			System.out.print(node.info + " ");
			inOrderRec(node.right);
		}
	}

	// Function to count the number of leaf nodes
	public int getNumLeafNodes() {
		return countLeaves(root);
	}

	// Recursive method to count leaf nodes
	private int countLeaves(NodeType<T> node) {
		if (node == null) return 0;
		if (node.left == null && node.right == null) return 1;
		return countLeaves(node.left) + countLeaves(node.right);
	}

	// Print nodes with only one child
	public void getSingleParent() {
		printSingleParent(root);
		System.out.println();
	}

	// Recursive method to print single parents
	private void printSingleParent(NodeType<T> node) {
		// Print the node if the it only has one child
		if (node == null) return;
		if (node.left != null && node.right == null || node.left == null && node.right != null) {
			System.out.print(node.info + " ");
		}
		printSingleParent(node.left);
		printSingleParent(node.right);
	}

	// Get the level of a node in the tree
	private int getLevel(NodeType<T> node, T key, int level) {
		if (node == null) return 0;
		if (node.info.equals(key)) return level;

		int downLevel = getLevel(node.left, key, level + 1);
		if (downLevel != 0) return downLevel;

		return getLevel(node.right, key, level + 1);
	}

	// Helper method to print all cousins at a given level, excluding siblings
	private void printCousinsAtLevel(NodeType<T> node, NodeType<T> parent, int level, T key) {
		if (node == null || level < 2) return;

		// If we are at the parent level
		if (level == 2) {
			if (node.left != null && node.left.info.equals(key) || node.right != null && node.right.info.equals(key)) {
				// Do not print siblings
				return;
			}
			if (node.left != null) System.out.print(node.left.info + " ");
			if (node.right != null) System.out.print(node.right.info + " ");
		} else {
			// Recurse for the next level
			printCousinsAtLevel(node.left, node, level - 1, key);
			printCousinsAtLevel(node.right, node, level - 1, key);
		}
	}

	// Get cousins of a given node
	public void getCousins(T key) {
		int level = getLevel(root, key, 1);
		if (level == 1 || root == null) {
			System.out.println(key + " has no cousins.");
			return;
		}
		System.out.print(key + " cousins: ");
		printCousinsAtLevel(root, null, level, key);
		System.out.println();
	}
}
