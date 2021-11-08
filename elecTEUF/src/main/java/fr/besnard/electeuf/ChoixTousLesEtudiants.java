/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
    
    public static ChoixTousLesEtudiants genererChoixTousLesEtudiants(int nbEtudiantParSpe, int nbGroupe) throws FileNotFoundException, IOException {
        ChoixTousLesEtudiants choixComplet = new ChoixTousLesEtudiants();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(nbEtudiantParSpe, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(nbGroupe);
        for(Etudiant etudiant : listeEtudiants){
            choixComplet.ajouterChoix(etudiant, ChoixUnEtudiant.genererChoixUnEtudiantAlea(etudiant, listeGroupes));
        }
        return choixComplet;

    }

    @Override
    public String toString(){

        String str = "";
        for (Map.Entry<Etudiant, ChoixUnEtudiant> entry : this.choix.entrySet()) {
            str += "\n--------------------- VOEUX DE " + entry.getKey() + " ---------------------\n";
            str += entry.getValue();
            str += "---------------------------------------------------------------------------\n";
        }
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        System.out.println(genererChoixTousLesEtudiants(1, 2));

    }
   
    
                
   
}
