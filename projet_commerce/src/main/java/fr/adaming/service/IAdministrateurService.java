package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Administrateur;

@Local
public interface IAdministrateurService {
	public Administrateur estExistant(Administrateur admin);
}
