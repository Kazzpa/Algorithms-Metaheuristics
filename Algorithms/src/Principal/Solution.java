package Principal;

import static Principal.Main.*;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author kzr
 */
//for a solution a patient must have asigned a doctor
public class Solution {

    //Array de entero que indica que doctor esta asignado a cada paciente
    public int[] sol;
    //Array que indica cuantos pacientes tiene cada doctor
    public int[] doctorsAsignated;

    public Solution() {
        doctorsAsignated = new int[doctors.size()];
        sol = new int[patients.size()];
    }

    public Solution(int[] sol, int[] doctorsASignated) {
        this.sol = new int[patients.size()];
        this.doctorsAsignated = new int[doctors.size()];
        System.arraycopy(sol, 0, this.sol, 0, this.sol.length);
        System.arraycopy(doctorsASignated, 0, this.doctorsAsignated, 0, this.doctorsAsignated.length);
    }

    public int[] getSol() {
        return sol;
    }

    public int[] getDoctorsAsignated() {
        return doctorsAsignated;
    }

    public double getCost() {
        double cost;
        //costDoctors is the average of the doctors salaries
        double costDoctors = 0;
        //costPatients is the average of the distances to the doctor asignated
        double costPatients = 0;
        LinkedList doctorAux = new LinkedList();
        Doctor d;
        Patient p;
        for (int i = 0; i < sol.length; i++) {
            p = patients.get(i);

            d = doctors.get(sol[i]);

            if (!doctorAux.contains(sol[i])) {
                doctorAux.add(sol[i]);
                costDoctors += d.getSalary();
            }
            costPatients += Math.sqrt(Math.pow(Math.abs(d.getX() - p.getX()), 2) + Math.pow(Math.abs(d.getY() - p.getY()), 2));

        }
        //Normalize valors:
        costDoctors /= doctorAux.size();
        costDoctors /= SALARY_MAX;
        costPatients /= patients.size();
        costPatients /= Math.sqrt(2 * Math.pow(COORDINATES_MAX, 2));
        cost = (costPatients + costDoctors) * 10000;
        return cost;
    }

    //We need to asignate a doctor to every patient
    public void generateIndividual() {
        Random rnd = new Random();
        int num;
        int numDoctors = doctors.size();
        //Asignamos a un paciente un doctor aleatorio
        for (int i = 0; i < patients.size(); i++) {
            boolean exito = false;
            while (!exito) {
                num = rnd.nextInt(numDoctors);
                Doctor d = doctors.get(num);
                if (puedeAsignar(d)) {
                    doctorsAsignated[num]++;
                    sol[i] = num;
                    exito = true;
                }

            }
        }
    }

    //Comprobamos si un doctor puede asignar a un nuevo paciente
    public boolean puedeAsignar(Doctor d) {
        boolean res = false;
        int x = doctors.indexOf(d);
        //consulta el array de doctores para ver si excede el limite que tiene
        if (doctorsAsignated[x] < d.getNumPacientes()) {
            res = true;
        }
        return res;
    }

    public void cambiarDoctor(int doctor, int paciente) {
        sol[paciente] = doctor;
        doctorsAsignated[doctor]++;
    }
}
