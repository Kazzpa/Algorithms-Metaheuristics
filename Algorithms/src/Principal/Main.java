package Principal;

import SimulatedAnnealing.*;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author kzr
 */
public class Main {

    public static final int NUM_DOCTORS = 80;
    public static final int NUM_PATIENTS = 240;

    //limit atributtes for doctors
    public static final int SALARY_MAX = 100;
    public static final int PATIENTS_ASSIGNED_MAX = 6;
    public static final int PATIENTS_ASSIGNED_MIN = 3;
    //EL NUMERO REAL MAXIMO DE ASIGNADOS SERA MIN+MAX
    //Atributtes for Patients
    public static final int COORDINATES_MAX = 100;
    //Atributtes for Population Algorithms
//    public static final int POPULATION_SIZE = 30;
//    public static final double ELITE_PERCENT = 0.10;
//    public static final double MUTATION_RATE = 0.01;
//    public static double ELITE_PARENT_RATE = 0.01;
//    public static int TOURNAMENT_SIZE = 5;
    
    public static final int POPULATION_SIZE = 100;
    public static final int NUM_EVOLUTION_ITERATIONS = 100;

    // When selecting two parents, we want the "fittest" parents to reproduce
    // This is done by randomly selecting X individuals in the population and 
    // selecting the top two from this sub-population. The size of the sub-population is tournament size
    // This must be less than POPULATION_SIZE
    public static double TOURNAMENT_SIZE_PCT = 0.1;
    public static int TOURNAMENT_SIZE = (int) (POPULATION_SIZE * TOURNAMENT_SIZE_PCT);
    // The probability a specific individual undergoes a single mutation
    public static double MUTATION_RATE = 0.5;
    // Probability of skipping crossover and using the best parent
    public static double CLONE_RATE = 0.01;
    // Elite percent is what we define as "high" fit individuals
    public static double ELITE_PERCENT = 0.1;
    // When selecting parents, the ELITE_PARENT_RATE is the probability that we select an elite parent
    public static double ELITE_PARENT_RATE = 0.1;
    // Forward progress epsilon (percent of first-attempt path cost)
    public static double EPSILON = 0.02;
    //Numero de iteraciones en las que se mejora
    public static int GENERATIONLOCALSEARCH = 10;
    //Porcentaje de individuos a los que se aplica la mejora local
    public static double RATELOCALSEARCH = 0.1;
    //saber si se aplica al top o a unos individuos aleatorios
    public static boolean BEST = true;
    
    public static LinkedList<Doctor> doctors;
    public static LinkedList<Patient> patients;

    public static void main(String[] args) {
        doctors = new LinkedList<Doctor>();
        patients = new LinkedList<Patient>();
        generateData();
        SimulatedAnnealing sa = new SimulatedAnnealing();
        //Run simulatedAnealing
        double time0 = System.currentTimeMillis();
        sa.run();
        time0 -= System.currentTimeMillis();
        sa = new SimulatedAnnealing();
        //Run Genetic
        double time1 = System.currentTimeMillis();

        //sa.run();
        time1 -= System.currentTimeMillis();
        sa = new SimulatedAnnealing();
        //Run Memetic
        double time2 = System.currentTimeMillis();

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
        for (int i = 0; i < NUM_DOCTORS; i++) {
            Doctor d = new Doctor();
            rndInt = rnd.nextInt(SALARY_MAX);
            d.setSalary(rndInt);
            rndInt = rnd.nextInt(PATIENTS_ASSIGNED_MAX) + PATIENTS_ASSIGNED_MIN;
            d.setNumPacientes(rndInt);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            d.setX(rndDouble);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            d.setY(rndDouble);
            doctors.add(d);

        }
        //Data generation for Patients

        for (int i = 0; i < NUM_PATIENTS; i++) {
            Patient p = new Patient();
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            p.setX(rndDouble);
            rndDouble = rnd.nextDouble() * COORDINATES_MAX;
            p.setY(rndDouble);
            patients.add(p);

        }

    }

}
