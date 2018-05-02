package Principal;

import SimulatedAnnealing.*;
import java.util.LinkedList;
import java.util.Random;

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

    //limit atributtes for doctors
    public static final int SALARY_MAX = 100;
    public static final int PATIENTS_ASSIGNED_MAX = 6;
    public static final int PATIENTS_ASSIGNED_MIN = 3;
    public static final int COORDINATES_MAX = 100;

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

        SimulatedAnnealing.run();
        time1 -= System.currentTimeMillis();

        //Run Memetic
        double time2 = System.currentTimeMillis();

        SimulatedAnnealing.run();
        time2 -= System.currentTimeMillis();
        //Compare results
        System.out.println("Simulated annealing calculated in :" + -time0
                + "\nGenetic solution calculated in : " + -time1 + "\nMemetic "
                + "solution calculated in : " + -time2);

    }

    //GENERATES THE DATA OF THE DOCTORS AND PATIENTS
    public static void generateData() {

        Random rnd = new Random();
        int rndInt;
        double rndDouble;
        //Data generation for Doctors
        for (int i = 0; i < numDoctors; i++) {
            Doctor d = new Doctor();
            rndInt = rnd.nextInt(SALARY_MAX);
            d.setSalary(rndInt);
            rndInt = rnd.nextInt(PATIENTS_ASSIGNED_MAX)+PATIENTS_ASSIGNED_MIN;
            d.setNumPacientes(rndInt);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            d.setX(rndDouble);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            d.setY(rndDouble);
            doctors.add(d);

        }
        //Data generation for Patients
        
        for (int i = 0; i < numPatients; i++) {
            Patient p = new Patient();
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            p.setX(rndDouble);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            p.setY(rndDouble);
            patients.add(p);

        }

    }

}
