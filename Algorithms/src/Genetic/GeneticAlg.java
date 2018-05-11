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
            if (i % 3 == 0 && Main.PRINT_ITERATION) {
                
                System.out.println("Finished Iteration: " + i + ". Best Solution: " + pop.getBestIndividualInPop().getCost());

            }
            if (i % 3 == 0 && Main.PRINT_BEST_POPULATION) {
                for (int j = 0; j < pop.solutions.size(); j++) {
                    System.out.println("Sol cost: " + j + "\t" + pop.solutions.get(j).getCost());
                    System.out.println(pop.solutions.get(j).toString());
                }
            }
        }
        Solution best = pop.getBestIndividualInPop();
        System.out.println("\nGenetic Algorithm:\nFinal solution: " + best.getCost()
                + "\nCoste medicos: " + best.getDinero() + " €\tDistancia "
                + "total: " + best.getDistancia() + " km");
        if (Main.PRINT_BEST) {
            System.out.println("Mejor solucion: " + best.toString());
        }
    }

}
