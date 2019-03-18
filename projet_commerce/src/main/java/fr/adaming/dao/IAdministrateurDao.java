package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Administrateur;

@Local
public interface IAdministrateurDao {
	public Administrateur estExistant(Administrateur admin);
}
