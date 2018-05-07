/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Genetic;

import Principal.Main;
import Principal.Population;
import Principal.Solution;
import SimulatedAnnealing.SimulatedAnnealing;

/**
 *
 * @author yo
 */
public class GeneticAlg {
    // Input data
    public static int[][] matrix;
    public static int numCities;

    

    public static void main(String[] args) {
        Population pop = new Population(numCities);
        pop.initializePopulationRandomly(Main.POPULATION_SIZE);
        for (int i = 0; i < Main.NUM_EVOLUTION_ITERATIONS; i++) {
            pop = pop.evolve();
            if (i % 3 == 0) {
                System.out.println("Finished Iteration: " + i + ". Best Solution: " + pop.getBestIndividualInPop());
            }

        }
        System.out.println("BEST SOLUTION:");
        System.out.println(pop.getBestIndividualInPop());
    }

}
