package fr.adaming.dao;

import java.util.List;

import fr.adaming.model.Categorie;

public interface ICategorieDao {

	public List<Categorie> afficherCategorieDao();

	public Categorie ajouterCategorieDao(Categorie ca);

	public int modifierCategorieDao(Categorie ca);

	public int supprimerCategorieDao(Categorie ca);

	public Categorie consulterCategorieParIDDao(Categorie ca);
	
	public List<String> consulterCategorieNomCategorieParIDDao();

}

// Enregistrer le client et la commande des produits de son panier.