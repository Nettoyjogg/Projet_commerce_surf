package fr.adaming.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.codec.binary.Base64;

import fr.adaming.model.Produit;

@Stateless
public class ProduitDaoImpl implements IProduitDao {
	// LIER AUX BONNES REGLES DE CONFIG
	@PersistenceContext(unitName = "pu_commerce")

	private EntityManager em;

	@Override
	public List<Produit> afficherProduitDao() {
		// Requete JPQL
		String req = "SELECT p FROM Produit as p";

		// Répérer un objet query
		Query query = em.createQuery(req);

		List<Produit> listeProduit = query.getResultList();

		for (Produit p : listeProduit) {

			p.setImg("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));

		}

		return listeProduit;
	}

	@Override
	public Produit ajouterProduitDao(Produit p) {
		em.persist(p);
		return p;
	}

	@SuppressWarnings("finally")
	@Override
	public int modifierProduitDao(Produit p) {
		// requete JPQL
		String req = "UPDATE Produit as p SET p.designation=:pDesignation, p.description=:pDescription, p.prix=:pPrix, p.quantite=:pQuantite, p.selectionne=:pSelectionne, p.photo=:pPhoto, p.categorie.idCategorie=:pIdCategorie WHERE p.idProduit=:pId";
		// récupérer un objet query
		Query query = em.createQuery(req);

		query.setParameter("pDesignation", p.getDesignation());
		query.setParameter("pDescription", p.getDescription());
		query.setParameter("pPrix", p.getPrix());
		query.setParameter("pQuantite", p.getQuantite());
		query.setParameter("pSelectionne", p.isSelectionne());
		query.setParameter("pPhoto", p.getPhoto());
	
		try {
			query.setParameter("pIdCategorie", p.getCategorie().getIdCategorie());
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			query.setParameter("pId", p.getIdProduit());
			int verif = query.executeUpdate();
			return verif;
		}
	
	}

	@Override
	public int supprimerProduitDao(Produit p) {
		// requete JPQL
		String req = "DELETE FROM Produit as p WHERE p.idProduit=:pId";
		// récupérer un objet query
		Query query = em.createQuery(req);

		query.setParameter("pId", p.getIdProduit());
		int verif = query.executeUpdate();
		return verif;
	}

	@Override
	public Produit consulterProduitDao(Produit p) {
		/*String req = "SELECT p FROM Produit as p WHERE p.idProduit=:pId";
		Query query = em.createQuery(req);
		query.setParameter("pId", p.getIdProduit());
		Produit pOut = (Produit) query.getSingleResult();
		return pOut;*/
		 return em.find(Produit.class, p.getIdProduit());
	}

	@Override
	public List<Produit> consulterProduitSelectionnesDao(Produit p) {
		return null;
	}

	@Override
	public Produit chercherProduitParMotCleDao() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Produit ajouterUnProduitQuantitePanierDao(Produit p) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int supprimerProduitPanierDao(Produit p) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Produit> consulterProduitCategorieDao(Produit p) {
		String reqListe = "SELECT p FROM Produit as p WHERE p.categorie.idCategorie=:pIdCA";
		Query queryListe = em.createQuery(reqListe);
		queryListe.setParameter("pIdCA", p.getCategorie().getIdCategorie());
		List<Produit> liste = (List<Produit>) queryListe.getResultList();
		return liste;
	}

}
