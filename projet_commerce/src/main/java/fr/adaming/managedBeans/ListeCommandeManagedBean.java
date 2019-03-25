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
import fr.adaming.service.IPDFService;
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
	@ManagedProperty("#{pdfService}")
	private IPDFService pdfService;

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
	private static int numero;
	private List<LigneCommande> filteredListeLigneCommande;
	private final static int sort = 1;
	private static int total;
	private static int remise;
	private static int totalar;

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

	public int getTotalar() {
		return totalar;
	}

	public int getRemise() {
		return remise;
	}

	public int getTotal() {
		return total;
	}

	public int getSort() {
		return sort;
	}

	public List<LigneCommande> getFilteredListeLigneCommande() {
		return filteredListeLigneCommande;
	}

	public void setFilteredListeLigneCommande(List<LigneCommande> filteredListeLigneCommande) {
		this.filteredListeLigneCommande = filteredListeLigneCommande;
	}

	public static int getNumero() {
		return numero;
	}

	public void setPdfService(IPDFService pdfService) {
		this.pdfService = pdfService;
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

	@PostConstruct // Cette annotation sert à dire que la méthode doit être
	// exécutée après l'instanciation de l'objet.
	public void init() {
		maSession = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
	}

	// Méthodes :

	public String ajouterLigneCommandeMB() {
		int remise2 = 0;
		int total2 = 0;
		int total3 = 0;
		int test = 0;
		// Ici on check si on a déjà un panier dans la session
		if (maSession.getAttribute("panierSession") != null) {
			// Si on en a un, on le récupère, lui et sa liste de ligneCommande,
			this.panier = (Panier) maSession.getAttribute("panierSession");
			this.panier.getListeLigneCommande();
		} else {
			// Ici, on a pas de panier, on en crée donc un nouveau, auquel on
			// ajoute une liste de ligne commande vide, pour éviter d'avoir des
			// erreurs de type "NullPointerException" (quand on va chercher la
			// liste, si on crée que le panier, il n'en trouvera pas)
			this.panier = new Panier();
			List<LigneCommande> listtest = new ArrayList<LigneCommande>();
			this.panier.setListeLigneCommande(listtest);
		}
		try {
			// Ici, on check que le produit existe bien en allant le consulter
			// dans la Dao, et on check sa quantité pour vérifier qu'il y ait
			// assez. L'attribut test, permettra de faire un test après le
			// catch
			produit = produitService.consulterProduitService(produit);
			test = produit.getQuantite() - ligneCommande.getQuantite();
			try {
				// Ici, si il existe, on vérifie le nombre de produit qu'on a
				// déjà en stock dans le panier pour vérifier qu'on puisse bien
				// en prendre suffisamment après
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
		// quantité? supérieur, on continue, inférieure, voir plus bas
		if (test >= 0) {
			int size = 0;
			int testfait = 0;

			try {
				// Ici on récupère la taille de la liste du panier
				size = panier.getListeLigneCommande().size();
			} catch (Exception e) {
				// Si la liste est vide, on set la taille à 0
				e.printStackTrace();
				size = 0;
			}
			// Ici, on va parcourir la liste du panier, si elle est vide, on
			// saute cette étape.
			for (int i = 0; i < size; i++) {
				// Si la liste n'est pas vide, on vérifie dans le if, que le
				// produit qu'on
				// veut ajouter n'est pas déjà existant dans la liste du panier
				if (produit.getDesignation()
						.equals(panier.getListeLigneCommande().get(i).getProduit().getDesignation())) {
					// Si un produit est existant, on le récupère, et on rajoute
					// la quantité du produit qu'on veut ajouter, et son prix.
					// De plus on set l'attribut testfait à 1, qui servira à
					// vérifier que le produit été existant ou non
					this.panier.getListeLigneCommande().get(i).setPrix(panier.getListeLigneCommande().get(i).getPrix()
							+ (produit.getPrix() * ligneCommande.getQuantite()));
					this.panier.getListeLigneCommande().get(i).setQuantite(
							panier.getListeLigneCommande().get(i).getQuantite() + ligneCommande.getQuantite());
					testfait = 1;
				}
			}
			// Ici, on test l'existance ou non du produit qu'on veut ajouter
			// dans le panier. Si il existe, il est forcément passé dans la
			// boucle for précédente et donc testfait=1, si non, test fait =0
			if (testfait == 0) {
				// Et donc on ajoute une nouvelle ligne de commande au panier
				// dans laquelle on met le prix et la quantitée
				ligneCommande.setPrix(produit.getPrix() * ligneCommande.getQuantite());
				this.ligneCommande.setProduit(produit);
				this.listeLigneCommande.add(ligneCommande);
				panier.getListeLigneCommande().addAll(listeLigneCommande);
			}

			try {
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					total2 = total2 + (int) panier.getListeLigneCommande().get(i).getPrix();
				}
				if (total2 > 10000) {
					remise2 = 1000;
					panier.getListeLigneCommande().get(0)
							.setPrix(panier.getListeLigneCommande().get(0).getPrix() - remise2);
					total3 = total2;
					total2 = total2 - remise2;
				} else {
					remise2 = 0;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			// Enfin on met le panier dans la session et on retourne à l'accueil
			// panier
			remise = remise2;
			totalar = total3;
			total = total2;
			maSession.setAttribute("panierSession", panier);
			return "panier";

		} else
		// Quantité limitée !
		{
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas assez de produit en stock"));
			return "testajoutlc";
		}
	}

	// Créer une commande pour nos produits
	public String lierCommandeLigneCommandeMB() {
		int size = 0;
		// Bon déjà, on récupère le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			// Si le panier à bien une liste de commande, on récupère sa taille
			// sinon, exception
			size = panier.getListeLigneCommande().size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// On vérifie que la liste soit existante
		if (size > 0) {
			// Si oui, on set la date du jour à notre commande
			commande.setDateCommande(date);
			// Ici, on ajoute la commande à toute les lignes de commande du
			// panier
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				this.panier.getListeLigneCommande().get(i).setCommande(commande);
			}
			// Enfin, on met le panier modifié dans la session
			maSession.setAttribute("panierSession", panier);
			return "testclient";
			// Si pas de liste dans le panier, message d'erreur
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de produit choisis"));
			return "accueilproduit";
		}

	}

	// Ici on ajoute le client à la commande
	public String lierClientCommandeMB() {
		int verif = 0;
		// Comme d'hab, on récupère le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		try {
			// On check qu'une commande à bien été crée pour y ajouter un client
			// (d'ailleurs le setId sert surement à rien, sinon, la commande
			// aura forcément une ligne commande à la ligne 0 de la liste)
			// Si pas de commande, go catch
			commande.setIdCommande(panier.getListeLigneCommande().get(0).getCommande().getIdCommande());
			commandeService.consulterCommandeParIDService(commande);
			// Du coup, si la commande existe, On check si un client est
			// connecté dans la session
			if ((Client) maSession.getAttribute("clientSession") != null) {
				// Si il est connecté, on le récupère, et on va chercher ses
				// attributs dans la bd avec consulterClient Service
				this.client = (Client) maSession.getAttribute("clientSession");
				clientService.consulterClientParIdService(client);
				// Ici, pour toutes les lignes commande du panier, on ajoute le
				// client à la commande(qui est la même commande pour toutes les
				// lignes)
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					this.panier.getListeLigneCommande().get(i).getCommande().setClient(client);
				}
				maSession.setAttribute("panierSession", panier);
				verif = 1;
				// Si le client n'est pas co, on ajoute celui qu'on a rentré
				// dans le formulaire dans commande, mais aussi dans la bd,
				// (même si il ne valide pas la commande finale, ca reste un
				// potentiel client )
			} else {
				// Ici, pour toutes les lignes commande du panier, on ajoute le
				// client à la commande(qui est la même commande pour toute les
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
		// Ici, on vérifie qu'on a bien ajouté un client, mais le programme fait
		// qu'il est impossible de ne pas en ajouter un pour l'instant (19/03),
		// ça peut en ajouter un vide, need validator, requested
		if (verif != 0) {
			return "validerpanier";
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Pas de client choisi"));
			return "testclient";
		}
	}

	// La méthode finale, pour supposer
	// que le client a payé, valider la commande, envoyer le mail et la facture.
	public String validerPanierMB() {
		int test = 0;
		int size = 0;
		int verifQuantite = 1;
		String message = null;
		Commande coOut = null;
		// On récupère le panier
		this.panier = (Panier) maSession.getAttribute("panierSession");
		// On récupère la longueur de la liste du panier
		try {
			size = panier.getListeLigneCommande().size();
			// SI pas de liste, on set la longueur à 0 et le test verif à 2 pour
			// dire qu'on a pas commandé de produit
		} catch (Exception e) {
			size = 0;
			verifQuantite = 2;
			e.printStackTrace();
		}
		// Ici on vérifie encore une fois que la quantité de produits dans notre
		// panier est dispo. (si quelqu'un a commandé entre temps par exemple,
		// il peut ne plus y en avoir). Il suffit d'un seul produit qui ne soit
		// pas en quantité suffisante pour annuler la commande
		for (int i = 0; i < size; i++) {
			produit = produitService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
			test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
			// SI un des produits n'est pas en quantité suffisante, on set
			// l'attribut à 0
			if (test < 0) {
				verifQuantite = 0;
			}
		}
		// On a bien commandé
		if (verifQuantite == 1) {
			try {
				int coFait = 0;
				// Un peu compliqué, ici, on récupère toute les lignes de
				// commande du panier dans l'attribut "listeligneCommande", car
				// le panier est "Transient", on ne pourra donc pas faire
				// panier.get... pour ajouter un élément dans la bd. On stocke
				// donc le panier dans "listelignecommadne"
				for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
					produit = produitService
							.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
					listeLigneCommande.addAll(panier.getListeLigneCommande());
					// De la même façon, on ajoute une commande dans la bd,
					// comme on est dans le "for", pour l'ajouter qu'une seule
					// fois, attribut coFait qui s'incrèmente. On aurait très
					// bien pu, sortir les trois lignes sous le if pour les
					// mettres hors du "for" et mettre get(0) à la place de
					// get(i)
					if (coFait == 0) {
						coOut = panier.getListeLigneCommande().get(i).getCommande();
						if (panier.getListeLigneCommande().get(i).getCommande().getClient() != null) {
							commandeService.ajouterCommandeService(coOut);
							coFait++;
						} else {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage("Votre commande n'a pas de client, ajouter en un"));
							// On set le panier (qui n'a pas changé normalement
							maSession.setAttribute("panierSession", panier);
							return "testclient";
						}
					}
					// Ici, on ajoute les lignes commandes dans la bd
					LigneCommande ligneCommandeIn = listeLigneCommande.get(i);
					// On vérifie que la ligne commande a bien une commande et
					// un client
					if (ligneCommandeIn.getCommande() != null && ligneCommandeIn.getCommande().getClient() != null) {
						// Ici, on ajoute les lignes commandes dans la bd
						ligneCommandeService.AjouterLigneCommandeService(ligneCommandeIn);
						// Si non, on renvoi à la validation
					} else {
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage("Un de vos produits n'a pas été commandé, veuillez commander"));
						// Suppression de la ligne de la bd et commande sans
						// client
						for (int i2 = 0; i2 < panier.getListeLigneCommande().size(); i2++) {
							ligneCommandeService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i2));
							// on supprime la commande de la bd et On set le
							// panier (qui
							// n'a pas changé normalement
						}
						commandeService.supprimerCommandeService(coOut);
						maSession.setAttribute("panierSession", panier);
						return "testcommande";
					}

				}
				// Ici, on récupère la commande pour s'en servir dans le message
				// de notre mail. Ici on a mis get(0)
				coOut = commandeService
						.consulterCommandeParIDService(panier.getListeLigneCommande().get(0).getCommande());
				// LE message du mail
				message = "Bonjour Mme/Mr " + coOut.getClient().getNomClient()
						+ "\n Nous vous informons que votre commande: " + coOut.getIdCommande() + " passée le "
						+ coOut.getDateCommande()
						+ " a bien été validée.\n Nous esperons que vos articles vous plairont (cf, pdf). \n A bientot !";
				// Préparation du PDF
				List<Produit> listeProduit = new ArrayList<>();
				for (int z = 0; z < panier.getListeLigneCommande().size(); z++) {
					Produit prodAjout = panier.getListeLigneCommande().get(z).getProduit();
					prodAjout.setQuantite(panier.getListeLigneCommande().get(z).getQuantite());
					listeProduit.add(prodAjout);
				}
				// Recup du client
				Client client = panier.getListeLigneCommande().get(0).getCommande().getClient();
				// On "test" l'envoi du mail
				try {
					// création du pdf
					pdfService.creerPDF(listeProduit, client, numero);

					SendMailService sm = new SendMailService();
					sm.sendMail(coOut.getClient().getEmail(), message);
					// Création du produit

					// si le mail est bien envoyé, on retire les quantités aux
					// produits de la bd et on update ,umero
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						produit.setQuantite(
								produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite());
						admin.setIdAdmin(1);// C'est moche, mais c'est pour le
											// test
						produitService.modifierProduitService(produit, admin);
					}
					numero++;
					// Enfin, on crée un nouveau panier qu'on met dans la
					// session !
					this.panier = new Panier();
					panier.setListeLigneCommande(new ArrayList<>());
					maSession.setAttribute("panierSession", panier);
					return "accueilclient";
					// Ici, le mail n'est pas envoyé
				} catch (Exception e) {
					e.printStackTrace();
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage("message non envoyé, annulation validation commande"));
					// Du coup, on annule la commande, et on supprime la
					// commande et ligne commande ajoutée dans la bd
					// création du pdf
					pdfService.creerPDFEchec(listeProduit, client, numero);
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						ligneCommandeService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i));
					}
					commandeService.supprimerCommandeService(coOut);

					// On set le panier (qui n'a pas changé normalement
					numero++;
					maSession.setAttribute("panierSession", panier);
					return "panier";
				}
				// Ici c'est pour tout problèmes survenu lors de la validation
				// de la commande
			} catch (Exception e) {
				e.printStackTrace();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
						"Un problème est apparu lors de la validation de votre commande, recommencez!"));
				// On vérifie qu'on a bien une liste de commande dans le panier,
				// si oui, on supprime les lignes commandes et commandes qu'on a
				// éventuellement mise dans la bd
				try {
					for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
						ligneCommandeService.supprimerLigneCommandeService(panier.getListeLigneCommande().get(i));
					}
					commandeService.supprimerCommandeService(coOut);
					// si il n'y en a pas, osef
				} catch (Exception e2) {
					e.printStackTrace();
				}
				// Enfin on récup le panier
				maSession.setAttribute("panierSession", panier);
				return "panier";
			}

		}
		// Ici, le produit n'est pas en quantité suffisante
		if (verifQuantite == 0) {
			// On envoie un message concernant le produit manquant.
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				produit = produitService.consulterProduitService(panier.getListeLigneCommande().get(i).getProduit());
				test = produit.getQuantite() - panier.getListeLigneCommande().get(i).getQuantite();
				if (test < 0) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le produit "
							+ produit.getDesignation() + " n'est plus disponible en stock, revalidez la commande"));
					// Et on le supprimer du panier
					panier.getListeLigneCommande().remove(panier.getListeLigneCommande().get(i));
				}
			}
			// On met le panier modifié dans la session
			maSession.setAttribute("panierSession", panier);
			return "panier";
			// verif quantité=2 ici, donc on a pas commandé
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Vous n'avez pas commandé de produits"));
			return "accueilproduit";
		}

	}

	// Supprimer une ligne de commande du panier
	public String supprimerlignecommandepanierMB() {
		int remise2 = 0;
		int total2 = 0;
		int total3 = 0;
		// On vérifie si il y a bien un panier, si il n'y en as pas la méthode
		// est inutile
		if (maSession.getAttribute("panierSession") == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Le panier est déjà vide, il n'y a rien à supprimer"));
			return "panier";
		}
		// Si on en a un, on le récupère, lui et sa liste de ligneCommande,
		this.panier = (Panier) maSession.getAttribute("panierSession");
		this.panier.getListeLigneCommande();

		// Comme les produits sont groupés ensemble et que la ligne de commande
		// n'a pas encore d'id propre c'est par le type de produit
		// que l'identification de la ligne va être réalisée
		try {
			produit = produitService.consulterProduitService(produit);
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Le produit n'existe pas"));
			return "panier";
		}
		// on recherche quel ligne contient le produit en question
		try {
			for (int i = 0; i < panier.getListeLigneCommande().size(); i++) {
				if (produit.getDesignation()
						.equals(panier.getListeLigneCommande().get(i).getProduit().getDesignation())) {

					this.panier.getListeLigneCommande().remove(i);

					for (int i2 = 0; i2 < panier.getListeLigneCommande().size(); i2++) {
						total2 = total2 + (int) panier.getListeLigneCommande().get(i2).getPrix();
					}
					if (total2 > 10000) {
						remise2 = 1000;
						panier.getListeLigneCommande().get(0)
								.setPrix(panier.getListeLigneCommande().get(0).getPrix() - remise2);
						total3 = total2;
						total2 = total2 - remise2;
					} else {
						remise2 = 0;
					}

					// Enfin on met le panier dans la session et on retourne à
					// l'accueil
					// panier
					remise = remise2;
					totalar = total3;
					total = total2;

					return "panier";
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Une erreur que je n'ai pas eu le temps de check est apparue!"));
			return "panier";
		}
		return "panier";
	}

}
