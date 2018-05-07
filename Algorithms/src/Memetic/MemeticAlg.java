/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memetic;

import Principal.Population;
import Principal.Solution;
import java.util.Random;
import SimulatedAnnealing.*;

/**
 *
 * @author kzr
 */
public class MemeticAlg {
    // The input file is a SPACE delimited distance matrix
    // For this implementation, the triangle inequality does not need to be satisfied
    public static final String INPUT_FILE = "TSP100.txt";

    // Input data
    public static int[][] matrix;
    public static int numCities;

    // Debugging
    public static boolean printNewChildren = false;

    // Genetic Algorithm Paramaters
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

    public static void main(String[] args) {
//        Population pop = new Population(numCities);
//        pop.initializePopulationRandomly(POPULATION_SIZE);
//        for (int i = 0; i < NUM_EVOLUTION_ITERATIONS; i++) {
//            pop = pop.evolve();
//            if (i % 3 == 0) {
//                System.out.println("Finished Iteration: " + i + ". Best Solution: " + pop.getBestIndividualInPop());
//            }
//
//            LocalSearch(pop,i);
//
//        }
//        System.out.println("BEST SOLUTION:");
//        System.out.println(pop.getBestIndividualInPop());
    }

    public static void LocalSearch(Population pop,int gen) {
//        if (gen % GENERATIONLOCALSEARCH == 0) {
//            if (BEST) {
//                int i;
//                for (i = 0; i < RATELOCALSEARCH * POPULATION_SIZE; i++) {
//                    Solution ind = pop.individuals.get(i);
//                    SimulatedAnnealing(ind);
//                }
//            } else {
//                int i;
//                Random rnd = new Random();
//                for (i = 0; i < POPULATION_SIZE; i++) {
//                    if (rnd.nextDouble() < RATELOCALSEARCH) {
//                        Solution ind = pop.individuals.get(i);
//                        SimulatedAnnealing(ind);
//                    }
//                }
//
//            }
//        }
    }
    public static void SimulatedAnnealing(Solution s){
        SimulatedAnnealing sa = new SimulatedAnnealing();
        sa.run(s);
    }
}
