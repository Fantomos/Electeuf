/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.Objects;

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

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getNbPlace() {
        return nbPlace;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlace = nbPlace;
    }
    
    @Override
    public String toString() {
        return "Module{" + "intitule=" + intitule + ", nbrPlace=" + nbPlace + '}';
    }
  
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Module other = (Module) obj;
        return Objects.equals(this.intitule, other.intitule);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.intitule);
        return hash;
    }
}
