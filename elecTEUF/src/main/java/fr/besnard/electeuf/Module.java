/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

/**
 *
 * @author nbesnard01
 */
public class Module {
    private String intitule;
    private int nbPlace;
    
    public Module (String mIntitule, int mNbPlace){
        this.intitule = mIntitule;
        this.nbPlace = mNbPlace;
    }
   
    public Module(){
        this("Vide", 1);
    }
    
    
    
}
