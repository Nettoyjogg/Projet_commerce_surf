package fr.adaming.service;

import fr.adaming.model.Adresse;
import fr.adaming.model.Client;


public interface IClientService {

	public Client estExistantService(Client c);
	
	public Client ajouterClientService(Client c, Adresse a);
	
	public Client consulterClientParIdService(Client c);
	
}
