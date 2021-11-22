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
    private int nbIteration;
    public static final float PROBA_MUTATION = 0.1f;
    public static final float PROBA_MUTATION_VIDE_MODULE = 0.1f;
   
    public Population(int mTaillePopulation, List<Etudiant> mListeEtudiants, List<Groupe> mListeGroupes, VoeuxTousLesEtudiants mListeVoeux) {
        this.listeVoeux = mListeVoeux; 
        this.listeIndividus = new ArrayList<>();
        this.nbIteration = 0;
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
        if(r.nextFloat() < PROBA_MUTATION){ // Mutation
            // MUTATION SPECIFIQUE : VIDE MODULE COMPLET
            if(r.nextFloat() < PROBA_MUTATION_VIDE_MODULE){
                Individu individuOriginal = this.getIndividu(r.nextInt(this.getTaillePopulation()));
                nouveauIndividu = Individu.mutationVideModule(individuOriginal, r);
            }
            // MUTATION CLASSIQUE
            else{
                Individu individuOriginal = this.getIndividu(r.nextInt(this.getTaillePopulation()));
                nouveauIndividu = Individu.mutationClassique(individuOriginal, r);
            }
            
        }
        else{ // Croisement
            Individu individu1 = this.getIndividu(r.nextInt(this.getTaillePopulation()));
            Individu individu2 = this.getIndividu(r.nextInt(this.getTaillePopulation()));
            nouveauIndividu = Individu.croisement(individu1, individu2, r);
        }

        // Si le nouvel individu est meilleur que le plus mauvais de la population, on supprime le plus mauvais et on rajoute le nouvel individus
        if(nouveauIndividu.getCouts().getCoutTotal() < this.getPireCout().getCoutTotal()){
            this.supprimerPlusMauvaisIndividu();
            this.ajouterIndividu(nouveauIndividu);
            trierPopulation();
        }



        this.setNbIteration(this.getNbIteration()+1);

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


    public int getNbIteration() {
        return nbIteration;
    }

    public void setNbIteration(int nbIteration) {
        this.nbIteration = nbIteration;
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
        List<List<String>> listeAnnuaire = Etudiant.genererAnnuaireDuTableau();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiants(listeAnnuaire,2,20);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);
        Population pop = new Population(100, listeEtudiants, listeGroupes, listeVoeux);
        System.out.println(pop);
        for(int i=0;i<100000;i++){
            pop.prochaineEvolution();
        }
        System.out.println(pop);
        System.out.println(pop.getIndividu(0).voirNombresEtudiantsParModule());
      

    }

}
