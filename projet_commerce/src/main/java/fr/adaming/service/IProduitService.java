package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Local
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
	
	
	

}
// Consulter les produits sélectionnés
// Chercher des produits par mot clé
// Ajouter un produit avec une quantité au panier
// Supprimer un produit du panier
// Enregistrer le client et la commande des produits de son panier.