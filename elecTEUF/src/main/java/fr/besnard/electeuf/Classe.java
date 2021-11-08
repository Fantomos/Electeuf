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
public class Classe {
    
    public static final List<String> SPECIALITE = Arrays.asList(new String[]{"STH", "G", "GC", "GCE", "GE", "MIQ", "PL", "GM"});
    private String specialite;
    private int annee;

    public Classe(String mSpecialite, int mAnnee) {
        if(SPECIALITE.contains(mSpecialite.toUpperCase())){    
            this.specialite = mSpecialite.toUpperCase();
        }
        else{
            System.out.println("Erreur : Le nom de la spécialité est incorrect. La specialité choisie sera STH.");
            this.specialite = "STH";
        }
        
        if("STH".equals(this.specialite) && mAnnee != 1){
            System.out.println("Erreur : Vous êtes en STH donc en 1ère année !");
            this.annee = 1;
        }     
        else if((mAnnee > 4 || mAnnee < 2) && !"STH".equals(this.specialite)){
            System.out.println("Erreur : Année incorrecte. L'année choisie sera 2.");
            this.annee = 2;
        }else{
            this.annee = mAnnee;
        }
    }
    
    public Classe(){
        this.specialite = "STH";
        this.annee = 1;
    }

    public String getSpecialite() {
        return specialite;
    }

    public void setSpecialite(String specialite) {
        this.specialite = specialite;
    }

    public int getAnnee() {
        return annee;
    }

    public void setAnnee(int annee) {
        this.annee = annee;
    }
    
    
    public static List<Classe> genererListeClasse(int nb){
        List<Classe> listeClasses = new ArrayList();
        for(int i = 0; i<nb; i++){
            listeClasses.add(Classe.genererClasse());
        }
        return listeClasses;     
    }
    
    
    public static Classe genererClasse(){
        Random r = new Random();
        String specialite = SPECIALITE.get(r.nextInt(8));
        int annee = 2 + r.nextInt(3);
        if(specialite.equals("STH")){
            annee = 1;
        }
        return new Classe(specialite, annee);
    }
    
    public static Classe genererClasseParAnnee(int mAnnee){
        Random r = new Random();
        String specialite = SPECIALITE.get(r.nextInt(7)+1);
        if(mAnnee == 1){
            return new Classe("STH", mAnnee);
        }
        else{
            return new Classe(specialite, mAnnee);
        }    
    }

        
    
    @Override
    public String toString(){
        return this.specialite + this.annee;
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
        final Classe other = (Classe) obj;
        if (!Objects.equals(this.specialite, other.specialite)) {
            return false;
        }
        
        return Objects.equals(this.annee, other.annee);
    }
    
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.specialite);
        hash = 97 * hash + Objects.hashCode(this.annee);
        return hash;
    }
    
     public static void main(String[] args) {
        List<Classe> cl = Classe.genererListeClasse(10);
        System.out.println(cl.toString());

    }
    
    
}
