import java.util.ArrayList;

public class FibHeap2 {
    private FibNode2 minNode;
    private int size;
    private FibNode2 last;

    public FibHeap2() {
        minNode = null;
        last = null;
        size = 0;
    }
    
    public void insert(FibNode2 node) {
        if (node == null) {
            return;
        }
        if (isEmpty()) {
            minNode = node;
        }
        else {
            insert(node, minNode);
            if (node.priority < minNode.priority) {
                minNode = node;
            } 
        }
        size++;
        last = node;
    }

    public FibNode2 getLast() {
        return last;
    }

    public boolean isEmpty() {
        return minNode == null;
    }
    public void clear() {
        minNode = null;
        size = 0;
    }
    public int size() {
        return size;
    }
    public FibNode2 peek() {
        return minNode;
    }

    public FibNode2 getMin() {
        if (isEmpty()) {
            return null;
        }
        FibNode2 first = minNode, child = minNode.child;
        if (child != null) {
            while (true) {
                child = child.next;
                if (child == minNode.child) break;
            }
        }
        removeMin();
        return first;
    }

    public FibNode2 findNode(String name) {
        if (isEmpty()) {
            return null; 
        } else {
            return findNode(minNode, name);
        }
    }

    public FibNode2 removeNode(FibNode2 node) {
        if (node == null) {
            return null; 
        }
        if (node == minNode) {
            removeMin();
        } else {
            cut(node, node.parent);
            cascadingCut(node);
        }
        size--;
        return node;
    }

    private void removeMin() {
        if (minNode.next == minNode) {
            minNode = null;
        } else {
            minNode.prev.next = minNode.next;
            minNode.next.prev = minNode.prev;
            minNode = minNode.next;
        }
        consolidate();
        size--;
    }

    private FibNode2 findNode(FibNode2 start, String name) {
        FibNode2 current = start;
        while (current != null) {
            if (current.name.equals(name)) {
                return current;
            }
    
            FibNode2 foundInChild = findNode(current.child, name);
            if (foundInChild != null) {
                return foundInChild;
            }
    
            current = current.next;
            if (current == start) {
                break;
            }
        }
        return null;
    }  

    private void consolidate() {
        int maxDegree = (int) Math.floor(Math.log(size) / Math.log(2)) + 1;
    
        FibNode2[] degreeTable = new FibNode2[maxDegree];
    
        // Loop will run at least once when roots is empty
        ArrayList<FibNode2> roots = new ArrayList<>();
        for (FibNode2 current = minNode; roots.isEmpty() || current != minNode; current = current.next) {
            roots.add(current);
        }
    
        for (FibNode2 root : roots) {
            FibNode2 current = root;
            int degree = current.degree;
    
            while (degreeTable[degree] != null) {
                FibNode2 other = degreeTable[degree];
                if (current.priority > other.priority) {
                    FibNode2 temp = current;
                    current = other;
                    other = temp;
                }
    
                link(other, current);
                degreeTable[degree] = null;
                degree++;
            }
    
            degreeTable[degree] = current;
        }
    
        // recheck for min node
        minNode = null;
        for (FibNode2 node : degreeTable) {
            if (node != null) {
                if (minNode == null || node.priority < minNode.priority) {
                    minNode = node;
                }
            }
        }
    }

    private void link(FibNode2 child, FibNode2 parent) {
        child.prev.next = child.next;
        child.next.prev = child.prev;
    
        child.prev = child;
        child.next = child;
        parent.degree++;
    
        if (parent.child == null) {
            parent.child = child;
        } else {
            child.next = parent.child;
            child.prev = parent.child.prev;
            parent.child.prev.next = child;
            parent.child.prev = child;
        }
        child.marked = false;
        child.parent = parent;
    }

    private void insert(FibNode2 newNode, FibNode2 anchor) {
        if (anchor == null) {
            newNode.next = newNode;
            newNode.prev = newNode;
        } else {
            newNode.next = anchor;
            newNode.prev = anchor.prev;
            anchor.prev.next = newNode;
            anchor.prev = newNode;
        }
    }

    private void cut(FibNode2 node, FibNode2 parent) {
        if (parent != null) {
            if (node.next == node) {
                parent.child = null;
            } else {
                node.next.prev = node.prev;
                node.prev.next = node.next;
                if (parent.child == node) {
                    parent.child = node.next;
                }
            }
            parent.degree--;
            insert(node, minNode);
            node.parent = null;
            node.marked = false;
            if (node.priority < minNode.priority) {
                minNode = node;
            }
        }
    }
    
    private void cascadingCut(FibNode2 node) {
        FibNode2 parent = node.parent;
        if (parent != null) {
            if (!node.marked) {
                node.marked = true;
            } else {
                cut(node, parent);
                cascadingCut(parent);
            }
        }
    }
}
