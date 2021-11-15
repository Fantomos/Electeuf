package fr.electeuf.algorithme;

import java.util.List;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Individu {
    
    AffectationTousLesEtudiants listeAffectations;
    VoeuxTousLesEtudiants listeVoeux;
    boolean estEvalue;
    Evaluation scores;

    public Individu(AffectationTousLesEtudiants mAffectations, VoeuxTousLesEtudiants mVoeux){
        this.listeAffectations = mAffectations;
        this.listeVoeux = mVoeux;
        this.estEvalue = false;
    }

    public static Individu genererIndividusAlea(VoeuxTousLesEtudiants listeVoeux){
        AffectationTousLesEtudiants listeAffectations = AffectationTousLesEtudiants.genererAffectationTousLesEtudiants(listeVoeux.getListeEtudiants(), listeVoeux.getListeGroupes());
        return new Individu(listeAffectations, listeVoeux);
    }

    public void faireEvaluation(){
        this.scores = Evaluation.evaluerIndividus(this);
        this.estEvalue = true;
    }

    public AffectationTousLesEtudiants getListeAffectations() {
        return this.listeAffectations;
    }

    public VoeuxTousLesEtudiants getListeVoeux() {
        return this.listeVoeux;
    }


}
