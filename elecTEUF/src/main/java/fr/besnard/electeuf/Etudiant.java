/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

import fr.besnard.cours.m3New.tds.TD1.ExemplePersonnesAlea;
import static fr.besnard.electeuf.Classe.SPECIALITE;
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
    
    
    public static List<Etudiant> genererListeEtudiantAlea(int nb){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 0; i<nb; i++){
            listeEtudiants.add(Etudiant.genererEtudiantAlea());
        }
        return listeEtudiants;     
    }
    
     
    public static List<Etudiant> genererListeEtudiantAleaAvecAnnee(int nb, int annee){
        List<Etudiant> listeEtudiants = new ArrayList();
        for(int i = 0; i<nb; i++){
            listeEtudiants.add(Etudiant.genererEtudiantAleaAvecAnnee(annee));
        }
        return listeEtudiants;     
    }
    
    
    public static Etudiant genererEtudiantAlea(){
        Random r = new Random();
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        String nom = noms.get(r.nextInt(noms.size()));
        String prenom = prenoms.get(r.nextInt(prenoms.size()));
        return new Etudiant(nom, prenom, Classe.genererClasseAlea());
    }
    
    public static Etudiant genererEtudiantAleaAvecAnnee(int annee){
        Random r = new Random();
        List<String> noms = ExemplePersonnesAlea.nomsAlea();
        List<String> prenoms = ExemplePersonnesAlea.nomsAlea();
        String nom = noms.get(r.nextInt(noms.size()));
        String prenom = prenoms.get(r.nextInt(prenoms.size()));
        return new Etudiant(nom, prenom, Classe.genererClasseAleaAvecAnnee(annee));
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
  
        System.out.println(Etudiant.genererListeEtudiantAleaAvecAnnee(40, 3).toString());

    }
  
    
}
