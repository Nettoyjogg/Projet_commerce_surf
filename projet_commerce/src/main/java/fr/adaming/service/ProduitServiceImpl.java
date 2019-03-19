package fr.adaming.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.ICategorieDao;
import fr.adaming.dao.IProduitDao;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;

@Service("pService")
@Transactional //ici toute les méthodes de la classe seront transactional ==> peuvent gérer les transaction 
//on peut le mettre sur Dao mais sur Service c'est la bonne méthode en terme d'économie de place 
public class ProduitServiceImpl implements IProduitService {

	
	
	// transformation de l'association UML en JAVA
	@Autowired //utilise by type de nom
	IProduitDao produitDao;

	@Autowired //utilise by type de nom
	ICategorieDao categorieDao;

	
	public void setProduitDao(IProduitDao produitDao) {
		this.produitDao = produitDao;
	}

	public void setCategorieDao(ICategorieDao categorieDao) {
		this.categorieDao = categorieDao;
	}

	@Override
	public List<Produit> afficherProduitService(Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return produitDao.afficherProduitDao();
		}
		return null;
	}

	@Override
	public List<Produit> afficherProduitService() {
		return produitDao.afficherProduitDao();
	}

	@Override
	public Produit ajouterProduitService(Produit p, Categorie ca, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			ca = categorieDao.consulterCategorieParIDDao(ca);
			p.setCategorie(ca);
			return produitDao.ajouterProduitDao(p);
		}
		return null;
	}

	@Override
	public int modifierProduitService(Produit p, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return produitDao.modifierProduitDao(p);
		}
		return 0;
	}

	@Override
	public int supprimerProduitService(Produit p, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return produitDao.supprimerProduitDao(p);
		}
		return 0;
	}

	@Override
	public Produit consulterProduitService(Produit p, Administrateur admin) {
		Produit Pout = produitDao.consulterProduitDao(p);

		// Vérifier si le produit existe et si on est dans une session admin
		if (Pout != null && admin.getIdAdmin() != 0) {
			return Pout;
		} else {

			return null;
		}
	}

	@Override
	public List<Produit> consulterProduitCategorieService(Produit p) {
		return produitDao.consulterProduitCategorieDao(p);
	}

	@Override
	public Produit consulterProduitService(Produit p) {
		return produitDao.consulterProduitDao(p);
	}

}
