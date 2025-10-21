// AvlTree class
//
// CONSTRUCTION: with no initializer
//
// ******************PUBLIC OPERATIONS*********************
// void insert( x )       --> Insert x
// void remove( x )       --> Remove x (unimplemented)
// boolean contains( x )  --> Return true if x is present
// boolean remove( x )    --> Return true if x was present
// Comparable findMin( )  --> Return smallest item
// Comparable findMax( )  --> Return largest item
// boolean isEmpty( )     --> Return true if empty; else false
// void makeEmpty( )      --> Remove all items
// void printTree( )      --> Print tree in sorted order
// ******************ERRORS********************************
// Throws UnderflowException as appropriate

/**
 * Implements an AVL tree.
 * Note that all "matching" is based on the compareTo method.
 */
class AvlTree<AnyType extends Comparable<? super AnyType>> {
    private AvlNode<AnyType> root;

    public AvlTree() {
        root = null;
    }

    public void insert(AnyType x) {
        root = insert(x, root);
    }

    private AvlNode<AnyType> insert(AnyType x, AvlNode<AnyType> root) {
        return null;
    }

    public void remove(AnyType x) {
        root = remove(x, root);
    }

    private AvlNode<AnyType> remove(AnyType x, AvlNode<AnyType> t) {
        if (t == null) return null;

        int cmp = x.compareTo(t.element);
        if (cmp < 0) {
            t.left = remove(x, t.left);
        } else if (cmp > 0) {
            t.right = remove(x, t.right);
        } else if (t.left != null && t.right != null) {
            t.element = findMin(t.right).element;
            t.right = remove(t.element, t.right);
        } else {
            t = (t.left != null) ? t.left : t.right;
        }

        return balance(t);
    }

    public AnyType findMin() {
        if (isEmpty()) throw new UnderflowException();
        return findMin(root).element;
    }

    private AvlNode<AnyType> findMin(AvlNode<AnyType> t) {
        if (t == null) return null;
        while (t.left != null) t = t.left;
        return t;
    }

    public AnyType findMax() {
        if (isEmpty()) throw new UnderflowException();
        return findMax(root).element;
    }

    private AvlNode<AnyType> findMax(AvlNode<AnyType> t) {
        if (t == null) return null;
        while (t.right != null) t = t.right;
        return t;
    }

    public boolean contains(AnyType x) {
        return contains(x, root);
    }

    private boolean contains(AnyType x, AvlNode<AnyType> t) {
        while (t != null) {
            int cmp = x.compareTo(t.element);
            if (cmp < 0) t = t.left;
            else if (cmp > 0) t = t.right;
            else return true;
        }
        return false;
    }

    public void makeEmpty() {
        root = null;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public void printTree() {
        if (isEmpty()) System.out.println("Empty tree");
        else printTree(root);
    }

    private void printTree(AvlNode<AnyType> t) {
        if (t != null) {
            printTree(t.left);
            System.out.println(t.element);
            printTree(t.right);
        }
    }

    private static final int ALLOWED_IMBALANCE = 1;

    private AvlNode<AnyType> balance(AvlNode<AnyType> t) {
        if (t == null) return t;

        if (height(t.left) - height(t.right) > ALLOWED_IMBALANCE) {
            if (height(t.left.left) >= height(t.left.right))
                t = rotateWithLeftChild(t);
            else
                t = doubleWithLeftChild(t);
        } else if (height(t.right) - height(t.left) > ALLOWED_IMBALANCE) {
            if (height(t.right.right) >= height(t.right.left))
                t = rotateWithRightChild(t);
            else
                t = doubleWithRightChild(t);
        }

        t.height = Math.max(height(t.left), height(t.right)) + 1;
        return t;
    }

    private int height(AvlNode<AnyType> t) {
        return (t == null) ? -1 : t.height;
    }

    private AvlNode<AnyType> rotateWithLeftChild(AvlNode<AnyType> k2) {
        AvlNode<AnyType> k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = Math.max(height(k2.left), height(k2.right)) + 1;
        k1.height = Math.max(height(k1.left), k2.height) + 1;
        return k1;
    }

    private AvlNode<AnyType> rotateWithRightChild(AvlNode<AnyType> k1) {
        AvlNode<AnyType> k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = Math.max(height(k1.left), height(k1.right)) + 1;
        k2.height = Math.max(height(k2.right), k1.height) + 1;
        return k2;
    }

    private AvlNode<AnyType> doubleWithLeftChild(AvlNode<AnyType> k3) {
        k3.left = rotateWithRightChild(k3.left);
        return rotateWithLeftChild(k3);
    }

    private AvlNode<AnyType> doubleWithRightChild(AvlNode<AnyType> k1) {
        k1.right = rotateWithLeftChild(k1.right);
        return rotateWithRightChild(k1);
    }

    public void checkBalance() {
    }

    private static class AvlNode<AnyType> {
        AnyType element;
        AvlNode<AnyType> left;
        AvlNode<AnyType> right;
        int height;

        AvlNode(AnyType element) {
            this(element, null, null);
        }

        AvlNode(AnyType element, AvlNode<AnyType> lt, AvlNode<AnyType> rt) {
            this.element = element;
            this.left = lt;
            this.right = rt;
            this.height = 0;
        }
    }
}
