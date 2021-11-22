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
import java.util.Objects;
import java.util.Random;

public class AffectationTousLesEtudiants {
    
    private Map<Etudiant,AffectationUnEtudiant> listeAffectations;
    
    public AffectationTousLesEtudiants(){
        this.listeAffectations = new HashMap<>();
    }
    public AffectationTousLesEtudiants(AffectationTousLesEtudiants original){
        this.listeAffectations = original.getListeAffectations();
    }
    
    public void ajouterAffectation(Etudiant etudiant, AffectationUnEtudiant affectationUnEtudiant){
        this.getListeAffectations().put(etudiant, affectationUnEtudiant);
    }
    
    public void supprimerAffectation(Etudiant etudiant){
       this.getListeAffectations().remove(etudiant);
    }

    public void modifierAffectation(Etudiant etudiant, AffectationUnEtudiant affectationUnEtudiant){
       this.ajouterAffectation(etudiant, affectationUnEtudiant);;
    }

    public Map<Etudiant, AffectationUnEtudiant> getListeAffectations() {
        return this.listeAffectations;
    }

    public AffectationUnEtudiant getAffectationPourEtudiant(Etudiant etudiant){
        return this.getListeAffectations().get(etudiant);
    }
    
    public static AffectationTousLesEtudiants genererAffectationTousLesEtudiants(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes) {
        AffectationTousLesEtudiants affectationTousLesEtudiants = new AffectationTousLesEtudiants();
        for(Etudiant etudiant : listeEtudiants){
            affectationTousLesEtudiants.ajouterAffectation(etudiant, AffectationUnEtudiant.genererAffectationUnEtudiant(listeGroupes));
        }
        return affectationTousLesEtudiants;
    }

    public static AffectationTousLesEtudiants supprimerAffectationModule(AffectationTousLesEtudiants affectationOriginal, Random r){
        AffectationTousLesEtudiants affectationModifie = new AffectationTousLesEtudiants(affectationOriginal);
        List<Module> listeModulesAffecte = affectationOriginal.getListeModuleAffecte();
        Module moduleAlea = listeModulesAffecte.get(r.nextInt(listeModulesAffecte.size()));
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : affectationOriginal.getListeAffectations().entrySet()) {
            affectationModifie.modifierAffectation(entry.getKey(), AffectationUnEtudiant.supprimeAffectationModule(entry.getValue(), moduleAlea, r));
        }
        return affectationModifie;
    }

    public static AffectationTousLesEtudiants modifierAffectationAlea(AffectationTousLesEtudiants affectationOriginal, Random r){
        AffectationTousLesEtudiants affectationModifie = new AffectationTousLesEtudiants(affectationOriginal);
        List<Etudiant> listeEtudiant = affectationModifie.getListeEtudiants();
        Etudiant etudiant = listeEtudiant.get(r.nextInt(listeEtudiant.size()));
        affectationModifie.modifierAffectation(etudiant, AffectationUnEtudiant.modifierModuleAlea(affectationModifie.getAffectationPourEtudiant(etudiant), r));
        return affectationModifie;
    }

    public static AffectationTousLesEtudiants fusionnerAffectationTousLesEtudiants(AffectationTousLesEtudiants a1 , AffectationTousLesEtudiants a2, Random r){
        AffectationTousLesEtudiants affectationTousLesEtudiants = new AffectationTousLesEtudiants();
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : a1.getListeAffectations().entrySet()) {
            AffectationUnEtudiant affectation = AffectationUnEtudiant.fusionnerAffectationsUnEtudiant(a1.getAffectationPourEtudiant(entry.getKey()), a2.getAffectationPourEtudiant(entry.getKey()), r);
            affectationTousLesEtudiants.ajouterAffectation(entry.getKey(), affectation);
        }
        return affectationTousLesEtudiants;
    }


    public Map<Module,Integer> getNbEtudiantParModule() {
        Map<Module,Integer> nbEtudiantParModule = new HashMap<>();
        for(Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.getListeAffectations().entrySet()){
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

    public List<Etudiant> getListeEtudiants(){
        return new ArrayList<>(this.getListeAffectations().keySet());
    }

    public List<Module> getListeModules(){
        List<Module> listeTousModules = new ArrayList<>();
        for(Groupe groupe : this.getListeGroupes()){
            listeTousModules.addAll(groupe.getModules());
        }
        return listeTousModules;
    }

    public List<Groupe> getListeGroupes(){
        return ((AffectationUnEtudiant) this.getListeAffectations().values().toArray()[0]).getListeGroupes();
    }

    public List<Module> getListeModuleAffecte(){
        List<Module> listeModuleAffecte = new ArrayList<>();
        for(Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.getListeAffectations().entrySet()){
            listeModuleAffecte.addAll(entry.getValue().getListeModulesAffecte());
        }
        return listeModuleAffecte;
    }


    public String voirNombresEtudiantsParModule(){
        String str = "\n----------------------- NOMBRES ETUDIANTS PAR MODULES ----------------------\n";
        for (Map.Entry<Module,Integer> entry : this.getNbEtudiantParModule2().entrySet()) {
            str += entry.getKey() + " : " + entry.getValue() + "\n";
        }
        str += "\n---------------------------------------------------------------------------\n";
        return str;
    }

    @Override
    public String toString(){

        String str = "";
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : this.getListeAffectations().entrySet()) {
            str += "\n--------------------- AFFECTATION DE " + entry.getKey() + " ---------------------\n";
            str += entry.getValue();
            str += "---------------------------------------------------------------------------\n";
        }
        return str;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AffectationTousLesEtudiants other = (AffectationTousLesEtudiants) obj;
     
        return Objects.equals(this.getListeAffectations(), other.getListeAffectations());
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.getListeAffectations());
        return hash;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<List<String>> listeAnnuaire = Etudiant.genererAnnuaireDuTableau();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiants(listeAnnuaire,2,20);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        AffectationTousLesEtudiants test = genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes);
        System.out.println(test);
        System.out.println(test.getNbEtudiantParModule());
    }
}
