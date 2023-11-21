public class HospitalQueue {

    private FibHeap fibHeap;
    private int numPatients = 0;


    /**
     * Constructor
     */
    public HospitalQueue() {
        fibHeap = new FibHeap();
    }

    /**
     * Adds a new patient to the queue
     */
    public FibNode addPatient(String name, int priority) {
        numPatients++;
        return fibHeap.insert(name, priority);
    }

    /**
     * Changes the priority of the patient
     */
    public void decreasePriority(String name, int newPriority) {
        fibHeap.decreasePriority(name, newPriority);
    }
    
    /**
     * returns the priority of the patient of a given name
     */
    public int findPriority(String name) {
    	return fibHeap.findNode(name, fibHeap.peek()).priority;
    }

    /**
     * Shows the first patient in the queue's name
     */
    public String firstPatient() {
    	if(fibHeap.peek() != null) {
    		return fibHeap.peek().name;
    	}
    	return null;
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
    public FibNode dequeue() {
        if (!this.isEmpty()) {
            numPatients--;
            return fibHeap.dequeue();
        }
        return null;
    }
    
    /**
     * Merge two HosptialQueues
     */
    public void merge(HospitalQueue toMerge) {
    	FibHeap fh = this.getFibHeap();
    	this.fibHeap = fh.merge(toMerge.getFibHeap());
    	numPatients = numPatients + toMerge.numPatients;
    }

    /**
     * Returns but does not remove the first patient in the queue
     */
    public FibNode peek() {
    	if(fibHeap.peek() != null) {
    		return fibHeap.peek();
    	}
    	return null;
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
        fibHeap = new FibHeap();
    }

    /**
     * gets the number of patients
     */
    public int numPatients() {
        return numPatients;
    }
    
    public FibHeap getFibHeap() {
    	return this.fibHeap;
    }
    
}
