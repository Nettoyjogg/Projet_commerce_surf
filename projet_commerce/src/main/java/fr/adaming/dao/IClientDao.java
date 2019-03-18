package fr.adaming.dao;

import javax.ejb.Local;

import fr.adaming.model.Client;

@Local
public interface IClientDao {
	public Client estExistant(Client c);
	
	public Client ajouterClientDao(Client c);
	
	public Client consulterClientParIdDao(Client c);

}
