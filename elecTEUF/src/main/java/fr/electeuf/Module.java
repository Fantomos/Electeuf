/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.electeuf;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nbesnard01
 */
public class Module {
    private String intitule;
    private List<Classe> classes;
    private int nbPlaceMin;
    private int nbPlaceOpti;
    private int nbPlaceMax;
    private int NB_PLACE_MIN_DEFAUT = 16;
    private int NB_PLACE_OPTI_DEFAUT = 20;
    private int NB_PLACE_MAX_DEFAUT = 24;

    
    public Module (String mIntitule, List<Classe> mClasses){
        this.intitule = mIntitule;
        this.classes = mClasses;
        this.nbPlaceMin = NB_PLACE_MIN_DEFAUT;
        this.nbPlaceOpti = NB_PLACE_OPTI_DEFAUT;
        this.nbPlaceMax = NB_PLACE_MAX_DEFAUT;
    }
   
    public Module(){
        this("Vide", Arrays.asList(new Classe()));
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public int getNbPlace() {
        return nbPlaceMax;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlaceMax = nbPlace;
    }
    
  

    
    @Override
    public String toString() {
        return "\nIntitule = " + intitule + " | Place = " + nbPlaceMax + " | Classes = " + classes;
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
        if(!Objects.equals(this.intitule, other.intitule)){
            return false;
        }
     
        return Objects.equals(this.classes, other.classes);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.intitule);
        hash = 37 * hash + Objects.hashCode(this.classes);
        return hash;
    }
}
