/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimulatedAnnealing;


/**
 *
 * @author kzr
 */
public class Doctor {

    public int Salary;
    public int numPacientes;
    public double x;
    public double y;

    public Doctor(){
        
    }
    public Doctor(int Sueldo,int numPacientes, double x, double y) {
        this.Salary = Sueldo;
        this.numPacientes = numPacientes;
        this.x = x;
        this.y = y;
    }

    public int getSalary() {
        return Salary;
    }

    public int getNumPacientes() {
        return numPacientes;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setSalary(int Salary) {
        this.Salary = Salary;
    }

    public void setNumPacientes(int numPacientes) {
        this.numPacientes = numPacientes;
    }

    
    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    //CALCULATEDISTANCETOPATIENTS
//    public double getDistance() {
//        double totalDistance = 0;
//        Iterator it = asignated.iterator();
//        while (it.hasNext()) {
//            Patient p = (Patient) it.next();
//            totalDistance += getDistance(p);
//        }
//        return totalDistance;
//    }
//
//    public double getDistance(Patient p) {
//        double res = 0;
//        res = Math.sqrt( Math.pow( Math.abs( x - p.getX()), 2) + Math.pow( Math.abs( y - p.getY() ), 2));
//        return res;
//    }
}
