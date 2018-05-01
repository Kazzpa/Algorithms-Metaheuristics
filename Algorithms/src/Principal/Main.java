package Principal;

import SimulatedAnnealing.*;
import java.util.LinkedList;

/**
 *
 * @author kzr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static final int numDoctors = 20;
    public static final int numPatients = 60;

    public static LinkedList<Doctor> doctors;
    public static LinkedList<Patient> patients;

    public static void main(String[] args) {
        doctors = new LinkedList<Doctor>();
        patients = new LinkedList<Patient>();
        generateData();
        //Run simulatedAnealing
        double time0 = System.currentTimeMillis();
        SimulatedAnnealing.run();
        time0 -= System.currentTimeMillis();
        //Run Genetic
        double time1 = System.currentTimeMillis();

        time1 -= System.currentTimeMillis();

        //Run Memetic
        double time2 = System.currentTimeMillis();

        time2 -= System.currentTimeMillis();
        //Compare results
        System.out.println("Simulated annealing calculated in :" + time0
        + "\nGenetic solution calculated in : " + time1 + "\nMemetic "
        + "solution calculated in" + time2);

    }

    //GENERATES THE DATA OF THE DOCTORS AND PATIENTS
    public static void generateData() {
    }

}
