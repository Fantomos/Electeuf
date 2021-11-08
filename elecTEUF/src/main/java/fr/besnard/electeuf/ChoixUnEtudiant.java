/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author nbesnard01
 */
public class ChoixUnEtudiant {
    private Map<Groupe,List<Module>> choixPourChaqueGroupe;
    private static final int NOMBRE_VOEUX = 3;
    
    public ChoixUnEtudiant(){
        this.choixPourChaqueGroupe = new HashMap<>();
    }
    
    public void ajouterChoix(Groupe mGroupe, List<Module> mListModule){
        this.choixPourChaqueGroupe.put(mGroupe, mListModule);
    }

    public Map<Groupe, List<Module>> getChoixPourChaqueGroupe() {
        return choixPourChaqueGroupe;
    }
    
    public static ChoixUnEtudiant genererChoixUnEtudiantAlea(Etudiant etudiant, List<Groupe> listeGroupe) {
        ChoixUnEtudiant choix = new ChoixUnEtudiant();
        Random r = new Random();
        for(Groupe groupe : listeGroupe){
            List<Module> listeModuleOrginale = new ArrayList<>();
            for(Module module : groupe.getModules()){
                if(module.getClasses().contains(etudiant.getClasse())){
                    listeModuleOrginale.add(module);
                }
            }
            
            List<Module> listeModuleVoeux = new ArrayList<>();
            for(int i=0; i<NOMBRE_VOEUX;i++){
                int randomIndex = r.nextInt(listeModuleOrginale.size());
                listeModuleVoeux.add(listeModuleOrginale.get(randomIndex));
                listeModuleOrginale.remove(randomIndex);
            }
            choix.ajouterChoix(groupe, listeModuleVoeux);
        }
        return choix;
    }

    @Override
    public String toString(){
        String str = "";
        for (Map.Entry<Groupe, List<Module>> entry : this.choixPourChaqueGroupe.entrySet()) {
            str += entry.getKey().getNom() + " : " + entry.getValue() + "\n";
        }
        return str;
    }
}
