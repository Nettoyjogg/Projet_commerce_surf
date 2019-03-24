package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;


public interface IProduitService {
	public List<Produit> afficherProduitService(Administrateur admin);
	// Redef pour client
	public List<Produit> afficherProduitService();

	public Produit ajouterProduitService(Produit p, Categorie ca, Administrateur admin);

	public int modifierProduitService(Produit p, Administrateur admin);

	public int supprimerProduitService(Produit p, Administrateur admin);

	public Produit consulterProduitService(Produit p, Administrateur admin);

	public Produit consulterProduitService(Produit p);

	public List<Produit> consulterProduitCategorieService(Produit p);
	
	public List<String> getDesignationProduit();
		
}
