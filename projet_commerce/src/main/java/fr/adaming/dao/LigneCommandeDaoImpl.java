package fr.adaming.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import fr.adaming.model.LigneCommande;

@Stateless
public class LigneCommandeDaoImpl implements ILigneCommandeDao {

	@PersistenceContext(unitName = "pu_commerce")
	private EntityManager em;

	@Override
	public LigneCommande AjouterLigneCommandeDao(LigneCommande lc) {
		em.persist(lc);
		return lc;
	}

	@Override
	public int LierLigneCommandeCommandeDao(LigneCommande lc) {

		Query req = em.createQuery(
				"UPDATE LigneCommande as lc SET lc.commande.idCommande=:pCommande WHERE lc.idLigneCommande=:pIdLigneCommande ");
		req.setParameter("pCommande", lc.getCommande().getIdCommande());
		req.setParameter("pIdLigneCommande", lc.getIdLigneCommande());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public int LierLigneCommandeProduitDao(LigneCommande lc) {

		Query req = em.createQuery(
				"UPDATE LigneCommande as lc SET lc.produit.idProduit=:pProduit WHERE lc.idLigneCommande=:pIdLigneCommande ");
		req.setParameter("pProduit", lc.getProduit().getIdProduit());
		req.setParameter("pIdLigneCommande", lc.getIdLigneCommande());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public int SupprimerLigneCommandeDao(LigneCommande lc) {
		Query req = em.createQuery("DELETE FROM LigneCommande as lc WHERE lc.idLigneCommande=:pId");
		req.setParameter("pId", lc.getIdLigneCommande());
		int verif = req.executeUpdate();
		return verif;
	}

}
