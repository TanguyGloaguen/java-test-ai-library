package com.gloaguen2u.common.markov;

public interface DataPool<T, U> {
	public void putData(U data);//rajouter un objet U
	public Chain<T> getChain();//r�cup�rer une chaine construite � partir des donn�es enregistrer dans la datapool
}
