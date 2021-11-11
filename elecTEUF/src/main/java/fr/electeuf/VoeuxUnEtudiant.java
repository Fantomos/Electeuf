/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.electeuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author nbesnard01
 */
public class VoeuxUnEtudiant {
    private Map<Groupe,List<Module>> voeuxPourChaqueGroupe;
    private static final int NOMBRE_VOEUX = 3;
    
    public VoeuxUnEtudiant(){
        this.voeuxPourChaqueGroupe = new HashMap<>();
    }
    
    public void ajouterChoix(Groupe mGroupe, List<Module> mListModule){
        this.voeuxPourChaqueGroupe.put(mGroupe, mListModule);
    }

    public Map<Groupe, List<Module>> getChoixPourChaqueGroupe() {
        return voeuxPourChaqueGroupe;
    }
    
    public static VoeuxUnEtudiant genererChoixUnEtudiant(Etudiant etudiant, List<Groupe> listeGroupe) {
        VoeuxUnEtudiant choix = new VoeuxUnEtudiant();
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
        for (Map.Entry<Groupe, List<Module>> entry : this.voeuxPourChaqueGroupe.entrySet()) {
            str += entry.getKey().getNom() + " : " + entry.getValue() + "\n";
        }
        return str;
    }
}
