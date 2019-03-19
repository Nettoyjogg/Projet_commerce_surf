package fr.adaming.dao;

import fr.adaming.model.Client;


public interface IClientDao {
	public Client estExistant(Client c);
	
	public Client ajouterClientDao(Client c);
	
	public Client consulterClientParIdDao(Client c);

}
