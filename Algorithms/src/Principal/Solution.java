package Principal;

import static Principal.Main.*;
import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author kzr
 */
//for a solution there must be a doctor assigned to every patient
public class Solution {

    public int[] sol;
    public int[] doctorsAsignated;
    public int numPatients;

    public Solution() {
        doctorsAsignated = new int[doctors.size()];
        sol = new int[patients.size()];
    }

    public Solution(int[] sol, int[] doctorsASignated) {
        this.sol = new int[patients.size()];
        this.doctorsAsignated = new int[doctors.size()];
        System.arraycopy(sol, 0, this.sol, 0, this.sol.length);
        System.arraycopy(doctorsASignated, 0, this.doctorsAsignated, 0, this.doctorsAsignated.length);
        numPatients = patients.size();
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
        //System.out.print("\tBALANCEADO doctores: "+costDoctors+" \tpacientes: "+costPatients+"\n");
        cost = costPatients + costDoctors;
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
                    numPatients++;
                    sol[i] = num;
                    exito = true;
                }

            }
        }
    }

    //Comprobamos si un doctor puede asignar a un nuevo paciente
    // comprobando cuantos pacientes tiene ya asignado y a cuantos puede asignar
    public boolean puedeAsignar(Doctor d) {
        boolean res = false;
        int x = doctors.indexOf(d);
//        int numAsignados = 0;
//        int i = 0;
//        while (i < numPatients) {
//            if (sol[i] == x) {
//                numAsignados++;
//            }
//            i++;
//        }
        //Si el numero de ya asignados es menor al de pacientes maximo
        //puede asignar a un nuevo paciente
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
