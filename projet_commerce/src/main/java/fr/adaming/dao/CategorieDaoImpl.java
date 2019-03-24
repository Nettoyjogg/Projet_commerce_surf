package fr.adaming.dao;

import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import fr.adaming.model.Categorie;

@Repository
public class CategorieDaoImpl implements ICategorieDao {

	@Autowired
	private SessionFactory sf;

	public void setSf(SessionFactory sf) {
		this.sf = sf;
	}

	@Override
	public List<Categorie> afficherCategorieDao() {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		// Requete
		Query req = s.createQuery("SELECT ca FROM Categorie as ca");

		// Répérer un objet query

		List<Categorie> ListeCategorie = req.list();
		for (Categorie ca : ListeCategorie) {
			ca.setImg("data:image/png;base64," + Base64.encodeBase64String(ca.getPhoto()));
		}
		return ListeCategorie;
	}

	@Override
	public Categorie ajouterCategorieDao(Categorie ca) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		s.save(ca);
		return ca;
	}

	@Override
	public int modifierCategorieDao(Categorie ca) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		Query req = s.createQuery(
				"UPDATE Categorie as ca SET ca.nomCategorie=:pNom, ca.photo=:pPhoto, ca.description=:pDescription WHERE ca.idCategorie=:pId");
		req.setParameter("pNom", ca.getNomCategorie());
		req.setParameter("pPhoto", ca.getPhoto());
		req.setParameter("pDescription", ca.getDescription());
		req.setParameter("pId", ca.getIdCategorie());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public int supprimerCategorieDao(Categorie ca) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		Query req = s.createQuery("DELETE FROM Categorie as ca WHERE ca.idCategorie=:pId");
		req.setParameter("pId", ca.getIdCategorie());
		int verif = req.executeUpdate();
		return verif;
	}

	@Override
	public Categorie consulterCategorieParIDDao(Categorie ca) {
		// Récupérer le bus
		Session s = sf.getCurrentSession();
		return (Categorie) s.get(Categorie.class, ca.getIdCategorie());
	}

	@Override
	public List<String> consulterCategorieNomCategorieParIDDao() {
		
		Session s = sf.getCurrentSession();
		// Requete
		Query req = s.createQuery("SELECT ca.nomCategorie FROM Categorie as ca");

		// Répérer un objet query

		List<String> ListeCategorie = req.list();
	
		return ListeCategorie;
	}

}
