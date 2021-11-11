/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.electeuf;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;


/**
 *
 * @author nbesnard01
 */
public class Groupe {
    
    private String nom;
    private List<Module> listeModules;
    
    public Groupe(String mNom, List<Module> mListeModules){
        this.nom = mNom;
        this.listeModules = mListeModules;
    }

    public Groupe(String mNom){
        this(mNom, new ArrayList<>());
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Module> getModules() {
        return listeModules;
    }

    public void setModules(List<Module> modules) {
        this.listeModules = modules;
    }

    public void ajouterModule(Module module){
        this.listeModules.add(module);
    }

    public void ajouterModule(List<Module> listeModule){
        this.listeModules.addAll(listeModule);
    }

    public void supprimerModule(Module module){
        this.listeModules.remove(module);
    }
    public int nombreModules(){
        return this.listeModules.size();
    }
            
    
    public static List<Groupe> genererGroupeDuTableau(int nbGroupe) throws FileNotFoundException, IOException{
        final int posNom = 0;
        final int posProf = 1;
        final int posCreneau = 3;
        final int posSpe = 4;
        final int posAnnee = 5;
        final int nbCreneau = 6;

        List<Groupe> groupeParCreneau = new ArrayList<>();
        for(int i=0; i<nbCreneau;i++){
            groupeParCreneau.add(new Groupe(String.valueOf(i+1)));
        }

        try (BufferedReader br = new BufferedReader(new FileReader("elecTEUF/src/main/java/fr/electeuf/bdd/tableau.csv"))) {
            String ligne = br.readLine();
            while ((ligne = br.readLine()) != null) {
                String[] values = ligne.split(";");
                List<Classe> listeClasse = new ArrayList<>();
                for (String val : values[posSpe].split(",")) {
                    listeClasse.add(new Classe(val,Integer.parseInt(values[posAnnee])));
                }
                int creneau = Integer.parseInt(values[3].split(",")[0]);
                groupeParCreneau.get(creneau-1).ajouterModule(new Module(values[posNom], 24, listeClasse));
            }
        }
        
        List<Groupe> groupeFinaux = new ArrayList<>();
        for(int i=0; i<nbGroupe;i++){
            groupeFinaux.add(new Groupe("GROUPE " + String.valueOf(i+1)));
        }
        for(Groupe groupe : groupeParCreneau){
            Groupe groupeMoinsModule = groupeFinaux.stream().min(Comparator.comparing(Groupe::nombreModules)).orElseThrow(NoSuchElementException::new);;
            groupeMoinsModule.ajouterModule(groupe.getModules());
        }

        return groupeFinaux;
    }
             
    public static void main(String[] args) throws FileNotFoundException, IOException {
        System.out.println(genererGroupeDuTableau(2));

    }
    
    
     @Override
    public String toString(){
        String str = "\n-------------------" + this.nom + "-------------------\n";
        str += "Modules : " + this.listeModules;
        str += "\n----------------------------------------------\n";
        return str;
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
        final Groupe other = (Groupe) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        
        return Objects.equals(this.listeModules, other.listeModules);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.listeModules);
        return hash;
    }
    
    
}
