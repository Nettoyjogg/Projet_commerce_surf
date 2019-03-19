package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.model.Produit;
import fr.adaming.service.IAdministrateurService;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IFormateurService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "adminMB")
@RequestScoped
public class AdministrateurManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	
	//Transformation de l'association UML en JAVA 
	@ManagedProperty(value="#{adminService}") //utilise by name
	private IAdministrateurService administrateurService;

	
	
	@ManagedProperty(value="#{caService}")
	private ICategorieService categorieService;
	@ManagedProperty(value="#{pService}")
	private IProduitService produitService;

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

	
	
	public void setAdministrateurService(IAdministrateurService administrateurService) {
		this.administrateurService = administrateurService;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	
	
	
	// Déclaration des méthodes métiers
	public String seConnecter() {
		//sedéconnecter d'une session antérieure aucasou
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		// chercher le administrateur par son mail et mdp
		Administrateur adminOut = administrateurService.estExistant(administrateur);

		if (adminOut != null) {
			// Récuprer les différentes liste sur la session de ce
			// administrateur
			
			//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++a ajouter apres
		//	List<Categorie> liste = categorieService.afficherCategorieService(adminOut);
		//	List<Produit> listep = produitService.afficherProduitService(adminOut);

			// Mettre la liste dans la session
		//	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("categorieSession", liste);

			// Mettre la liste dans la session
		//	FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("produitSession", listep);

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
