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
    private int taillePopulation;
    public static final float PROBA_MUTATION = 0.1f;
    public static final float PROBA_MUTATION_VIDE_MODULE = 0.1f;
   
    public Population(int mTaillePopulation, List<Etudiant> mListeEtudiants, List<Groupe> mListeGroupes, VoeuxTousLesEtudiants mListeVoeux) {
        this.listeVoeux = mListeVoeux; 
        this.taillePopulation = mTaillePopulation;
        this.listeIndividus = Collections.synchronizedList(new ArrayList<Individu>());
        this.nbIteration = 0;
        for (int i = 0; i < mTaillePopulation; i++) {
            this.listeIndividus.add(Individu.genererIndividusAlea(mListeEtudiants, mListeGroupes, mListeVoeux));
        }
        this.trierPopulation();
    }

    public void ajouterIndividu(Individu individu){
        this.getListeIndividus().add(individu);
    }

    public void supprimerIndivusSupp(){
        for(int i=0; i<getTaillePopulation() - taillePopulation;i++){
            this.getListeIndividus().remove(this.getTaillePopulation()-1);
        }
    }


    public void prochaineEvolution() throws InterruptedException{
        int NB_THREAD = 8;
        List<Thread> listeThread = new ArrayList<>();
        for(int i=0; i<NB_THREAD; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random r = new Random();
                    Individu nouveauIndividu;
                    if(r.nextFloat() < PROBA_MUTATION){ // Mutation
                        // MUTATION SPECIFIQUE : VIDE MODULE COMPLET
                        if(r.nextFloat() < PROBA_MUTATION_VIDE_MODULE){
                            Individu individuOriginal = getIndividu(r.nextInt(getTaillePopulation()));
                            nouveauIndividu = Individu.mutationVideModule(individuOriginal, r);
                        }
                        // MUTATION CLASSIQUE
                        else{
                            Individu individuOriginal = getIndividu(r.nextInt(getTaillePopulation()));
                            nouveauIndividu = Individu.mutationClassique(individuOriginal, r);
                        }
                    }
                    else{ // Croisement
                        Individu individu1 = getIndividu(r.nextInt(getTaillePopulation()));
                        Individu individu2 = getIndividu(r.nextInt(getTaillePopulation()));
                        nouveauIndividu = Individu.croisement(individu1, individu2, r);
                    }
                  
                    if(nouveauIndividu.getCouts().getCoutTotal() < getPireCout().getCoutTotal()){
                        synchronized(getListeIndividus()) {
                            ajouterIndividu(nouveauIndividu);
                        }
                    }
                }
            });
            listeThread.add(t);
            t.start();
        }

        for(Thread t : listeThread){
            t.join();
        }

        trierPopulation();
        supprimerIndivusSupp();

        this.setNbIteration(this.getNbIteration()+NB_THREAD);
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

    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        List<List<String>> listeAnnuaire = Etudiant.genererAnnuaireDuTableau();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiants(listeAnnuaire,15,50);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);
        Population pop = new Population(100, listeEtudiants, listeGroupes, listeVoeux);
        System.out.println(pop);
        for(int i=0;i<12500;i++){
            pop.prochaineEvolution();
            if(i%100 == 0){
                System.out.println(pop);
            }
        }
        System.out.println(pop);
        System.out.println(pop.getIndividu(0).voirNombresEtudiantsParModule());
      

    }

}
