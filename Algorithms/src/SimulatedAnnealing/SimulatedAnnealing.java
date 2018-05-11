package SimulatedAnnealing;

import Principal.Doctor;
import Principal.Main;
import Principal.Solution;
import static Principal.Main.*;
import java.util.ArrayList;

/**
 *
 * @author kzr
 */
public class SimulatedAnnealing {

    public boolean memetic = false;
    Solution memSol = null;

    
    public void run(Solution s) {
        memetic = true;
        memSol = s;
        run();
    }
    public void run() {

        // Set initial temp
        double temp = Main.TEMPERATURE;

        // Cooling rate
        double coolingRate = 0.03;

        Solution currentSolution = new Solution();
        currentSolution.generateIndividual();
        if (memetic) {

            coolingRate = 0.10;
            currentSolution = memSol;
        } else {
            //System.out.println("SimulatedAnnealing:\nInitial solution distance: " + currentSolution.getCost());
        }

        // Set as current best
        Solution best = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated(), currentSolution.getCost());

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            //Solution newSolution = new Solution(currentSolution.getSol());
            Solution newSolution = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated(), currentSolution.getCost());
            // Generamos un doctor aleatorio que no tenga el maximo de pacientes
            newSolution = neighbour(newSolution);
//            boolean exito = false;
//            int numDoc = 0;
//            while (!exito) {
//
//                numDoc = (int) (doctors.size() * Math.random());
//                Doctor d = doctors.get(numDoc);
//                if (newSolution.puedeAsignar(d)) {
//                    exito = true;
//                }
//            }
//            int numPac = (int) (patients.size() * Math.random());
//            newSolution.doctorsAsignated[newSolution.sol[numPac]]--;
//            //CAMBIAMOS EL DOCTOR ASIGNADO AL PACIENTE POR EL GENERADO ALEATORIO
//            newSolution.cambiarDoctor(numDoc, numPac);
            newSolution.calculateCost();
            // Get energy of solutions
            double currentEnergy = currentSolution.getCost();
            double neighbourEnergy = newSolution.getCost();

            // Decide if we should accept the neighbour
            if (acceptanceProbability(currentEnergy, neighbourEnergy, temp) >= Math.random()) {
                //System.out.println("Aceptada");
                currentSolution = new Solution(newSolution.getSol(), newSolution.getDoctorsAsignated(), newSolution.getCost());
            }

            // Keep track of the best solution found
            if (currentSolution.getCost() < best.getCost()) {
                best = new Solution(currentSolution.getSol(), currentSolution.getDoctorsAsignated(), currentSolution.getCost());

            }

            // Cool system when the solution is better
            temp *= 1 - coolingRate;

        }
        if (!memetic&& !Main.DEBUG) {

            System.out.println("Simulated Annealing:\n" + "Final solution distance: " + best.getCost()
                    + "\nCoste medicos: " + best.getDinero() + " €\tDistancia "
                    + "total: " + best.getDistancia() + " km");

        }
        if(Main.DEBUG){
            System.out.println(best.getCost());
        }
        
    }

    public Solution neighbour(Solution sol) {
        ArrayList<Integer> bagDoc = new ArrayList();
        ArrayList<Integer> bagPat = new ArrayList();
        for (int i = 0; i < Main.doctors.size(); i++) {
            bagDoc.add(i);
        }
        for (int j = 0; j < Main.patients.size(); j++) {
            bagPat.add(j);
        }

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

    public static double acceptanceProbability(double energy, double newEnergy, double temperature) {
        // If the new solution is better, accept it
        if (newEnergy < energy) {
            return 1.0;
        }
        // If the new solution is worse, calculate an acceptance probability
        //System.out.println(Math.exp(((energy - newEnergy) ) / temperature));
        return Math.exp((energy - newEnergy) / temperature);
    }

}
