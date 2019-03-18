package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Stateful
public class CommandeServiceImpl implements ICommandeService {
	@EJB
	ICommandeDao coDao;
	@EJB
	IClientDao cDao;

	@Override
	public Commande ajouterCommandeService(Commande co) {
		return coDao.ajouterCommandeDao(co);
	}

	@Override
	public Commande consulterCommandeParIDService(Commande co) {

		return coDao.consulterCommandeParIDDao(co);
	}

	@Override
	public int ajouterClientCommandeService(Commande co, Client c) {
		c = cDao.consulterClientParIdDao(c);
		co.setClient(c);
		return coDao.ajouterClientCommandeDao(co);
	}

	@Override
	public int supprimerCommandeService(Commande co) {
		return coDao.supprimerCommandeDao(co);
	}

}
