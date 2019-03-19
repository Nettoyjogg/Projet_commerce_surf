package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import fr.adaming.dao.ICommandeDao;
import fr.adaming.dao.ILigneCommandeDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.LigneCommande;

@Service("lcService")
@Transactional
public class LigneCommandeServiceImpl implements ILigneCommandeService {

	@Autowired
	ILigneCommandeDao lcDao;
	@Autowired
	ICommandeDao coDao;
	@Autowired
	IProduitDao pDao;

	public void setLcDao(ILigneCommandeDao lcDao) {
		this.lcDao = lcDao;
	}

	public void setCoDao(ICommandeDao coDao) {
		this.coDao = coDao;
	}

	public void setpDao(IProduitDao pDao) {
		this.pDao = pDao;
	}

	@Override
	public LigneCommande AjouterLigneCommandeService(LigneCommande lc) {
		return lcDao.AjouterLigneCommandeDao(lc);
	}

	/*@Override
	public LigneCommande AjouterLigneCommandeService(LigneCommande lc, Client c) {
		return null;
	}*/

	/*@Override
	public int LierLigneCommandeCommandeService(LigneCommande lc, Commande co) {
		co = coDao.consulterCommandeParIDDao(co);
		lc.setCommande(co);
		return lcDao.LierLigneCommandeCommandeDao(lc);
	}*/

	/*@Override
	public int LierLigneCommandeProduitService(LigneCommande lc, Produit p) {
		p = pDao.consulterProduitDao(p);
		lc.setProduit(p);
		return lcDao.LierLigneCommandeProduitDao(lc);
	}*/

	@Override
	public int supprimerLigneCommandeService(LigneCommande lc) {
		return lcDao.SupprimerLigneCommandeDao(lc);
	}

}
