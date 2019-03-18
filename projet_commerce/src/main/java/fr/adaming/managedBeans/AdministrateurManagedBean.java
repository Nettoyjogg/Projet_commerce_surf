package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IAdministrateurService;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "adminMB")
@RequestScoped
public class AdministrateurManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Transformation de l'association UML en Java
	@EJB
	private IAdministrateurService adminService;
	@EJB
	private ICategorieService caService;
	@EJB
	private IProduitService pService;

	// Déclaration des attributs
	private Administrateur administrateur;

	// COnstructeurs
	public AdministrateurManagedBean() {
		this.administrateur = new Administrateur();
	}

	// Getters and Setters

	public Administrateur getAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(Administrateur administrateur) {
		this.administrateur = administrateur;
	}

	// Déclaration des méthodes métiers
	public String seConnecter() {
		//sedéconnecter d'une session antérieure aucasou
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		// chercher le administrateur par son mail et mdp
		Administrateur adminOut = adminService.estExistant(administrateur);

		if (adminOut != null) {
			// Récuprer les différentes liste sur la session de ce
			// administrateur
			List<Categorie> liste = caService.afficherCategorieService(adminOut);
			List<Produit> listep = pService.afficherProduitService(adminOut);

			// Mettre la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categorieSession", liste);

			// Mettre la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitSession", listep);

			// Mettre le administrateur dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("adminSession", adminOut);
			return "accueil";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le mot de passe ou mail est mauvais"));
			return "accueilclient";
		}
	}

	public String seDeconnecter() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("L'admin s'est déconnecté"));
		return "accueilclient";
		
	}

}
