package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Produit;

@Local
public interface IProduitDao {
	
	public List<Produit> afficherProduitDao();
	
	public Produit ajouterProduitDao(Produit p);
	
	public int modifierProduitDao(Produit p);
	
	public int supprimerProduitDao(Produit p);
	
	public Produit consulterProduitDao(Produit p);
	
	public List<Produit> consulterProduitCategorieDao(Produit p);

	public List<Produit> consulterProduitSelectionnesDao(Produit p);
	
	public Produit chercherProduitParMotCleDao();
	
	public Produit ajouterUnProduitQuantitePanierDao(Produit p);
	
	public int supprimerProduitPanierDao(Produit p);
	
	

}
