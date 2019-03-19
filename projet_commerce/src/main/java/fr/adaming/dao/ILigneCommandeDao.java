package fr.adaming.dao;

import fr.adaming.model.LigneCommande;

public interface ILigneCommandeDao {
	
	public LigneCommande ajouterLigneCommandeDao(LigneCommande lc);

	//public int LierLigneCommandeCommandeDao(LigneCommande lc);
	
	//public int LierLigneCommandeProduitDao(LigneCommande lc);
	
	public int supprimerLigneCommandeDao(LigneCommande lc);
	
	/*public int modifierLigneCommandeDao(LigneCommande lc);*/
	
}
