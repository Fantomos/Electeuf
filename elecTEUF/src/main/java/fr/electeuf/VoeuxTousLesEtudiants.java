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
    
    public void ajouterVoeux(Etudiant mEtudiant, VoeuxUnEtudiant mChoixUnEtudiant){
        listeVoeux.put(mEtudiant, mChoixUnEtudiant);
    }

    public Map<Etudiant, VoeuxUnEtudiant> getVoeux() {
        return listeVoeux;
    }

    public List<Etudiant> getListeEtudiants(){
        return new ArrayList<>(this.listeVoeux.keySet());
    }
    
    public static VoeuxTousLesEtudiants genererVoeuxTousLesEtudiants(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes) {
        VoeuxTousLesEtudiants voeuxTousLesEtudiants = new VoeuxTousLesEtudiants();
        for(Etudiant etudiant : listeEtudiants){
            voeuxTousLesEtudiants.ajouterVoeux(etudiant, VoeuxUnEtudiant.genererVoeuxUnEtudiant(etudiant, listeGroupes));
        }
        return voeuxTousLesEtudiants;

    }

    public List<Groupe> getListeGroupes(){
        VoeuxUnEtudiant voeux = (VoeuxUnEtudiant) listeVoeux.values().toArray()[0];
        return voeux.getListeGroupes();
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
        List<List<String>> listeAnnuaire = Etudiant.genererAnnuaireDuTableau();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiants(listeAnnuaire,2,20);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        System.out.println(listeGroupes);
        System.out.println(genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes));

    }
   
    
                
   
}
