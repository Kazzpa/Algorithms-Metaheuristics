/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SimulatedAnnealing;

import java.util.LinkedList;

/**
 *
 * @author kzr
 */
public class Solution {
    public LinkedList<Doctor> contratados;
    public Solution(){
        contratados = new LinkedList<Doctor>();
        
    }
    public Solution(LinkedList<Doctor> contratados){
        this.contratados = contratados;
    }
    public void generateIndividual(){
        
    }
}
