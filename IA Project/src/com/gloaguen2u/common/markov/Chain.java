package com.gloaguen2u.common.markov;

public interface Chain<T> {
	public T get();//récupérer l'élément que tu regardes
	public void next();//regarder l'élément suivant
	public void begin();//aller à l'élément du début
	public int length();//retourne le nbr d'élément dans la chaine
	public float getProbability();//retourne la probabilité que l'élément regardé soit sélectionné au moment de la création de la chaine
}
