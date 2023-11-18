public class HospitalQueue {

    private FibHeap2 fibHeap;

    /**
     * Constructor
     */
    public HospitalQueue() {
        fibHeap = new FibHeap2();
    }

    /**
     * Adds a new patient to the queue
     */
    public void insert(String name, int priority) {
        FibNode2 newNode = new FibNode2(name, priority);
        fibHeap.insert(newNode);
    }

    /**
     * Changes the priority of the patient
     */
    public void changePriority(String name, int newPriority) {
        fibHeap.removeNode(this.checkPatient(name));
        this.insert(name, newPriority);
    }

    /**
     * Shows the first patient in the queue's name and priority
     */
    public String firstPatient() {
        String patient = fibHeap.peek().name;
        int priority = fibHeap.peek().priority;
        return ("[ " + patient + " " + priority + " ]");
    }

    public int highestPriority() {
        return fibHeap.peek().priority;
    }

    /**
     * Dequeues the first patient in the queue
     */
    public FibNode2 dequeue() {
        return fibHeap.getMin();
    }

    /**
     * Dequeues the patient by name
     */
    public FibNode2 dequeue(String name) {
        return fibHeap.removeNode(this.checkPatient(name));
    }

    /**
     * Gets the first patient in the queue and does not remove them
     */
    public FibNode2 checkFirstPatient() {
        return fibHeap.peek();
    }
    
    /**
     * Gets the last patient in the queue and does not remove them
     */
    public FibNode2 checkLastPatient() {
        return fibHeap.getLast();
    }

    /**
     * Gets the patient of a specific name and does not remove them
     */
    public FibNode2 checkPatient(String name) {
        return fibHeap.findNode(name);
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
    public void emptyQueue() {
        fibHeap.clear();
    }

    /**
     * gets the number of patients
     */
    public int numPatients() {
        return fibHeap.size();
    }
}