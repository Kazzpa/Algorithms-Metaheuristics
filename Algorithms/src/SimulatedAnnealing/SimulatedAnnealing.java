package SimulatedAnnealing;

import Principal.Doctor;
import Principal.Solution;
import static Principal.Main.*;

/**
 *
 * @author kzr
 */
public class SimulatedAnnealing {

    public boolean memetic = false;
    Solution memSol = null;

    public void run() {

        // Set initial temp
        double temp = 100000;

        // Cooling rate
        double coolingRate = 0.03;
        // Initialize intial solution

        Solution currentSolution = new Solution();
        currentSolution.generateIndividual();
        if (memetic) {
            currentSolution = memSol;
        }
        if (!memetic) {
            System.out.println("SimulatedAnnealing:\nInitial solution distance: " + currentSolution.getCost());
        }

        // Set as current best
        Solution best = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated());

        // Loop until system has cooled
        while (temp > 1) {

            // Create new neighbour tour
            //Solution newSolution = new Solution(currentSolution.getSol());
            Solution newSolution = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated());
            // Generamos un doctor aleatorio que no tenga el maximo de pacientes
            boolean exito = false;
            int numDoc = 0;
            while (!exito) {

                numDoc = (int) (doctors.size() * Math.random());
                Doctor d = doctors.get(numDoc);
                if (newSolution.puedeAsignar(d)) {
                    exito = true;
                }
            }
            int numPac = (int) (patients.size() * Math.random());
            newSolution.doctorsAsignated[newSolution.sol[numPac]]--;
            //CAMBIAMOS EL DOCTOR ASIGNADO AL PACIENTE POR EL GENERADO ALEATORIO
            newSolution.cambiarDoctor(numDoc, numPac);

            // Get energy of solutions
            double currentEnergy = currentSolution.getCost();
            double neighbourEnergy = newSolution.getCost();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) >= Math.random()) {
                //System.out.println("Aceptada");
                currentSolution = new Solution(newSolution.getSol(), newSolution.getDoctorsAsignated());
            }

            // Keep track of the best solution found
            if (currentSolution.getCost() < best.getCost()) {
                best = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated());

            }

            // Cool system when the solution is better
            temp *= 1 - coolingRate;

        }
        if (!memetic) {

            System.out.println("Final solution distance: " + best.getCost()
            +  "\nCoste medicos: "+ best.getDinero()+ "\tDistancia "
            + "total: "+ best.getDistancia());
            
        }
    }

    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        //System.out.println(Math.exp(((energy - newEnergy) ) / temperature));
        return Math.exp((energy - newEnergy) / temperature);
    }

    public void run(Solution s) {
        //System.out.println("Busqueda ini");
        memetic = true;
        memSol = s;
        run();
        //System.out.print("Busquedafin");
    }
}
