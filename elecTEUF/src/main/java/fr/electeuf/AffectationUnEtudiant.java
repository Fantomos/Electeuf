package fr.electeuf;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    
    public Map<Groupe,Module> getAffectationPourChaqueGroupe() {
        return affectationPourChaqueGroupe;
    }

    public void ajouterAffectationModule(Groupe groupe, Module module){
        this.affectationPourChaqueGroupe.put(groupe, module);
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


    @Override
    public String toString(){
        String str = "";
        for (Map.Entry<Groupe, Module> entry : this.affectationPourChaqueGroupe.entrySet()) {
            str += entry.getKey().getNom() + " : " + entry.getValue() + "\n";
        }
        return str;
    }
}
