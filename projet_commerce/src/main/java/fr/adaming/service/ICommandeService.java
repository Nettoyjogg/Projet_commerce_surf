package fr.adaming.service;

import fr.adaming.model.Client;
import fr.adaming.model.Commande;

public interface ICommandeService {

	public Commande ajouterCommandeService(Commande co);
	
	public Commande consulterCommandeParIDService(Commande co);
	
	public int ajouterClientCommandeService(Commande co, Client c);
	
	public int supprimerCommandeService(Commande co);
}
