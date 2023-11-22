/**
 * file: main.java
 * @author Carson Nannini, Michael Williams
 * Purpose: This program proves that the internal structure of our Fibonacci 
 * Heap by printing the root list and nodes removed on our Fibonacci Heap. 
 *
 */
public class Main {
    public static void main(String[] args) {
    	// insert
        HospitalQueue q = new HospitalQueue();
        q.addPatient("Tim", 2);
        q.addPatient("Jim", 6);
        q.addPatient("Kim", 3);
        q.addPatient("Al", 5);
        q.addPatient("Allen", 7);
        
        // print and dequeue
        System.out.println(q.toString());
        FibNode patient = q.dequeue();
        System.out.println("Patient removed: " + patient.name + " " + patient.priority);
        System.out.println(q.toString());
        patient = q.dequeue();
        System.out.println("Patient removed: " + patient.name + " " + patient.priority);
        System.out.println(q.toString());
        patient = q.dequeue();
        System.out.println("Patient removed: " + patient.name + " " + patient.priority);
        System.out.println(q.toString());
        patient = q.dequeue();
        System.out.println("Patient removed: " + patient.name + " " + patient.priority);
        System.out.println(q.toString());
        patient = q.dequeue();
        System.out.println("Patient removed: " + patient.name + " " + patient.priority);
    }
}