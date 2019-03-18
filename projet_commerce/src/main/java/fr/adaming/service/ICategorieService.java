package fr.adaming.service;

import java.util.List;

import javax.ejb.Local;

import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;

@Local
public interface ICategorieService {

	public List<Categorie> afficherCategorieService(Administrateur admin);
	// Redéfinition for Client
	public List<Categorie> afficherCategorieService();

	public Categorie ajouterCategorieService(Categorie ca, Administrateur admin);

	public int modifierCategorieService(Categorie ca, Administrateur admin);

	public int supprimerCategorieService(Categorie ca, Administrateur admin);

	public Categorie consulterCategorieParIDService(Categorie ca, Administrateur admin);
	// Redéfinition for Client
	public Categorie consulterCategorieParIDService(Categorie ca);
}
