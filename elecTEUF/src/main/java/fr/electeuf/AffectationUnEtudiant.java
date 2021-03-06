package fr.electeuf;

import java.io.Serial;
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
        this.affectationPourChaqueGroupe = new HashMap<>(original.getAffectationPourChaqueGroupe());
    }
    
    
    public Map<Groupe,Module> getAffectationPourChaqueGroupe() {
        return affectationPourChaqueGroupe;
    }

    public void ajouterAffectationModule(Groupe groupe, Module module){
        this.affectationPourChaqueGroupe.put(groupe, module);
    }

    public void modifierAffectationModule(Groupe groupe, Module module){
        this.affectationPourChaqueGroupe.replace(groupe, module);
    }
    
    public static AffectationUnEtudiant genererAffectationUnEtudiant(List<Groupe> listeGroupes) {
        AffectationUnEtudiant affectationUnEtudiant = new AffectationUnEtudiant();
        Random r = new Random();
        for(Groupe groupe : listeGroupes){
            List<Module> listeModule = new ArrayList<>(groupe.getModules());
            for(Module m1 : affectationUnEtudiant.getListeModulesAffecte()){
                for(Module m2 : groupe.getModules()){
                    if(m1.getIntitule().equals(m2.getIntitule())){
                        listeModule.remove(m2);
                    }
                }
            }
            int indiceAlea = r.nextInt(listeModule.size());
            Module module = listeModule.get(indiceAlea);
            affectationUnEtudiant.ajouterAffectationModule(groupe, module);
        }
        return affectationUnEtudiant;
    }

    public static AffectationUnEtudiant supprimeAffectationModule(AffectationUnEtudiant affectationOriginal, Module module, Random r){
        AffectationUnEtudiant affectationModifie = new AffectationUnEtudiant(affectationOriginal);
        for(Map.Entry<Groupe,Module> entry :  affectationOriginal.getAffectationPourChaqueGroupe().entrySet()){
            if(entry.getValue().equals(module)){
                List<Module> listeModules = new ArrayList<>(entry.getKey().getModules());
                listeModules.remove(module);
                affectationModifie.modifierAffectationModule(entry.getKey(), listeModules.get(r.nextInt(listeModules.size())));
            }
        }
        return affectationModifie;
    }

    public static AffectationUnEtudiant modifierModuleAlea(AffectationUnEtudiant affectationOriginal ,Random r){
        AffectationUnEtudiant affectationModifie = new AffectationUnEtudiant(affectationOriginal);
        List<Groupe> listeGroupes = affectationModifie.getListeGroupes();
        Groupe groupe = listeGroupes.get(r.nextInt(listeGroupes.size()));
        List<Module> listeModule = new ArrayList<>(groupe.getModules());
        for(Module m1 : affectationModifie.getListeModulesAffecte()){
            for(Module m2 : groupe.getModules()){
                if(m1.getIntitule().equals(m2.getIntitule())){
                    listeModule.remove(m2);
                }
            }
        }
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
            str += "\n" + entry.getKey().getNom() + " : " + entry.getValue();
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
