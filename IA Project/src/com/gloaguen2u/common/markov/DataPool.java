package com.gloaguen2u.common.markov;

public interface DataPool<T, U> {
	public void putData(U data);//rajouter un objet U
	public Chain<T> getChain();//récupérer une chaine construite à partir des données enregistrer dans la datapool
}
