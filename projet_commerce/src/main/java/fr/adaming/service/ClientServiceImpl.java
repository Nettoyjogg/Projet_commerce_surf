package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import fr.adaming.dao.IClientDao;
import fr.adaming.model.Adresse;
import fr.adaming.model.Client;

@Stateful
public class ClientServiceImpl implements IClientService {

	// Transformation de l'association UML en JAVA
	@EJB
	private IClientDao cDao;

	@Override
	public Client estExistantService(Client c) {

		return cDao.estExistant(c);
	}

	@Override
	public Client ajouterClientService(Client c, Adresse a) {
		c.setAdresse(a);
		return cDao.ajouterClientDao(c);
	}

	@Override
	public Client consulterClientParIdService(Client c) {
		return cDao.consulterClientParIdDao(c);
	}
		
}
