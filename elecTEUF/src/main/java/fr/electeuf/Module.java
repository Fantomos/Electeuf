/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.electeuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author nbesnard01
 */
public class Module {
    private String intitule;
    private List<Classe> classesAcceptees;
    private List<Classe> classesPrioritaires;
    private List<Classe> classesReticentes;
    private int nbPlaceMin;
    private int nbPlaceOpti;
    private int nbPlaceMax;
    private int creneau;
    private int NB_PLACE_MIN_DEFAUT = 16;
    private int NB_PLACE_OPTI_DEFAUT = 20;
    private int NB_PLACE_MAX_DEFAUT = 24;

    public Module (String mIntitule, int mCreneau, int mNbPlaceMin, int mNbPlaceOpti, int mNbPLaceMax, List<Classe> mClassesAcceptees){
        this.intitule = mIntitule;
        this.classesAcceptees = mClassesAcceptees;
        this.classesPrioritaires = new ArrayList<>();
        this.classesReticentes = new ArrayList<>();
        this.creneau = mCreneau;
        this.nbPlaceMin = mNbPlaceMin;
        this.nbPlaceOpti = mNbPlaceOpti;
        this.nbPlaceMax = mNbPLaceMax;
    }

    public Module (String mIntitule, List<Classe> mClassesAcceptees){
        this(mIntitule,1, 16, 20, 24, mClassesAcceptees);
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

    public int getNbPlaceMax() {
        return nbPlaceMax;
    }

    public int getNbPlaceMin() {
        return nbPlaceMin;
    }

    public int getNbPlaceOpti() {
        return nbPlaceOpti;
    }

    public List<Classe> getClassesAcceptees() {
        return classesAcceptees;
    }

    public List<Classe> getClassesPrioritaires() {
        return classesPrioritaires;
    }

    public List<Classe> getClassesReticentes() {
        return classesReticentes;
    }

    public void setClasses(List<Classe> classes) {
        this.classesAcceptees = classes;
    }

    public void setNbPlace(int nbPlace) {
        this.nbPlaceMax = nbPlace;
    }
    
  

    
    @Override
    public String toString() {
        return "\nIntitule = " + intitule + " (" + creneau + ") | Place (Min/Opti/Max)= " + nbPlaceMin + "/" + nbPlaceOpti + "/" + nbPlaceMax + " | Classes (Acceptees/Prioritaires/Reticentes) = " + classesAcceptees + "/" + classesPrioritaires + "/" + classesReticentes;
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
        if(!Objects.equals(this.creneau, other.creneau)){
            return false;
        }
     
        return Objects.equals(this.classesAcceptees, other.classesAcceptees);
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.intitule);
        hash = 37 * hash + Objects.hashCode(this.creneau);
        hash = 37 * hash + Objects.hashCode(this.classesAcceptees);
        return hash;
    }

}
