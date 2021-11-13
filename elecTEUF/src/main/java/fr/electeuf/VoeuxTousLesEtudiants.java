/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.electeuf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author nbesnard01
 */
public class VoeuxTousLesEtudiants {
    
    private Map<Etudiant,VoeuxUnEtudiant> listeVoeux;
    
    public VoeuxTousLesEtudiants(){
        this.listeVoeux = new HashMap<>();
    }
    
    public void ajouterChoix(Etudiant mEtudiant, VoeuxUnEtudiant mChoixUnEtudiant){
        listeVoeux.put(mEtudiant, mChoixUnEtudiant);
    }

    public Map<Etudiant, VoeuxUnEtudiant> getChoix() {
        return listeVoeux;
    }

    public List<Etudiant> getListeEtudiants(){
        return new ArrayList<>(this.listeVoeux.keySet());
    }
    
    public static VoeuxTousLesEtudiants genererChoixTousLesEtudiants(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes) {
        VoeuxTousLesEtudiants voeuxTousLesEtudiants = new VoeuxTousLesEtudiants();
        for(Etudiant etudiant : listeEtudiants){
            voeuxTousLesEtudiants.ajouterChoix(etudiant, VoeuxUnEtudiant.genererChoixUnEtudiant(etudiant, listeGroupes));
        }
        return voeuxTousLesEtudiants;

    }

    @Override
    public String toString(){

        String str = "";
        for (Map.Entry<Etudiant, VoeuxUnEtudiant> entry : this.listeVoeux.entrySet()) {
            str += "\n--------------------- VOEUX DE " + entry.getKey() + " ---------------------\n";
            str += entry.getValue();
            str += "---------------------------------------------------------------------------\n";
        }
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(1, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        System.out.println(listeGroupes);
        System.out.println(genererChoixTousLesEtudiants(listeEtudiants, listeGroupes));

    }
   
    
                
   
}
