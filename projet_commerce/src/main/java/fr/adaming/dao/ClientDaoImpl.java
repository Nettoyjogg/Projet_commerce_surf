package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Client;

@Repository // pour dire que c'est un DAO
public class ClientDaoImpl implements IClientDao {

	@Autowired
	private SessionFactory sf;
	
	// le setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Client estExistant(Client c) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		// Requete JPQL
		String req = "FROM Client as c WHERE c.email=:pEmail AND c.mdpClient=:pMdp";

		//récupérer le query
		Query query = s.createQuery(req);

		// Passage des params
		query.setParameter("pEmail", c.getEmail());
		query.setParameter("pMdp", c.getMdpClient());

		return (Client) query.uniqueResult();
	}

	@Override
	public Client ajouterClientDao(Client c) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		
		s.save(c);
		
		return c;
	}

	@Override
	public Client consulterClientParIdDao(Client c) {
		Session s=sf.getCurrentSession();
		
		String req="FROM Client as c WHERE c.idClient=:pId";
		
		
		Query queryHQL = s.createQuery(req);
		
		//passage des params
		queryHQL.setParameter("pId", c.getIdClient());
	
		return  (Client) queryHQL.uniqueResult();
	}

}
