package fr.adaming.dao;

import javax.ejb.Local;
import fr.adaming.model.LigneCommande;

@Local
public interface ILigneCommandeDao {
	
	public LigneCommande AjouterLigneCommandeDao(LigneCommande lc);

	public int LierLigneCommandeCommandeDao(LigneCommande lc);
	
	public int LierLigneCommandeProduitDao(LigneCommande lc);
	
	public int SupprimerLigneCommandeDao(LigneCommande lc);
	
}
