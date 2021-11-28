package fr.electeuf.algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.AffectationUnEtudiant;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.Module;
import fr.electeuf.VoeuxTousLesEtudiants;
import fr.electeuf.VoeuxUnEtudiant;

public class Evaluation{

    double coutTotal;
    double coutClasse;
    double coutVoeux;
    double coutEtudiant;
    double coutRemplissage;
    public static final double COUT_PROHIBITIF = 10000;
    public static final double COUT_MODULE_PRIORITAIRE = -50;
    public static final double COUT_MODULE_RETICENT = 50;
    public static final double COUT_COEF_ETUDIANT = 1.5;
    public static final double COUT_COEF_REMPLISSAGE = 1;

    public Evaluation (double mCoutClasse, double mCoutVoeux, double mCoutRemplissage){
        this.coutClasse = mCoutClasse;
        this.coutVoeux = mCoutVoeux;
        this.coutEtudiant = mCoutClasse + mCoutVoeux;
        this.coutRemplissage = mCoutRemplissage;
        this.coutTotal = COUT_COEF_ETUDIANT*coutEtudiant + COUT_COEF_REMPLISSAGE*coutRemplissage;
    }

    public static Evaluation evaluerIndividus(Individu individu){
        AffectationTousLesEtudiants listeAffectations = individu.getAffectationTousLesEtudiants();
        VoeuxTousLesEtudiants listeVoeux = individu.getListeVoeux();
        double coutClasse = evaluerClasseTousEtudiants(listeAffectations);
        double coutVoeux = evaluerVoeuxTousEtudiants(listeVoeux, listeAffectations);
        double coutRemplissage = evaluerRemplissageTousModules(listeAffectations);
        return new Evaluation(coutClasse, coutVoeux, coutRemplissage);
    }

    public static double evaluerClasseUnEtudiant(Etudiant etudiant, AffectationUnEtudiant affectation){
        double cout = 0;
        List<String> listeIntitule = new ArrayList<>();
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

            if(listeIntitule.contains(module.getIntitule())){
                cout += COUT_PROHIBITIF;
            }
            listeIntitule.add(module.getIntitule());
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
            cout+= (Math.abs(nbEtudiants - module.getNbPlaceOpti()))^2;
        }
        return cout;
    }

    public double getCoutTotal() {
        return coutTotal;
    }

    public double getCoutClasse() {
        return coutClasse;
    }


    public double getCoutVoeux() {
        return coutVoeux;
    }

    public double getCoutEtudiant() {
        return coutEtudiant;
    }


    public double getCoutRemplissage() {
        return coutRemplissage;
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
        final Evaluation other = (Evaluation) obj;
        if (!Objects.equals(this.getCoutTotal(), other.getCoutTotal())) {
            return false;
        }
        if (!Objects.equals(this.getCoutEtudiant(), other.getCoutEtudiant())) {
            return false;
        }
        if (!Objects.equals(this.getCoutVoeux(), other.getCoutVoeux())) {
            return false;
        }
        if (!Objects.equals(this.getCoutClasse(), other.getCoutClasse())) {
            return false;
        }
        return Objects.equals(this.getCoutRemplissage(), other.getCoutRemplissage());
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.getCoutTotal());
        hash = 97 * hash + Objects.hashCode(this.getCoutEtudiant());
        hash = 97 * hash + Objects.hashCode(this.getCoutVoeux());
        hash = 97 * hash + Objects.hashCode(this.getCoutClasse());
        hash = 97 * hash + Objects.hashCode(this.getCoutRemplissage());
        return hash;
    }

    @Override
    public String toString(){
        String str = "COUT TOTAL : " + this.getCoutTotal() + " \n";
        str += "COUT ETUDIANT : " + this.getCoutEtudiant() + " (Classe : " + this.getCoutClasse() + " et Voeux : " + this.getCoutVoeux() + ")\n";
        str += "COUT ETABLISSEMENT : " + this.getCoutRemplissage() + " (Remplissage)";
        return str;
    }

}
