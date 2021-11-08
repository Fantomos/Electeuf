/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nbesnard01
 */
public class ChoixUnEtudiant {
    private Map<Groupe,List<Module>> choixPourChaqueGroupe;
    
    public ChoixUnEtudiant(){
        this.choixPourChaqueGroupe = new HashMap<>();
    }
    
    public void ajouterChoix(Groupe mGroupe, List<Module> mListModule){
        this.choixPourChaqueGroupe.put(mGroupe, mListModule);
    }

    public Map<Groupe, List<Module>> getChoixPourChaqueGroupe() {
        return choixPourChaqueGroupe;
    }
    
    public static ChoixUnEtudiant genererChoixUnEtudiantAlea(int nbGroupe) {
        
    }
}
