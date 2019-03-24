package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.apache.commons.codec.binary.Base64;
import org.primefaces.model.UploadedFile;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Categorie;
import fr.adaming.service.ICategorieService;

@ManagedBean(name = "caMB")
@RequestScoped
public class CategorieManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManagedProperty(value = "#{caService}")
	private ICategorieService categorieService;

	// Attribut
	private Administrateur administrateur;
	private Categorie categorie;
	private HttpSession maSession;
	private boolean indice;
	private UploadedFile image;
	private List<Categorie> listeCa;
	private List<Categorie> filteredListeCa;

	// Constructeur vide
	public CategorieManagedBean() {
		this.categorie = new Categorie();
		// R�cup�rer le administrateur de la session
		this.indice = false;
	}

	@PostConstruct // Cette annotation sert � dire que la m�thode doit �tre
					// ex�cut�e apr�s l'instanciation de l'objet.
	public void init() {
		this.listeCa = categorieService.afficherCategorieService();
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.administrateur = (Administrateur) maSession.getAttribute("adminSession");
	}

	// Getters and Setters

	public List<String> getnomCategorie() {
		return categorieService.getNomCategorie();
	}

	public List<Categorie> getFilteredListeCa() {
		return filteredListeCa;
	}

	public void setFilteredListeCa(List<Categorie> filteredListeCa) {
		this.filteredListeCa = filteredListeCa;
	}

	public Categorie getCategorie() {
		return categorie;
	}

	public void setCategorieService(ICategorieService categorieService) {
		this.categorieService = categorieService;
	}

	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}

	public boolean isIndice() {
		return indice;
	}

	public void setIndice(boolean indice) {
		this.indice = indice;
	}

	public UploadedFile getImage() {
		return image;
	}

	public void setImage(UploadedFile image) {
		this.image = image;
	}

	public List<Categorie> getListeCa() {
		return listeCa;
	}

	public void setListeCa(List<Categorie> listeCa) {
		this.listeCa = listeCa;
	}

	// M�thodes m�tiers
	public String ajouterCategorieMB() {
		if (this.image != null) {
			this.categorie.setPhoto(this.image.getContents());
		}
		Categorie caAjout = categorieService.ajouterCategorieService(categorie, administrateur);
		if (caAjout.getIdCategorie() != 0) {
			// R�cuperer la liste
			this.listeCa = categorieService.afficherCategorieService(administrateur);

			return "accueil";
		} else {
			// Ajouter un message d'erreur
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ajout a �chou�"));
			return "ajoutcategorie";
		}

	}

	public String modifierCategorieMB() {
		if (this.image != null) {
			this.categorie.setPhoto(this.image.getContents());
		}
		int verif = categorieService.modifierCategorieService(categorie, administrateur);
		if (verif != 0) {
			// R�cuperer la liste
			this.listeCa = categorieService.afficherCategorieService(administrateur);

			return "accueil";
		} else {
			// Ajouter un message d'erreur
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Modif a �chou�"));
			return "modifiercategorie";
		}

	}

	public String supprimerCategorieMB() {
		int verif = categorieService.supprimerCategorieService(categorie, administrateur);
		if (verif != 0) {
			// R�cuperer la liste
			this.listeCa = categorieService.afficherCategorieService(administrateur);

			return "accueil";
		} else {
			// Ajouter un message d'erreur
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Suppression a �chou�"));
			return "supprimercategorie";
		}

	}

	public void rechercherCategorieParIdMB() {
		this.categorie = categorieService.consulterCategorieParIDService(categorie, administrateur);
		this.categorie.setImg("data:image/png;base64," + Base64.encodeBase64String(categorie.getPhoto()));
		if (categorie == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La cat�gorie est null"));
			indice = false;
		} else {
			indice = true;
		}
	}

	public void rechercherCategorieClientParIdMB() {
		this.categorie = categorieService.consulterCategorieParIDService(categorie);
		this.categorie.setImg("data:image/png;base64," + Base64.encodeBase64String(categorie.getPhoto()));
		if (categorie == null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("La cat�gorie est null"));
			indice = false;
		} else {
			indice = true;
		}
	}

	public void modifierCategorieAutoMB() {
		this.categorie = categorieService.consulterCategorieParIDService(categorie, administrateur);
		this.categorie.setImg("data:image/png;base64," + Base64.encodeBase64String(categorie.getPhoto()));
	}

}
