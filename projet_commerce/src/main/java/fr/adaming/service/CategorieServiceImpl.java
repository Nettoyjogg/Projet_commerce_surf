package fr.adaming.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateful;
import fr.adaming.dao.ICategorieDao;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;

@Stateful
public class CategorieServiceImpl implements ICategorieService {

	// Transfo UML Java
	@EJB
	ICategorieDao caDao;

	@Override
	public List<Categorie> afficherCategorieService(Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return caDao.afficherCategorieDao();
		}
		return null;
	}

	@Override
	public Categorie ajouterCategorieService(Categorie ca, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return caDao.ajouterCategorieDao(ca);
		}
		return null;
	}

	@Override
	public int modifierCategorieService(Categorie ca, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return caDao.modifierCategorieDao(ca);
		}
		return 0;
	}

	@Override
	public int supprimerCategorieService(Categorie ca, Administrateur admin) {
		if (admin.getIdAdmin() != 0) {
			return caDao.supprimerCategorieDao(ca);
		}
		return 0;
	}

	@Override
	public Categorie consulterCategorieParIDService(Categorie ca, Administrateur admin) {
		Categorie caFind = caDao.consulterCategorieParIDDao(ca);

		// Vérifier si l'étudiant est celui du formateur
		if (caFind != null && admin.getIdAdmin() != 0) {
			return caFind;
		} else {

			return null;
		}
	}

	@Override
	public List<Categorie> afficherCategorieService() {
		return caDao.afficherCategorieDao();
	}

	@Override
	public Categorie consulterCategorieParIDService(Categorie ca) {
		return caDao.consulterCategorieParIDDao(ca);
	}

}
