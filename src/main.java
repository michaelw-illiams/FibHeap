public class main {
    public static void main(String[] args) {
        HospitalQueue q = new HospitalQueue();
        q.addPatient("Tim", 2);
        q.addPatient("Jim", 6);
        q.addPatient("Kim", 3);
        // should print tim
        System.out.println(q.firstPatient());
        q.addPatient("Al", 5);
        q.addPatient("Allen", 7);
        q.addPatient("Timmy", 3);
        q.addPatient("Tommy", 1);
        q.addPatient("Hilfiger", 10);

        //should print Tommy, Tim, Timmy, Kim, Al, Jim, Allen, Hilfiger
        FibNode2 patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
        patient = q.dequeue();
        System.out.println(patient.name + ": " + patient.priority);
    }
}