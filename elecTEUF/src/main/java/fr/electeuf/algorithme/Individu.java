package fr.electeuf.algorithme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.AffectationUnEtudiant;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Individu implements Comparable<Individu>{
    
    private AffectationTousLesEtudiants affectationTousLesEtudiants;
    private VoeuxTousLesEtudiants listeVoeux;
    private Evaluation couts;
    private int id;

    private static int idDernierIndividu = -1;

    public Individu(AffectationTousLesEtudiants mAffectationTousLesEtudiants, VoeuxTousLesEtudiants mListeVoeux){
        idDernierIndividu++;
        this.id = idDernierIndividu;
        this.affectationTousLesEtudiants = mAffectationTousLesEtudiants;
        this.listeVoeux = mListeVoeux;
        this.couts = Evaluation.evaluerIndividus(this);
    }

    public static Individu genererIndividusAlea(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes, VoeuxTousLesEtudiants listeVoeux){
        AffectationTousLesEtudiants listeAffectations = AffectationTousLesEtudiants.genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes);
        return new Individu(listeAffectations, listeVoeux);
    }

    public AffectationTousLesEtudiants getAffectationTousLesEtudiants() {
        return this.affectationTousLesEtudiants;
    }


    public VoeuxTousLesEtudiants getListeVoeux() {
        return listeVoeux;
    }


    public String voirNombresEtudiantsParModule(){
        return this.getAffectationTousLesEtudiants().voirNombresEtudiantsParModule();
    }




    public Evaluation getCouts(){
            return couts;
    }
    
    public int getId() {
        return id;
    }

    public static Individu mutation(Individu individuOriginal, Random r){
        AffectationTousLesEtudiants affectationTousLesEtudiants = AffectationTousLesEtudiants.modifierAffectationAlea(individuOriginal.getAffectationTousLesEtudiants(), r);
        return new Individu(affectationTousLesEtudiants, individuOriginal.getListeVoeux());
    }

    public static Individu croisement(Individu a1, Individu a2, Random r){
        AffectationTousLesEtudiants affectationTousLesEtudiants = AffectationTousLesEtudiants.fusionnerAffectationTousLesEtudiants(a1.getAffectationTousLesEtudiants(), a2.getAffectationTousLesEtudiants(), r);
        return new Individu(affectationTousLesEtudiants,  a1.getListeVoeux());
    }

    @Override
	public int compareTo(Individu individu) {
		return Double.compare(this.getCouts().getCoutTotal(),individu.getCouts().getCoutTotal());
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
        final Individu other = (Individu) obj;
     
        return Objects.equals(this.getId(), other.getId());
    }
    
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.getId());
        return hash;
    }


    @Override
    public String toString(){
        String str = "\n------------------------- INDIVIDU " + this.id + " ----------------------------\n";
        str += this.couts;
        str += "\n---------------------------------------------------------------------------\n";
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(1, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);
        Individu a = genererIndividusAlea(listeEtudiants, listeGroupes, listeVoeux);
        Individu b = genererIndividusAlea(listeEtudiants, listeGroupes, listeVoeux);
        System.out.println(a);
        System.out.println(b);

    }


}
