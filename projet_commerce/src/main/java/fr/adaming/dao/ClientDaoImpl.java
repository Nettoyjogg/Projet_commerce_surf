package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Client;

@Stateless
public class ClientDaoImpl implements IClientDao {
	// instancier le context approprié
	@PersistenceContext(unitName = "pu_commerce")

	private EntityManager em;

	@Override
	public Client estExistant(Client c) {
		// Requete JPQL
		String req = "SELECT c FROM Client as c WHERE c.email=:pEmail AND c.mdpClient=:pMdp";

		// Recupérer un objet de type Query
		Query query = em.createQuery(req);

		// Passage des params
		query.setParameter("pEmail", c.getEmail());
		query.setParameter("pMdp", c.getMdpClient());
		try {
			return (Client) query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Client ajouterClientDao(Client c) {
		em.persist(c);
		return c;
	}

	@Override
	public Client consulterClientParIdDao(Client c) {
		return em.find(Client.class, c.getIdClient());
	}

}
