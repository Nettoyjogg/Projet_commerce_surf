package fr.adaming.dao;


import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Administrateur;

@Repository // pour dire que c'est un DAO
public class AdministrateurDaoImpl implements IAdministrateurDao {

	@Autowired
	private SessionFactory sf;

	
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}
	
	
	@Override
	public Administrateur estExistant(Administrateur admin) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		// Requete HQL
		String req="FROM Administrateur as a WHERE a.mailAdmin=:pMail AND a.mdp=:pMdp";

		//récupérer le query
		Query query = s.createQuery(req);

		// Passage des params
		query.setParameter("pMail", admin.getMailAdmin());
		query.setParameter("pMdp", admin.getMdp());

		return (Administrateur) query.uniqueResult();
	}


}
