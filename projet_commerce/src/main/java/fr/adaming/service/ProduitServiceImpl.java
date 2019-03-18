package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Stateful
public class ProduitServiceImpl implements IProduitService {

	// Transformation de l'assocation UML en Java
	@EJB
	IProduitDao pDao;

	@EJB
	ICategorieDao caDao;

	@Override
	public List<Produit> afficherProduitService(Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return pDao.afficherProduitDao();
		}
		return null;
	}

	@Override
	public List<Produit> afficherProduitService() {
		return pDao.afficherProduitDao();
	}

	@Override
	public Produit ajouterProduitService(Produit p, Categorie ca, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			ca = caDao.consulterCategorieParIDDao(ca);
			p.setCategorie(ca);
			return pDao.ajouterProduitDao(p);
		}
		return null;
	}

	@Override
	public int modifierProduitService(Produit p, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return pDao.modifierProduitDao(p);
		}
		return 0;
	}

	@Override
	public int supprimerProduitService(Produit p, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return pDao.supprimerProduitDao(p);
		}
		return 0;
	}

	@Override
	public Produit consulterProduitService(Produit p, Administrateur admin) {
		Produit Pout = pDao.consulterProduitDao(p);

		// Vérifier si le produit existe et si on est dans une session admin
		if (Pout != null && admin.getIdAdmin() != 0) {
			return Pout;
		} else {

			return null;
		}
	}

	@Override
	public List<Produit> consulterProduitCategorieService(Produit p) {
		return pDao.consulterProduitCategorieDao(p);
	}

	@Override
	public Produit consulterProduitService(Produit p) {
		return pDao.consulterProduitDao(p);
	}

}
