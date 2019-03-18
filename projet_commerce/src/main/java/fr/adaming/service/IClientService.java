package fr.adaming.service;

import javax.ejb.Local;

import fr.adaming.model.Adresse;
import fr.adaming.model.Client;

@Local
public interface IClientService {

	public Client estExistantService(Client c);
	
	public Client ajouterClientService(Client c, Adresse a);
	
	public Client consulterClientParIdService(Client c);
	
}
