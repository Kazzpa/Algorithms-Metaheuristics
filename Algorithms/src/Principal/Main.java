package Principal;

import Genetic.GeneticAlg;
import Memetic.MemeticAlg;
import SimulatedAnnealing.*;
import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author kzr
 */
public class Main {

    //DEBUG:
    public static int error = 0;
    public static boolean DEBUG = false;
    //DEBUG
    //SELECT WHICH ALGORITHMS DO YOU WANT TO EXECUTE
    public static boolean EXECUTE_SA = true;
    public static boolean EXECUTE_GA = true;
    public static boolean EXECUTE_MA = true;
    //SELECT IF YOU WANT TO PRINT THE BEST SOLUTION
    public static boolean PRINT_BEST = true;
    //SELECT IF YOU WANT TO PRINT EVERY 3 ITERATIONS
    public static boolean PRINT_ITERATION = false;
    //SELECT IF YOU WANT TO PRINT THE COST OF EVERY INDIVIDUAL
    public static boolean PRINT_BEST_POPULATION = false;

    //GENERATE DATA ATRIBUTTES
    public static int NUM_DOCTORS = 80;
    public static int NUM_PATIENTS = 240;

    //DOCTORS
    public static int SALARY_MAX = 3400;
    public static int SALARY_MIN = 1400;
    public static int PATIENTS_ASSIGNED_MAX = 12;
    public static int PATIENTS_ASSIGNED_MIN = 6;
    //EL NUMERO REAL MAXIMO DE ASIGNADOS SERA MIN+MAX
    //PATIENTS
    public static int COORDINATES_MAX = 100;
    //Importance given to COST OF DOCTORS AND TO COST OF PATIENTS (DISTANCE)
    public static double DOCTORS_COST_RATE = 0.80;
    public static double PATIENTS_COST_RATE = 1 - DOCTORS_COST_RATE;

    //Atributes for SA
    public static double TEMPERATURE = 10000000;
    //Atributtes for Population Algorithms
    public static int POPULATION_SIZE = 10;
    public static int NUM_EVOLUTION_ITERATIONS = 100;

    // When selecting two parents, we want the "fittest" parents to reproduce
    // This is done by randomly selecting X individuals in the population and 
    // selecting the top two from this sub-population. The size of the sub-population is tournament size
    // This must be less than POPULATION_SIZE
    public static double TOURNAMENT_SIZE_PCT = 0.1;
    public static int TOURNAMENT_SIZE = (int) (POPULATION_SIZE * TOURNAMENT_SIZE_PCT);
    // The probability a specific individual undergoes a single mutation
    public static double MUTATION_RATE = 0.003;
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
    public static boolean LOCALSEARCH_BEST = false;

    public static LinkedList<Doctor> doctors = new LinkedList<Doctor>();
    public static LinkedList<Patient> patients = new LinkedList<Patient>();

    public static void main(String[] args) {
        generateData();
        SimulatedAnnealing sa = new SimulatedAnnealing();
        GeneticAlg ga = new GeneticAlg();
        MemeticAlg ma = new MemeticAlg();
        double time0 = 0, time1 = 0, time2 = 0;
        if (DEBUG) {
            Debug();
        }
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
            System.out.println("Simulated annealing calculated in :" + -time0+" ms");
        }
        if (EXECUTE_GA) {
            System.out.println("Genetic solution calculated in : " + -time1+" ms");
        }
        if (EXECUTE_MA) {
            System.out.println("Memetic solution calculated in : " + -time2+" ms");
        }
        System.out.println("Errores " + error);
    }

    //GENERATES THE DATA OF THE DOCTORS AND PATIENTS
    public static void generateData() {

        Random rnd = new Random();
        int rndInt;
        double rndDouble;
        //Data generation for Doctors
        for (int i = 0; i < NUM_DOCTORS; i++) {
            Doctor d = new Doctor();
            rndInt = rnd.nextInt(SALARY_MAX) + SALARY_MIN;
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

    public static void paralelizacion() {
        int numHilos = 4;
        //FALTA IMPLEMENTAR
    }

    public static void Debug() {
        //TESTEO GENETICO
        //TESTEAMOS LA PARAMETRIZACION DEL TAMAÑO DE POBLACION CON EL QUE TRABAJAMOS
        // Y EL NUMERO DE GENERACIONES QUE VA A EVOLUCIONAR
        GeneticAlg ga = new GeneticAlg();
        int[] tamPoblacion = {10, 20, 50, 100, 150, 200, 250, 300, 350, 400, 500, 750, 1000};
        int[] Generaciones = {5, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100, 150, 200};
        for (int i = 0; i < tamPoblacion.length; i++) {
            POPULATION_SIZE = tamPoblacion[i];
            for (int j = 0; j < Generaciones.length; j++) {
                NUM_EVOLUTION_ITERATIONS = Generaciones[j];
                double time = System.currentTimeMillis();
                for (int k = 0; k < 10; k++) {
                    ga.run();
                }
                time -= System.currentTimeMillis();
                System.out.println(time / 10);
            }
        }
        //TESTEO ENFRIAMIENTO SIMULADO
        //TESTEAMOS LA PARAMETRIZACION DE LA TEMPERATURA
        SimulatedAnnealing sa = new SimulatedAnnealing();
        int[] temperatura = {100, 1000, 2000, 5000, 10000, 100000, 10000000, 100000000, 100000000, 100000000};
        for (int i = 0; i < temperatura.length; i++) {
            TEMPERATURE = temperatura[i];
            double timeSa = System.currentTimeMillis();
            for (int j = 0; j < 10; j++) {
                sa.run();
            }
            timeSa -= System.currentTimeMillis();
            System.out.println(timeSa / 10);
        }
        //TESTEO MEMETICO
        //TESTEAMOS TANTO LA TEMPERATURA COMO EL ALGORITMO GENETICO
        MemeticAlg ma = new MemeticAlg();
        for (int i = 0; i < tamPoblacion.length; i++) {
            POPULATION_SIZE = tamPoblacion[i];
            for (int j = 0; j < Generaciones.length; j++) {
                NUM_EVOLUTION_ITERATIONS = Generaciones[j];
                for (int l = 0; l < temperatura.length; l++) {
                    double time = System.currentTimeMillis();
                    for (int k = 0; k < 10; k++) {
                        ma.run();
                    }
                    time -= System.currentTimeMillis();
                    System.out.println(time / 10);
                }
            }
        }
    }

}
