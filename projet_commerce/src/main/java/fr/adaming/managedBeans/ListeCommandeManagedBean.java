package fr.adaming.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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

	@ManagedProperty(value = "#{lcService}")
	private ILigneCommandeService ligneCommandeService;
	@ManagedProperty(value = "#{coService}")
	private ICommandeService commandeService;
	@ManagedProperty(value = "#{pService}")
	private IProduitService produitService;
	@ManagedProperty(value = "#{cService}")
	private IClientService clientService;

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

	public void setLigneCommandeService(ILigneCommandeService ligneCommandeService) {
		this.ligneCommandeService = ligneCommandeService;
	}

	public void setCommandeService(ICommandeService commandeService) {
		this.commandeService = commandeService;
	}

	public void setProduitService(IProduitService produitService) {
		this.produitService = produitService;
	}

	public void setClientService(IClientService clientService) {
		this.clientService = clientService;
	}

	@PostConstruct // Cette annotation sert � dire que la m�thode doit �tre
	// ex�cut�e apr�s l'instanciation de l'objet.
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// M�thodes :

	public String ajouterLigneCommandeMB() {
		int test = 0;
		// Ici on check si on a d�j� un panier dans la session
		if (maSession.getAttribute("panierSession") != null) {
			// Si on en a un, on le r�cup�re, lui et sa liste de ligneCommande,
			this.panier = (Panier) maSession.getAttribute("panierSession");
			this.panier.getListeLigneCommande();
		} else {
			// Ici, on a pas de panier, on en cr�e donc un nouveau, auquel on
			// ajoute une liste de ligne commande vide, pour �viter d'avoir des
			// erreurs de type "NullPointerException" (quand on va chercher la
			// liste, si on cr�e que le panier, il n'en trouvera pas)
			this.panier = new Panier();
			List<LigneCommande> listtest = new ArrayList<LigneCommande>();
			this.panier.setListeLigneCommande(listtest);
		}
		try {
			// Ici, on check que le produit existe bien en allant le consulter
			// dans la Dao, et on check sa quantit� pour v�rifier qu'il y ait
			// assez. L'attribut test, permettra de faire un test apr�s le
			// catch
			produit = produitService.consulterProduitService(produit);
			test = produit.getQuantite() - ligneCommande.getQuantite();
			try {
				// Ici, si il existe, on v�rifie le nombre de produit qu'on a
				// d�j� en stock dans le panier pour v�rifier qu'on puisse bien
				// en prendre suffisamment apr�s
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					if (produit.getDesignation()
							.equals(panier.getListeLigneCommande().get(i).getProduit().getDesignation())) {
						test = test - panier.getListeLigneCommande().get(i).getQuantite();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			// Si produit insuffisant, ou inexistant, catch, et message.
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Ce produit n'existe pas"));
			return "accueilclient";
		}
		// quantit�? sup�rieur, on continue, inf�rieure, voir plus bas
		if (test >= 0) {
			int size = 0;
			int testfait = 0;

			try {
				// Ici on r�cup�re la taille de la liste du panier
				size = panier.getListeLigneCommande().size();
			} catch (Exception e) {
				// Si la liste est vide, on set la taille � 0
				e.printStackTrace();
				size = 0;
			}
			// Ici, on va parcourir la liste du panier, si elle est vide, on
			// saute cette �tape.
			for (int i = 0; i < size; i++) {
				// Si la liste n'est pas vide, on v�rifie dans le if, que le
				// produit qu'on
				// veut ajouter n'est pas d�j� existant dans la liste du panier
				if (produit.getDesignation()
						.equals(panier.getListeLigneCommande().get(i).getProduit().getDesignation())) {
					// Si un produit est existant, on le r�cup�re, et on rajoute
					// la quantit� du produit qu'on veut ajouter, et son prix.
					// De plus on set l'attribut testfait � 1, qui servira �
					// v�rifier que le produit �t� existant ou non
					this.panier.getListeLigneCommande().get(i).setPrix(panier.getListeLigneCommande().get(i).getPrix()
							+ (produit.getPrix() * ligneCommande.getQuantite()));
					this.panier.getListeLigneCommande().get(i).setQuantite(
							panier.getListeLigneCommande().get(i).getQuantite() + ligneCommande.getQuantite());
					testfait = 1;
				}
			}
			// Ici, on test l'existance ou non du produit qu'on veut ajouter
			// dans le panier. Si il existe, il est forc�ment pass� dans la
			// boucle for pr�c�dente et donc testfait=1, si non, test fait =0
			if (testfait == 0) {
				// Et donc on ajoute une nouvelle ligne de commande au panier
				// dans laquelle on met le prix et la quantit�e
				ligneCommande.setPrix(produit.getPrix() * ligneCommande.getQuantite());
				this.ligneCommande.setProduit(produit);
				this.listeLigneCommande.add(ligneCommande);
				panier.getListeLigneCommande().addAll(listeLigneCommande);
			}
			// Enfin on met le panier dans la session et on retourne � l'accueil
			// panier
			maSession.setAttribute("panierSession", panier);
			return "panier";

		} else
		// Quantit� limit�e !
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas assez de produit en stock"));
			return "testajoutlc";
		}
	}

	// Cr�er une commande pour nos produits
	public String lierCommandeLigneCommandeMB() {
		int size = 0;
		// Bon d�j�, on r�cup�re le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			// Si le panier � bien une liste de commande, on r�cup�re sa taille
			// sinon, exception
			size = panier.getListeLigneCommande().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// On v�rifie que la liste soit existante
		if (size > 0) {
			// Si oui, on set la date du jour � notre commande
			commande.setDateCommande(date);
			// Ici, on ajoute la commande � toute les lignes de commande du
			// panier
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				this.panier.getListeLigneCommande().get(i).setCommande(commande);
			}
			// Enfin, on met le panier modifi� dans la session
			maSession.setAttribute("panierSession", panier);
			return "accueilproduit";
			// Si pas de liste dans le panier, message d'erreur
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de produit choisis"));
			return "testclient";
		}
	}

	// Ici on ajoute le client � la commande
	public String lierClientCommandeMB() {
		int verif = 0;
		// Comme d'hab, on r�cup�re le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			// On check qu'une commande � bien �t� cr�e pour y ajouter un client
			// (d'ailleurs le setId sert surement � rien, sinon, la commande
			// aura forc�ment une ligne commande � la ligne 0 de la liste)
			// Si pas de commande, go catch
			commande.setIdCommande(panier.getListeLigneCommande().get(0).getCommande().getIdCommande());
			commandeService.consulterCommandeParIDService(commande);
			// Du coup, si la commande existe, On check si un client est
			// connect� dans la session
			if ((Client) maSession.getAttribute("clientSession") != null) {
				// Si il est connect�, on le r�cup�re, et on va chercher ses
				// attributs dans la bd avec consulterClient Service
				this.client = (Client) maSession.getAttribute("clientSession");
				clientService.consulterClientParIdService(client);
				// Ici, pour toutes les lignes commande du panier, on ajoute le
				// client � la commande(qui est la m�me commande pour toutes les
				// lignes)
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					this.panier.getListeLigneCommande().get(i).getCommande().setClient(client);
				}
				maSession.setAttribute("panierSession", panier);
				verif = 1;
				// Si le client n'est pas co, on ajoute celui qu'on a rentr�
				// dans le formulaire dans commande, mais aussi dans la bd,
				// (m�me si il ne valide pas la commande finale, ca reste un
				// potentiel client :) )
			} else {
				// Ici, pour toutes les lignes commande du panier, on ajoute le
				// client � la commande(qui est la m�me commande pour toute les
				// lignes)
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					this.panier.getListeLigneCommande().get(i).getCommande().setClient(client);
				}
				// On ajoute le client et le panier dans la BD
				clientService.ajouterClientService(client, adresse);
				maSession.setAttribute("panierSession", panier);
				verif = 1;
			}
			// Pas de commande, on renvoi un message d'erreur
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de commandes en cour"));
			return "accueilproduit";
		}
		// Ici, on v�rifie qu'on a bien ajout� un client, mais le programme fait
		// qu'il est impossible de ne pas en ajouter un pour l'instant (19/03),
		// �a peut en ajouter un vide, need validator, requested
		if (verif != 0) {
			return "validerpanier";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de client choisi"));
			return "testclient";
		}
	}

	// La m�thode finale, pour supposer
	// que le client a pay�, valider la commande, envoyer le mail et la facture.
	public String validerPanierMB() {
		int test;
		int size = 0;
		int verifQuantite = 1;
		String message = null;
		Commande coOut = null;
		// On r�cup�re le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		// On r�cup�re la longueur de la liste du panier
		try {
			size = panier.getListeLigneCommande().size();
			// SI pas de liste, on set la longueur � 0 et le test verif � 2 pour
			// dire qu'on a pas command� de produit
		} catch (Exception e) {
			size = 0;
			verifQuantite = 2;
			e.printStackTrace();
		}
		// Ici on v�rifie encore une fois que la quantit� de produits dans notre
		// panier est dispo. (si quelqu'un a command� entre temps par exemple,
		// il peut ne plus y en avoir). Il suffit d'un seul produit qui ne soit
		// pas en quantit� suffisante pour annuler la commande
		for (int i = 0; i < size; i++) {
			produit = produitService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
			test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
			// SI un des produits n'est pas en quantit� suffisante, on set
			// l'attribut � 0
			if (test < 0) {
				verifQuantite = 0;
			}
		}
		// On a bien command�
		if (verifQuantite == 1) {
			try {
				int coFait = 0;
				// Un peu compliqu�, ici, on r�cup�re toute les lignes de
				// commande du panier dans l'attribut "listeligneCommande", car
				// le panier est "Transient", on ne pourra donc pas faire
				// panier.get... pour ajouter un �l�ment dans la bd. On stocke
				// donc le panier dans "listelignecommadne"
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					produit = produitService
							.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
					listeLigneCommande.addAll(panier.getListeLigneCommande());
					// De la m�me fa�on, on ajoute une commande dans la bd,
					// comme on est dans le "for", pour l'ajouter qu'une seule
					// fois, attribut coFait qui s'incr�mente. On aurait tr�s
					// bien pu, sortir les trois lignes sous le if pour les
					// mettres hors du "for" et mettre get(0) � la place de
					// get(i)
					if (coFait == 0) {
						coOut = panier.getListeLigneCommande().get(i).getCommande();
						commandeService.ajouterCommandeService(coOut);
						coFait++;
					}
					// Ici, on ajoute les lignes commandes dans la bd
					LigneCommande ligneCommandeIn = listeLigneCommande.get(i);
					ligneCommandeService.AjouterLigneCommandeService(ligneCommandeIn);
				}
				// Ici, on r�cup�re la commande pour s'en servir dans le message
				// de notre mail. Ici on a mis get(0)
				coOut = commandeService
						.consulterCommandeParIDService(panier.getListeLigneCommande().get(0).getCommande());
				// LE message du mail
				message = "Bonjour Mme/Mr " + coOut.getClient().getNomClient()
						+ "\n Nous vous informons que votre commande: " + coOut.getIdCommande() + " pass�e le "
						+ coOut.getDateCommande() + " a bien �t� valid�e.\n Nous esperons que vos articles: "
						+ panier.getListeLigneCommande() + " vous plairont. \n A bientot !";
				// On "test" l'envoi du mail
				try {
					SendMailService sm = new SendMailService();
					sm.sendMail(coOut.getClient().getEmail(), message);
					// si le mail est bien envoy�, on retire les quantit�s aux
					// produits de la bd
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						produit.setQuantite(
								produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite());
						admin.setIdAdmin(1);// C'est moche, mais c'est pour le
											// test
						produitService.modifierProduitService(produit, admin);
					}
					// Enfin, on cr�e un nouveau panier qu'on met dans la
					// session !
					this.panier = new Panier();
					panier.setListeLigneCommande(new ArrayList<>());
					maSession.setAttribute("panierSession", panier);
					return "accueilclient";
					// Ici, le mail n'est pas envoy�
				} catch (Exception e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("message non envoy�, annulation validation commande"));
					// Du coup, on annule la commande, et on supprime la
					// commande et ligne commande ajout�e dans la bd
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						ligneCommandeService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i));
					}
					commandeService.supprimerCommandeService(coOut);
					// On set le panier (qui n'a pas chang� normalement
					maSession.setAttribute("panierSession", panier);
					return "panier";
				}
				// Ici c'est pour tout probl�mes survenu lors de la validation
				// de la commande
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"Un probl�me est apparu lors de la validation de votre commande, recommencez!"));
				// On v�rifie qu'on a bien une liste de commande dans le panier,
				// si oui, on supprime les lignes commandes et commandes qu'on a
				// �ventuellement mise dans la bd
				try {
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						ligneCommandeService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i));
					}
					commandeService.supprimerCommandeService(coOut);
					// si il n'y en a pas, osef
				} catch (Exception e2) {
					e.printStackTrace();
				}
				// Enfin on r�cup le panier
				maSession.setAttribute("panierSession", panier);
				return "panier";
			}

		}
		// Ici, le produit n'est pas en quantit� suffisante
		if (verifQuantite == 0) {
			// On envoie un message concernant le produit manquant.
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				produit = produitService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
				test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Le produit " + produit.getDesignation() + " ayant l'id "
								+ produit.getIdProduit() + " n'est plus disponible en stock"));
				// Et on le supprimer du panier
				panier.getListeLigneCommande().remove(panier.getListeLigneCommande().get(i));

			}
			// On met le panier modifi� dans la session
			maSession.setAttribute("panierSession", panier);
			return "panier";
			// verif quantit�=2 ici, donc on a pas command�
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Vous n'avez pas command� de produits"));
			return "accueilproduit";
		}

	}

}