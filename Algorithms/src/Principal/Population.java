package Principal;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author yo
 */
public class Population {

    public ArrayList<Solution> solutions = new ArrayList<Solution>();
    private int num;
    private ArrayList<Solution> sorted;

    public Population(int numCities) {
        this.num = numCities;
    }

    public void initializePopulationRandomly(int numSolutions) {  //la primera generacion debe iniciarse, lo hacemos de forma random
        for (int i = 0; i < numSolutions; i++) {
            Solution s = new Solution();
            s.generateIndividual();
            solutions.add(s);
        }
    }

    /*
	 * This is where we create the next generation of the population
	 * Step 1: Select the best fit (elitism)
	 * Step 2: Create offspring given two parents (genetic crossover)
	 * Step 3: Add mutations to some of the new children (mutation)
     */
    public Population evolve() {    //en el evolve hacemos para saber que individuos pasan a la siguiente generacion
        // STEP 1: Select the best fit (elitism)
        sorted = new ArrayList<Solution>();
        Population nextGenPop = new Population(this.num);
        int populationSpaceAvailable = solutions.size();

        for (int i = 0; i < Main.POPULATION_SIZE; i++) { //Aqui ordenamos los individuos de la poblacion de menor a peor coste (nos interesa minimizar)
            double bestCost = Double.MAX_VALUE;
            int bestIndex = -1;
            for (int j = 0; j < solutions.size(); j++) {
                if (solutions.get(j).getCost() < bestCost) {
                    bestCost = solutions.get(j).getCost();
                    bestIndex = j;
                }
            }
            sorted.add(solutions.get(bestIndex));
            solutions.remove(bestIndex);
        }
        // Keep the best individual
        nextGenPop.solutions.add(sorted.get(0));
        //sorted.remove(0);   //el mejor pasa, no lo elimino de la poblacion, se queda para poder usarlo en torneo
        --populationSpaceAvailable;
        // Add "top" individuals to the next generation
        int numElite = (int) (Main.POPULATION_SIZE * Main.ELITE_PERCENT);
        for (int i = 0; i < numElite + 1; i++) {    //parte de elitismo, el numElite +1 es porque el 0 se mantiene en la lista y quiero no tenerlo en cuenta
            if (i != 0) {   //la posicion 0 (el mejor) no se contempla para la mutacion
                if (Math.random() < Main.MUTATION_RATE) {
                    nextGenPop.solutions.add(mutate(sorted.get(i)));
                    nextGenPop.solutions.get(nextGenPop.solutions.size() - 1).getCost();  //esto no sirve para nada
                } else {
                    nextGenPop.solutions.add(sorted.get(i));
                    nextGenPop.solutions.get(nextGenPop.solutions.size() - 1).getCost();
                }
                populationSpaceAvailable--;
            }
        }

        // STEP 2: Select 2 parents from population and generate children
        while (populationSpaceAvailable > 0) {  //parte de torneo
            // STEP 2: Create offspring given two parents (genetic crossover)
            Solution p1 = selectParentViaTournament();
            Solution p2 = selectParentViaTournament();
            Solution child = crossover(p1, p2);

            // STEP 3: Add mutations
            if (Math.random() < Main.MUTATION_RATE) {
                mutate(child);
            }

            child.getCost();
            nextGenPop.solutions.add(child);
            populationSpaceAvailable--;
//            if (AsignacionCuadratica.printNewChildren == true) {    //Esto esta por si quieres ver la impresion de cada hijo
//                System.out.println(child);
//            }
        }
        return nextGenPop;
    }

    //Mutacion sera a un paciente aleatorio cambiar el doctor que se le asigna
    //de esta manera cambia el valor del coste doctores y el de paciente
    public Solution mutate(Solution ind) {
        int num1 = (int) Math.random() * Main.NUM_PATIENTS;
        int num2 = -1;
        boolean exito = false;
        while (!exito) {
            num2 = (int) Math.random() * Main.NUM_DOCTORS;
            Doctor d = Main.doctors.get(num2);
            if (ind.puedeAsignar(d)) {
                exito = true;
            }
        }
        ind.doctorsAsignated[ind.sol[num1]]--;
        ind.cambiarDoctor(num2, num1);

        return ind;
    }

