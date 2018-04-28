/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimulatedAnnealing;

import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author kzr
 */
public class Doctor {

    public LinkedList<Patient> asignated;
    public int Salary;
    public double x;
    public double y;

    public Doctor(int Sueldo, double x, double y) {
        this.Salary = Sueldo;
        this.x = x;
        this.y = y;
    }

    public int getSalary() {
        return Salary;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void addPatient(Patient p) {
        if (!asignated.contains(p)) {
            asignated.add(p);
        }
    }

    public void removePatient(Patient p) {
        asignated.remove(p);
    }

    public double getDistance() {
        double totalDistance = 0;
        Iterator it = asignated.iterator();
        while (it.hasNext()) {
            Patient p = (Patient) it.next();
            totalDistance += getDistance(p);
        }
        return totalDistance;
    }

    public double getDistance(Patient p) {
        double res = 0;
        res = Math.sqrt( Math.pow( Math.abs( x - p.getX()), 2) + Math.pow( Math.abs( y - p.getY() ), 2));
        return res;
    }
}
