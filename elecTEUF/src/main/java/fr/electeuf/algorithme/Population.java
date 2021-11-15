package fr.electeuf.algorithme;

import java.util.ArrayList;
import java.util.List;

import fr.electeuf.Groupe;
import fr.electeuf.VoeuxTousLesEtudiants;

public class Population {

    List<Individu> listeIndividus;

    public Population(int mTaillePopulation, VoeuxTousLesEtudiants listeVoeux) {
        listeIndividus = new ArrayList<>();
        for (int i = 0; i < mTaillePopulation; i++) {
            listeIndividus.add(Individu.genererIndividusAlea(listeVoeux));
        }
	}


}
