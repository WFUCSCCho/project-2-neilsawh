/*
@file: Node.java
@description: Defines the structure of a BST node, which stores a value and links to its left and right children.
@author: Neil Sawhney
@date: September 18, 2025
 */
public class Node<T extends Comparable<T>> {
    T value; // Node value
    Node<T> left; // Left child
    Node<T> right; // Right child

    // Create a new node with given value
    public Node(T value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public T getValue() {
        return value;
    } // Return the node value

    public void setValue(T value) {
        this.value = value;
    } // Update the node value

    public Node<T> getLeft() {
        return left;
    } // Return left child

    public void setLeft(Node<T> left) {
        this.left = left;
    } // Update left child

    public Node<T> getRight() {
        return right;
    } // Return right child

    public void setRight(Node<T> right) {
        this.right = right; // Update right child
    }
}
