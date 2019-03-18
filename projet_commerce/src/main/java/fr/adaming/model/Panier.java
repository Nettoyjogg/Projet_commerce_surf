package fr.adaming.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

public class Panier implements Serializable{


	private static final long serialVersionUID = 1L;
	//transformation de l'association UML
	List<LigneCommande> listeLigneCommande;

	public Panier() {
		super();
	}
	
	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	@Override
	public String toString() {
		return "Panier [listeLigneCommande=" + listeLigneCommande + "]";
	}
	
}
