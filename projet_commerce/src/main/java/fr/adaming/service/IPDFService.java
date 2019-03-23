package fr.adaming.service;

import java.util.List;

import fr.adaming.model.Client;
import fr.adaming.model.Produit;

public interface IPDFService {

	// public int creerPDF(Produit prod);
	public int creerPDF(List<Produit> produitListe, Client client, int numero);

	public int creerPDFEchec(List<Produit> produitListe, Client client, int numero);
}
