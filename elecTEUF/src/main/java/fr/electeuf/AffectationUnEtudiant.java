package fr.electeuf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author nbesnard01
 */
public class AffectationUnEtudiant {
    private Map<Groupe,Module> affectationPourChaqueGroupe;
    
    public AffectationUnEtudiant(){
        this.affectationPourChaqueGroupe = new HashMap<>();
    }

    public AffectationUnEtudiant(AffectationUnEtudiant original){
        this.affectationPourChaqueGroupe = original.getAffectationPourChaqueGroupe();
    }
    
    
    public Map<Groupe,Module> getAffectationPourChaqueGroupe() {
        return affectationPourChaqueGroupe;
    }

    public void ajouterAffectationModule(Groupe groupe, Module module){
        this.affectationPourChaqueGroupe.put(groupe, module);
    }

    public void modifierAffectationModule(Groupe groupe, Module module){
        this.ajouterAffectationModule(groupe, module);
    }
    
    public static AffectationUnEtudiant genererAffectationUnEtudiant(List<Groupe> listeGroupes) {
        AffectationUnEtudiant affectationUnEtudiant = new AffectationUnEtudiant();
        Random r = new Random();
        for(Groupe groupe : listeGroupes){
            List<Module> listeModule = groupe.getModules();
            int indiceAlea = r.nextInt(listeModule.size());
            Module module = listeModule.get(indiceAlea);
            affectationUnEtudiant.ajouterAffectationModule(groupe, module);
        }
        return affectationUnEtudiant;
    }

    public static AffectationUnEtudiant modifierModuleAlea(AffectationUnEtudiant affectationOriginal ,Random r){
        AffectationUnEtudiant affectationModifie = new AffectationUnEtudiant(affectationOriginal);
        List<Groupe> listeGroupes = affectationModifie.getListeGroupes();
        Groupe groupe = listeGroupes.get(r.nextInt(listeGroupes.size()));
        List<Module> listeModule = groupe.getModules();
        affectationModifie.modifierAffectationModule(groupe, listeModule.get(r.nextInt(listeModule.size())));
        return affectationModifie;
    }

    public static AffectationUnEtudiant fusionnerAffectationsUnEtudiant(AffectationUnEtudiant a1 , AffectationUnEtudiant a2, Random r){
        AffectationUnEtudiant affectationUnEtudiant = new AffectationUnEtudiant();
        for (Map.Entry<Groupe, Module> entry : a1.getAffectationPourChaqueGroupe().entrySet()) {
            Module module;
           if(r.nextBoolean()){
                module = entry.getValue();
           }
           else{
                module = a2.getAffectationPourChaqueGroupe().get(entry.getKey());
           }

           affectationUnEtudiant.ajouterAffectationModule(entry.getKey(), module);
        }
        return affectationUnEtudiant;
    }


    public List<Module> getListeModulesAffecte(){
        return new ArrayList<>(this.getAffectationPourChaqueGroupe().values());
    }

    public List<Groupe> getListeGroupes(){
        return new ArrayList<>(this.getAffectationPourChaqueGroupe().keySet());
    }

    @Override
    public String toString(){
        String str = "";
        for (Map.Entry<Groupe, Module> entry : this.getAffectationPourChaqueGroupe().entrySet()) {
            str += entry.getKey().getNom() + " : " + entry.getValue();
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
        final AffectationUnEtudiant other = (AffectationUnEtudiant) obj;
     
        return Objects.equals(this.getAffectationPourChaqueGroupe(), other.getAffectationPourChaqueGroupe());
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.getAffectationPourChaqueGroupe());
        return hash;
    }
}
