package fr.electeuf.algorithme;

import java.util.List;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;

public class Individu {
    
    AffectationTousLesEtudiants affectations;
    boolean estEvalue;
    Evaluation scores;

    public Individu(AffectationTousLesEtudiants mAffectations){
        this.affectations = mAffectations;
        this.estEvalue = false;
    }

    public static Individu genererIndividusAlea(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes){
        AffectationTousLesEtudiants listeAffectations = AffectationTousLesEtudiants.genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes);
        return new Individu(listeAffectations);
    }

    public void faireEvaluation(){
        this.scores = Evaluation.evaluerIndividus(this);
        this.estEvalue = true;
    }

    public AffectationTousLesEtudiants getAffectations() {
        return this.affectations;
    }


}
