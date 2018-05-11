package Principal;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author yo
 */
public class Population {

    public ArrayList<Solution> solutions = new ArrayList<Solution>();
    private ArrayList<Solution> sorted;

    public void initializePopulationRandomly(int numSolutions) {  //la primera generacion debe iniciarse, lo hacemos de forma random
        for (int i = 0; i < numSolutions; i++) {
            Solution s = new Solution();
            s.generateIndividual();
            //System.out.println(s);
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
        Population nextGenPop = new Population();
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
        // Keep the best individual para la siguiente poblacion
        nextGenPop.solutions.add(sorted.get(0));
        sorted.remove(0);   //el mejor pasa, no lo elimino de la poblacion, se queda para poder usarlo en torneo
        --populationSpaceAvailable;
        // Añade el top de la poblacion a la siguiente generación
        int numElite = (int) (Main.POPULATION_SIZE * Main.ELITE_PERCENT);
        for (int i = 1; i < numElite; i++) {

            if (Math.random() < Main.MUTATION_RATE) {
                nextGenPop.solutions.add(mutate(sorted.get(i)));
                nextGenPop.solutions.get(nextGenPop.solutions.size() - 1).getCost();
            } else {
                nextGenPop.solutions.add(sorted.get(i));
                nextGenPop.solutions.get(nextGenPop.solutions.size() - 1).getCost();
            }
            populationSpaceAvailable--;

        }
        // STEP 2: Select 2 parents from population and generate children
        while (populationSpaceAvailable > 0) {

            // STEP 2: Genera un hijo a partir de 2* padres
            Solution p1 = selectParentViaTournament();
            Solution p2 = selectParentViaTournament();
            Solution child = crossover(p1, p2);
            // STEP 3: Añade la mutacion al hijo
            if (Math.random() < Main.MUTATION_RATE) {
                mutate(child);
            }
            child.getCost();
            nextGenPop.solutions.add(child);
            populationSpaceAvailable--;
        }
        return nextGenPop;
    }

    //Mutacion sera a un paciente aleatorio cambiar el doctor que se le asigna
    //de esta manera cambia el valor del coste doctores y el de paciente
    public Solution mutate(Solution sol) {
        ArrayList<Integer> bagDoc = new ArrayList();
        ArrayList<Integer> bagPat = new ArrayList();
        for (int i = 0; i < Main.doctors.size(); i++) {
            bagDoc.add(i);
        }
        for (int j = 0; j < Main.patients.size(); j++) {
            bagPat.add(j);
        }
        //utilizamos una mochila para evitar caer en el mismo paciente aleatorio
        // o el mismo doctor una y otra vez cuando ya hemos comprobado que no sirve
        int num1 = -1;
        int num2 = -1;
        boolean exito = false;
        while (!exito) {

            int num1Aux = (int) Math.random() * bagPat.size();
            num1 = bagPat.get(num1Aux);
            bagPat.remove(num1Aux);
            //ANTES SE LE ASIGNABA UN PACIENTE
            while (!exito) {

                int num2aux = (int) Math.random() * bagDoc.size();
                num2 = bagDoc.get(num2aux);
                bagDoc.remove(num2aux);

                Doctor d = Main.doctors.get(num2);
                if (sol.puedeAsignar(d)) {
                    exito = true;
                } else {
                    System.out.println("Error mutate");
                    Main.error++;
                }
            }

        }
        sol.doctorsAsignated[sol.sol[num1]]--;
        sol.cambiarDoctor(num2, num1);
        sol.calculateCost();
        return sol;
    }

    //OPERADOR CRUCE
    public Solution crossover(Solution p1, Solution p2) {
        //genera un hijo entre el padre y la madre
        //Si ocurre que no puede recibir los genes ni de la madre ni del padre pq 
        // no cumple el maximo de pacientes asignados a un doctor
        // asigna a esos pacientes un doctor aleatorio que se le pueda asignar
        Solution child = new Solution();
        int mid = (int) Main.NUM_PATIENTS / 2;
        for (int i = 0; i < mid; i++) {
            child.sol[i] = p1.sol[i];
            child.doctorsAsignated[child.sol[i]]++;
        }
        //A partir de aqui pueden haber problemas
        for (int j = mid; j < Main.NUM_PATIENTS; j++) {
            Doctor d = Main.doctors.get(p2.sol[j]);
            if (child.puedeAsignar(d)) {

                child.sol[j] = p2.sol[j];

            } else {
                //En el caso de que falle buscamos otra solucion del array de sorted
                System.out.println("Error cruce");
                int padreAux = (int) Math.random() * sorted.size();
                Solution p3 = sorted.get(padreAux);
                int k = j;
                while (k < Main.NUM_PATIENTS) {
                    d = Main.doctors.get(p3.sol[k]);
                    if (child.puedeAsignar(d)) {
                        child.sol[k] = p3.sol[k];
                    } else {
                        //si vuelve a fallar cambiamos el padre hasta que pueda seguir
                        // y continuamos copiando el array
                        boolean cambiar = false;
                        while (!cambiar) {
                            padreAux = (int) Math.random() * sorted.size();
                            p3 = sorted.get(padreAux);
                            d = Main.doctors.get(p3.sol[k]);
                            if (child.puedeAsignar(d)) {
                                cambiar = true;
                                child.sol[k] = p3.sol[k];
                            }
                        }
                    }
                }
            }
        }
//IMPLEMENTATION 2 OF CROSSOVER
//        for (int i = 0; i < p1.sol.length; i++) {
//            if (Math.random() < 0.5) {
//                child.sol[i] = p1.sol[i];
//                child.doctorsAsignated[p1.sol[i]]++;
//            }else{
//                child.sol[i] = p2.sol[i];
//                child.doctorsAsignated[p2.sol[i]]++;
//            }
//        }
        child.calculateCost();
        return child;
    }

    //seleccion por torneo
    public Solution selectParentViaTournament() {
        Random rand = new Random();

        // Selecciona individuos
        // con una preferencia sobre los mejores individuos
        if (rand.nextDouble() < Main.ELITE_PARENT_RATE) {
            int numElite = (int) (Main.ELITE_PARENT_RATE * Main.POPULATION_SIZE);
            return sorted.get(rand.nextInt(numElite));
        }

        // Si no selecciona individuos de la poblacion total
        ArrayList<Solution> tournamentPopulation = new ArrayList<Solution>();
        for (int i = 0; i < Main.TOURNAMENT_SIZE; i++) {
            int randIndex = (int) (Math.random() * sorted.size());
            tournamentPopulation.add(sorted.get(randIndex));
        }
        return getBestIndividual(tournamentPopulation);
    }

    //Devuelve el mejor individual
    public Solution getBestIndividualInPop() {
        if (sorted != null) {
            return sorted.get(0);
        }
        return getBestIndividual(this.solutions);
    }

    //Devuelve el mejor individuo de la poblacion
    public Solution getBestIndividual(ArrayList<Solution> pop) {
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
