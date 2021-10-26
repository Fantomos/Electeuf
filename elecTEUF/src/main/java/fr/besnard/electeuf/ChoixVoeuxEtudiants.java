/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.List;

/**
 *
 * @author nbesnard01
 */
public class ChoixVoeuxEtudiants {
    
    private Etudiant etudiant;
    private Groupe groupe;
    private List<Module> voeuxModules;
    
    public ChoixVoeuxEtudiants(Etudiant mEtudiant, Groupe mGroupe, List<Module> mVoeuxModules){
        this.etudiant = mEtudiant;
        this.groupe = mGroupe;
        this.voeuxModules = mVoeuxModules;    
    }
    
    
    
    
}
