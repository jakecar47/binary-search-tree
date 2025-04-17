/** 
 * File: BinarySearchTreeDriver.java
 * Package: project3
 * Name: Jake Caruana
 * Email: jtc94964@uga.edu
 * Class: CSCI2720
 * Date Created: 10/22/2024
 * Description: Class to run command line for the user to interact with the tree
 */
package project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class BinarySearchTreeDriver {

	public static void main(String[] args) {
		// Detect the list type from file contents
		try {
			// Create input file and scanners for the file and type
			Scanner scanner = new Scanner(System.in); // scanner to find file type
			File file = new File(args[0]);
			Scanner fileScanner = new Scanner(file);

			if (!fileScanner.hasNext()) {
				System.out.println("The file is empty.");
			} else {
				// Take user input on type and process the file based on type
				System.out.print("Enter list type (i - int, d - double, s - string): ");
				char type = scanner.next().charAt(0);
				switch (type) {
				case 'i':
					processIntFile(fileScanner, new BinarySearchTree<Integer>(), type);
					break;
				case 'd':
					processDoubleFile(fileScanner, new BinarySearchTree<Double>(), type);
					break;
				case 's':
					processStringFile(fileScanner, new BinarySearchTree<String>(), type);
					break;
				default:
					System.out.println("Invalid list type.");
					break;
				}
			}
			// Close scanners to save memory
			fileScanner.close();
			scanner.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found.");
		}
	}

	// Method to process integer files
	private static void processIntFile(Scanner fileScanner, BinarySearchTree<Integer> tree, char type) {
		// Read through file and insert items into the tree
		while (fileScanner.hasNext()) {
			try {
				int value = fileScanner.nextInt();
				tree.insert(value);
			} catch (Exception e) {
				System.out.println("Error reading value from file: " + e.getMessage());
			}
		}
		// After reading from the file, interact with the tree via commands
		interactWithTree(tree, type);
	}

	// Method to process double files
	private static void processDoubleFile(Scanner fileScanner, BinarySearchTree<Double> tree, char type) {
		// Read through file and insert items into the tree
		while (fileScanner.hasNext()) {
			try {
				double value = fileScanner.nextDouble();
				tree.insert(value);
			} catch (Exception e) {
				System.out.println("Error reading value from file: " + e.getMessage());
			}
		}
		// After reading from the file, interact with the tree via commands
		interactWithTree(tree, type);
	}

	// Method to process String files
	private static void processStringFile(Scanner fileScanner, BinarySearchTree<String> tree, char type) {
		// Read through file and insert items into the tree
		while (fileScanner.hasNext()) {
			try {
				String value = fileScanner.next();
				tree.insert(value);
			} catch (Exception e) {
				System.out.println("Error reading value from file: " + e.getMessage());
			}
		}
		// After reading from the file, interact with the tree via commands
		interactWithTree(tree, type);
	}

	// Generic method to handle user commands after the tree is populated
	private static <T extends Comparable<T>> void interactWithTree(BinarySearchTree<T> tree, char type) {
		// Create scanner to read command being entered
		Scanner scanner = new Scanner(System.in);
		String command;
		System.out.printf("%nCommands:%n(i) - Insert Item%n(d) - Delete Item%n(p) - Print Tree%n(s) - Search Item%n(l) - Count Leaf Nodes%n(sp) - Find Single Parents%n(c) - Find Cousins%n(q) - Quit program%n");
		do {
			System.out.print("\nEnter a command: ");
			command = scanner.next();

			switch (command) {
			case "i": // Insert item
				System.out.print("In-order: ");
				tree.inOrder();
				System.out.print("\nEnter a value to insert: ");
				try {
					T value = readValue(scanner, type); // Reading based on type
					tree.insert(value);
					System.out.print("In-order: ");
					tree.inOrder();
					System.out.println();
				} catch (ClassCastException e) {
					System.out.println("Invalid input for tree type.");
				}
				break;
			case "d": // Delete item
				System.out.print("In-order: ");
				tree.inOrder();
				System.out.print("\nEnter a value to delete: ");
				try {
					T value = readValue(scanner, type); // Reading based on type
					tree.delete(value);
					System.out.print("In-order: ");
					tree.inOrder();
					System.out.println();
				} catch (ClassCastException e) {
					System.out.println("Invalid input for tree type.");
				}
				break;
			case "p": // Print tree
				System.out.print("In-order: ");
				tree.inOrder();
				System.out.println();
				break;
			case "s": // Search for an item in the tree
				System.out.print("In-order: ");
				tree.inOrder();
				System.out.println();
				System.out.print("Enter a value to search: ");
				try {
					T value = readValue(scanner, type); // Reading based on type
					if (tree.search(value)) {
						System.out.println("Item is present in the tree");
					} else {
						System.out.println("Item is not present in the tree");
					}
				} catch (ClassCastException e) {
					System.out.println("Invalid input for tree type");
				}
				break;
			case "l": // Count leaf nodes in the tree
				System.out.printf("The number of leaf nodes are %d", tree.getNumLeafNodes());
				System.out.println();
				break;
			case "sp": // Find number of single parents
				System.out.print("Single Parents: ");
				tree.getSingleParent();
				break;
			case "c": // Find cousins of a key node
				System.out.print("In-order: ");
				tree.inOrder();
				System.out.print("\nEnter a value: ");
				try {
					T value = readValue(scanner, type); // Reading based on type
					tree.getCousins(value);
				} catch (ClassCastException e) {
					System.out.println("Invalid input for tree type");
				}
				break;
			case "q": // Exit the program
				System.out.println("Exiting the program...");
				break;
			default: // Invalid command error
				System.out.println("Invalid command.");
				break;
			}

		} while (!command.equals("q"));
		scanner.close(); // Close command string to save resources
	}

	// Helper method to read and parse values based on the type
	private static <T> T readValue(Scanner scanner, char type) {
		switch (type) {
		case 'i':
			return (T) Integer.valueOf(scanner.nextInt()); // For int type
		case 'd':
			return (T) Double.valueOf(scanner.nextDouble()); // For double type
		case 's':
			return (T) scanner.next(); // for String type
		default:
			System.out.println("Invalid input for tree type.");
			return null;
		}
	}
}
