package com.gloaguen2u.common.markov;

public interface Chain<T> {
	public T get();//r�cup�rer l'�l�ment que tu regardes
	public void next();//regarder l'�l�ment suivant
	public void begin();//aller � l'�l�ment du d�but
	public int length();//retourne le nbr d'�l�ment dans la chaine
	public float getProbability();//retourne la probabilit� que l'�l�ment regard� soit s�lectionn� au moment de la cr�ation de la chaine
}
