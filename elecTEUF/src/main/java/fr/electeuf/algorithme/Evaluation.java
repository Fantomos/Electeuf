package fr.electeuf.algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.AffectationUnEtudiant;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.Module;

public class Evaluation {

    double scoreTotal;
    double scoreClasse;
    public static final double COUT_PROHIBITIF = 10000;

    public Evaluation (double mScoreClasse){
        this.scoreClasse = mScoreClasse;
        this.scoreTotal = scoreClasse;
    }

    public static Evaluation evaluerIndividus(Individu individu){
        AffectationTousLesEtudiants affectations = individu.getAffectations();
        double scoreClasse = evaluerClasseTousEtudiant(affectations);
        return new Evaluation(scoreClasse);
    }

    public static double evaluerClasseUnEtudiant(Etudiant etudiant, AffectationUnEtudiant affectation){
        double score = 0;
        for(Module module : affectation.getAffectationPourChaqueGroupe().values()){
            if(module.getClasses().contains(etudiant.getClasse())){
                score += 0;
            }else{
                score += COUT_PROHIBITIF;
            }
        }
        return score;
    }

    public static double evaluerClasseTousEtudiant(AffectationTousLesEtudiants affectations){
        double score = 0;
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : affectations.getListeAffectations().entrySet()) {
            score += evaluerClasseUnEtudiant(entry.getKey(), entry.getValue());
        }
        return score;
    }
}
