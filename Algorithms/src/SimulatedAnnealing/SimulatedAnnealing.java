package SimulatedAnnealing;

import static Principal.Main.*;

/**
 *
 * @author kzr
 */
public class SimulatedAnnealing {

    public static void run() {

        // Set initial temp
        double temp = 10000;

        // Cooling rate
        double coolingRate = 0.003;

        // Initialize intial solution
        Solution currentSolution = new Solution();
        currentSolution.generateIndividual();

        System.out.println("Initial solution distance: " + currentSolution.getCost());

        // Set as current best
        Solution best = new Solution(currentSolution.getSol());

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Solution newSolution = new Solution(currentSolution.getSol());

            // Generamos un doctor aleatorio que no tenga el maximo de pacientes
            boolean exito = false;
            int num1 = 0;
            while (!exito) {

                num1 = (int) (doctors.size() * Math.random());
                Doctor d = doctors.get(num1);
                if (newSolution.puedeAsignar(d)) {
                    exito = true;
                }
            }
            int num2 = (int) (patients.size() * Math.random());
            //CAMBIAMOS EL DOCTOR ASIGNADO AL PACIENTE POR EL GENERADO ALEATORIO
            newSolution.cambiarDoctor(num1, num2);

            // Get energy of solutions
            double currentEnergy = currentSolution.getCost();
            double neighbourEnergy = newSolution.getCost();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) > Math.random()) {
                currentSolution = new Solution(newSolution.getSol());
            }

            // Keep track of the best solution found
            if (currentSolution.getCost() < best.getCost()) {
                best = new Solution(currentSolution.getSol());
            }

            // Cool system
            temp *= 1 - coolingRate;
        }

        System.out.println("Final solution distance: " + best.getDistance());
        System.out.println("Tour: " + best);
    }

    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        return Math.exp((energy - newEnergy) / temperature);
    }
}
