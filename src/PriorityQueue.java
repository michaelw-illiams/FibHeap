public class PriorityQueue {

    private FibonacciHeap fibHeap;

    /**
     * Constructor
     */
    public PriorityQueue() {
        fibHeap = new FibHeap();
    }

    /**
     * Adds a new patient to the queue
     */
    public void insert(String name, int priority) {
        fibHeap.insert(name, priority);
    }

    /**
     * Changes the priority of the patient
     */
    public void changePriority(String name, int newPriority) {
        fibHeap.changePriority(name, newPriority);
    }

    /**
     * Gets the first patient in the queue
     */
    public HeapNode getFirstPatient() {
        return fibHeap.removeMin();
    }

    /**
     * Gets the patient of a specific name
     */
    public HeapNode getPatient(String name) {
        return fibHeap.extract(name);
    }

    /**
     * Checks if the number of patients is 0
     */
    public boolean isEmpty() {
        return fibHeap.isEmpty();
    }

    /**
     * Empties the queue
     */
    public boolean emptyQueue() {
        fibHeap.clear()
    }
}
