/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimulatedAnnealing;

import static Principal.Main.*;
import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author kzr
 */
//for a solution there must be a doctor assigned to every patient
public class Solution {

    public boolean[][] sol;

    public Solution() {
        int numPatients = patients.size();
        int numDoctors = doctors.size();
        sol = new boolean[numPatients][numDoctors];
    }

    public Solution(boolean[][] sol) {
        this.sol = new boolean [sol.length][sol[0].length];
        for(int i = 0;i < sol.length;i++){
            for(int j = 0; j < sol[0].length;j++){
                this.sol[i][j] = sol[i][j];
            }
        }
    }

    public boolean[][] getSol() {
        return sol;
    }

    public double getCost() {
        double cost = 0;
        //NEEDS IMPLEMENTATION
        return cost;
    }

    //We need to asignate a doctor to every patient
    public void generateIndividual() {
        Random rnd = new Random();
        int num;
        int numPatients = patients.size();
        int numDoctors = doctors.size();
        //Asignamos a un paciente un doctor aleatorio
        for (int i = 0; i < numPatients; i++) {
            boolean exito = false;

            while (!exito) {
                num = rnd.nextInt(numDoctors);
                Doctor d = doctors.get(num);

                if (puedeAsignar(d)) {
                    sol[i][num] = true;
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
        int numAsignados = 0;
        boolean exito = false;
        int i = 0;
        while (!exito && i < patients.size()) {
            if (sol[i][x]) {
                numAsignados++;
            }

            i++;
        }
        //Si el numero de ya asignados es menor al de pacientes maximo
        //puede asignar a un nuevo paciente
        if (numAsignados < d.getNumPacientes()) {
            res = true;
        }
        return res;
    }

    void cambiarDoctor(int doctor, int paciente) {
        //Buscamos cual es el doctor asignado al paciente indicado por el entero

        boolean exito = false;
        int j = 0;
        while (!exito && j < sol[0].length) {

            if (sol[paciente][j]) {
                exito = true;
                sol[paciente][j] = false;
            }
            j++;
        }
        sol[paciente][doctor] = true;
    }
}
