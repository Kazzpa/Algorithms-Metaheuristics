/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Memetic;

import Principal.Main;
import Principal.Population;
import Principal.Solution;
import java.util.Random;
import SimulatedAnnealing.*;

/**
 *
 * @author kzr
 */
public class MemeticAlg {

    //Numero de iteraciones en las que se mejora
    public static int GENERATIONLOCALSEARCH = 10;
    //Porcentaje de individuos a los que se aplica la mejora local
    public static double RATELOCALSEARCH = 0.1;
    //saber si se aplica al top o a unos individuos aleatorios
    public static boolean BEST = true;

    public static void run() {
        Population pop = new Population();
        pop.initializePopulationRandomly(Main.POPULATION_SIZE);
        for (int i = 0; i < Main.NUM_EVOLUTION_ITERATIONS; i++) {
            pop = pop.evolve();
            if (i % 3 == 0&& Main.PRINT_ITERATION) {
                System.out.println("Finished Iteration: " + i + ". Best Solution: " + pop.getBestIndividualInPop().getCost());
            }
            LocalSearch(pop,i);
        }
        
        Solution best = pop.getBestIndividualInPop();
        System.out.println("\nMemetic Algorithm:\nFinal solution: " + best.getCost()
                + "\nCoste medicos: "  + best.getDinero() + " €\tDistancia "
                    + "total: " + best.getDistancia() + " km");
        if(Main.PRINT_BEST)
            System.out.println("Mejor solucion: "+ best.toString());
    }

    public static void LocalSearch(Population pop, int gen) {
        if (gen % GENERATIONLOCALSEARCH == 0) {
            if (BEST) {
                int i;
                for (i = 0; i < RATELOCALSEARCH * Main.POPULATION_SIZE; i++) {
                    Solution ind = pop.solutions.get(i);
                    SimulatedAnnealing(ind);
                }
            } else {
                int i;
                Random rnd = new Random();
                for (i = 0; i < Main.POPULATION_SIZE; i++) {
                    if (rnd.nextDouble() < RATELOCALSEARCH) {
                        Solution ind = pop.solutions.get(i);
                        SimulatedAnnealing(ind);
                    }
                }

            }
        }
    }

    public static void SimulatedAnnealing(Solution s) {
        SimulatedAnnealing sa = new SimulatedAnnealing();
        sa.run(s);
    }
}
