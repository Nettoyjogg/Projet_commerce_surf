package fr.adaming.dao;

import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import fr.adaming.model.Produit;

@Repository // pour dire que c'est un DAO
public class ProduitDaoImpl implements IProduitDao {

	@Autowired
	private SessionFactory sf;
	
	// le setter pour l'injection de dépendance
	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public List<Produit> afficherProduitDao() {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		
		String req="FROM Produit as p";
		
		Query queryHQL = s.createQuery(req);
		
		@SuppressWarnings("unchecked")
		List<Produit> listeProduit = queryHQL.list();

		for (Produit p : listeProduit) {

			p.setImg("data:image/png;base64," + Base64.encodeBase64String(p.getPhoto()));

		}

		return listeProduit;
	}
	
	
	
		

	

	@Override
	public Produit ajouterProduitDao(Produit p) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		
		s.save(p);
		
		return p;
	}


	
	@Override
	public int modifierProduitDao(Produit p) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		// requete HQL
		String req = "UPDATE Produit as p SET p.designation=:pDesignation, p.description=:pDescription, p.prix=:pPrix, p.quantite=:pQuantite, p.selectionne=:pSelectionne, p.photo=:pPhoto, p.categorie.idCategorie=:pIdCategorie WHERE p.idProduit=:pId";
		// récupérer un objet query
		Query query = s.createQuery(req);

		query.setParameter("pDesignation", p.getDesignation());
		query.setParameter("pDescription", p.getDescription());
		query.setParameter("pPrix", p.getPrix());
		query.setParameter("pQuantite", p.getQuantite());
		query.setParameter("pSelectionne", p.isSelectionne());
		query.setParameter("pPhoto", p.getPhoto());
		query.setParameter("pIdCategorie", p.getCategorie().getIdCategorie());
		query.setParameter("pId", p.getIdProduit());
		
		return query.executeUpdate();
		}
	

	@Override
	public int supprimerProduitDao(Produit p) {
		//récupérer le bus (session de hibernate
				Session s=sf.getCurrentSession();
		// requete hql
		String req = "DELETE FROM Produit as p WHERE p.idProduit=:pId";
		// récupérer un objet query
		Query query = s.createQuery(req);

		query.setParameter("pId", p.getIdProduit());
		
		return query.executeUpdate();
	}

	@Override
	public Produit consulterProduitDao(Produit p) {
		Session s=sf.getCurrentSession();
		
		String req="FROM Produit as p WHERE p.idProduit=:pId";
		
		
		Query queryHQL = s.createQuery(req);
		
		//passage des params
		queryHQL.setParameter("pId", p.getIdProduit());
	
		return  (Produit) queryHQL.uniqueResult();
	
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Produit> consulterProduitCategorieDao(Produit p) {
		//récupérer le bus (session de hibernate
		Session s=sf.getCurrentSession();
		String reqListe = "SELECT p FROM Produit as p WHERE p.categorie.idCategorie=:pIdCA";
		Query queryListe = s.createQuery(reqListe);
		queryListe.setParameter("pIdCA", p.getCategorie().getIdCategorie());
		
		return (List<Produit>) queryListe.list();
	}

}
