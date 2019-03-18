package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import fr.adaming.model.Administrateur;
import fr.adaming.model.Adresse;
import fr.adaming.model.Client;
import fr.adaming.model.Commande;
import fr.adaming.model.LigneCommande;
import fr.adaming.model.Panier;
import fr.adaming.model.Produit;
import fr.adaming.service.IClientService;
import fr.adaming.service.ICommandeService;
import fr.adaming.service.ILigneCommandeService;
import fr.adaming.service.IProduitService;
import fr.adaming.service.SendMailService;

@ManagedBean(name = "lcMB")
@RequestScoped
public class ListeCommandeManagedBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private ILigneCommandeService lcService;
	@EJB
	private ICommandeService coService;
	@EJB
	private IProduitService pService;
	@EJB
	private IClientService cService;

	// Attribut
	private LigneCommande ligneCommande;
	private HttpSession maSession;
	private List<LigneCommande> listeLigneCommande;
	private Commande commande;
	private Produit produit;
	private Administrateur admin;
	private Date date;
	private Client client;
	private Adresse adresse;
	private Panier panier;

	// Constructeur vide
	public ListeCommandeManagedBean() {
		this.ligneCommande = new LigneCommande();
		this.commande = new Commande();
		this.produit = new Produit();
		this.admin = new Administrateur();
		this.maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		this.listeLigneCommande = new ArrayList<LigneCommande>();
		this.date = new Date();
		this.client = new Client();
		this.adresse = new Adresse();
		this.panier = new Panier();
	}

	// Getters and Setters

	public HttpSession getMaSession() {
		return maSession;
	}

	public Panier getPanier() {
		return panier;
	}

	public void setPanier(Panier panier) {
		this.panier = panier;
	}

	public Adresse getAdresse() {
		return adresse;
	}

	public void setAdresse(Adresse adresse) {
		this.adresse = adresse;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Administrateur getAdmin() {
		return admin;
	}

	public void setAdmin(Administrateur admin) {
		this.admin = admin;
	}

	public LigneCommande getLigneCommande() {
		return ligneCommande;
	}

	public void setLigneCommande(LigneCommande ligneCommande) {
		this.ligneCommande = ligneCommande;
	}

	public Commande getCommande() {
		return commande;
	}

	public void setCommande(Commande commande) {
		this.commande = commande;
	}

	public Produit getProduit() {
		return produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public void setMaSession(HttpSession maSession) {
		this.maSession = maSession;
	}

	public List<LigneCommande> getListeLigneCommande() {
		return listeLigneCommande;
	}

	public void setListeLigneCommande(List<LigneCommande> listeLigneCommande) {
		this.listeLigneCommande = listeLigneCommande;
	}

	@PostConstruct // Cette annotation sert à dire que la méthode doit être
	// exécutée après l'instanciation de l'objet.
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// Méthodes :

	public String ajouterLigneCommandeMB() {
		int test = 0;
		try {
			produit = pService.consulterProduitService(produit);
			test = produit.getQuantite() - ligneCommande.getQuantite();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Ce produit n'est pas en stock ou n'existe pas"));
			return "testajoutlc";
		}
		if (maSession.getAttribute("panierSession") != null) {
			this.panier = (Panier) maSession.getAttribute("panierSession");
			this.panier.getListeLigneCommande();
		} else {
			this.panier = new Panier();
			List<LigneCommande> listtest = new ArrayList<LigneCommande>();
			this.panier.setListeLigneCommande(listtest);
		}
		if (test >= 0) {
			// On ajoute a lc en mettant le prix
			ligneCommande.setPrix(produit.getPrix() * ligneCommande.getQuantite());
			lcService.AjouterLigneCommandeService(ligneCommande);
			lcService.LierLigneCommandeProduitService(ligneCommande, produit);
			this.listeLigneCommande.add(ligneCommande);
			panier.getListeLigneCommande().addAll(listeLigneCommande);
			maSession.setAttribute("panierSession", panier);
			return "accueilproduit";

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas assez de produit en stock"));
			return "testajoutlc";
		}
	}

	public String lierCommandeLigneCommandeMB() {
		int c = 0;
		this.panier = (Panier) maSession.getAttribute("panierSession");

		try {
			c = panier.getListeLigneCommande().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (c > 0) {

			commande.setDateCommande(date);
			Commande com = coService.ajouterCommandeService(commande);
			com = coService.consulterCommandeParIDService(com);
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				lcService.LierLigneCommandeCommandeService(panier.getListeLigneCommande().get(i), com);
			}
			return "accueilproduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de produit choisis"));
			return "testcommande";
		}
	}

	public String lierClientCommandeMB() {

		int verif;
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			commande.setIdCommande(panier.getListeLigneCommande().get(0).getCommande().getIdCommande());
			coService.consulterCommandeParIDService(commande);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ((Client) maSession.getAttribute("clientSession") != null) {
			this.client = (Client) maSession.getAttribute("clientSession");
			verif = coService.ajouterClientCommandeService(commande, client);
		} else {
			cService.ajouterClientService(client, adresse);
			verif = coService.ajouterClientCommandeService(commande, client);
		}

		if (verif != 0) {
			return "accueilproduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de client choisi"));
			return "testclient";
		}
	}

	public String validerPanierMB() {
		int test;
		int w = 0;
		int verifQuantite = 0;
		String message = null;
		Commande coOut = null;
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			coOut = coService.consulterCommandeParIDService(panier.getListeLigneCommande().get(0).getCommande());
			message = "Bonjour Mme/Mr " + coOut.getClient().getNomClient()
					+ "\n Nous vous informons que votre commande: " + coOut.getIdCommande() + " passée le "
					+ coOut.getDateCommande() + " a bien été validée.\n Nous esperons que vos articles: "
					+ panier.getListeLigneCommande() + " vous plairont. \n A bientot !";
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			w = panier.getListeLigneCommande().size();
		} catch (Exception e) {
			w = 0;
			verifQuantite = 2;
			e.printStackTrace();
		}
		for (int i = 0; i < w; i++) {
			produit = pService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
			test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
			if (test < 0) {
				verifQuantite = 0;
			} else {
				verifQuantite = 1;
			}

		}

		if (verifQuantite == 1) {
			try {
				SendMailService sm = new SendMailService();
				sm.sendMail(coOut.getClient().getEmail(), message);
				// Ici on, modifie la quantité du produit dans la BD
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					produit = pService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
					produit.setQuantite(produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite());
					admin.setIdAdmin(1);// C'est moche, mais c'est pour le
										// test
					pService.modifierProduitService(produit, admin);
				}
				this.panier = new Panier();
				maSession.setAttribute("panierSession", panier);
				return "accueilclient";
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("message non envoyé, annulation validation commande"));
				return "validerpanier";
			}
		}
		if (verifQuantite == 0) {
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				produit = pService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
				test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Le produit " + produit.getDesignation() + " ayant l'id "
								+ produit.getIdProduit() + "n'est plus disponible en stock"));
				lcService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i));
				coService.supprimerCommandeService(coOut);
			}
			this.panier = new Panier();
			maSession.setAttribute("panierSession", panier);
			return "accueilproduit";
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Vous n'avez pas commandé de produit"));
			return "accueilproduit";
		}

	}

}
