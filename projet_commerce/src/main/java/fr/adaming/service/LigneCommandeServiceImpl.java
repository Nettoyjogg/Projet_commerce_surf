package fr.adaming.service;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Produit;

@Stateful
public class LigneCommandeServiceImpl implements ILigneCommandeService {
	
	@EJB
	ILigneCommandeDao lcDao;
	@EJB
	ICommandeDao coDao;
	@EJB
	IProduitDao pDao;

	@Override
	public LigneCommande AjouterLigneCommandeService(LigneCommande lc) {
		return lcDao.AjouterLigneCommandeDao(lc);
	}

	@Override
	public LigneCommande AjouterLigneCommandeService(LigneCommande lc, Client c) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int LierLigneCommandeCommandeService(LigneCommande lc, Commande co) {
		co = coDao.consulterCommandeParIDDao(co);
		lc.setCommande(co);
		return lcDao.LierLigneCommandeCommandeDao(lc);
	}

	@Override
	public int LierLigneCommandeProduitService(LigneCommande lc, Produit p) {
		p = pDao.consulterProduitDao(p);
		lc.setProduit(p);
		return lcDao.LierLigneCommandeProduitDao(lc);
	}

	@Override
	public int supprimerLigneCommandeService(LigneCommande lc) {
		return lcDao.SupprimerLigneCommandeDao(lc);
	}

}
