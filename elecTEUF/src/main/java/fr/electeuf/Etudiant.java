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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import fr.electeuf.bdd.ExemplePersonnesAlea;

/**
 *
 * @author nbesnard01
 */
public class Etudiant {
    
    
    private String nom;
    private String prenom;
    private String date;
    private Classe classe;
    private List<Module> modulesPrioritaires;
    private List<Module> modulesReticent;
    
    public Etudiant(String mNom, String mPrenom, String mDate, Classe mClasse){
        this.nom = mNom;
        this.prenom = mPrenom;
        this.date = mDate;
        this.classe = mClasse;
        this.modulesPrioritaires = new ArrayList<>();
        this.modulesReticent = new ArrayList<>();
    }

    public Etudiant(String mNom, String mPrenom, Classe mClasse){
        this(mNom,mPrenom,"1/1/2021",mClasse);
    }


    public Etudiant() {
        this("Nom", "Prenom", "1/1/2021", new Classe());
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
    
    public List<Module> getModulesPrioritaires() {
        return modulesPrioritaires;
    }
    public List<Module> getModulesReticent() {
        return modulesReticent;
    }
    
    
    public static List<Etudiant> genererListeEtudiantParAnneeSpe(List<List<String>> listeAnnuaire,int nb, int annee, String specialite){
        List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();
        for(int i = 0; i<nb; i++){
            Etudiant nouveauEtudiant = Etudiant.genererEtudiantParAnneeSpe(listeAnnuaire,annee, specialite);
            if(!listeEtudiants.contains(nouveauEtudiant)){
                listeEtudiants.add(nouveauEtudiant);
            }
        }
        return listeEtudiants;     
    }


    public static List<Etudiant> genererListeEtudiants(List<List<String>> listeAnnuaire, int effectifClasse, int effectifSTH){
        List<Etudiant> listeEtudiants = new ArrayList<Etudiant>();
        listeEtudiants.addAll(genererListeEtudiantParAnneeSpe(listeAnnuaire, effectifSTH, 1, Classe.SPECIALITE.get(0)));
        for(int i = 1; i<Classe.SPECIALITE.size(); i++){
            listeEtudiants.addAll(genererListeEtudiantParAnneeSpe(listeAnnuaire, effectifClasse, 2, Classe.SPECIALITE.get(i)));
            listeEtudiants.addAll(genererListeEtudiantParAnneeSpe(listeAnnuaire, effectifClasse, 3, Classe.SPECIALITE.get(i)));
            listeEtudiants.addAll(genererListeEtudiantParAnneeSpe(listeAnnuaire, effectifClasse, 4, Classe.SPECIALITE.get(i)));
        }
        return listeEtudiants;     
    }
    

    public static Etudiant genererEtudiant(){
        Random r = new Random();
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        String nom = noms.get(r.nextInt(noms.size()));
        String prenom = prenoms.get(r.nextInt(prenoms.size()));
        return new Etudiant(nom, prenom, Classe.genererClasse());
    }
    
    public static Etudiant genererEtudiantParAnnee(int annee){
        Random r = new Random();
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        String nom = noms.get(r.nextInt(noms.size()));
        String prenom = prenoms.get(r.nextInt(prenoms.size()));
        return new Etudiant(nom, prenom, Classe.genererClasseParAnnee(annee));
    }

    public static Etudiant genererEtudiantParAnneeSpe(List<List<String>> listeAnnuaire, int annee, String specialite){
        Random r = new Random();
        List<String> listePrenom = listeAnnuaire.get(0);
        List<String> listeNom = listeAnnuaire.get(1);
        List<String> listeDate = listeAnnuaire.get(2);
        String prenoms =listePrenom.get(r.nextInt(listeNom.size()));
        String nom = listeNom.get(r.nextInt(listeNom.size()));
        String date = listeDate.get(r.nextInt(listeDate.size()));
        return new Etudiant(prenoms, nom, date, new Classe(specialite,annee));
    }
    
    public static List<List<String>> genererAnnuaireDuTableau() throws FileNotFoundException, IOException{
        final int posPrenom = 0;
        final int posNom = 1;
        final int posDate = 2;

        List<String> listePrenom = new ArrayList<>();
        List<String> listeNom = new ArrayList<>();
        List<String> listeDate = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("Electeuf/elecTEUF/src/main/java/fr/electeuf/bdd/tableau_annuaire.csv"))) {
            String ligne = br.readLine();

            while ((ligne = br.readLine()) != null) {
                String[] values = ligne.split(",");
                listePrenom.add(values[posPrenom]);
                listeNom.add(values[posNom]);
                listeDate.add(values[posDate]);
            }
        }
        return new ArrayList<>(Arrays.asList(listePrenom, listeNom, listeDate));
    }
    

    @Override
    public String toString(){
        return this.prenom + " " + this.nom + " " + this.classe + "\n";
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
        final Etudiant other = (Etudiant) obj;
        if (!Objects.equals(this.nom, other.nom)) {
            return false;
        }
        if(!Objects.equals(this.prenom, other.prenom)) {
            return false;
        }
        
        return Objects.equals(this.classe, other.classe);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nom);
        hash = 97 * hash + Objects.hashCode(this.prenom);
        hash = 97 * hash + Objects.hashCode(this.classe);
        return hash;
    }
    
     public static void main(String[] args) throws FileNotFoundException, IOException {
        List<List<String>> listeAnnuaire = genererAnnuaireDuTableau();
        System.out.println(genererListeEtudiants(listeAnnuaire,2,20));

    }
  
    
}
