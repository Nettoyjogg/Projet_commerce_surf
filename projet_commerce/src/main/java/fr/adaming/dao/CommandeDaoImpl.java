package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fr.adaming.model.Commande;

@Stateless
public class CommandeDaoImpl implements ICommandeDao {
	@PersistenceContext(unitName = "pu_commerce")

	private EntityManager em;

	@Override
	public Commande ajouterCommandeDao(Commande co) {
		em.persist(co);
		return co;
	}

	@Override
	public Commande consulterCommandeParIDDao(Commande co) {
		return em.find(Commande.class, co.getIdCommande());
	}

	@Override
	public int ajouterClientCommandeDao(Commande co) {
		Query req = em
				.createQuery("UPDATE Commande as co SET co.client.idClient=:pClient WHERE co.idCommande=:pIdCommande");
		req.setParameter("pClient", co.getClient().getIdClient());
		req.setParameter("pIdCommande", co.getIdCommande());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public int supprimerCommandeDao(Commande co) {
		Query req = em.createQuery("DELETE FROM Commande as co WHERE co.idCommande=:pId");
		req.setParameter("pId", co.getIdCommande());
		int verif = req.executeUpdate();
		return verif;
	}
}
