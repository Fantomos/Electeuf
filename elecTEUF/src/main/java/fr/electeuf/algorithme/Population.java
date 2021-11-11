package fr.electeuf.algorithme;

import java.util.ArrayList;
import java.util.List;

import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Population {

    List<Individus> listeIndividus;

    public Population(int mTaillePopulation, List<Groupe> listeGroupe, VoeuxTousLesEtudiants listeChoix) {
        listeIndividus = new ArrayList<>();
        for (int i = 0; i < mTaillePopulation; i++) {
            listeIndividus.add(new Individus(listeGroupe, listeChoix));
        }
	}


}
