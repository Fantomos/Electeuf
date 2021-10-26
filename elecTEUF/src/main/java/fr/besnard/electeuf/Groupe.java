/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author nbesnard01
 */
public class Groupe {
    
    private String nom;
    private List<Module> modules;
    
    public Groupe(String mNom, List<Module> mModules){
        this.nom = mNom;
        this.modules = mModules;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }
            
     @Override
    public String toString(){
        return this.nom + " " + this.modules;
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
        final Groupe other = (Groupe) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        
        return Objects.equals(this.modules, other.modules);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.modules);
        return hash;
    }
    
    
}
