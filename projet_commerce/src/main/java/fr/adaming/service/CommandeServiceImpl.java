package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.adaming.dao.IClientDao;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;

@Service("coService")
@Transactional
public class CommandeServiceImpl implements ICommandeService {
	@Autowired
	ICommandeDao coDao;
	@Autowired
	IClientDao cDao;

	public void setCoDao(ICommandeDao coDao) {
		this.coDao = coDao;
	}

	public void setcDao(IClientDao cDao) {
		this.cDao = cDao;
	}

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
