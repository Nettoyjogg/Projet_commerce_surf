package fr.adaming.service;

import fr.adaming.model.LigneCommande;

public interface ILigneCommandeService {

	public LigneCommande AjouterLigneCommandeService(LigneCommande lc);
	// Red�finition pour client connect�
	//public LigneCommande AjouterLigneCommandeService(LigneCommande lc, Client c);

	//public int LierLigneCommandeCommandeService(LigneCommande lc, Commande co);
	
	//public int LierLigneCommandeProduitService(LigneCommande lc, Produit p);
	
	public int supprimerLigneCommandeService(LigneCommande lc);
}
