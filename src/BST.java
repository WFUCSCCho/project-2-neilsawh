/*
@file: BST.java
@description: Implements the generic Binary Search Tree with methods for insertion, searching, removal, and traversal.
@author: Neil Sawhney
@date: September 25, 2025
 */
public class BST <T extends Comparable<T>>{
    private Node<T> root;
    private int movie;

    // Initialize tree with empty root
    public BST() {
        this.root = null;
    }

    // Insert a new value into the BST
    public void insert(T value) {
        root = insertRec(root, value);
    }

    // Recursively find the correct position and insert the node
    private Node<T> insertRec(Node<T> current, T value) {
        if (current == null) {
            return new Node<>(value);
        }

        int compare = value.compareTo(current.getValue());

        if (compare < 0) {
            current.setLeft(insertRec(current.getLeft(), value));
        } else if (compare > 0) {
            current.setRight(insertRec(current.getRight(), value));
        }

        return current;
    }

    // Search for a node with the given value
    public Node<T> search(T value) {
        return searchRec(root, movie);
    }

    // Currently returns a new node if not found
    private Node<T> searchRec(Node<T> current, int value) {
        if (current == null) {
            return null;
        }
        int compare = value;

        if (compare < 0)
            return searchRec(current.getLeft(), value);
        else if (compare > 0)
            return searchRec(current.getRight(), value);
        else
            return current;
    }

    // Remove a node from the BST and return the removed node
    public Node<T> remove(T value) {
        Node<T>[] removed = new Node[1];
        root = removeRec(root, value, removed);
        return removed[0];
    }

    // Recursively find and remove the node while maintaining BST properties
    private Node<T> removeRec(Node<T> current, T value, Node<T>[] removed) {
        if (current == null)
            return null;

        int compare = value.compareTo(current.getValue());
        if (compare < 0) {
            current.setLeft(removeRec(current.getLeft(), value, removed));
        } else if (compare > 0) {
            current.setRight(removeRec(current.getRight(), value, removed));
        } else {
            removed [0] = new Node<>(current.getValue());

            if (current.getLeft() == null && current.getRight() == null)
                return null;
            if (current.getLeft() == null)
                return current.getRight();
            if (current.getRight() == null)
                return current.getLeft();

            Node<T> successor = findMin(current.getRight());
            current.setValue(successor.getValue());
            current.setRight(removeRec(current.getRight(), successor.getValue(), removed));
        }
        return current;
    }

    // Find the minimum value in the BST
    private Node<T> findMin(Node<T> node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    // Return an inorder traversal of the BST as a string
    public String inorder() {
        StringBuilder sb = new StringBuilder();
        inorderRec(root, sb);
        return sb.toString().trim();
    }

    // Helper function to build inorder traversal recursively
    public void inorderRec(Node<T> node, StringBuilder sb) {
        if (node != null) {
            inorderRec(node.getLeft(), sb);
            sb.append(node.getValue()).append(" ");
            inorderRec(node.getRight(), sb);
        }
    }

    // Creates the BSTIterator class sets the index to 0
    public class BSTIterator {
        private Node<T>[] nodes;
        private int index = 0;

        // Build an array of nodes based on chosen traversal order
        public BSTIterator(String order) {
            int size = countNodes(root);
            nodes = new Node[size];
            index = 0;

            switch (order.toLowerCase()) {
                case "in":
                    inorder(root);
                    break;
                case "pre":
                    preorder(root);
                    break;
                case "post":
                    postorder(root);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid traversal order");
            }
            index = 0;
        }

        // Count the total number of nodes in the tree
        private int countNodes(Node<T> node) {
            if (node == null)
                return 0;
            return 1 + countNodes(node.getLeft()) + countNodes(node.getRight());
        }

        // Fill array with inorder traversal
        private void inorder(Node <T> node) {
            if (node == null)
                return;
            inorder(node.getLeft());
            nodes[index++] = node;
            inorder(node.getRight());
        }

        // Fill array with preorder traversal
        private void preorder(Node<T> node) {
            if (node == null)
                return;
            nodes[index++] = node;
            preorder(node.getLeft());
            preorder(node.getRight());
        }

        // Fill array with postorder traversal
        private void postorder(Node<T> node) {
            if (node == null)
                return;
            postorder(node.getLeft());
            postorder(node.getRight());
            nodes[index++] = node;
        }

        // Check if more nodes exist
        public boolean hasNext() {
            return index < nodes.length;
        }

        // Return next node
        public Node<T> next() {
            if (!hasNext()) return null;
            return nodes[index++];
        }
    }

    // Create and return a new iterator with given traversal order
    public BSTIterator iterator(String order) {
        return new BSTIterator(order);
    }
}

