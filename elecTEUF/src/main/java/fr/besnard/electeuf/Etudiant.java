/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.besnard.electeuf;

/**
 *
 * @author nbesnard01
 */
public class Etudiant {
    
    private String nom;
    private String prenom;
    
    public Etudiant(String mNom, String mPrenom){
        this.nom = mNom;
        this.prenom = mPrenom;
    }

    public Etudiant() {
        this("Nom", "Prenom");
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
    
    @Override
    public String toString(){
        return this.prenom + " " + this.nom;
    }
    
    public static void main(String[] args){
        Etudiant e1 = new Etudiant("Bob", "Marley");
        e1.toString();
    }
    
}
