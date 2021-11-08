/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author nbesnard01
 */
public class ChoixTousLesEtudiants {
    
    private Map<Etudiant,ChoixUnEtudiant> choix;
    
    public ChoixTousLesEtudiants(){
        this.choix = new HashMap<>();
    }
    
    public void ajouterChoix(Etudiant mEtudiant, ChoixUnEtudiant mChoixUnEtudiant){
        choix.put(mEtudiant, mChoixUnEtudiant);
    }
    
    public void supprimerChoix(Etudiant mEtudiant){
        choix.remove(mEtudiant);
    }

    public Map<Etudiant, ChoixUnEtudiant> getChoix() {
        return choix;
    }
    
    
                
   
}
