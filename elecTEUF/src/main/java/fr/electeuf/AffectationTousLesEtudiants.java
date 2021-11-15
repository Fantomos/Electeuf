package fr.electeuf;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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

    public Map<Module,Integer> getNbEtudiantParModule() {
        Map<Module,Integer> nbEtudiantParModule = new HashMap<>();
        for(Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.listeAffectations.entrySet()){
            for(Module module : entry.getValue().getListeModulesAffecte()){
                if(nbEtudiantParModule.containsKey(module)){
                    nbEtudiantParModule.put(module, nbEtudiantParModule.get(module)+1);
                }else{
                    nbEtudiantParModule.put(module, 1);
                }
            }
        }
        return nbEtudiantParModule;
    }

    public Map<Module,Integer> getNbEtudiantParModule2() {
        Map<Module,Integer> nbEtudiantParModule = new HashMap<>();
        List<Module> listeModuleAffecte = getListeModuleAffecte();
        for(Module module : this.getListeModules()){
            nbEtudiantParModule.put(module, Collections.frequency(listeModuleAffecte, module));
        }
        return nbEtudiantParModule;
    }


    public List<Module> getListeModules(){
        List<Module> listeTousModules = new ArrayList<>();
        for(Groupe groupe : getListeGroupes()){
            listeTousModules.addAll(groupe.getModules());
        }
        return listeTousModules;
    }

    public List<Groupe> getListeGroupes(){
        return ((AffectationUnEtudiant) listeAffectations.values().toArray()[0]).getListeGroupes();
    }

    public List<Module> getListeModuleAffecte(){
        List<Module> listeModuleAffecte = new ArrayList<>();
        for(Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.listeAffectations.entrySet()){
            listeModuleAffecte.addAll(entry.getValue().getListeModulesAffecte());
        }
        return listeModuleAffecte;
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
        AffectationTousLesEtudiants test = genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes);
        System.out.println(test);
        System.out.println(test.getNbEtudiantParModule());
        System.out.println(test.getNbEtudiantParModule2());
    }
}
