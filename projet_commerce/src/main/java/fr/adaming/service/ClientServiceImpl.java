package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IClientDao;
import fr.adaming.model.Adresse;
import fr.adaming.model.Client;

@Service("cService")
@Transactional //ici toute les méthodes de la classe seront transactional ==> peuvent gérer les transaction 
//on peut le mettre sur Dao mais sur Service c'est la bonne méthode en terme d'économie de place 
public class ClientServiceImpl implements IClientService {

	// transformation de l'association UML en JAVA
		@Autowired //utilise by type de nom
		private IClientDao ClientDao;
		

	public void setClientDao(IClientDao clientDao) {
			this.ClientDao = clientDao;
		}

	
	@Override
	public Client estExistantService(Client c) {

		return ClientDao.estExistant(c);
	}

	@Override
	public Client ajouterClientService(Client c, Adresse a) {
		c.setAdresse(a);
		return ClientDao.ajouterClientDao(c);
	}

	@Override
	public Client consulterClientParIdService(Client c) {
		return ClientDao.consulterClientParIdDao(c);
	}
		
}
