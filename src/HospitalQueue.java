public class HospitalQueue {

    private FibHeap2 fibHeap;
    private int numPatients = 0;


    /**
     * Constructor
     */
    public HospitalQueue() {
        fibHeap = new FibHeap2();
    }

    /**
     * Adds a new patient to the queue
     */
    public void addPatient(String name, int priority) {
        numPatients++;
        fibHeap.insert(name, priority);
    }

    /**
     * Changes the priority of the patient
     */
    public void decreasePriority(FibNode2 patient, int newPriority) {
        fibHeap.decreasepriority(patient, newPriority);;
    }

    /**
     * Shows the first patient in the queue's name
     */
    public String firstPatient() {
        return fibHeap.peek().name;
    }

    /**
     * Shows the priority of the patient first in line
     * @return
     */
    public int firstPriority() {
        return fibHeap.peek().priority;
    }

    /**
     * Dequeues the first patient in the queue
     */
    public FibNode2 dequeue() {
        if (!this.isEmpty()) {
           numPatients--; 
           return fibHeap.dequeue();
        }
        return null;
    }

    /**
     * Returns but does not remove the first patient in the queue
     */
    public FibNode2 peek() {
        return fibHeap.peek();
    }
  
    /**
     * Checks if the number of patients is 0
     */
    public boolean isEmpty() {
        return numPatients == 0;
    }

    /**
     * Empties the queue
     */
    public void emptyQueue() {
        numPatients = 0;
        fibHeap = new FibHeap2();
    }

    /**
     * gets the number of patients
     */
    public int numPatients() {
        return numPatients;
    }
}
