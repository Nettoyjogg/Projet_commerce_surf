package fr.adaming.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.adaming.model.Commande;

@Repository
public class CommandeDaoImpl implements ICommandeDao {
	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public Commande ajouterCommandeDao(Commande co) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		s.persist(co);
		return co;
	}

	@Override
	public Commande consulterCommandeParIDDao(Commande co) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		return (Commande) s.get(Commande.class, co.getIdCommande());
	}

	@Override
	public int ajouterClientCommandeDao(Commande co) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		Query req = s
				.createQuery("UPDATE Commande as co SET co.client.idClient=:pClient WHERE co.idCommande=:pIdCommande");
		req.setParameter("pClient", co.getClient().getIdClient());
		req.setParameter("pIdCommande", co.getIdCommande());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public int supprimerCommandeDao(Commande co) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		Query req = s.createQuery("DELETE FROM Commande as co WHERE co.idCommande=:pId");
		req.setParameter("pId", co.getIdCommande());
		int verif = req.executeUpdate();
		return verif;
	}
}
