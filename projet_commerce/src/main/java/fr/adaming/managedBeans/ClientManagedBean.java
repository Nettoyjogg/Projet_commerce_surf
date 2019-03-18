package fr.adaming.managedBeans;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import fr.adaming.model.Client;
import fr.adaming.model.Panier;
import fr.adaming.service.ICategorieService;
import fr.adaming.service.IClientService;
import fr.adaming.service.IProduitService;

@ManagedBean(name = "cMB")
@RequestScoped
public class ClientManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	// Transformation de l'association UML en Java
	@EJB
	private IClientService cService;
	@EJB
	private ICategorieService caService;
	@EJB
	private IProduitService pService;

	// Déclaration des attributs
	private Client client;
	private HttpSession maSession;

	// déclaration du constructeur
	public ClientManagedBean() {
		super();
		this.client = new Client();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// getter et setter

	public Client getClient() {
		return client;
	}

	public HttpSession getMaSession() {
		return maSession;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	// méthodes et métiers
	public String connecterClient() {
		// sedéconnecter d'une session antérieure aucasou

		// chercher le administrateur par son mail et mdp
		Client cOut = cService.estExistantService(client);

		if (cOut != null) {
			// Récuprer les différentes liste sur la session de ce
			// administrateur
			// ------------------------------------------------Il va falloir
			// rédéfinir les méthodes sans les admins
			// List<Categorie> liste =
			// caService.afficherCategorieService(adminOut);
			// List<Produit> listep = pService.afficherProduitService(adminOut);

			// Mettre la liste dans la session
			Panier panier = (Panier) maSession.getAttribute("panierSession");
			FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
			//
			// // Mettre la liste dans la session
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("panierSession", panier);

			// Mettre le administrateur dans la session

			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("clientSession", cOut);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Vous êtes connecté"));

			return "accueilclient";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le mot de passe ou mail est mauvais"));

			return "accueilclient";
		}
	}

	public String seDeconnecterClient() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le client s'est déconnecté"));
		return "accueilclient";

	}

}
