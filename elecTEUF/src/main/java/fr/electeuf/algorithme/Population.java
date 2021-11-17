package fr.electeuf.algorithme;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Population {

    private List<Individu> listeIndividus;
    private VoeuxTousLesEtudiants listeVoeux;
    public static final float PROBA_MUTATION = 0.7f;
    public static final int NB_MUTATION = 1;
   
    public Population(int mTaillePopulation, List<Etudiant> mListeEtudiants, List<Groupe> mListeGroupes, VoeuxTousLesEtudiants mListeVoeux) {
        this.listeVoeux = mListeVoeux; 
        this.listeIndividus = new ArrayList<>();
        for (int i = 0; i < mTaillePopulation; i++) {
            this.listeIndividus.add(Individu.genererIndividusAlea(mListeEtudiants, mListeGroupes, mListeVoeux));
        }
        this.trierPopulation();
    }

    public void ajouterIndividu(Individu individu){
        this.getListeIndividus().add(individu);
    }

    public void supprimerPlusMauvaisIndividu(){
        this.getListeIndividus().remove(this.getTaillePopulation()-1);
    }

    public void prochaineEvolution(){
        Random r = new Random();
        Individu nouveauIndividu;
        if(r.nextFloat() < 0.7){
            Individu individuOriginal = this.getIndividu(r.nextInt(this.getTaillePopulation()));
            nouveauIndividu = Individu.mutation(individuOriginal, r);
        }
        else{
            Individu individu1 = this.getIndividu(r.nextInt(this.getTaillePopulation()));
            Individu individu2 = this.getIndividu(r.nextInt(this.getTaillePopulation()));
            nouveauIndividu = Individu.croisement(individu1, individu2, r);
        }
        if(nouveauIndividu.getCouts().getCoutTotal() < this.getPireCout().getCoutTotal()){
            this.supprimerPlusMauvaisIndividu();
            this.ajouterIndividu(nouveauIndividu);
            trierPopulation();
        }
    }

    public int getTaillePopulation(){
        return this.getListeIndividus().size();
    }

    public void trierPopulation(){
        Collections.sort(this.getListeIndividus());
    }

    public Individu getIndividu(int id) {
        return this.getListeIndividus().get(id);
    }

    public List<Individu> getListeIndividus() {
        return listeIndividus;
    }


    public VoeuxTousLesEtudiants getListeVoeux() {
        return listeVoeux;
    }


    public Evaluation getCoutMedian() {
        return this.getListeIndividus().get((int) Math.round(this.getListeIndividus().size()-1)/2).getCouts();
    }


    public Evaluation getPireCout() {
        return this.getListeIndividus().get(this.getListeIndividus().size()-1).getCouts();
    }


    public Evaluation getMeilleurCout() {
        return this.getListeIndividus().get(0).getCouts();
    }


    @Override
    public String toString(){
        String str = "\n------------------------- POPULATION ----------------------------\n";
        str += "    NOMBRE D'INDIVIDUS : " + this.getTaillePopulation();
        str += "\n\n    MEILLEUR COUT (MIN) \n" + this.getMeilleurCout();
        str += "\n\n    COUT MEDIAN \n" + this.getCoutMedian();
        str += "\n\n    PIRE COUT (MAX) \n" + this.getPireCout();
        str += "\n---------------------------------------------------------------------------\n";
        return str;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiantToutesSpeParAnnee(20, 3);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);
        Population pop = new Population(100, listeEtudiants, listeGroupes, listeVoeux);
        System.out.println(pop);
        for(int i=0;i<10000;i++){
            pop.prochaineEvolution();
        }
        System.out.println(pop);

    }

}
