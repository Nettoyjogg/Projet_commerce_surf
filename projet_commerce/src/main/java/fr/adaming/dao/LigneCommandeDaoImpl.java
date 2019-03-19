package fr.adaming.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.adaming.model.LigneCommande;

@Repository
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@Autowired
	private SessionFactory sf;

	// Le setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public LigneCommande AjouterLigneCommandeDao(LigneCommande lc) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		// Requete HQL
		s.save(lc);
		return lc;
	}

	/*@Override
	public int LierLigneCommandeCommandeDao(LigneCommande lc) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		// Requete HQL
		String req = "UPDATE LigneCommande as lc SET lc.commande.idCommande=:pCommande WHERE lc.idLigneCommande=:pIdLigneCommande ";
		Query query = s.createQuery(req);
		query.setParameter("pCommande", lc.getCommande().getIdCommande());
		query.setParameter("pIdLigneCommande", lc.getIdLigneCommande());
		int verif = query.executeUpdate();
		return verif;
	}*/

	/*@Override
	public int LierLigneCommandeProduitDao(LigneCommande lc) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		// Requete HQL
		Query req = s.createQuery(
				"UPDATE LigneCommande as lc SET lc.produit.idProduit=:pProduit WHERE lc.idLigneCommande=:pIdLigneCommande ");
		req.setParameter("pProduit", lc.getProduit().getIdProduit());
		req.setParameter("pIdLigneCommande", lc.getIdLigneCommande());
		int verif = req.executeUpdate();
		return verif;
	}*/

	@Override
	public int SupprimerLigneCommandeDao(LigneCommande lc) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		Query req = s.createQuery("DELETE FROM LigneCommande as lc WHERE lc.idLigneCommande=:pId");
		req.setParameter("pId", lc.getIdLigneCommande());
		int verif = req.executeUpdate();
		return verif;
	}

}
