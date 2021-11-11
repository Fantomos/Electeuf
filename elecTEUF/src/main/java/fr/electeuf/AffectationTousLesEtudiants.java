package fr.electeuf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AffectationTousLesEtudiants {
    
    private Map<Etudiant,AffectationUnEtudiant> listeAffectations;
    
    public AffectationTousLesEtudiants(){
        this.listeAffectations = new HashMap<>();
    }
    
    public void ajouterAffectation(Etudiant etudiant, AffectationUnEtudiant affectationUnEtudiant){
        listeAffectations.put(etudiant, affectationUnEtudiant);
    }
    
    public void supprimerAffectation(Etudiant etudiant){
        listeAffectations.remove(etudiant);
    }

    public void modifierAffectation(Etudiant etudiant, AffectationUnEtudiant affectationUnEtudiant){
        listeAffectations.replace(etudiant, affectationUnEtudiant);
    }

    public Map<Etudiant, AffectationUnEtudiant> getListeAffectations() {
        return listeAffectations;
    }
    
    public static AffectationTousLesEtudiants genererAffectationTousLesEtudiants(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes) {
        AffectationTousLesEtudiants affectationTousLesEtudiants = new AffectationTousLesEtudiants();
        for(Etudiant etudiant : listeEtudiants){
            affectationTousLesEtudiants.ajouterAffectation(etudiant, AffectationUnEtudiant.genererAffectationUnEtudiant(listeGroupes));
        }
        return affectationTousLesEtudiants;
    }

    @Override
    public String toString(){

        String str = "";
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.listeAffectations.entrySet()) {
            str += "\n--------------------- AFFECTATION DE " + entry.getKey() + " ---------------------\n";
            str += entry.getValue();
            str += "---------------------------------------------------------------------------\n";
        }
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(1, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        System.out.println(genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes));

    }
}
