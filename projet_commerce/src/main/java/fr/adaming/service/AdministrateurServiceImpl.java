package fr.adaming.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.adaming.dao.IAdministrateurDao;
import fr.adaming.model.Administrateur;


@Service("adminService")
@Transactional //ici toute les m�thodes de la classe seront transactional ==> peuvent g�rer les transaction 
//on peut le mettre sur Dao mais sur Service c'est la bonne m�thode en terme d'�conomie de place 
public class AdministrateurServiceImpl implements IAdministrateurService {
	
	// transformation de l'association UML en JAVA
	@Autowired //utilise by type de nom
	private IAdministrateurDao administateurDao;
	

//setter de l'association
	public void setAdministateurDao(IAdministrateurDao administateurDao) {
		this.administateurDao = administateurDao;
	}




	@Override
	public Administrateur estExistant(Administrateur admin) {

		return administateurDao.estExistant(admin);

	}

}
