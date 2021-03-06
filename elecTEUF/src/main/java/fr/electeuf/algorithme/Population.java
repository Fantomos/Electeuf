package fr.electeuf.algorithme;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import fr.electeuf.AffectationUnEtudiant;
import fr.electeuf.Etudiant;
import fr.electeuf.Groupe;
import fr.electeuf.Module;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Population {

    private List<Individu> listeIndividus;
    private VoeuxTousLesEtudiants listeVoeux;
    private int nbIteration;
    private int taillePopulation;
    public static final float PROBA_MUTATION = 0.25f;
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
        getListeIndividus().subList(taillePopulation, getTaillePopulation()).clear();
    }


    public void prochaineEvolution() throws InterruptedException{
        int NB_THREAD = Runtime.getRuntime().availableProcessors();
        List<Thread> listeThread = new ArrayList<>();
        for(int i=0; i<NB_THREAD; i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Random r = new Random(System.currentTimeMillis() *Thread.currentThread().getId());
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

    
    public void voirTauxSatisfaction(){
        int nbHorsVoeux = 0;
        int nbVoeux1 = 0;
        int nbVoeux2 = 0;
        int nbVoeux3 = 0;
        for(Map.Entry<Etudiant, AffectationUnEtudiant> entry : getIndividu(0).getAffectationTousLesEtudiants().getListeAffectations().entrySet()){
            for(Map.Entry<Groupe, Module> entry2 : entry.getValue().getAffectationPourChaqueGroupe().entrySet()){
                int positionVoeux = getListeVoeux().getVoeux().get(entry.getKey()).getVoeuxPourChaqueGroupe().get(entry2.getKey()).indexOf(entry2.getValue());
                if(positionVoeux == -1){
                    nbHorsVoeux++;
                }
                else if(positionVoeux == 0){
                    nbVoeux1++;
                }
                else if(positionVoeux == 1){
                    nbVoeux2++;
                }
                else if(positionVoeux == 2){
                    nbVoeux3++;
                }
            }
        }
        String str = "\n NOMBRE VOEUX 1 : " + nbVoeux1;
        str += "\n NOMBRE VOEUX 2 : " + nbVoeux2;
        str += "\n NOMBRE VOEUX 3 : " + nbVoeux3;
        str += "\n NOMBRE HORS-VOEUX : " + nbHorsVoeux;
        System.out.println(str);

    }


    @Override
    public String toString(){
        String str = "\n------------------------- POPULATION ----------------------------\n";
        str += "    NOMBRE D'INDIVIDUS : " + this.getTaillePopulation();
        str += "    NOMBRE D'ITERATIONS : " + this.getNbIteration();
        str += "\n\n    MEILLEUR COUT (MIN) \n" + this.getMeilleurCout();
        str += "\n\n    COUT MEDIAN \n" + this.getCoutMedian();
        str += "\n\n    PIRE COUT (MAX) \n" + this.getPireCout();
        str += "\n---------------------------------------------------------------------------\n";
        return str;
    }


    public static void main(String[] args) throws FileNotFoundException, IOException, InterruptedException {
        List<List<String>> listeAnnuaire = Etudiant.genererAnnuaireDuTableau();
        List<Etudiant> listeEtudiants = Etudiant.genererListeEtudiants(listeAnnuaire,15,40);
        List<Groupe> listeGroupes = Groupe.genererGroupeDuTableau(2);
        VoeuxTousLesEtudiants listeVoeux = VoeuxTousLesEtudiants.genererVoeuxTousLesEtudiants(listeEtudiants, listeGroupes);

        Population pop = new Population(1000, listeEtudiants, listeGroupes, listeVoeux);
        
        long t1 = System.currentTimeMillis();
        pop.voirTauxSatisfaction();
        
        while(true){
            pop.prochaineEvolution();
            if(pop.getNbIteration()%10000 == 0){
                BufferedWriter writer = new BufferedWriter(new FileWriter("results.log", true));
                writer.append(String.valueOf(pop.getNbIteration())+ "," +  String.valueOf(pop.getMeilleurCout().getCoutEtudiant()) + "," +  String.valueOf(pop.getMeilleurCout().getCoutRemplissage()) + "\n");
                writer.close();
                System.out.println(pop);
            }
            if(pop.getNbIteration()%50000 == 0){
                pop.voirTauxSatisfaction();
                System.out.println(pop.getIndividu(0).getAffectationTousLesEtudiants().voirNombresEtudiantsParModule());
                System.out.println(System.currentTimeMillis() - t1);
            }
            
        }
        // System.out.println(System.currentTimeMillis() - t1);
        // System.out.println(pop);
        // System.out.println(pop.getIndividu(0).voirNombresEtudiantsParModule());
        // pop.voirTauxSatisfaction();
      

    }

}
