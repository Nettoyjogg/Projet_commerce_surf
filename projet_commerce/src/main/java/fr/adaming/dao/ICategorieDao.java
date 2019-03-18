package fr.adaming.dao;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Local
public interface ICategorieDao {

	public List<Categorie> afficherCategorieDao();

	public Categorie ajouterCategorieDao(Categorie ca);

	public int modifierCategorieDao(Categorie ca);

	public int supprimerCategorieDao(Categorie ca);

	public Categorie consulterCategorieParIDDao(Categorie ca);

}

// Enregistrer le client et la commande des produits de son panier.