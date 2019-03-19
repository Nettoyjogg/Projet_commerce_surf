package fr.adaming.dao;

import fr.adaming.model.Commande;

public interface ICommandeDao {

	public Commande ajouterCommandeDao(Commande co);
	
	public Commande consulterCommandeParIDDao(Commande co);
	
	public int ajouterClientCommandeDao(Commande co);
	
	public int supprimerCommandeDao(Commande co);
}
