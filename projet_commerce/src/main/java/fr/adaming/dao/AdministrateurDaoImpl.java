package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import fr.adaming.model.Administrateur;

@Stateless
public class AdministrateurDaoImpl implements IAdministrateurDao {

	@PersistenceContext(unitName = "pu_commerce") // Cette annotation permet
													// d'injecter un em
													// instancié par le
													// conteneur EJB
	private EntityManager em;

	@Override
	public Administrateur estExistant(Administrateur admin) {
		// Requete JPQL
		String req = "SELECT a FROM Administrateur as a WHERE a.mailAdmin=:pMail AND a.mdp=:pMdp";

		// Recupérer un objet de type Query
		Query query = em.createQuery(req);

		// Passage des params
		query.setParameter("pMail", admin.getMailAdmin());
		query.setParameter("pMdp", admin.getMdp());
		try {
			return (Administrateur) query.getSingleResult();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
