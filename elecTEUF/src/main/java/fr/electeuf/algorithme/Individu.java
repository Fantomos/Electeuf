package fr.electeuf.algorithme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import fr.electeuf.AffectationTousLesEtudiants;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Individu {
    
    private AffectationTousLesEtudiants listeAffectations;
    private boolean estEvalue;
    private Evaluation couts;
    private int id;

    private static int idDernierIndividu = -1;

    public Individu(AffectationTousLesEtudiants mAffectations){
        idDernierIndividu++;
        this.id = idDernierIndividu;
        this.listeAffectations = mAffectations;
        this.estEvalue = false;
    }

    public static Individu genererIndividusAlea(List<Etudiant> listeEtudiants, List<Groupe> listeGroupes){
        AffectationTousLesEtudiants listeAffectations = AffectationTousLesEtudiants.genererAffectationTousLesEtudiants(listeEtudiants, listeGroupes);
        return new Individu(listeAffectations);
    }

    public Evaluation evaluer(VoeuxTousLesEtudiants listeVoeux){
        this.couts = Evaluation.evaluerIndividus(this,listeVoeux);
        this.estEvalue = true;
        return couts;
    }

    public AffectationTousLesEtudiants getListeAffectations() {
        return this.listeAffectations;
    }


    

    @Override
    public String toString(){
        String str = "\n------------------------- INDIVIDU " + this.id + " ----------------------------\n";
        if(estEvalue){
            str += this.couts;
        }
        else{
            str += "L'INDIVIDU N'A PAS ETE ENCORE EVALUE.";
        }
        str += "\n---------------------------------------------------------------------------\n";
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(1, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        Individu a = genererIndividusAlea(listeEtudiants, listeGroupes);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);
        a.evaluer(listeVoeux);
        Individu b = genererIndividusAlea(listeEtudiants, listeGroupes);
        System.out.println(a);
        System.out.println(b);
        

    }


}