    public Solution crossover(Solution p1, Solution p2) { 
    //genera un hijo entre el padre y la madre
    //Si ocurre que no puede recibir los genes ni de la madre ni del padre pq 
    // no cumple el maximo de pacientes asignados a un doctor
    // asigna a esos pacientes un doctor aleatorio que se le pueda asignar
        Solution child = new Solution();
        int mid = (int) Main.NUM_PATIENTS / 2;
        for (int i = 0; i < mid / 2; i++) {
            child.sol[i] = p1.sol[i];
        }
        for (int j = mid; j < Main.NUM_PATIENTS; j++) {
            
        }
        return child;
    }

    public Solution selectParentViaTournament() { //selecciona un individuo por torneo
        Random rand = new Random();
        // Select individuals randomly from the population
        // WITH a bias towards selecting high fitness individuals
        if (rand.nextDouble() < Main.ELITE_PARENT_RATE) {
            int numElite = (int) (Main.ELITE_PARENT_RATE * Main.POPULATION_SIZE);
            return sorted.get(rand.nextInt(numElite));
        }

        // Otherwise select a parent from the general population with a uniform distribution
        ArrayList<Solution> tournamentPopulation = new ArrayList<Solution>();
        for (int i = 0; i < Main.TOURNAMENT_SIZE; i++) {
            int randIndex = (int) (Math.random() * sorted.size());
            tournamentPopulation.add(sorted.get(randIndex));
        }
        //System.out.println("BEST: "+getBestIndividual(tournamentPopulation));
        return getBestIndividual(tournamentPopulation);
    }

    private Solution rouletteSelection() {
        double totalFitness = 0;
        Random rand = new Random();

        for (int i = 0; i < sorted.size() - 1; i++) { //como estamos trabajando con sorted cogemos su tamaño, en el de la mochila pilla toda la poblacion
            totalFitness += sorted.get(i).getCost();    //entre los que quedan en sorted el fitness total
        }

        //pick the parents based on their percent fitness
        Solution selected = null;
        int check = sorted.size();

        //pick a spot on the roulette (from 0 to 1), and subtract the fractional fitness
        //until we find a roulette-selected parent.             
        double theSpot = rand.nextDouble();
        int j = 0;
        while (j < sorted.size() - 1 && theSpot > 0) {
            theSpot -= sorted.get(j).getCost() / totalFitness;
            j++;
        }
        selected = sorted.get(j);

        return selected;
    }

    private Solution rouletteSelectionPropio() { //referencia usada http://www.aic.uniovi.es/ssii/Tutorial/Seleccion.htm
        double totalFitness = 0;
        Random rand = new Random();

        for (int i = 0; i < sorted.size() - 1; i++) { //como estamos trabajando con sorted cogemos su tamaño, en el de la mochila pilla toda la poblacion
            totalFitness += sorted.get(i).getCost();    //entre los que quedan en sorted el fitness total
        } //lo de arriba es cuando hay que maximizar el problema, si hubiera que minimizarlo habria que hacer totalFitness += (1/sorted.get(i).getCost()); gracias a amdres por la idea :)

        //pick the parents based on their percent fitness
        Solution selected = null;
        int check = sorted.size();
        int numTotal = rand.nextInt((int) totalFitness);    //en vez de int habria que hacerlo con double en caso de minimizar

        //pick a spot on the roulette (from 0 to 1), and subtract the fractional fitness
        //until we find a roulette-selected parent.             
        double total = 0;
        int j = 0;
        while (j < sorted.size() - 1 && numTotal >= total) {
            total += sorted.get(j).getCost();   //si hubiera que minimizar pones total += (1/sorted.get(j).getCost());
            j++;
        }
        selected = sorted.get(j);

        return selected;
    }

    public Solution getBestIndividualInPop() {    //si sorted no es null
        if (sorted != null) {
            return sorted.get(0);
        }
        return getBestIndividual(this.solutions);
    }

    public Solution getBestIndividual(ArrayList<Solution> pop) {    //encuentra al mejor individuo de la poblacion actual
        double minCost = Double.MAX_VALUE;
        int minIndex = -1;
        for (int i = 0; i < pop.size(); i++) {
            if (pop.get(i).getCost() < minCost) {
                minIndex = i;
                minCost = pop.get(i).getCost();
            }
        }
        return pop.get(minIndex);
    }

}
