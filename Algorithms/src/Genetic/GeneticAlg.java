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

    

    public void run() {
        Population pop = new Population();
        pop.initializePopulationRandomly(Main.POPULATION_SIZE);
        for (int i = 0; i < Main.NUM_EVOLUTION_ITERATIONS; i++) {
            pop = pop.evolve();
            if (i % 3 == 0) {
                //System.out.println("Finished Iteration: " + i + ". Best Solution: " + pop.getBestIndividualInPop().getCost());
            }

        }
        System.out.println("\nGenetic Algorithm:\nFinal solution: "+pop.getBestIndividualInPop().getCost());
    }

}
