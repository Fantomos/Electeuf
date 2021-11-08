/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 *
 * @author nbesnard01
 */
public class Etudiant {
    
    
    private String nom;
    private String prenom;
    private Classe classe;
    
    public Etudiant(String mNom, String mPrenom, Classe mClasse){
        this.nom = mNom;
        this.prenom = mPrenom;
        this.classe = mClasse;
    }

    public Etudiant() {
        this("Nom", "Prenom", new Classe());
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
    
    public static List<Etudiant> genererListeEtudiant(int nb){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 0; i<nb; i++){
            Etudiant nouveauEtudiant = Etudiant.genererEtudiant();
            if(!listeEtudiants.contains(nouveauEtudiant)){
                listeEtudiants.add(nouveauEtudiant);
            }else{
                i--;
            }
        }
        return listeEtudiants;     
    }
    
    public static List<Etudiant> genererListeEtudiantParAnnee(int nb, int annee){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 0; i<nb; i++){
            Etudiant nouveauEtudiant = Etudiant.genererEtudiantParAnnee(annee);
            if(!listeEtudiants.contains(nouveauEtudiant)){
                listeEtudiants.add(nouveauEtudiant);
            }
        }
        return listeEtudiants;     
    }

    public static List<Etudiant> genererListeEtudiantParAnneeSpe(int nb, int annee, String specialite){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 0; i<nb; i++){
            Etudiant nouveauEtudiant = Etudiant.genererEtudiantParAnneeSpe(annee, specialite);
            if(!listeEtudiants.contains(nouveauEtudiant)){
                listeEtudiants.add(nouveauEtudiant);
            }
        }
        return listeEtudiants;     
    }

    public static List<Etudiant> genererListeEtudiantToutesSpeParAnnee(int nbParSpe, int annee){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 1; i<Classe.SPECIALITE.size(); i++){
            listeEtudiants.addAll(genererListeEtudiantParAnneeSpe(nbParSpe, annee, Classe.SPECIALITE.get(i)));
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

    public static Etudiant genererEtudiantParAnneeSpe(int annee, String specialite){
        Random r = new Random();
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        String nom = noms.get(r.nextInt(noms.size()));
        String prenom = prenoms.get(r.nextInt(prenoms.size()));
        return new Etudiant(nom, prenom, new Classe(specialite,annee));
    }
    

    @Override
    public String toString(){
        return this.prenom + " " + this.nom + " " + this.classe;
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
    
     public static void main(String[] args) {
  
        System.out.println(genererListeEtudiantToutesSpeParAnnee(5,3));

    }
  
    
}
