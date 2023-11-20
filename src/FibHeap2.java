import java.util.ArrayList;

public class FibHeap2 {
    private FibNode2 minNode;
    private int size;
    private FibNode2 last;
    private FibNode2 root;

    public FibHeap2() {
        minNode = null;
        last = null;
        size = 0;
        root = null;
    }
    
    public void insert(FibNode2 node) {
        if (node == null) {
            return;
        }
        
        node.next = node;
    	node.prev = node;
       
        addToRootList(node);
        if (minNode == null || node.priority < minNode.priority) {
            minNode = node;
        }
        size++;
        last = node;
    }

    public FibNode2 getLast() {
        return last;
    }
    
    public FibNode2 getRoot() {
        return root;
    }

    public boolean isEmpty() {
        if(minNode == null) {
        	return true;
        }
        return false;
    }
    
    public void clear() {
    	root = null;
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
        FibNode2 first = minNode, child = minNode.child, anchor = minNode.child;
        if (child != null && minNode != null) {
            do {
            	FibNode2 nextChild = child.next;
                addToRootList(child);
                child.parent = null;
                child = nextChild;
            } while (child != anchor);
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
    	removeFromRootList(minNode);
        if (minNode.next == minNode) {
            minNode = null;
            root = null;
        } else {
            minNode = minNode.next;
            consolidate();
        }
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

    private void addToRootList(FibNode2 newNode) {
        if (root == null) {
            root = newNode;
        } else {
            newNode.next = root;
            newNode.prev = root.prev;
            root.prev.next = newNode;
            root.prev = newNode;
        }
    }
    
    private void removeFromRootList(FibNode2 node) {
    	if (node == root) {
    		root = node.next;
    	}
		node.prev.next = node.next;
		node.next.prev = node.prev;
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
            addToRootList(node);
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
    
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FibonacciHeap [\n");
        
        if (minNode != null) {
            sb.append("  Min Node: ").append(minNode.priority).append("\n");
        } else {
            sb.append("  Min Node: null\n");
        }

        sb.append("  Size: ").append(size).append("\n");

        if (root != null) {
            sb.append("  Root List: [");
            FibNode2 current = root;
            do {
                sb.append(current.priority);
                current = current.next;
                if (current != root) {
                    sb.append(", ");
                }
            } while (current != root);
            sb.append("]\n");
        } else {
            sb.append("  Root List: empty\n");
        }

        sb.append("]");

        return sb.toString();
    }

}