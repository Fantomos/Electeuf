package fr.electeuf.algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.AffectationUnEtudiant;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.Module;
import fr.electeuf.VoeuxTousLesEtudiants;
import fr.electeuf.VoeuxUnEtudiant;

public class Evaluation {

    double coutTotal;
    double coutClasse;
    double coutVoeux;
    double scoreEffectif;
    double coutEtudiant;
    double coutRemplissage;
    public static final double COUT_PROHIBITIF = 10000;
    public static final double COUT_MODULE_PRIORITAIRE = -50;
    public static final double COUT_MODULE_RETICENT = 50;

    public Evaluation (double mCoutClasse, double mCoutVoeux, double mCoutRemplissage){
        this.coutClasse = mCoutClasse;
        this.coutVoeux = mCoutVoeux;
        this.coutEtudiant = mCoutClasse + mCoutVoeux;
        this.coutRemplissage = mCoutRemplissage;
        this.coutTotal = coutEtudiant + coutRemplissage;
    }

    public static Evaluation evaluerIndividus(Individu individu, VoeuxTousLesEtudiants listeVoeux){
        AffectationTousLesEtudiants listeAffectations = individu.getListeAffectations();
        double coutClasse = evaluerClasseTousEtudiants(listeAffectations);
        double coutVoeux = evaluerVoeuxTousEtudiants(listeVoeux, listeAffectations);
        double coutRemplissage = evaluerRemplissageTousModules(listeAffectations);
        return new Evaluation(coutClasse, coutVoeux, coutRemplissage);
    }

    public static double evaluerClasseUnEtudiant(Etudiant etudiant, AffectationUnEtudiant affectation){
        double cout = 0;
        for(Module module : affectation.getAffectationPourChaqueGroupe().values()){
            if(module.getClassesPrioritaires().contains(etudiant.getClasse()) || etudiant.getModulesPrioritaires().contains(module)){
                cout += COUT_MODULE_PRIORITAIRE;
            }
            else if(module.getClassesReticentes().contains(etudiant.getClasse()) || etudiant.getModulesReticent().contains(module)){
                cout += COUT_MODULE_RETICENT;
            }
            else if(module.getClassesAcceptees().contains(etudiant.getClasse())){
                cout += 0;
            }
            else{
                cout += COUT_PROHIBITIF;
            }
        }
        return cout;
    }

    public static double evaluerClasseTousEtudiants(AffectationTousLesEtudiants listeAffectations){
        double cout = 0;
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : listeAffectations.getListeAffectations().entrySet()) {
            cout += evaluerClasseUnEtudiant(entry.getKey(), entry.getValue());
        }
        return cout;
    }

    public static double evaluerVoeuxUnEtudiant(VoeuxUnEtudiant voeux, AffectationUnEtudiant affectation){
        double cout = 0;
        for(Map.Entry<Groupe, Module> entry : affectation.getAffectationPourChaqueGroupe().entrySet()){
            int positionVoeux = voeux.getVoeuxPourChaqueGroupe().get(entry.getKey()).indexOf(entry.getValue());
            if(positionVoeux == -1){
                cout += (VoeuxUnEtudiant.NOMBRE_VOEUX+1)^2;
            }
            else if(positionVoeux == 0){
                cout += 0;
            }
            else{
                cout += positionVoeux^2;
            }
        }
        return cout;
    }

    public static double evaluerVoeuxTousEtudiants(VoeuxTousLesEtudiants listeVoeux, AffectationTousLesEtudiants listeAffectation){
        double cout = 0;
        for (Map.Entry<Etudiant, AffectationUnEtudiant> entry : listeAffectation.getListeAffectations().entrySet()) {
            cout += evaluerVoeuxUnEtudiant(listeVoeux.getVoeux().get(entry.getKey()), entry.getValue());
        }
        return cout;
    }

    public static double evaluerRemplissageTousModules(AffectationTousLesEtudiants listeAffectation){
        double cout = 0; 
        for(Map.Entry<Module,Integer> entry : listeAffectation.getNbEtudiantParModule().entrySet()){
            cout += evaluerRemplissageUnModules(entry.getKey(), entry.getValue());
        }
        return cout;
    }

    public static double evaluerRemplissageUnModules(Module module, int nbEtudiants){
        double cout = 0; 
        if(nbEtudiants == module.getNbPlaceOpti() || nbEtudiants == 0){
            cout += 0;
        }
        else if(nbEtudiants > module.getNbPlaceMax() || nbEtudiants < module.getNbPlaceMin()){
            cout += COUT_PROHIBITIF;
        }
        else
        {
            cout+= (nbEtudiants - module.getNbPlaceOpti())^2;
        }
        return cout;
    }

    @Override
    public String toString(){
        String str = "COUT TOTAL : " + this.coutTotal + " \n";
        str += "COUT ETUDIANT : " + this.coutEtudiant + " (Classe : " + this.coutClasse + " et Voeux : " + this.coutVoeux + ")\n";
        str += "COUT ETABLISSEMENT : " + this.coutRemplissage + " (Remplissage)";
        return str;
    }

}
