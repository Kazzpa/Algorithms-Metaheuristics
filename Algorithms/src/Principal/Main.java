package Principal;

import Genetic.GeneticAlg;
import Memetic.MemeticAlg;
import SimulatedAnnealing.*;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author kzr
 */
public class Main {

    //DEBUG:
    public static int error = 0;
    //DEBUG
    //SELECT WHICH ALGORITHMS DO YOU WANT TO EXECUTE
    public static final boolean EXECUTE_SA = true;
    public static final boolean EXECUTE_GA = true;
    public static final boolean EXECUTE_MA = true;

    //GENERATE DATA ATRIBUTTES
    public static final int NUM_DOCTORS = 80;
    public static final int NUM_PATIENTS = 240;

    //DOCTORS
    public static final int SALARY_MAX = 100;
    public static final int PATIENTS_ASSIGNED_MAX = 10;
    public static final int PATIENTS_ASSIGNED_MIN = 6;
    //EL NUMERO REAL MAXIMO DE ASIGNADOS SERA MIN+MAX
    //PATIENTS
    public static final int COORDINATES_MAX = 100;
    //Importance given to COST OF DOCTORS AND TO COST OF PATIENTS (DISTANCE)
    public static final double DOCTORS_COST_RATE = 0.5;
    public static final double PATIENTS_COST_RATE = 1 - DOCTORS_COST_RATE;
    //Atributtes for Population Algorithms

    public static final int POPULATION_SIZE = 20;
    public static final int NUM_EVOLUTION_ITERATIONS = 20;

    // When selecting two parents, we want the "fittest" parents to reproduce
    // This is done by randomly selecting X individuals in the population and 
    // selecting the top two from this sub-population. The size of the sub-population is tournament size
    // This must be less than POPULATION_SIZE
    public static double TOURNAMENT_SIZE_PCT = 0.1;
    public static int TOURNAMENT_SIZE = (int) (POPULATION_SIZE * TOURNAMENT_SIZE_PCT);
    // The probability a specific individual undergoes a single mutation
    public static double MUTATION_RATE = 0.50;
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
    //PORCENTAJE DE INDIVIDUOS A LOS QUE SE LE APLICA LA LOCALSEARCH
    public static double LOCALSEARCH_RATE = 0.1;
    //SE APLICA AL PORCENTAJE DE INDIVIDUOS MEJORES O RANDOM
    public static boolean LOCALSEARCH_BEST = true;

    public static LinkedList<Doctor> doctors;
    public static LinkedList<Patient> patients;

    public static void main(String[] args) {
        doctors = new LinkedList<Doctor>();
        patients = new LinkedList<Patient>();
        generateData();
        SimulatedAnnealing sa = new SimulatedAnnealing();
        GeneticAlg ga = new GeneticAlg();
        MemeticAlg ma = new MemeticAlg();
        double time0, time1, time2;
        if (EXECUTE_SA) {
            //Run simulatedAnealing
            time0 = System.currentTimeMillis();
            sa.run();
            time0 -= System.currentTimeMillis();
        }
        if (EXECUTE_GA) {
            //RUN genetic
            time1 = System.currentTimeMillis();
            ga.run();
            time1 -= System.currentTimeMillis();
        }
        if (EXECUTE_MA) {

            //Run Memetic
            time2 = System.currentTimeMillis();
            ma.run();
            time2 -= System.currentTimeMillis();
        }
        //Compare results
        System.out.println();
        if (EXECUTE_SA) {
            System.out.println("Simulated annealing calculated in :" + -time0);
        }
        if (EXECUTE_GA) {
            System.out.println("Genetic solution calculated in : " + -time1);
        }
        System.out.println("Memetic solution calculated in : " + -time2);
        System.out.println("errores" + error);
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
